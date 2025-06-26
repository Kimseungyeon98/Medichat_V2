package ksy.medichat.init;

import com.google.common.util.concurrent.RateLimiter;
import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.service.DiseaseService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class DiseaseInit implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) {
        if (diseaseService.isEmpty()) {
            init();
        }
    }

    @Autowired
    private DiseaseService diseaseService;

    @Value("${API.KSY.DISEASE.DATA-API-KEY}")
    private String apiKey; /* Service Key */
    @Value("${API.KSY.DISEASE.X-Naver-Client-Id}")
    private String serviceIdKey; /* Service Key */
    @Value("${API.KSY.DISEASE.X-Naver-Client-Secret}")
    private String serviceSecretKey; /* Service Key */

    // api url
    private String apiUrl = "https://apis.data.go.kr/B551182/diseaseInfoService1/getDissNameCodeList1?";
    private String apiNumOfRows = "1000";
    private String urlInit(List<String> queryList) {
        for(String query : queryList) {
            apiUrl += "&" + query;
        }
        return apiUrl;
    }
    // api url 쿼리값
    private List<String> apiUrlQyeryList = new ArrayList<>();
    // api 정보 담을 리스트
    private List<DiseaseDTO> apiDataList = new ArrayList<>();


    public void init(){
        /* 정적 쿼리에 필요한 값들 넣기 시작 */
        apiUrlQyeryList.add("serviceKey=" + apiKey);
        apiUrlQyeryList.add("numOfRows=" + apiNumOfRows);
        apiUrlQyeryList.add("sickType=" + "2");
        apiUrlQyeryList.add("medTp=" + "1");
        apiUrlQyeryList.add("diseaseType=" + "SICK_NM");
        /* 정적 쿼리에 필요한 값들 넣기 끝 */
        urlInit(apiUrlQyeryList);
        initDisease();

        try{
            diseaseService.initDB(apiDataList.stream().map(DiseaseDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // url의 모든 정보(NUMSOFROWS의 갯수 만큼의 item)를 list에 담아 반환
    public void initDisease() {
        // 병 코드와 병 명 init 시작
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
                    DiseaseDTO disease = new DiseaseDTO();
                    try {
                        disease.setCode(getElementValue(element, "sickCd"));
                        disease.setName(getElementValue(element, "sickNm"));
                        apiDataList.add(disease);
                    } catch (Exception e) {
                        System.err.println("에러 발생: " + e.getMessage() + ", index: " + num);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // 병 설명 init 시작
        apiUrl = "https://openapi.naver.com/v1/search/encyc.json?&display=1";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id",serviceIdKey);
        requestHeaders.put("X-Naver-Client-Secret",serviceSecretKey);

        /* list 가지고 조회돌면서 새*/
        RateLimiter rateLimiter = RateLimiter.create(5.0);
        for(DiseaseDTO disease : apiDataList){
            // 중복 병명 가지치기
            if(diseaseService.existsByNameContaining(disease.getName())) continue;

            rateLimiter.acquire();
            try {
                URL url = new URL(apiUrl + "&query=" + URLEncoder.encode(disease.getName(), "UTF-8"));
                System.out.println("<<url>> : " + url);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // 정상 호출
                    BufferedReader lineReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = lineReader.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject jsonObject = new JSONObject(sb.toString());
                    JSONArray items = jsonObject.getJSONArray("items");
                    if(!items.isEmpty()){
                        String rawDescription = items.getJSONObject(0).getString("description");
                        String description = Jsoup.parse(rawDescription).text();
                        disease.setDescription(description);
                    } else {
                        disease.setDescription("미상");
                    }

                    System.out.println("[병명]" + disease.getName() + " : " + disease.getDescription());

                }
            } catch (Exception e){
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
