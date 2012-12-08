package com.rop.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-29
 * Time: 上午4:46
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasName {

    String name() default "";

}
