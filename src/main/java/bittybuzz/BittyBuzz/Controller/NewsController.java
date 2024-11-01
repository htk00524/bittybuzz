package bittybuzz.BittyBuzz.Controller;

import bittybuzz.BittyBuzz.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/articles")
    public ResponseEntity<String> searchArticles(
            @RequestParam(required = false) String company_name,
            @RequestParam(required = false) String symbols,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to) {

        String response = newsService.fetchArticles(company_name, symbols, keyword, date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/aggregation")
    public ResponseEntity<String> getAggregatedArticles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to,
            @RequestParam(required = false) String groupby,
            @RequestParam(required = false, defaultValue = "100") int page_size
    ) {
        String response = newsService.fetchAggregatedArticles(keyword, groupby, date_from, date_to, page_size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/{section}")
    public ResponseEntity<String> getArticlesBySection(
            @RequestParam String section,
            @RequestParam String date_from,
            @RequestParam String date_to) {

        String response = newsService.fetchArticlesBySection(section, date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/global-articles")
    public ResponseEntity<String> searchGlobalArticles(
            @RequestParam(required = false) String company_name,
            @RequestParam(required = false) String symbols,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to) {

        String response = newsService.fetchGlobalArticles(company_name, symbols, keyword, date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/global-articles/aggregation")
    public ResponseEntity<String> getGlobalAggregatedArticles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to,
            @RequestParam(required = false) String groupby,
            @RequestParam(required = false, defaultValue = "100") int page_size
    ) {
        String response = newsService.fetchGlobalAggregatedArticles(keyword, groupby, date_from, date_to, page_size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/topics")
    public ResponseEntity<String> getTopics(
            @RequestParam(required = false) String company_name,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to
    ) {
        String response = newsService.getTopics(company_name, date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/topics/trending")
    public ResponseEntity<String> getTrendingTopics(
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to
    ) {
        String response = newsService.getTrendingTopics(date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articles/topics/{section}")
    public ResponseEntity<String> getTopicsBySection(
            @PathVariable String section,
            @RequestParam(required = false) String company_name,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to) {

        String response = newsService.getTopicsBySection(section, company_name, date_from, date_to);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/articles/topics/{section}/trending")
    public ResponseEntity<String> getTrendingTopicsBySection(
            @PathVariable String section,
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to
    ) {
        String response = newsService.getTrendingTopicsBySection(section, date_from, date_to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/global-articles/topics")
    public ResponseEntity<String> getGlobalTopics(
            @RequestParam(required = false) String date_from,
            @RequestParam(required = false) String date_to
    ) {
        String response = newsService.getGlobalTopics( date_from, date_to);
        return ResponseEntity.ok(response);
    }
}
