package com.hotnerds.diet.presentation;

import com.hotnerds.common.security.oauth2.annotation.Authenticated;
import com.hotnerds.common.security.oauth2.service.AuthenticatedUser;
import com.hotnerds.diet.application.DietService;
import com.hotnerds.diet.domain.dto.DietRequestByDateDto;
import com.hotnerds.diet.domain.dto.DietResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hotnerds.diet.presentation.DietController.DEFAULT_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(DEFAULT_URL)
public class DietController {

    public static final String DEFAULT_URL = "/api/diets";

    private final DietService dietService;

    @GetMapping("/{dietId}")
    public ResponseEntity<DietResponseDto> getDiet(@PathVariable Long dietId) {
        return ResponseEntity.ok(
                dietService.find(dietId)
        );
    }

    @GetMapping
    public ResponseEntity<List<DietResponseDto>> getDietByMealDate(DietRequestByDateDto requestDto, @Authenticated AuthenticatedUser user) {
        return ResponseEntity.ok(
                dietService.searchByDate(requestDto, user.getId())
        );
    }
}