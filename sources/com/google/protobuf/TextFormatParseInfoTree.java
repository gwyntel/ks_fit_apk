package com.google.protobuf;

import com.google.protobuf.Descriptors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class TextFormatParseInfoTree {

    /* renamed from: a, reason: collision with root package name */
    Map f15346a;
    private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;

    public static class Builder {
        private Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> locationsFromField;
        private Map<Descriptors.FieldDescriptor, List<Builder>> subtreeBuildersFromField;

        public TextFormatParseInfoTree build() {
            return new TextFormatParseInfoTree(this.locationsFromField, this.subtreeBuildersFromField);
        }

        public Builder getBuilderForSubMessageField(Descriptors.FieldDescriptor fieldDescriptor) {
            List<Builder> arrayList = this.subtreeBuildersFromField.get(fieldDescriptor);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.subtreeBuildersFromField.put(fieldDescriptor, arrayList);
            }
            Builder builder = new Builder();
            arrayList.add(builder);
            return builder;
        }

        public Builder setLocation(Descriptors.FieldDescriptor fieldDescriptor, TextFormatParseLocation textFormatParseLocation) {
            List<TextFormatParseLocation> arrayList = this.locationsFromField.get(fieldDescriptor);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.locationsFromField.put(fieldDescriptor, arrayList);
            }
            arrayList.add(textFormatParseLocation);
            return this;
        }

        private Builder() {
            this.locationsFromField = new HashMap();
            this.subtreeBuildersFromField = new HashMap();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static <T> T getFromList(List<T> list, int i2, Descriptors.FieldDescriptor fieldDescriptor) {
        if (i2 >= list.size() || i2 < 0) {
            throw new IllegalArgumentException(String.format("Illegal index field: %s, index %d", fieldDescriptor == null ? "<null>" : fieldDescriptor.getName(), Integer.valueOf(i2)));
        }
        return list.get(i2);
    }

    public TextFormatParseLocation getLocation(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
        return (TextFormatParseLocation) getFromList(getLocations(fieldDescriptor), i2, fieldDescriptor);
    }

    public List<TextFormatParseLocation> getLocations(Descriptors.FieldDescriptor fieldDescriptor) {
        List<TextFormatParseLocation> list = this.locationsFromField.get(fieldDescriptor);
        return list == null ? Collections.emptyList() : list;
    }

    public TextFormatParseInfoTree getNestedTree(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
        return (TextFormatParseInfoTree) getFromList(getNestedTrees(fieldDescriptor), i2, fieldDescriptor);
    }

    public List<TextFormatParseInfoTree> getNestedTrees(Descriptors.FieldDescriptor fieldDescriptor) {
        List<TextFormatParseInfoTree> list = (List) this.f15346a.get(fieldDescriptor);
        return list == null ? Collections.emptyList() : list;
    }

    private TextFormatParseInfoTree(Map<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> map, Map<Descriptors.FieldDescriptor, List<Builder>> map2) {
        HashMap map3 = new HashMap();
        for (Map.Entry<Descriptors.FieldDescriptor, List<TextFormatParseLocation>> entry : map.entrySet()) {
            map3.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
        this.locationsFromField = Collections.unmodifiableMap(map3);
        HashMap map4 = new HashMap();
        for (Map.Entry<Descriptors.FieldDescriptor, List<Builder>> entry2 : map2.entrySet()) {
            ArrayList arrayList = new ArrayList();
            Iterator<Builder> it = entry2.getValue().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().build());
            }
            map4.put(entry2.getKey(), Collections.unmodifiableList(arrayList));
        }
        this.f15346a = Collections.unmodifiableMap(map4);
    }
}
