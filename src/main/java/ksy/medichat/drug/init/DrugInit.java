package ksy.medichat.drug.init;

import ksy.medichat.drug.dto.DrugDTO;
import ksy.medichat.drug.service.DrugService;
import ksy.medichat.hospital.dto.HospitalDTO;
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
public class DrugInit implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        if (drugService.isEmpty()) {
            init();
        }
    }

    @Value("${API.WHR.DRUG-API-KEY}")
    private String WHR_KEY; /* Service Key */

    @Autowired
    private DrugService drugService;

    final static String TYPE = "xml"; /* xml(기본값), JSON */
    final static String NUMSOFROWS = "100"; /* 한 페이지 결과 수 */

    //전체 약을 담을 리스트
    public List<DrugDTO> list;

    public void init() {
        list = new ArrayList<>();
        for(int i=1; i<=49; i++) {
            System.out.println("<<PageNum>> : " + i);
            try {
                getDrugs(urlMaker(i));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        try{
            drugService.initDB(list.stream().map(DrugDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // pageNo를 동적으로 변경해서 url 생성
    public String urlMaker(int pageNo) {
        String url = "";
        try {
            url += "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
            url += "?serviceKey=" + WHR_KEY; /* Service Key */
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
    public List<DrugDTO> getDrugs(String urlString) {
        try {
            URL url = new URL(urlString);

            // URL에서 XML 데이터를 읽어오기
            InputStream inputStream = url.openStream();

            /* & -> &amp 오류 수정 시작 */
            // InputStream을 BufferedReader로 변환
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringWriter writer = new StringWriter();
            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line); // escape는 나중에 일괄 수행
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
                DrugDTO drug = new DrugDTO();
                try{
                    drug.setDrugNum(Long.parseLong(getElementValue(element, "itemSeq")));
                    if(getElementValue(element, "itemName").getBytes(StandardCharsets.UTF_8).length>255){
                        drug.setDrugName(getElementValue(element, "itemName").split(",")[0]);
                    }else {
                        drug.setDrugName(getElementValue(element, "itemName"));
                    }
                    drug.setDrugCompany(getElementValue(element, "entpName"));
                    drug.setDrugEffect(getElementValue(element, "efcyQesitm"));
                    drug.setDrugDosage(getElementValue(element, "useMethodQesitm"));
                    drug.setDrugWarning(getElementValue(element, "atpnWarnQesitm"));
                    drug.setDrugPrecaution(getElementValue(element, "atpnQesitm"));
                    drug.setDrugInteraction(getElementValue(element, "intrcQesitm"));
                    drug.setDrugSideEffect(getElementValue(element, "seQesitm"));
                    drug.setDrugStorage(getElementValue(element, "depositMethodQesitm"));
                    drug.setDrugImageLink(getElementValue(element, "itemImage"));

                    list.add(drug);
                } catch (Exception e){
                    System.err.println("에러 발생: " + e.getMessage() + ", index: " + i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
