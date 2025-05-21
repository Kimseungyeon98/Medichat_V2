package ksy.medichat.drug.crawling;

import ksy.medichat.drug.vo.DrugInfoVO;
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
public class DrugCrawling {
	static int pageNum = 0;

	// 첫번째
	@Value("${API.WHR.DRUG-API-KEY}")
	private String WHR_KEY; /*서비스키*/
	
	public String urlMaker(int pageNum) throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=" + WHR_KEY);
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode(String.valueOf(pageNum), "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "="
				+ URLEncoder.encode("xml", "UTF-8"));

		return (String) urlBuilder.toString();

	}

	// 전체 의약품 데이터를 담을 리스트
	// 세번째
	public List<DrugInfoVO> list = new ArrayList<>();

	public void main() {
		// B: 10, C: 216
		for (int i = 1; i <= 48; i++) {
			System.out.println("<<PageNum>> : " + i);
			try {
				getdrugs(urlMaker(i));
				// 3초의 딜레이 추가
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// 현재 스레드가 인터럽트되면, 인터럽트 상태를 복원하고 예외를 기록합니다.
					Thread.currentThread().interrupt();
				}
			} catch (Exception e) {
			}
		}
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
	// 두번째
	public void getdrugs(String urlString) {
		try {
			URL url = new URL(urlString);
			
			System.out.println(url);
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
				DrugInfoVO drug = new DrugInfoVO();
				drug.setDrg_name(getElementValue(element, "itemName"));
				drug.setDrg_code(getElementValue(element, "itemSeq"));
				drug.setDrg_company(getElementValue(element, "entpName"));
				drug.setDrg_effect(getElementValue(element, "efcyQesitm"));
				drug.setDrg_dosage(getElementValue(element, "useMethodQesitm"));
				drug.setDrg_warning(getElementValue(element, "atpnWarnQesitm"));
				drug.setDrg_precaution(getElementValue(element, "atpnQesitm"));
				drug.setDrg_interaction(getElementValue(element, "intrcQesitm"));
				drug.setDrg_seffect(getElementValue(element, "seQesitm"));
				drug.setDrg_storage(getElementValue(element, "depositMethodQesitm"));
				drug.setDrg_img(getElementValue(element, "itemImage"));

				list.add(drug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
