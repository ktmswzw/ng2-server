package com.xecoder.common.exception.factor;

import com.xecoder.common.exception.ExcepFactor;
import org.springframework.http.HttpStatus;

/**
 * Created by yanglu
 */
public class UserExcepFactor {
    public static final ExcepFactor AUTH_FAILED = new ExcepFactor("error.auth.failed", HttpStatus.NOT_ACCEPTABLE);

}
