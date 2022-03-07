package com.hotnerds.fatsecret.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotnerds.common.FatSecretConfig;
import com.hotnerds.fatsecret.domain.dto.FatSecretDetailResponseDto;
import com.hotnerds.fatsecret.domain.dto.FatSecretFood;
import com.hotnerds.fatsecret.domain.dto.FoodWrapper;
import java.net.URI;
import java.util.Map;

import com.hotnerds.fatsecret.exception.FatSecretResponseError;
import com.hotnerds.fatsecret.exception.FatSecretResponseErrorException;
import com.hotnerds.fatsecret.exception.FatSecretResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FatSecretService {

    private final String API_URI_PREFIX = "https://platform.fatsecret.com/rest/server.api";

    private final RestTemplate restTemplate;

    private final FatSecretConfig fatSecretConfig;

    @Autowired
    public FatSecretService(RestTemplateBuilder restTemplateBuilder, FatSecretConfig fatSecretConfig, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .errorHandler(new FatSecretResponseErrorHandler(objectMapper))
                .build();
        this.fatSecretConfig = fatSecretConfig;
    }

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    public ResponseEntity<Map<String, Object>> getFoodById(Long foodId) throws FatSecretResponseErrorException {
        final String METHOD = "food.get.v2";
        final String FOOD_ID = foodId.toString();
        final String FORMAT = "json";

        final String token = fatSecretConfig.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity httpEntity = new HttpEntity(headers);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("method", METHOD);
        params.add("food_id", FOOD_ID);
        params.add("format", FORMAT);

        URI uri = UriComponentsBuilder
            .fromHttpUrl(API_URI_PREFIX)
            .queryParams(params)
            .build()
            .toUri();

        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, PARAMETERIZED_RESPONSE_TYPE);
    }

}
