package com.xecoder.common.exception;

public class SysException extends RuntimeException {

    private ExcepFactor factor;

    public SysException(ExcepFactor factor) {
        this.factor = factor;
    }

    public SysException(ExcepFactor factor, String message) {
        super(message);
        this.factor = factor;
    }

    public SysException(ExcepFactor factor, String message, Throwable cause) {
        super(message, cause);
        this.factor = factor;
    }

    public ExcepFactor getFactor() {
        return factor;
    }

    public void setFactor(ExcepFactor factor) {
        this.factor = factor;
    }
}

