package com.hotnerds.food.application;

import com.hotnerds.common.exception.BusinessException;
import com.hotnerds.common.exception.ErrorCode;
import com.hotnerds.fatsecret.application.FatSecretApiClient;
import com.hotnerds.food.domain.Food;
import com.hotnerds.food.domain.Nutrient;
import com.hotnerds.food.domain.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    FatSecretApiClient apiClient;
    FoodRepository foodRepository;

    @Transactional
    public Food findOrCreate(Long apiId) {
        Food food = foodRepository.findByApiId(apiId)
                .orElseGet(() -> mapApiResponseToFood(
                        apiClient.searchFoodById(apiId)));

        return foodRepository.save(food);
    }

    public Food findById(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(() -> new BusinessException(ErrorCode.FOOD_NOT_FOUND_EXCEPTION));
    }

    @SuppressWarnings("unchecked")
    protected Food mapApiResponseToFood(ResponseEntity<Map<String, Object>> response) {
        Map<String, Object> food = (Map<String, Object>) response.getBody().get("food");
        Map<String, Object> servings = (Map<String, Object>) food.get("servings");
        Map<String, String> serving = ((ArrayList<Map<String, String>>) servings.get("serving")).get(0);

        String apiId = food.get("food_id").toString();
        String foodName = food.get("food_name").toString();
        String calories = serving.get("calories");
        String carbs = serving.get("carbohydrate");
        String protein = serving.get("protein");
        String fat = serving.get("fat");

        return Food.builder()
                .apiId(Long.parseLong(apiId))
                .foodName(foodName)
                .nutrient(Nutrient.builder()
                        .calories(Double.parseDouble(calories))
                        .carbs(Double.parseDouble(carbs))
                        .protein(Double.parseDouble(protein))
                        .fat(Double.parseDouble(fat))
                        .build())
                .build();

    }
}