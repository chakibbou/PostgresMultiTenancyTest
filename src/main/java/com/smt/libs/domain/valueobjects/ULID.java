package com.smt.libs.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.f4b6a3.ulid.UlidCreator;
import com.smt.libs.domain.Exception.InvalidULIDFormatError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ULID extends ID {

    @JsonCreator
    public ULID(String value) {
        super(value);
    }

    public static ULID generate() {
        return new ULID(UlidCreator.getMonotonicUlid().toString());
    }

    public static ULID generate(long time) {
        return new ULID(UlidCreator.getUlid(time).toString());
    }

    @Override
    protected void validate(String value) {
        if (!this.hasValidULIDFormat(value)) {
            throw new InvalidULIDFormatError();
        }
    }

    private boolean hasValidULIDFormat(String value) {
        final Pattern pattern = Pattern.compile("[0-9A-HJKMNP-TV-Z]{26}");
        final Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
