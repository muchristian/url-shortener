package com.example.urlshortener.urlShortenerModule.shared.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidTtlValidator implements ConstraintValidator<ValidTtl, String> {
  private static final Pattern TTL_PATTERN = Pattern.compile("^\\d+_(SECOND|MINUTE|HOUR|DAY|MONTH|YEAR)$");
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true;
    }
    return TTL_PATTERN.matcher(value).matches();
  }
}