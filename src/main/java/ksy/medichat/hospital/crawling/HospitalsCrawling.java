package ksy.medichat.hospital.crawling;

import ksy.medichat.hospital.vo.HospitalVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class HospitalsCrawling {
	
	@Value("${API.KSY.DATA-API-KEY}")
	private String KSY_KEY; /* Service Key */
	
	final static String TYPE = "xml"; /* xml(기본값), JSON */
	final static String DUTY_DIV = "W"; /* 병원 타입 B:병원, C:의원 */
	final static String NUMSOFROWS = "100"; /* 한 페이지 결과 수 */
	
	//전체 병원 데이터를 담을 리스트
	public static List<HospitalVO> list = new ArrayList<>();
	public void main() {
		// A:3, B:10, C:216, D:11, E:4, G:93, N:120, M:2 (150기준), W:1, O:보건소(안넣음)
		// A:종합병원, B:병원, C:의원, D:요양병원, E:한방병원, G:한의원, N:치과의원, M:치과병원, W:응급의원 ,O:보건소(안넣음)
		for(int i=1; i<=1; i++) {
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
			}
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
	public void getHospitals(String urlString) {
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
                HospitalVO hospital = new HospitalVO();
                hospital.setHos_num(Long.parseLong(getElementValue(element, "NUM")));
                hospital.setHos_addr(getElementValue(element, "DUTYADDR"));
                hospital.setHos_div(getElementValue(element, "DUTYDIV"));
                hospital.setHos_divName(getElementValue(element, "DUTYDIVNAME"));
                hospital.setHos_emcls(getElementValue(element, "DUTYEMCLS"));
                hospital.setHos_emclsName(getElementValue(element, "DUTYEMCLSNAME"));
                hospital.setHos_eryn(getElementValue(element, "DUTYERYN"));
                hospital.setHos_etc(getElementValue(element, "DUTYETC"));
                hospital.setHos_mapImg(getElementValue(element, "DUTYMAPIMG"));
                hospital.setHos_name(getElementValue(element, "DUTYNAME"));
                hospital.setHos_tell1(getElementValue(element, "DUTYTEL1"));
                hospital.setHos_tell3(getElementValue(element, "DUTYTEL3"));
                hospital.setHos_time1C(getElementValue(element, "DUTYTIME1C"));
                hospital.setHos_time2C(getElementValue(element, "DUTYTIME2C"));
                hospital.setHos_time3C(getElementValue(element, "DUTYTIME3C"));
                hospital.setHos_time4C(getElementValue(element, "DUTYTIME4C"));
                hospital.setHos_time5C(getElementValue(element, "DUTYTIME5C"));
                hospital.setHos_time6C(getElementValue(element, "DUTYTIME6C"));
                hospital.setHos_time7C(getElementValue(element, "DUTYTIME7C"));
                hospital.setHos_time8C(getElementValue(element, "DUTYTIME8C"));
                hospital.setHos_time1S(getElementValue(element, "DUTYTIME1S"));
                hospital.setHos_time2S(getElementValue(element, "DUTYTIME2S"));
                hospital.setHos_time3S(getElementValue(element, "DUTYTIME3S"));
                hospital.setHos_time4S(getElementValue(element, "DUTYTIME4S"));
                hospital.setHos_time5S(getElementValue(element, "DUTYTIME5S"));
                hospital.setHos_time6S(getElementValue(element, "DUTYTIME6S"));
                hospital.setHos_time7S(getElementValue(element, "DUTYTIME7S"));
                hospital.setHos_time8S(getElementValue(element, "DUTYTIME8S"));
                hospital.setHos_hpId(getElementValue(element, "HPID"));
                hospital.setHos_postCdn1(getElementValue(element, "POSTCDN1"));
                hospital.setHos_postCdn2(getElementValue(element, "POSTCDN2"));
                hospital.setHos_info(getElementValue(element, "DUTYINF"));
                hospital.setHos_lon(getElementValue(element, "LON"));
                hospital.setHos_lat(getElementValue(element, "LAT"));
                hospital.setHos_x(getElementValue(element, "X"));
                hospital.setHos_y(getElementValue(element, "Y"));
                hospital.setHos_weekendAt(getElementValue(element, "DUTYWEEKENDAT"));
                list.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
