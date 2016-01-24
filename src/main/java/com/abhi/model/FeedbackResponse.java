package com.abhi.model;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedbackResponse extends BaseResponse
{
    private Map<String,Date> invalidTokens;

    public Map<String,Date> getInvalidTokens() {
        return invalidTokens;
    }

    public void setInvalidTokens(Map<String,Date> invalidTokens) {
        this.invalidTokens = invalidTokens;
    }
}
