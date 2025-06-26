package ksy.medichat.init;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HospitalInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (hospitalService.isEmpty()) { // DB가 비어 있을 때만 실행
            init();
        }
    }

    @Autowired
    private HospitalService hospitalService;

    @Value("${API.KSY.HOSPITAL.DATA-API-KEY}")
    private String apiKey; /* Service Key */
    // api url
    private String apiUrl = "https://safemap.go.kr/openApiService/data/getTotHospitalData.do?";
    private String apiNumOfRows = "150";
    private String urlInit(List<String> queryList) {
        for(String query : queryList) {
            apiUrl += "&" + query;
        }
        return apiUrl;
    }
    // api url 쿼리값
    private List<String> apiUrlQyeryList = new ArrayList<>();
    // api 정보 담을 리스트
    private List<HospitalDTO> apiDataList = new ArrayList<>();

    public void init() {
        /* 정적 쿼리에 필요한 값들 넣기 시작 */
        apiUrlQyeryList.add("type=xml");
        apiUrlQyeryList.add("serviceKey=" + apiKey);
        apiUrlQyeryList.add("numOfRows=" + apiNumOfRows);
        /* 정적 쿼리에 필요한 값들 넣기 끝 */
        urlInit(apiUrlQyeryList);

        // A:3, B:10, C:216, D:11, E:4, G:93, N:120, M:2 (150기준), W:1, O:보건소(안넣음)
        // A:종합병원, B:병원, C:의원, D:요양병원, E:한방병원, G:한의원, N:치과의원, M:치과병원, W:응급의원 ,O:보건소(안넣음)
        for(int i=0; i<9; i++){
            if(i==0) {
                initHospital("A");
            } else if(i==1) {
                initHospital("B");
            } else if(i==2) {
                initHospital("C");
            } else if(i==3) {
                initHospital("D");
            } else if(i==4) {
                initHospital("E");
            } else if(i==5) {
                initHospital("G");
            } else if(i==6) {
                initHospital("N");
            } else if(i==7) {
                initHospital("M");
            } else {
                initHospital("W");
            }
        }
    }

    public void initHospital(String dutyDiv) {
        int i = 0;
        while(i++>=0) {
            try {
                // 동적 쿼리 컨트롤 시작
                URL url = new URL(apiUrl + "&DutyDiv=" + dutyDiv + "&pageNo=" + i);
                System.out.println("<<url>> : " + url);
                // 동적 쿼리 컨트롤 끝

                InputStream inputStream = url.openStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringWriter writer = new StringWriter();
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                }

                String rawXml = writer.toString();
                String fixedXml = escapeXmlTextContentOnly(rawXml);
                InputStream fixedInputStream = new ByteArrayInputStream(fixedXml.getBytes(StandardCharsets.UTF_8));

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(fixedInputStream);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("item");

                if(nodeList.getLength()<=0){
                    break;
                }

                for (int j = 0; j < nodeList.getLength(); j++) {
                    Element element = (Element) nodeList.item(j);
                    HospitalDTO hospital = new HospitalDTO();
                    try {
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
                        // 필요한 다른 필드도 추가 가능
                        apiDataList.add(hospital);
                    } catch (Exception e) {
                        System.err.println("에러 발생: " + e.getMessage() + ", index: " + j);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            //hospitalService.initDB(apiDataList.stream().map(HospitalDTO::toEntity).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // xml 헬퍼 함수
    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
    // xml 헬퍼 함수
    private String escapeXmlTextContentOnly(String xml) {
        Pattern pattern = Pattern.compile(">([^<>]+)<|<DUTY[A-Z]+>(.*?)</DUTY[A-Z]+>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String original = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            String escaped = original
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
            if (matcher.group(1) != null) {
                matcher.appendReplacement(sb, ">" + Matcher.quoteReplacement(escaped) + "<");
            } else {
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

}
