package ksy.medichat.pharmacy.crawling;

import ksy.medichat.pharmacy.vo.PharmacyVO;
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
public class PharmacyCrawling {
	
	@Value("${API.KSY.DATA-API-KEY}")
	private String KSY_KEY; /* Service Key */
	
	final static String TYPE = "xml"; /* xml(기본값), JSON */
	final static String NUMSOFROWS = "150"; /* 한 페이지 결과 수 */
	
	//전체 약국 데이터를 담을 리스트
	public static List<PharmacyVO> list = new ArrayList<>();
	public void main() {
		//약국 164page
		for(int i=1; i<=164; i++) {
			System.out.println("<<PageNum>> : " + i);
			try {
				getPharmacy(urlMaker(i));
				 // 3초의 딜레이 추가
	            try {
	                Thread.sleep(1500);
	            } catch (InterruptedException e) {
	                // 현재 스레드가 인터럽트되면, 인터럽트 상태를 복원하고 예외를 기록합니다.
	                Thread.currentThread().interrupt();
	            }
			} catch(Exception e) {
			}
		}
	}
	
	// pageNo를 동적으로 변경해서 url 생성
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
    
	// url의 모든 정보(NUMSOFROWS의 갯수 만큼의 item)를 list에 담아 반환
	public void getPharmacy(String urlString) {
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
                PharmacyVO pharmacy = new PharmacyVO();
                pharmacy.setPha_num(Long.parseLong(getElementValue(element, "NUM")));
                pharmacy.setPha_addr(getElementValue(element, "DUTYADDR"));
                pharmacy.setPha_etc(getElementValue(element, "DUTYETC"));
                pharmacy.setPha_mapImg(getElementValue(element, "DUTYMAPIMG"));
                pharmacy.setPha_name(getElementValue(element, "DUTYNAME"));
                pharmacy.setPha_tell1(getElementValue(element, "DUTYTEL1"));
                pharmacy.setPha_time1C(getElementValue(element, "DUTYTIME1C"));
                pharmacy.setPha_time2C(getElementValue(element, "DUTYTIME2C"));
                pharmacy.setPha_time3C(getElementValue(element, "DUTYTIME3C"));
                pharmacy.setPha_time4C(getElementValue(element, "DUTYTIME4C"));
                pharmacy.setPha_time5C(getElementValue(element, "DUTYTIME5C"));
                pharmacy.setPha_time6C(getElementValue(element, "DUTYTIME6C"));
                pharmacy.setPha_time7C(getElementValue(element, "DUTYTIME7C"));
                pharmacy.setPha_time8C(getElementValue(element, "DUTYTIME8C"));
                pharmacy.setPha_time1S(getElementValue(element, "DUTYTIME1S"));
                pharmacy.setPha_time2S(getElementValue(element, "DUTYTIME2S"));
                pharmacy.setPha_time3S(getElementValue(element, "DUTYTIME3S"));
                pharmacy.setPha_time4S(getElementValue(element, "DUTYTIME4S"));
                pharmacy.setPha_time5S(getElementValue(element, "DUTYTIME5S"));
                pharmacy.setPha_time6S(getElementValue(element, "DUTYTIME6S"));
                pharmacy.setPha_time7S(getElementValue(element, "DUTYTIME7S"));
                pharmacy.setPha_time8S(getElementValue(element, "DUTYTIME8S"));
                pharmacy.setPha_hpId(getElementValue(element, "HPID"));
                pharmacy.setPha_postCdn1(getElementValue(element, "POSTCDN1"));
                pharmacy.setPha_postCdn2(getElementValue(element, "POSTCDN2"));
                pharmacy.setPha_info(getElementValue(element, "DUTYINF"));
                pharmacy.setPha_lon(getElementValue(element, "LON"));
                pharmacy.setPha_lat(getElementValue(element, "LAT"));
                pharmacy.setPha_x(getElementValue(element, "X"));
                pharmacy.setPha_y(getElementValue(element, "Y"));
                pharmacy.setPha_weekendAt(getElementValue(element, "DUTYWEEKENDAT"));
                list.add(pharmacy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
