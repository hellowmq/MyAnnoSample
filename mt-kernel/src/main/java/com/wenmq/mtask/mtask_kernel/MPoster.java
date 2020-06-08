package com.wenmq.mtask.mtask_kernel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *    can send different task as poster.
 */
@Target(ElementType.TYPE)
public @interface MPoster {
    String receptorAddress();

    int priority() default 0;

    boolean hasContext() default false;
}
