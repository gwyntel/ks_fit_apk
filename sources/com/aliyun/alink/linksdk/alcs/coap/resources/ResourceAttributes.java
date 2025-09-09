package com.aliyun.alink.linksdk.alcs.coap.resources;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class ResourceAttributes {
    private final ConcurrentMap<String, AttributeValues> attributes = new ConcurrentHashMap();

    private static final class AttributeValues {
        private final List<String> list;

        private AttributeValues() {
            this.list = Collections.synchronizedList(new LinkedList());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void add(String str) {
            this.list.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List<String> getAll() {
            return this.list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized String getFirst() {
            if (this.list.isEmpty()) {
                return "";
            }
            return this.list.get(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void setOnly(String str) {
            this.list.clear();
            if (str != null) {
                this.list.add(str);
            }
        }
    }

    private AttributeValues findAttributeValues(String str) {
        AttributeValues attributeValuesPutIfAbsent;
        AttributeValues attributeValues = this.attributes.get(str);
        return (attributeValues != null || (attributeValuesPutIfAbsent = this.attributes.putIfAbsent(str, (attributeValues = new AttributeValues()))) == null) ? attributeValues : attributeValuesPutIfAbsent;
    }

    public void addAttribute(String str) {
        addAttribute(str, "");
    }

    public void addContentType(int i2) {
        findAttributeValues("ct").add(Integer.toString(i2));
    }

    public void addInterfaceDescription(String str) {
        findAttributeValues(LinkFormat.INTERFACE_DESCRIPTION).add(str);
    }

    public void addResourceType(String str) {
        findAttributeValues(LinkFormat.RESOURCE_TYPE).add(str);
    }

    public void clearAttribute(String str) {
        this.attributes.remove(str);
    }

    public void clearContentType() {
        this.attributes.remove("ct");
    }

    public void clearResourceType() {
        this.attributes.remove(LinkFormat.RESOURCE_TYPE);
    }

    public boolean containsAttribute(String str) {
        return this.attributes.containsKey(str);
    }

    public Set<String> getAttributeKeySet() {
        return this.attributes.keySet();
    }

    public List<String> getAttributeValues(String str) {
        AttributeValues attributeValues = this.attributes.get(str);
        return attributeValues != null ? attributeValues.getAll() : Collections.emptyList();
    }

    public List<String> getContentTypes() {
        return getAttributeValues("ct");
    }

    public int getCount() {
        return this.attributes.size();
    }

    public List<String> getInterfaceDescriptions() {
        return getAttributeValues(LinkFormat.INTERFACE_DESCRIPTION);
    }

    public String getMaximumSizeEstimate() {
        return findAttributeValues(LinkFormat.MAX_SIZE_ESTIMATE).getFirst();
    }

    public List<String> getResourceTypes() {
        return getAttributeValues(LinkFormat.RESOURCE_TYPE);
    }

    public String getTitle() {
        if (containsAttribute("title")) {
            return getAttributeValues("title").get(0);
        }
        return null;
    }

    public boolean hasObservable() {
        return !getAttributeValues(LinkFormat.OBSERVABLE).isEmpty();
    }

    public void setAttribute(String str, String str2) {
        findAttributeValues(str).setOnly(str2);
    }

    public void setMaximumSizeEstimate(String str) {
        findAttributeValues(LinkFormat.MAX_SIZE_ESTIMATE).setOnly(str);
    }

    public void setObservable() {
        findAttributeValues(LinkFormat.OBSERVABLE).setOnly("");
    }

    public void setTitle(String str) {
        findAttributeValues("title").setOnly(str);
    }

    public void addAttribute(String str, String str2) {
        findAttributeValues(str).add(str2);
    }

    public void setMaximumSizeEstimate(int i2) {
        findAttributeValues(LinkFormat.MAX_SIZE_ESTIMATE).setOnly(Integer.toString(i2));
    }
}
