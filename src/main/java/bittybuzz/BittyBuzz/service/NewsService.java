package bittybuzz.BittyBuzz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;


@Service
public class NewsService {

    private final RestTemplate restTemplate;


    @Value("${deepsearch.api.key}")
    private String apiKey;

    @Autowired
    public NewsService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;

    }
    public String fetchArticles(String company_name, String symbols, String keyword, String date_from, String date_to) {
        StringBuilder apiUrl = new StringBuilder("https://api-v2.deepsearch.com/v1/articles?");

        if (company_name != null) apiUrl.append("company_name=").append(company_name).append("&");
        if (symbols != null) apiUrl.append("symbols=").append(symbols).append("&");
        if (keyword != null) {
            // 복합 쿼리나 필드별 검색을 지원하기 위해 `keyword`를 URL 인코딩
            String encodedKeyword = UriUtils.encode(keyword, StandardCharsets.UTF_8);
            apiUrl.append("keyword=").append(encodedKeyword).append("&");
        }
        if (date_from != null) apiUrl.append("date_from=").append(date_from).append("&");
        if (date_to != null) apiUrl.append("date_to=").append(date_to).append("&");


        apiUrl.append   ("api_key=").append(apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl.toString(), HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
    public String fetchAggregatedArticles(String keyword, String groupby, String date_from, String date_to, int page_size) {
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/articles/aggregation?keyword=%s&groupby=%s&date_from=%s&date_to=%s&page_size=%d&api_key=%s",
                keyword, groupby, date_from, date_to, page_size, apiKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
    public String fetchArticlesBySection(String sections, String date_from, String date_to) {
        // API URL을 섹션과 날짜로 동적으로 생성
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/articles/%s?date_from=%s&date_to=%s&api_key=%s",
                sections, date_from, date_to, apiKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
    public String fetchGlobalArticles(String company_name, String symbols, String keyword, String date_from, String date_to) {
        StringBuilder apiUrl = new StringBuilder("https://api-v2.deepsearch.com/v1/global-articles?");

        if (company_name != null) apiUrl.append("company_name=").append(company_name).append("&");
        if (symbols != null) apiUrl.append("symbols=").append(symbols).append("&");
        if (keyword != null) apiUrl.append("keyword=").append(keyword).append("&");
        if (date_from != null) apiUrl.append("date_from=").append(date_from).append("&");
        if (date_to != null) apiUrl.append("date_to=").append(date_to).append("&");

        apiUrl.append("api_key=").append(apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl.toString(), HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
    public String fetchGlobalAggregatedArticles(String keyword, String groupby, String date_from, String date_to, int page_size) {
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/global-articles/aggregation?keyword=%s&groupby=%s&date_from=%s&date_to=%s&page_size=%d&api_key=%s",
                keyword, groupby, date_from, date_to, page_size, apiKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
    //해외 섹션 추가예정


    public String getTopics(String company_name, String date_from, String date_to) {
        // API URL 생성
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/articles?company_name=%s&date_from=%s&date_to=%s&api_key=%s",
                company_name, date_from, date_to, apiKey
        );

        // GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        return response.getBody(); // 받은 데이터를 반환
    }
    public String getTrendingTopics(String date_from, String date_to) {
        // API URL 생성
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/articles?date_from=%s&date_to=%s&api_key=%s",
                date_from, date_to, apiKey
        );

        // GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        return response.getBody(); // 받은 데이터를 반환
    }
    public String getTopicsBySection(String section, String companyName, String dateFrom, String dateTo) {
        // 기본 API URL을 구성하고, 동적 매개변수를 사용하여 필요한 정보를 추가
        String apiUrl = String.format(
                "https://news.deepsearch.com/v1/articles/topics/%s?api_key=%s",
                section, apiKey);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("company_name", companyName)
                .queryParam("date_from", dateFrom)
                .queryParam("date_to", dateTo);

        // 최종 URI 생성
        String finalUrl = uriBuilder.toUriString();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        // GET 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();  // 받은 데이터 반환
    }
    public String getTrendingTopicsBySection(String section, String date_from, String date_to) {
        // URL 구성: section 및 date 파라미터 추가
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/articles/topics/%s/trending?api_key=%s",
                section, apiKey);

        // URI 빌더를 사용하여 파라미터를 URL에 추가
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("date_from", date_from)
                .queryParam("date_to", date_to);

        // 최종 URI 생성
        String finalUrl = uriBuilder.toUriString();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        // GET 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();  // API 응답 반환
    }
    public String getGlobalTopics( String date_from, String date_to) {
        // API URL 생성
        String apiUrl = String.format(
                "https://api-v2.deepsearch.com/v1/global-articles/topics?&date_from=%s&date_to=%s&api_key=%s",
                date_from, date_to, apiKey
        );

        // GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        return response.getBody(); // 받은 데이터를 반환
    }

}




