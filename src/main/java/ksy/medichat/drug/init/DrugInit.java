package ksy.medichat.drug.init;

import ksy.medichat.disease.dto.DiseaseDTO;
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

    @Autowired
    private DrugService drugService;

    @Value("API.KSY.DRUG.DATA-API-KEY")
    private String apiKey; /* Service Key */
    // api url
    private String apiUrl = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?";
    private String apiNumOfRows = "100";
    private String urlInit(List<String> queryList) {
        for(String query : queryList) {
            apiUrl += "&" + query;
        }
        return apiUrl;
    }
    // api url 쿼리값
    private List<String> apiUrlQyeryList = new ArrayList<>();
    // api 정보 담을 리스트
    private List<DrugDTO> apiDataList = new ArrayList<>();

    public void init() {
        /* 정적 쿼리에 필요한 값들 넣기 시작 */
        apiUrlQyeryList.add("serviceKey=" + apiKey);
        apiUrlQyeryList.add("numOfRows=" + apiNumOfRows);
        /* 정적 쿼리에 필요한 값들 넣기 끝 */
        urlInit(apiUrlQyeryList);
        initDrug();

        try{
            drugService.initDB(apiDataList.stream().map(DrugDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    // url의 모든 정보(NUMSOFROWS의 갯수 만큼의 item)를 list에 담아 반환
    public void initDrug() {
        int num = 0;
        while(num++>=0) {
            try {
                // 동적 쿼리 컨트롤 시작
                URL url = new URL(apiUrl + "&pageNo=" + num);
                System.out.println("<<url>> : " + url);
                // 동적 쿼리 컨트롤 끝

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

                if(nodeList.getLength()<=0){
                    break;
                }

                // 모든 <item> 요소를 순회하며 데이터 출력
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    DrugDTO drug = new DrugDTO();
                    try {
                        drug.setCode(Long.parseLong(getElementValue(element, "itemSeq")));
                        if (getElementValue(element, "itemName").getBytes(StandardCharsets.UTF_8).length > 255) {
                            drug.setName(getElementValue(element, "itemName").split(",")[0]);
                        } else {
                            drug.setName(getElementValue(element, "itemName"));
                        }
                        drug.setCompany(getElementValue(element, "entpName"));
                        drug.setEffect(getElementValue(element, "efcyQesitm"));
                        drug.setDosage(getElementValue(element, "useMethodQesitm"));
                        drug.setWarning(getElementValue(element, "atpnWarnQesitm"));
                        drug.setPrecaution(getElementValue(element, "atpnQesitm"));
                        drug.setInteraction(getElementValue(element, "intrcQesitm"));
                        drug.setSideEffect(getElementValue(element, "seQesitm"));
                        drug.setStorage(getElementValue(element, "depositMethodQesitm"));
                        drug.setImageLink(getElementValue(element, "itemImage"));

                        apiDataList.add(drug);
                    } catch (Exception e) {
                        System.err.println("에러 발생: " + e.getMessage() + ", index: " + i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}
