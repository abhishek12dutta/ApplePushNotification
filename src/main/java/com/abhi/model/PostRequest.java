package com.abhi.model;

import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PostRequest
{
    private String appName = null;
    private String alert = null; // Goes in the alertBody
    private String sound = null;

    private boolean showBadge = false;
    private int badgeCount = 0;
    private boolean contentAvailable = false;
    private String metadata;

    // For Safari notifications
    private String title = null;
    private String action = null;
    private String urlArgs = null;

    private Set<String> devices;

    public Set<String> getDevices() {
        return devices;
    }

    public void setDevices(Set<String> devices) {
        this.devices = devices;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert){
        this.alert = alert;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isShowBadge() {
        return showBadge;
    }

    public void setShowBadge(boolean showBadge) {
        this.showBadge = showBadge;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable= contentAvailable;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrlArgs() {
        return urlArgs;
    }

    public void setUrlArgs(String urlArgs) {
        this.urlArgs = urlArgs;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder( "appName - " + appName );
        if ( alert != null )
            sb.append( "\nalert - " + alert );
        if ( title != null )
            sb.append( "\ntitle - " + title );
        if ( action != null )
            sb.append( "\naction - " + action );
        if ( urlArgs != null )
            sb.append( "\nurlArgs - " + urlArgs );
        if ( sound != null )
            sb.append( "\nsound - " + sound );
        if ( showBadge )
            sb.append( "\nshowBadge - YES" );
        if ( showBadge )
            sb.append( "\nbadgeCount - " + badgeCount );
        if ( contentAvailable )
            sb.append( "\ncontentAvailable - YES");
        if ( metadata != null )
            sb.append( "\nmetadata - " + metadata );

        sb.append( "\ndevices - " + devices );

        return sb.toString();
    }
}

