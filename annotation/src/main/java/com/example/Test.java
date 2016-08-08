package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by linxj on 16/8/6.
 */

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.CLASS)
    public @interface Test {
    }

