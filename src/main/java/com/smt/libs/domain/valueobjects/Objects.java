package com.smt.libs.domain.valueobjects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Objects {
    public static String toString(Object object) {
        return ReflectionToStringBuilder.toString(object, ToStringStyle.JSON_STYLE);
    }
}
