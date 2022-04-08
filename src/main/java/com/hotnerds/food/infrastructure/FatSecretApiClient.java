package com.hotnerds.food.infrastructure;

import com.hotnerds.food.infrastructure.FatSecretToken;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FatSecretApiClient implements FoodApiClient {

    @Value("${fat-secret.api-url}")
    private String API_URI_PREFIX;

    private final String METHOD = "method";

    private final String FORMAT = "format";

    private final String JSON_FORMAT = "json";

    private final RestTemplate restTemplate;

    private final FatSecretToken fatSecretToken;

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<>() {
    };

    @Autowired
    public FatSecretApiClient(RestTemplateBuilder restTemplateBuilder, FatSecretToken fatSecretToken) {
        this.fatSecretToken = fatSecretToken;
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<Map<String, Object>> searchFoodById(final Long foodId) throws RestClientResponseException {
        final String FOOD_GET_METHOD = "food.get.v2";
        final String FOOD_ID = "food_id";

        URI url = UriComponentsBuilder
                .fromHttpUrl(API_URI_PREFIX)
                .queryParam(METHOD, FOOD_GET_METHOD)
                .queryParam(FOOD_ID, foodId.toString())
                .queryParam(FORMAT, JSON_FORMAT)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(fatSecretToken.getToken());

        RequestEntity<Void> request = RequestEntity.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .build();

        return restTemplate.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
    }

    public ResponseEntity<Map<String, Object>> searchFoods(final String foodName, final int pageNumber, final int pageSize) throws RestClientResponseException {
        final String FOODS_SEARCH_METHOD = "foods.search";
        final String SEARCH_EXPRESSION = "search_expression";
        final String PAGE_NUMBER = "page_number";
        final String MAX_RESULTS = "max_results";

        URI url = UriComponentsBuilder
                .fromHttpUrl(API_URI_PREFIX)
                .queryParam(METHOD, FOODS_SEARCH_METHOD)
                .queryParam(SEARCH_EXPRESSION, foodName)
                .queryParam(PAGE_NUMBER, pageNumber)
                .queryParam(MAX_RESULTS, pageSize)
                .queryParam(FORMAT, JSON_FORMAT)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(fatSecretToken.getToken());

        RequestEntity<Void> request = RequestEntity.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .build();

        return restTemplate.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
    }


}
