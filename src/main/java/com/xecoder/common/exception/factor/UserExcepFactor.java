package com.xecoder.common.exception.factor;

import com.xecoder.common.exception.ExcepFactor;
import org.springframework.http.HttpStatus;

public class UserExcepFactor {
    public static final ExcepFactor AUTH_FAILED = new ExcepFactor("error.auth.failed", HttpStatus.NOT_ACCEPTABLE);
    public static final ExcepFactor DELETE_SUPER_FAILED = new ExcepFactor("error.delete.super.failed", HttpStatus.NOT_ACCEPTABLE);

}
