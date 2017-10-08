package com.github.chencye.app.file2db.config.filter;

import com.github.chencye.app.file2db.config.SuperConfig;

import java.util.regex.Pattern;

public class NameFilterConfig extends SuperConfig {
    private Pattern regex;

    @Override
    public String toString() {
        return "NameFilterConfig{" +
                "regex=" + regex +
                "} " + super.toString();
    }

    public Pattern getRegex() {
        return regex;
    }

    public void setRegex(Pattern regex) {
        this.regex = regex;
    }
}
