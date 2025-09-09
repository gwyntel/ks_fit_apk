package com.aliyun.alink.linksdk.tmp.devicemodel;

import android.text.TextUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class DeviceModel {
    private List<Event> events;
    private List<String> extend;
    private String id;
    private String link;
    private Profile profile;
    private List<Property> properties;
    private String schema;
    private List<Service> services;

    public Event getEvent(String str) {
        List<Event> list;
        if (!TextUtils.isEmpty(str) && (list = this.events) != null && !list.isEmpty()) {
            for (Event event : this.events) {
                if (str.equalsIgnoreCase(event.getIdentifier())) {
                    return event;
                }
            }
        }
        return null;
    }

    public String getEventMethod(String str) {
        List<Event> list;
        if (!TextUtils.isEmpty(str) && (list = this.events) != null && !list.isEmpty()) {
            for (Event event : this.events) {
                if (str.equalsIgnoreCase(event.getIdentifier())) {
                    return event.getMethod();
                }
            }
        }
        return null;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    @Deprecated
    public List<String> getExtend() {
        return this.extend;
    }

    public String getId() {
        return this.id;
    }

    public String getLink() {
        return this.link;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getServiceMethod(String str) {
        List<Service> list;
        if (!TextUtils.isEmpty(str) && (list = this.services) != null && !list.isEmpty()) {
            for (Service service : this.services) {
                if (str.equalsIgnoreCase(service.getIdentifier())) {
                    return service.getMethod();
                }
            }
        }
        return null;
    }

    public List<Service> getServices() {
        return this.services;
    }

    public void setEvents(List<Event> list) {
        this.events = list;
    }

    @Deprecated
    public void setExtend(List<String> list) {
        this.extend = list;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setProperties(List<Property> list) {
        this.properties = list;
    }

    public void setSchema(String str) {
        this.schema = str;
    }

    public void setServices(List<Service> list) {
        this.services = list;
    }
}
