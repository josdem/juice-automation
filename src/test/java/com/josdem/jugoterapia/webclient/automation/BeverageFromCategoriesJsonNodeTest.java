package com.josdem.jugoterapia.webclient.automation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.josdem.jugoterapia.webclient.automation.config.TestDataSource;
import com.josdem.jugoterapia.webclient.service.BeverageService;
import com.josdem.jugoterapia.webclient.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BeverageFromCategoriesJsonNodeTest {

  private static final int EXPECTED_CATEGORY_ID = 5;
  private static final int EXPECTED_BEVERAGE_ID = 85;

  private final CategoryService categoryService;
  private final BeverageService beverageService;
  private final TestDataSource data;

  @Test
  @Order(1)
  @DisplayName("it filters category by id")
  void shouldGetCategoriesById(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    Mono<List<String>> publisher =
        categoryService.getCategoriesByLanguageJson("en").map(it -> it.findValuesAsText("id"));
    StepVerifier.create(publisher)
        .assertNext(
            categoriesIds -> {
              assertTrue(
                  categoriesIds.contains(String.valueOf(EXPECTED_CATEGORY_ID)),
                  "it should contains healthy category");
            })
        .verifyComplete();
  }

  @Test
  @Order(2)
  @DisplayName("It filters beverage by id")
  void shouldGetBeverageById(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Mono<List<String>> publisher =
        categoryService
            .getBeveragesByCategoryJson(EXPECTED_CATEGORY_ID)
            .map(it -> it.findValuesAsText("id"));
    StepVerifier.create(publisher)
        .assertNext(
            beveragesIds -> {
              assertTrue(
                  beveragesIds.contains(String.valueOf(EXPECTED_BEVERAGE_ID)),
                  "it should contains Anti-constipation Smoothie beverage");
            })
        .verifyComplete();
  }

  @Test
  @Order(3)
  @DisplayName("it gets specific beverage")
  void shouldGetBeverage(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Mono<JsonNode> publisher = beverageService.getBeverageAsJson(EXPECTED_BEVERAGE_ID);
    StepVerifier.create(publisher)
        .assertNext(
            beverage -> {
              assertAll(
                  "beverage",
                  () -> assertEquals(data.getBeverage().getId(), beverage.get("id").asInt()),
                  () -> assertEquals(data.getBeverage().getName(), beverage.get("name").asText()),
                  () ->
                      assertEquals(
                          data.getBeverage().getIngredients(),
                          beverage.get("ingredients").asText()),
                  () ->
                      assertEquals(data.getBeverage().getRecipe(), beverage.get("recipe").asText()),
                  () ->
                      assertEquals(data.getBeverage().getImage(), beverage.get("image").asText()));
            })
        .verifyComplete();
  }
}
