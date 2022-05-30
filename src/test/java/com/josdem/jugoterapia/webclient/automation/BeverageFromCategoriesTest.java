package com.josdem.jugoterapia.webclient.automation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.josdem.jugoterapia.webclient.automation.config.TestDataSource;
import com.josdem.jugoterapia.webclient.automation.utils.ApplicationState;
import com.josdem.jugoterapia.webclient.model.Beverage;
import com.josdem.jugoterapia.webclient.model.Category;
import com.josdem.jugoterapia.webclient.service.BeverageService;
import com.josdem.jugoterapia.webclient.service.CategoryService;
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
class BeverageFromCategoriesTest {

  private final CategoryService categoryService;
  private final BeverageService beverageService;
  private final ApplicationState applicationState;
  private final TestDataSource data;

  @Test
  @Order(1)
  @DisplayName("it filters category by id")
  void shouldGetCategoriesById(TestInfo testInfo) {
    log.info("Running: {}", testInfo.getDisplayName());
    Flux<Category> publisher =
        categoryService.getCategoriesByLanguage("en").filter(category -> category.getId() == 5);
    StepVerifier.create(publisher)
        .assertNext(
            category -> {
              assertEquals(5, category.getId());
              assertEquals("Healing", category.getName());
              applicationState.setCategory(category);
            })
        .verifyComplete();
  }

  @Test
  @Order(2)
  @DisplayName("It filters beverage by id")
  void shouldGetBeverageById(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Flux<Beverage> publisher =
        categoryService
            .getBeveragesByCategory(applicationState.getCategory().getId())
            .filter(beverage -> beverage.getId() == 85);
    StepVerifier.create(publisher)
        .assertNext(
            beverage -> {
              assertEquals(data.getBeverage().getId(), beverage.getId());
              assertEquals(data.getBeverage().getName(), beverage.getName());
              assertNotNull(data.getBeverage().getIngredients());
              assertEquals(data.getBeverage().getImage(), beverage.getImage());
              applicationState.setBeverage(beverage);
            })
        .verifyComplete();
  }

  @Test
  @Order(3)
  @DisplayName("it gets specific beverage")
  void shouldGetBeverage(TestInfo testInfo) {
    log.info("Running {}", testInfo.getDisplayName());
    Mono<Beverage> publisher = beverageService.getBeverage(applicationState.getBeverage().getId());
    StepVerifier.create(publisher).expectNext(data.getBeverage()).verifyComplete();
  }
}
