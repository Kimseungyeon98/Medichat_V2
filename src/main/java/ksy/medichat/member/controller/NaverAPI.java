package ksy.medichat.member.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



public class NaverAPI {
	private static final String NAVER_CLIENT_ID = "RNP28hanNjPbrV17UuZb";
    private static final String NAVER_CLIENT_SECRET = "utrdK5mSrl";
    
    public HttpEntity<MultiValueMap<String, String>> authCodeRequest(String code,String state){
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    	param.add("grant_type","authorization_code");
    	param.add("client_id",NAVER_CLIENT_ID);
    	param.add("client_secret",NAVER_CLIENT_SECRET);
    	param.add("code",code);
    	
    	return new HttpEntity<>(param, headers);
    }
    
    public ResponseEntity<String> accessToken(HttpEntity<MultiValueMap<String, String>> request){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	return restTemplate.exchange("https://nid.naver.com/oauth2.0/token",
    							HttpMethod.POST,
    							request,
    							String.class);
    }
    public ResponseEntity<String> userInfo(HttpEntity<MultiValueMap<String, String>> request){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	ResponseEntity<String> responseEntity = restTemplate.exchange("https://openapi.naver.com/v1/nid/me",
    							HttpMethod.POST,
    							request,
    							String.class);
    	String responseBody = responseEntity.getBody();
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode responseNode = jsonNode.get("response");
            
            String id = responseNode.get("id").asText();
            String name = responseNode.get("name").asText();
            String email = responseNode.get("email").asText();
            String gender = responseNode.get("gender").asText();
            
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Gender: " + gender);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseEntity;
    }
    
	public HttpEntity<MultiValueMap<String, String>> getUserInfo(String accessToken){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+accessToken);
		headers.add("Content_Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		return new HttpEntity<>(headers);
	}
	
	public String extractAccessToken(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            accessToken = jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리는 실제 상황에 맞게 변경할 수 있습니다.
        }

        return accessToken;
    }
}
