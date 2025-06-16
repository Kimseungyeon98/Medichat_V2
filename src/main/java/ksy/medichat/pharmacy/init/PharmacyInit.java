package ksy.medichat.pharmacy.init;

import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.pharmacy.dto.PharmacyDTO;
import ksy.medichat.pharmacy.repository.PharmacyRepository;
import ksy.medichat.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PharmacyInit implements ApplicationRunner {

    @Autowired
    private PharmacyService pharmacyService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (pharmacyService.isEmpty()) { // DB가 비어 있을 때만 실행
            initPharmacy();
        }
    }

    //전체 약국 데이터를 담을 리스트
    public List<PharmacyDTO> list;

    @Value("${API.KSY.PHARMACY.DATA-API-KEY}")
    private String KSY_KEY; /* Service Key */

    final static String TYPE = "xml"; /* xml(기본값), JSON */
    final static String NUMSOFROWS = "150"; /* 한 페이지 결과 수 */



    public void initPharmacy() {
        list = new ArrayList<>();
        //약국 164page
        for(int i=1; i<=164; i++) {
            System.out.println("<<PageNum>> : " + i);
            try {
                getPharmacies(urlMaker(i));
            } catch(Exception e) {

            }
        }

        try{
            pharmacyService.initDB(list.stream().map(PharmacyDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String urlMaker(int pageNo) {
        String url = "";
        try {
            url += "https://safemap.go.kr/openApiService/data/getPharmacyData.do";
            url += "?serviceKey=" + URLEncoder.encode(KSY_KEY,"UTF-8"); /* Service Key */
            url += "&pageNo=" + URLEncoder.encode(String.valueOf(pageNo),"UTF-8"); /* 페이지번호 */
            url += "&numOfRows=" + URLEncoder.encode(NUMSOFROWS,"UTF-8"); /* 한 페이지 결과 수 */
            url += "&type=" + URLEncoder.encode(TYPE,"UTF-8"); /* xml(기본값), JSON */
        } catch(Exception e) {
        }
        System.out.println("<<URL>> : " + url);
        return url;
    }
    // 특정 태그의 값을 가져오는 헬퍼 메소드
    public String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
    private static String escapeXmlTextContentOnly(String xml) {
        Pattern pattern = Pattern.compile(">([^<>]+)<|<DUTY[A-Z]+>(.*?)</DUTY[A-Z]+>",Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String original = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            String escaped = original
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
            if(matcher.group(1) != null){
                matcher.appendReplacement(sb, ">" + Matcher.quoteReplacement(escaped) + "<");
            } else{
                // 태그 이름을 다시 복원해야 함
                String matched = matcher.group(0);
                Matcher tagNameMatcher = Pattern.compile("<(DUTY[A-Z]+)>").matcher(matched);
                if (tagNameMatcher.find()) {
                    String tagName = tagNameMatcher.group(1);
                    matcher.appendReplacement(sb, "<" + tagName + ">" + Matcher.quoteReplacement(escaped) + "</" + tagName + ">");
                }
            }

        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    // url의 모든 정보(NUMSOFROWS의 갯수 만큼의 item)를 list에 담아 반환
    public List<PharmacyDTO> getPharmacies(String urlString) {
        try {
            URL url = new URL(urlString);

            // URL에서 XML 데이터를 읽어오기
            InputStream inputStream = url.openStream();

            /* & -> &amp 오류 수정 시작 */
            // InputStream을 BufferedReader로 변환
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringWriter writer = new StringWriter();
            String line;

            // 한 줄씩 읽어서 "&"를 "&amp;"로 바꾸기
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }

            String rawXml = writer.toString();
            String fixedXml = escapeXmlTextContentOnly(rawXml);
            InputStream fixedInputStream = new ByteArrayInputStream(fixedXml.getBytes(StandardCharsets.UTF_8));

            /* &오류 수정 끝 */

            // DocumentBuilderFactory를 사용하여 DocumentBuilder 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            // InputStream을 사용하여 Document 객체 생성
            Document document = builder.parse(fixedInputStream);
            document.getDocumentElement().normalize();

            // 특정 태그 이름을 가진 모든 요소를 가져오기 (예: <item>)
            NodeList nodeList = document.getElementsByTagName("item");

            // 모든 <item> 요소를 순회하며 데이터 출력
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                PharmacyDTO pharmacy = new PharmacyDTO();
                pharmacy.setCode(Long.parseLong(getElementValue(element, "NUM")));
                pharmacy.setAddress(getElementValue(element, "DUTYADDR"));
                pharmacy.setEtc(getElementValue(element, "DUTYETC"));
                pharmacy.setMapImage(getElementValue(element, "DUTYMAPIMG"));
                pharmacy.setName(getElementValue(element, "DUTYNAME"));
                pharmacy.setMainPhone(getElementValue(element, "DUTYTEL1"));
                pharmacy.setTime1C(getElementValue(element, "DUTYTIME1C"));
                pharmacy.setTime2C(getElementValue(element, "DUTYTIME2C"));
                pharmacy.setTime3C(getElementValue(element, "DUTYTIME3C"));
                pharmacy.setTime4C(getElementValue(element, "DUTYTIME4C"));
                pharmacy.setTime5C(getElementValue(element, "DUTYTIME5C"));
                pharmacy.setTime6C(getElementValue(element, "DUTYTIME6C"));
                pharmacy.setTime7C(getElementValue(element, "DUTYTIME7C"));
                pharmacy.setTime8C(getElementValue(element, "DUTYTIME8C"));
                pharmacy.setTime1S(getElementValue(element, "DUTYTIME1S"));
                pharmacy.setTime2S(getElementValue(element, "DUTYTIME2S"));
                pharmacy.setTime3S(getElementValue(element, "DUTYTIME3S"));
                pharmacy.setTime4S(getElementValue(element, "DUTYTIME4S"));
                pharmacy.setTime5S(getElementValue(element, "DUTYTIME5S"));
                pharmacy.setTime6S(getElementValue(element, "DUTYTIME6S"));
                pharmacy.setTime7S(getElementValue(element, "DUTYTIME7S"));
                pharmacy.setTime8S(getElementValue(element, "DUTYTIME8S"));
                pharmacy.setHpId(getElementValue(element, "HPID"));
                pharmacy.setPostCode1(getElementValue(element, "POSTCDN1"));
                pharmacy.setPostCode2(getElementValue(element, "POSTCDN2"));
                pharmacy.setDescription(getElementValue(element, "DUTYINF"));
                if(getElementValue(element, "LON").equals("null") || getElementValue(element, "LAT").equals("null")){
                    continue;
                }
                pharmacy.setLat(Double.parseDouble(getElementValue(element, "LAT")));
                pharmacy.setLng(Double.parseDouble(getElementValue(element, "LON")));
                pharmacy.setWeekendAt(getElementValue(element, "DUTYWEEKENDAT"));
                list.add(pharmacy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
