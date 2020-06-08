package com.wenmq.mtask.mtask_kernel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *    can receive different name as receptor.
 */
@Target(ElementType.METHOD)
public @interface MReceptors {
    String[] receptorAddress() default {};
}
