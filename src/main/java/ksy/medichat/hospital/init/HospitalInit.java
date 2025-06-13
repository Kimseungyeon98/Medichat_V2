package ksy.medichat.hospital.init;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Component
public class HospitalInit implements ApplicationRunner {
    @Value("${API.KSY.HOSPITAL.DATA-API-KEY}")
    private String KSY_KEY; /* Service Key */

    final static String TYPE = "xml"; /* xml(기본값), JSON */
    final static String NUMSOFROWS = "100"; /* 한 페이지 결과 수 */
    static String DUTY_DIV; /* 병원 타입 B:병원, C:의원 */

    @Autowired
    private HospitalService hospitalService;

    //전체 병원 데이터를 담을 리스트
    public List<HospitalDTO> list;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (hospitalService.isEmpty()) { // DB가 비어 있을 때만 실행
            init();
        }
    }

    public void init() {
        // A:3, B:10, C:216, D:11, E:4, G:93, N:120, M:2 (150기준), W:1, O:보건소(안넣음)
        // A:종합병원, B:병원, C:의원, D:요양병원, E:한방병원, G:한의원, N:치과의원, M:치과병원, W:응급의원 ,O:보건소(안넣음)
        int opCount;
        for(int i=0; i<9; i++){
            // 병원 담을 리스트 초기화
            list = new ArrayList<>();
            
            if(i==0) {
                initHospital("A",3);
            } else if(i==1) {
                initHospital("B",10);
            } else if(i==2) {
                initHospital("C",216);
            } else if(i==3) {
                initHospital("D",11);
            } else if(i==4) {
                initHospital("E",4);
            } else if(i==5) {
                initHospital("G",93);
            } else if(i==6) {
                initHospital("N",120);
            } else if(i==7) {
                initHospital("M",2);
            } else {
                initHospital("W",1);
            }
        }
    }

    public void initHospital(String dutyDiv,int opCount) {
        DUTY_DIV = dutyDiv;
        for(int i=1; i<=opCount; i++) {
            System.out.println("<<PageNum>> : " + i);
            try {
                getHospitals(urlMaker(i));
                // API가 요구하는 Request Delay 만큼의 시간
                //Thread.sleep(500);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        try{
            hospitalService.initDB(list.stream().map(HospitalDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // pageNo를 동적으로 변경해서 url 생성
    public String urlMaker(int pageNo) {
        String url = "";
        try {
            url += "https://safemap.go.kr/openApiService/data/getTotHospitalData.do";
            url += "?serviceKey=" + URLEncoder.encode(KSY_KEY,"UTF-8"); /* Service Key */
            url += "&pageNo=" + URLEncoder.encode(String.valueOf(pageNo),"UTF-8"); /* 페이지번호 */
            url += "&numOfRows=" + URLEncoder.encode(NUMSOFROWS,"UTF-8"); /* 한 페이지 결과 수 */
            url += "&type=" + URLEncoder.encode(TYPE,"UTF-8"); /* xml(기본값), JSON */
            url += "&DutyDiv=" + URLEncoder.encode(DUTY_DIV,"UTF-8"); /* 병원 타입 B:병원, C:의원 */
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
    public List<HospitalDTO> getHospitals(String urlString) {
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
                HospitalDTO hospital = new HospitalDTO();
                try{
                    hospital.setCode(Long.parseLong(getElementValue(element, "NUM")));
                    hospital.setAddress(getElementValue(element, "DUTYADDR"));
                    hospital.setDivision(getElementValue(element, "DUTYDIV"));
                    hospital.setDivisionName(getElementValue(element, "DUTYDIVNAME"));
                    hospital.setEmergencyMedicalCode(getElementValue(element, "DUTYEMCLS"));
                    hospital.setEmergencyMedicalCodeName(getElementValue(element, "DUTYEMCLSNAME"));
                    hospital.setEmergencyRoomOperatingStatus(getElementValue(element, "DUTYERYN"));
                    hospital.setEtc(getElementValue(element, "DUTYETC"));
                    hospital.setMapImage(getElementValue(element, "DUTYMAPIMG"));
                    hospital.setName(getElementValue(element, "DUTYNAME"));
                    hospital.setMainPhone(getElementValue(element, "DUTYTEL1"));
                    hospital.setEmergencyRoomPhone(getElementValue(element, "DUTYTEL3"));
                    hospital.setTime1C(getElementValue(element, "DUTYTIME1C"));
                    hospital.setTime2C(getElementValue(element, "DUTYTIME2C"));
                    hospital.setTime3C(getElementValue(element, "DUTYTIME3C"));
                    hospital.setTime4C(getElementValue(element, "DUTYTIME4C"));
                    hospital.setTime5C(getElementValue(element, "DUTYTIME5C"));
                    hospital.setTime6C(getElementValue(element, "DUTYTIME6C"));
                    hospital.setTime7C(getElementValue(element, "DUTYTIME7C"));
                    hospital.setTime8C(getElementValue(element, "DUTYTIME8C"));
                    hospital.setTime1S(getElementValue(element, "DUTYTIME1S"));
                    hospital.setTime2S(getElementValue(element, "DUTYTIME2S"));
                    hospital.setTime3S(getElementValue(element, "DUTYTIME3S"));
                    hospital.setTime4S(getElementValue(element, "DUTYTIME4S"));
                    hospital.setTime5S(getElementValue(element, "DUTYTIME5S"));
                    hospital.setTime6S(getElementValue(element, "DUTYTIME6S"));
                    hospital.setTime7S(getElementValue(element, "DUTYTIME7S"));
                    hospital.setTime8S(getElementValue(element, "DUTYTIME8S"));
                    hospital.setHpId(getElementValue(element, "HPID"));
                    hospital.setPostCode1(getElementValue(element, "POSTCDN1"));
                    hospital.setPostCode2(getElementValue(element, "POSTCDN2"));
                    hospital.setDescription(getElementValue(element, "DUTYINF"));
                    if(getElementValue(element, "LON").equals("null") || getElementValue(element, "LAT").equals("null")){
                        continue;
                    }
                    hospital.setLng(Double.parseDouble((getElementValue(element, "LON"))));
                    hospital.setLat(Double.parseDouble((getElementValue(element, "LAT"))));
                    hospital.setWeekendAt(getElementValue(element, "DUTYWEEKENDAT"));
                    list.add(hospital);
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
