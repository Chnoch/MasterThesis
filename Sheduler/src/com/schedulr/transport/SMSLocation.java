package com.schedulr.transport;

import java.util.regex.Pattern;

public class SMSLocation
{
    private String title;
    private String code;
    private Pattern pattern;

    public SMSLocation(String title, String code, String pattern)
    {
        this.title = title;
        this.code = code;
        this.pattern = Pattern.compile(pattern);
    }

    public String getTitle()
    {
        return title;
    }

    public String getCode()
    {
        return code;
    }

    public Pattern getPattern()
    {
        return pattern;
    }
}
