package com.josdem.jugoterapia.webclient.automation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.josdem.jugoterapia.webclient.automation.config.TestDataSource;
import com.josdem.jugoterapia.webclient.service.BeverageService;
import com.josdem.jugoterapia.webclient.service.CategoryService;
import java.util.Map;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BeverageFromCategoriesMapTest {

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
    Flux<Map> publisher =
        categoryService.getCategoriesByLanguageMap("en").filter(it -> (int) it.get("id") == 5);
    StepVerifier.create(publisher)
        .assertNext(
            category -> {
              assertEquals(5, ((Map) category).get("id"));
              assertEquals("Healing", ((Map) category).get("name"));
            })
        .verifyComplete();
  }

  @Test
  @Order(2)
  @DisplayName("It filters beverage by id")
  void shouldGetBeverageById(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Flux<Map> publisher =
        categoryService
            .getBeveragesByCategoryMap(EXPECTED_CATEGORY_ID)
            .filter(it -> (int) it.get("id") == 85);
    StepVerifier.create(publisher)
        .assertNext(
            beverage -> {
              assertEquals(data.getBeverage().getId(), beverage.get("id"));
              assertEquals(data.getBeverage().getName(), beverage.get("name"));
              assertEquals(data.getBeverage().getIngredients(), beverage.get("ingredients"));
              assertEquals(data.getBeverage().getImage(), beverage.get("image"));
            })
        .verifyComplete();
  }

  @Test
  @Order(3)
  @DisplayName("it gets specific beverage")
  void shouldGetBeverage(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Mono<Map> publisher = beverageService.getBeverageAsMap(EXPECTED_BEVERAGE_ID);
    StepVerifier.create(publisher)
        .assertNext(
            beverage -> {
              assertAll(
                  "beverage",
                  () -> assertEquals(data.getBeverage().getId(), beverage.get("id")),
                  () -> assertEquals(data.getBeverage().getName(), beverage.get("name")),
                  () ->
                      assertEquals(
                          data.getBeverage().getIngredients(), beverage.get("ingredients")),
                  () -> assertEquals(data.getBeverage().getRecipe(), beverage.get("recipe")),
                  () -> assertEquals(data.getBeverage().getImage(), beverage.get("image")));
            })
        .verifyComplete();
  }
}
