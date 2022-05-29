package com.josdem.jugoterapia.automation;

import com.josdem.jugoterapia.webclient.config.DataProperties;
import com.josdem.jugoterapia.webclient.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CategoryServiceTest {

  private final CategoryService categoryService;
  private final DataProperties dataProperties;

  @Test
  @DisplayName("it validates categories size")
  void shouldGetCategories(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    StepVerifier.create(categoryService.getCategoriesByLanguage("en"))
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  @DisplayName("it validates category ids")
  void shouldValidateCategoryIds(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    Flux<Integer> categories =
        categoryService.getCategoriesByLanguage("en").map(category -> category.getId());
    StepVerifier.create(categories)
        .expectNext(
            dataProperties.getCategories().get(0).getId(),
            dataProperties.getCategories().get(1).getId(),
            dataProperties.getCategories().get(2).getId(),
            dataProperties.getCategories().get(3).getId())
        .verifyComplete();
  }

  @Test
  @DisplayName("it validates category names")
  void shouldValidateCategoryNames(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    Flux<String> categories =
        categoryService.getCategoriesByLanguage("en").map(category -> category.getName());
    StepVerifier.create(categories)
        .expectNext(
            dataProperties.getCategories().get(0).getName(),
            dataProperties.getCategories().get(1).getName(),
            dataProperties.getCategories().get(2).getName(),
            dataProperties.getCategories().get(3).getName())
        .verifyComplete();
  }
}
