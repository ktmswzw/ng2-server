package com.xecoder.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2016/1/11-13:21
 * Feeling.com.xecoder.model
 * 不需要登陆认证
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NonAuthoritative {
}
