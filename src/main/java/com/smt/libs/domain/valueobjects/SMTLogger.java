package com.smt.libs.domain.valueobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

public class SMTLogger {
    private final Logger logger;

    private SMTLogger(Logger logger) {
        this.logger = logger;
    }

    public static SMTLogger getLogger(Class<?> clazz) {
        return new SMTLogger(LogManager.getLogger(clazz));
    }

    public void info(Object context, String message, Object... args) {
        this.logger.info(() -> this.addContext(context, message, args));
    }

    public void warn(Object context, String message, Object... args) {
        this.logger.warn(() -> this.addContext(context, message, args));
    }

    public void error(Object context, Throwable throwable, String message, Object... args) {
        this.logger.error(() -> this.addContext(context, message, args)
                , throwable);
    }

    public void error(Object context, String message, Object... args) {
        this.logger.error(() -> this.addContext(context, message, args));
    }

    private String addContext(Object context, String message, Object[] args) {
        return "[context: %s] %s".formatted(context != null ? context.toString() : null, this.formatMessage(message, args));
    }

    private String formatMessage(String message, Object[] args) {
        if (Strings.isEmpty(message)) return "";
        return message.formatted(args);
    }
}
