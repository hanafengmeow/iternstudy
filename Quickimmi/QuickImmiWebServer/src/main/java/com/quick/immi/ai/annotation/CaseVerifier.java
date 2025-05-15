/* (C) 2024 */
package com.quick.immi.ai.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaseVerifier {
  boolean value() default true;
}
