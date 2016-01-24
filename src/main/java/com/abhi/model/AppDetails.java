package com.abhi.model;

public class AppDetails
{
    private String appName;
    private String pushType;
    private String sslKeyFilename;
    private String sslKeypassword;
    private boolean production;

    public String getAppName() { return appName; }

    public void setAppName(String value) {
        this.appName = value;
    }

    public String getPushType() { return pushType; }

    public void setPushType(String value) { this.pushType = value; }

    public String getSslKeyFilename() {
        return sslKeyFilename;
    }

    public void setSslKeyFilename(String value) {
        this.sslKeyFilename = value;
    }

    public String getSslKeypassword() {
        return sslKeypassword;
    }

    public void setSslKeypassword(String value) {
        this.sslKeypassword = value;
    }

    public boolean isProduction() {
        return production;
    }

    public void setProduction(boolean value) {
        this.production = value;
    }

    public String toString()
    {
        return appName + ", pushType - " + pushType +
                ", sslKeyFilename - " + sslKeyFilename + ", production - " + production;
    }


}
