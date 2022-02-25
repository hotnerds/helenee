package com.hotnerds.fatsecret.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FatSecretResponseDto {

    private Long foodApiId;
    private String name;
    private Double calories;
    private Double protein;
    private Double carbs;

    @Builder
    public FatSecretResponseDto(Long foodApiId, String name, Double calories, Double protein,
        Double carbs) {
        this.foodApiId = foodApiId;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
    }

    public static FatSecretResponseDto of(FatSecretFood fatSecretFood) {
        return FatSecretResponseDto.builder()
            .foodApiId(fatSecretFood.getFoodId())
            .name(fatSecretFood.getFoodName())
            .calories(fatSecretFood.getServingWrapper()
                .getFatSecretServingList()
                .get(0)
                .getCalories())
            .protein(fatSecretFood.getServingWrapper()
                .getFatSecretServingList()
                .get(0)
                .getProtein())
            .carbs(fatSecretFood.getServingWrapper()
                .getFatSecretServingList()
                .get(0)
                .getCarbohydrate())
            .build();
    }

}
