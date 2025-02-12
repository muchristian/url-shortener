package com.example.urlshortener.urlShortenerModule.shared.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({
  ElementType.TYPE_USE,
  ElementType.FIELD,
  ElementType.PARAMETER,
  ElementType.CONSTRUCTOR,
  ElementType.METHOD
})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidTtlValidator.class)
@Documented
public @interface ValidTtl {
  String message() default "Invalid TTL format. It should follow the 'X_TIMEUNIT', e.g., '1_DAY'";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}