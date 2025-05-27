package ksy.medichat.hospital.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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
    @Value("${API.KSY.DATA-API-KEY}")
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
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // 현재 스레드가 인터럽트되면, 인터럽트 상태를 복원하고 예외를 기록합니다.
                    Thread.currentThread().interrupt();
                    System.out.println("url을 파싱하는 과정에서 에러가 발생했습니다.");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        try{
            hospitalService.initDB(list.stream().map(hospital -> HospitalDTO.toEntity(hospital)).collect(Collectors.toList()));
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

            // 한 줄씩 읽어서 "&"를 "&amp;"로 바꾸기
            while ((line = reader.readLine()) != null) {
                writer.write(line.replace("&", "&amp;"));
            }

            // 수정된 XML 데이터를 문자열로 가져오기
            String fixedXml = writer.toString();

            // 문자열을 InputStream으로 변환
            InputStream fixedInputStream = new ByteArrayInputStream(fixedXml.getBytes());

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
                hospital.setHosNum(Long.parseLong(getElementValue(element, "NUM")));
                hospital.setHosAddr(getElementValue(element, "DUTYADDR"));
                hospital.setHosDiv(getElementValue(element, "DUTYDIV"));
                hospital.setHosDivName(getElementValue(element, "DUTYDIVNAME"));
                hospital.setHosEmcls(getElementValue(element, "DUTYEMCLS"));
                hospital.setHosEmclsName(getElementValue(element, "DUTYEMCLSNAME"));
                hospital.setHosEryn(getElementValue(element, "DUTYERYN"));
                hospital.setHosEtc(getElementValue(element, "DUTYETC"));
                hospital.setHosMapImg(getElementValue(element, "DUTYMAPIMG"));
                hospital.setHosName(getElementValue(element, "DUTYNAME"));
                hospital.setHosTell1(getElementValue(element, "DUTYTEL1"));
                hospital.setHosTell3(getElementValue(element, "DUTYTEL3"));
                hospital.setHosTime1C(getElementValue(element, "DUTYTIME1C"));
                hospital.setHosTime2C(getElementValue(element, "DUTYTIME2C"));
                hospital.setHosTime3C(getElementValue(element, "DUTYTIME3C"));
                hospital.setHosTime4C(getElementValue(element, "DUTYTIME4C"));
                hospital.setHosTime5C(getElementValue(element, "DUTYTIME5C"));
                hospital.setHosTime6C(getElementValue(element, "DUTYTIME6C"));
                hospital.setHosTime7C(getElementValue(element, "DUTYTIME7C"));
                hospital.setHosTime8C(getElementValue(element, "DUTYTIME8C"));
                hospital.setHosTime1S(getElementValue(element, "DUTYTIME1S"));
                hospital.setHosTime2S(getElementValue(element, "DUTYTIME2S"));
                hospital.setHosTime3S(getElementValue(element, "DUTYTIME3S"));
                hospital.setHosTime4S(getElementValue(element, "DUTYTIME4S"));
                hospital.setHosTime5S(getElementValue(element, "DUTYTIME5S"));
                hospital.setHosTime6S(getElementValue(element, "DUTYTIME6S"));
                hospital.setHosTime7S(getElementValue(element, "DUTYTIME7S"));
                hospital.setHosTime8S(getElementValue(element, "DUTYTIME8S"));
                hospital.setHosHpId(getElementValue(element, "HPID"));
                hospital.setHosPostCdn1(getElementValue(element, "POSTCDN1"));
                hospital.setHosPostCdn2(getElementValue(element, "POSTCDN2"));
                hospital.setHosInfo(getElementValue(element, "DUTYINF"));
                hospital.setHosLon(getElementValue(element, "LON"));
                hospital.setHosLat(getElementValue(element, "LAT"));
                hospital.setHosX(getElementValue(element, "X"));
                hospital.setHosY(getElementValue(element, "Y"));
                hospital.setHosWeekendAt(getElementValue(element, "DUTYWEEKENDAT"));
                list.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
