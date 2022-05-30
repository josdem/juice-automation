package com.josdem.jugoterapia.webclient.automation.utils;

import com.josdem.jugoterapia.webclient.model.Beverage;
import com.josdem.jugoterapia.webclient.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApplicationState {
  private Category category;
  private Beverage beverage;
}
