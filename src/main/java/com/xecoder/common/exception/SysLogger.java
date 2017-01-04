package com.xecoder.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysLogger {

    private static final Logger ERROR_LOG = LoggerFactory.getLogger("apierror");
    private static final Logger INFO_LOG = LoggerFactory.getLogger("info");
    private static final Logger REQUEST_LOG = LoggerFactory.getLogger("request");

    public static void error(Object msg, Throwable e) {
        ERROR_LOG.error(msg.toString(), e);
    }

    public static void error(Object msg) {
        if (msg instanceof Throwable) {
            error(((Throwable) msg).getMessage(), (Throwable) msg);
        } else {
            ERROR_LOG.error(msg.toString());
        }
    }

    public static void info(String format, Object... arguments) {
        if (INFO_LOG.isInfoEnabled()) {
            INFO_LOG.info(format, arguments);
        }
    }

    public static void info(Object msg) {
        if (INFO_LOG.isInfoEnabled()) {
            INFO_LOG.info(msg.toString());
        }
    }

    public static void request(String format, Object... arguments) {
        if (REQUEST_LOG.isInfoEnabled()) {
            REQUEST_LOG.info(format, arguments);
        }
    }
}
