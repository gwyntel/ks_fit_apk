package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

/* loaded from: classes2.dex */
public class JSONArray extends JSON implements List<Object>, Cloneable, RandomAccess, Serializable {
    private static final long serialVersionUID = 1;
    protected transient Type componentType;
    private final List<Object> list;
    protected transient Object relatedArray;

    public JSONArray() {
        this.list = new ArrayList();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        JSONObject.SecureObjectInputStream.ensureFields();
        if (JSONObject.SecureObjectInputStream.fields != null && !JSONObject.SecureObjectInputStream.fields_error) {
            try {
                new JSONObject.SecureObjectInputStream(objectInputStream).defaultReadObject();
                return;
            } catch (NotActiveException unused) {
            }
        }
        objectInputStream.defaultReadObject();
        for (Object obj : this.list) {
            if (obj != null) {
                String name = obj.getClass().getName();
                if (TypeUtils.getClassFromMapping(name) == null) {
                    ParserConfig.global.checkAutoType(name, null);
                }
            }
        }
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        return this.list.add(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<?> collection) {
        return this.list.addAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
    }

    public Object clone() {
        return new JSONArray(new ArrayList(this.list));
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.list.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.list.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof JSONArray ? this.list.equals(((JSONArray) obj).list) : this.list.equals(obj);
    }

    public JSONArray fluentAdd(Object obj) {
        this.list.add(obj);
        return this;
    }

    public JSONArray fluentAddAll(Collection<?> collection) {
        this.list.addAll(collection);
        return this;
    }

    public JSONArray fluentClear() {
        this.list.clear();
        return this;
    }

    public JSONArray fluentRemove(Object obj) {
        this.list.remove(obj);
        return this;
    }

    public JSONArray fluentRemoveAll(Collection<?> collection) {
        this.list.removeAll(collection);
        return this;
    }

    public JSONArray fluentRetainAll(Collection<?> collection) {
        this.list.retainAll(collection);
        return this;
    }

    public JSONArray fluentSet(int i2, Object obj) {
        set(i2, obj);
        return this;
    }

    @Override // java.util.List
    public Object get(int i2) {
        return this.list.get(i2);
    }

    public BigDecimal getBigDecimal(int i2) {
        return TypeUtils.castToBigDecimal(get(i2));
    }

    public BigInteger getBigInteger(int i2) {
        return TypeUtils.castToBigInteger(get(i2));
    }

    public Boolean getBoolean(int i2) {
        Object obj = get(i2);
        if (obj == null) {
            return null;
        }
        return TypeUtils.castToBoolean(obj);
    }

    public boolean getBooleanValue(int i2) {
        Object obj = get(i2);
        if (obj == null) {
            return false;
        }
        return TypeUtils.castToBoolean(obj).booleanValue();
    }

    public Byte getByte(int i2) {
        return TypeUtils.castToByte(get(i2));
    }

    public byte getByteValue(int i2) {
        Byte bCastToByte = TypeUtils.castToByte(get(i2));
        if (bCastToByte == null) {
            return (byte) 0;
        }
        return bCastToByte.byteValue();
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public Date getDate(int i2) {
        return TypeUtils.castToDate(get(i2));
    }

    public Double getDouble(int i2) {
        return TypeUtils.castToDouble(get(i2));
    }

    public double getDoubleValue(int i2) {
        Double dCastToDouble = TypeUtils.castToDouble(get(i2));
        if (dCastToDouble == null) {
            return 0.0d;
        }
        return dCastToDouble.doubleValue();
    }

    public Float getFloat(int i2) {
        return TypeUtils.castToFloat(get(i2));
    }

    public float getFloatValue(int i2) {
        Float fCastToFloat = TypeUtils.castToFloat(get(i2));
        if (fCastToFloat == null) {
            return 0.0f;
        }
        return fCastToFloat.floatValue();
    }

    public int getIntValue(int i2) {
        Integer numCastToInt = TypeUtils.castToInt(get(i2));
        if (numCastToInt == null) {
            return 0;
        }
        return numCastToInt.intValue();
    }

    public Integer getInteger(int i2) {
        return TypeUtils.castToInt(get(i2));
    }

    public JSONArray getJSONArray(int i2) {
        Object obj = this.list.get(i2);
        return obj instanceof JSONArray ? (JSONArray) obj : obj instanceof List ? new JSONArray((List<Object>) obj) : (JSONArray) JSON.toJSON(obj);
    }

    public JSONObject getJSONObject(int i2) {
        Object obj = this.list.get(i2);
        return obj instanceof JSONObject ? (JSONObject) obj : obj instanceof Map ? new JSONObject((Map<String, Object>) obj) : (JSONObject) JSON.toJSON(obj);
    }

    public Long getLong(int i2) {
        return TypeUtils.castToLong(get(i2));
    }

    public long getLongValue(int i2) {
        Long lCastToLong = TypeUtils.castToLong(get(i2));
        if (lCastToLong == null) {
            return 0L;
        }
        return lCastToLong.longValue();
    }

    public <T> T getObject(int i2, Class<T> cls) {
        return (T) TypeUtils.castToJavaBean(this.list.get(i2), cls);
    }

    public Object getRelatedArray() {
        return this.relatedArray;
    }

    public Short getShort(int i2) {
        return TypeUtils.castToShort(get(i2));
    }

    public short getShortValue(int i2) {
        Short shCastToShort = TypeUtils.castToShort(get(i2));
        if (shCastToShort == null) {
            return (short) 0;
        }
        return shCastToShort.shortValue();
    }

    public Object getSqlDate(int i2) {
        return TypeUtils.castToSqlDate(get(i2));
    }

    public String getString(int i2) {
        return TypeUtils.castToString(get(i2));
    }

    public Object getTimestamp(int i2) {
        return TypeUtils.castToTimestamp(get(i2));
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.list.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.list.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.list.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.list.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.list.retainAll(collection);
    }

    @Override // java.util.List
    public Object set(int i2, Object obj) {
        if (i2 == -1) {
            this.list.add(obj);
            return null;
        }
        if (this.list.size() > i2) {
            return this.list.set(i2, obj);
        }
        for (int size = this.list.size(); size < i2; size++) {
            this.list.add(null);
        }
        this.list.add(obj);
        return null;
    }

    public void setComponentType(Type type) {
        this.componentType = type;
    }

    public void setRelatedArray(Object obj) {
        this.relatedArray = obj;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.list.size();
    }

    @Override // java.util.List
    public List<Object> subList(int i2, int i3) {
        return this.list.subList(i2, i3);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    public <T> List<T> toJavaList(Class<T> cls) {
        ArrayList arrayList = new ArrayList(size());
        ParserConfig globalInstance = ParserConfig.getGlobalInstance();
        Iterator<Object> it = iterator();
        while (it.hasNext()) {
            arrayList.add(TypeUtils.cast(it.next(), (Class) cls, globalInstance));
        }
        return arrayList;
    }

    @Override // java.util.List
    public void add(int i2, Object obj) {
        this.list.add(i2, obj);
    }

    @Override // java.util.List
    public boolean addAll(int i2, Collection<? extends Object> collection) {
        return this.list.addAll(i2, collection);
    }

    public JSONArray fluentAdd(int i2, Object obj) {
        this.list.add(i2, obj);
        return this;
    }

    public JSONArray fluentAddAll(int i2, Collection<?> collection) {
        this.list.addAll(i2, collection);
        return this;
    }

    public JSONArray fluentRemove(int i2) {
        this.list.remove(i2);
        return this;
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator(int i2) {
        return this.list.listIterator(i2);
    }

    @Override // java.util.List
    public Object remove(int i2) {
        return this.list.remove(i2);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.list.toArray(tArr);
    }

    public JSONArray(List<Object> list) {
        if (list != null) {
            this.list = list;
            return;
        }
        throw new IllegalArgumentException("list is null.");
    }

    public <T> T getObject(int i2, Type type) {
        Object obj = this.list.get(i2);
        if (type instanceof Class) {
            return (T) TypeUtils.castToJavaBean(obj, (Class) type);
        }
        return (T) JSON.parseObject(JSON.toJSONString(obj), type, new Feature[0]);
    }

    public JSONArray(int i2) {
        this.list = new ArrayList(i2);
    }
}
