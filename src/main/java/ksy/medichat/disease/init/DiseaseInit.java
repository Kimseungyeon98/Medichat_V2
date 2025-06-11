package ksy.medichat.disease.init;

import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.service.DiseaseService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
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

    @Autowired
    private DiseaseService diseaseService;

    @Override
    public void run(ApplicationArguments args) {
        if (diseaseService.isEmpty()) {
            init();
        }
    }

    @Value("${API.KSY.DISEASE.DATA-API-KEY}")
    private String KSY_KEY; /* Service Key */

    @Value("${API.KSY.DISEASE.X-Naver-Client-Id}")
    private String serviceIdKey; /* Service Key */
    @Value("${API.KSY.DISEASE.X-Naver-Client-Secret}")
    private String serviceSecretKey; /* Service Key */

    final static String TYPE = "xml"; /* xml(기본값), JSON */
    final static String NUMSOFROWS = "1000"; /* 한 페이지 결과 수 */

    //전체 약을 담을 리스트
    public List<DiseaseDTO> list;

    public void init(){
        list = new ArrayList<>();
        /*for(int i=1; i<=49; i++) {
            System.out.println("<<PageNum>> : " + i);
            try {
                getDisease(urlMaker(i));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }*/
        for (int j = 1; j <= 2; j++) {
            /*getDisease(urlMaker(i));*/
            if(j==1){
                for (int l = 1; l <= 15; l++) {
                    getDisease(urlMaker(l,"1"));
                }
            }else if(j==2){
                for (int l = 1; l <= 16; l++) {
                    getDisease(urlMaker(l,"2"));
                }
            }
        }


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id",serviceIdKey);
        requestHeaders.put("X-Naver-Client-Secret",serviceSecretKey);
        HttpURLConnection con;
        /* list 가지고 조회돌면서 새*/
        String key_apiUrl = "";

        for(DiseaseDTO disease : list){
            try {
                key_apiUrl = "https://openapi.naver.com/v1/search/encyc.json?query=" + URLEncoder.encode(disease.getDiseaseName() + "증상", "UTF-8") + "&display=5";

                URL url = new URL(key_apiUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }
                String responseBody = "";
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                    InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
                    BufferedReader lineReader = new BufferedReader(streamReader);
                    StringBuilder rb = new StringBuilder();

                    String line;
                    while ((line = lineReader.readLine()) != null) {
                        rb.append(line);
                    }

                    responseBody = rb.toString();
                }
                JSONObject jObject = new JSONObject(responseBody);
                StringBuilder l = new StringBuilder();

                //https://openapi.naver.com/v1/captcha/nkey 호출해서 받은 키값

                JSONArray key = jObject.getJSONArray("items");
                String str = key.get(0).toString();

                String viewerUrl = str.substring(str.indexOf("link") + 6, str.indexOf("description")).replaceAll("\"", "").replaceAll(",", "");

                org.jsoup.nodes.Document document = Jsoup.connect(viewerUrl).get();

                Elements titleUrlElements = document.getElementsByClass("txt");

                for (org.jsoup.nodes.Element titleUrlElement : titleUrlElements) {
                    l.append(titleUrlElement.text()).append("<br>");
                }

                if (!l.isEmpty()) {
                    disease.setDiseaseSymptoms(l.toString());
                } else {
                    disease.setDiseaseSymptoms("미상");
                }
            } catch (Exception e){

            }
        }
        try{
            diseaseService.initDB(list.stream().map(DiseaseDTO::toEntity).collect(Collectors.toList()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // pageNo를 동적으로 변경해서 url 생성
    public String urlMaker(int pageNo, String medTp) {
        String url = "";
        try {
            url += "https://apis.data.go.kr/B551182/diseaseInfoService1/getDissNameCodeList1";
            url += "?serviceKey=" + KSY_KEY; /* Service Key */
            url += "&pageNo=" + URLEncoder.encode(String.valueOf(pageNo),"UTF-8"); /* 페이지번호 */
            url += "&numOfRows=" + URLEncoder.encode(NUMSOFROWS,"UTF-8"); /* 한 페이지 결과 수 */
            url += "&sickType=" + URLEncoder.encode("2","UTF-8"); /* 1,2 */
            url += "&medTp=" + URLEncoder.encode(medTp,"UTF-8"); /* 1,2 */
            url += "&diseaseType=" + URLEncoder.encode("SICK_NM","UTF-8"); /* SICK_CD: 상병코드, SICK_NM: 상병명 */
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
    public List<DiseaseDTO> getDisease(String urlString) {
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
                DiseaseDTO disease = new DiseaseDTO();
                try{
                    disease.setDiseaseCode(getElementValue(element, "sickCd"));
                    disease.setDiseaseName(getElementValue(element, "sickNm"));
                    list.add(disease);
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
