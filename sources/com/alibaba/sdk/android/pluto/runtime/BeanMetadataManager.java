package com.alibaba.sdk.android.pluto.runtime;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.pluto.PlutoConstants;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alibaba.sdk.android.pluto.annotation.BeanProperty;
import com.alibaba.sdk.android.pluto.annotation.Qualifier;
import com.alibaba.sdk.android.pluto.meta.BeanInfo;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;
import com.alibaba.sdk.android.pluto.util.SortUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class BeanMetadataManager {
    private String[] systemPackagePrefixes = {"android.", "java."};

    public static class BeanRuntimeInfo extends SortUtils.SortInfo {
        public BeanInfo beanInfo;
        public Method initMethod;
        public List<Field> injectFields;
    }

    private String getBeanImplDescriptionInfo(List<BeanRuntimeInfo> list) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (BeanRuntimeInfo beanRuntimeInfo : list) {
            sb.append(i2);
            sb.append(" : ");
            BeanInfo beanInfo = beanRuntimeInfo.beanInfo;
            Class<?> cls = beanInfo.implType;
            if (cls != null) {
                sb.append(cls);
            } else {
                Object obj = beanInfo.instance;
                if (obj != null) {
                    sb.append(obj);
                } else {
                    sb.append("null implType and instance");
                }
            }
            sb.append("\n");
            i2++;
        }
        return sb.toString();
    }

    public BeanRuntimeInfo[] buildBeanRuntimeInfos(Set<ModuleInfo> set) {
        HashMap map;
        Iterator it;
        Iterator<Field> it2;
        BeanProperty[] beanPropertyArr;
        Iterator<ModuleInfo> it3;
        List arrayList;
        ArrayList arrayList2 = new ArrayList();
        HashMap map2 = new HashMap();
        Iterator<ModuleInfo> it4 = set.iterator();
        int i2 = 0;
        while (it4.hasNext()) {
            ModuleInfo next = it4.next();
            if (next.beanInfos != null) {
                Map<String, String> mapSingletonMap = Collections.singletonMap("module", next.name);
                for (BeanInfo beanInfo : next.beanInfos) {
                    if (beanInfo.properties == null) {
                        beanInfo.properties = mapSingletonMap;
                    } else {
                        HashMap map3 = new HashMap(beanInfo.properties);
                        beanInfo.properties = map3;
                        map3.put("module", next.name);
                    }
                    BeanRuntimeInfo beanRuntimeInfo = parse(beanInfo);
                    Class<?> cls = beanInfo.implType;
                    if (cls == null) {
                        cls = beanInfo.instance.getClass();
                    }
                    String simpleName = cls.getSimpleName();
                    StringBuilder sb = new StringBuilder();
                    sb.append(simpleName);
                    sb.append(".");
                    int i3 = i2 + 1;
                    sb.append(i2);
                    beanRuntimeInfo.name = sb.toString();
                    arrayList2.add(beanRuntimeInfo);
                    Class<?>[] clsArr = beanInfo.types;
                    int length = clsArr.length;
                    int i4 = 0;
                    while (i4 < length) {
                        Class<?> cls2 = clsArr[i4];
                        List list = (List) map2.get(cls2);
                        if (list == null) {
                            it3 = it4;
                            arrayList = new ArrayList(2);
                            map2.put(cls2, arrayList);
                        } else {
                            it3 = it4;
                            arrayList = list;
                        }
                        arrayList.add(beanRuntimeInfo);
                        i4++;
                        it4 = it3;
                    }
                    i2 = i3;
                }
            }
        }
        Iterator it5 = arrayList2.iterator();
        while (it5.hasNext()) {
            BeanRuntimeInfo beanRuntimeInfo2 = (BeanRuntimeInfo) it5.next();
            if (beanRuntimeInfo2.injectFields == null) {
                beanRuntimeInfo2.after = Collections.emptyList();
            } else {
                beanRuntimeInfo2.after = new ArrayList(5);
            }
            Iterator<Field> it6 = beanRuntimeInfo2.injectFields.iterator();
            while (it6.hasNext()) {
                Field next2 = it6.next();
                Autowired autowired = (Autowired) next2.getAnnotation(Autowired.class);
                List<BeanRuntimeInfo> list2 = (List) map2.get(next2.getType());
                if (list2 == null) {
                    String str = "fail to find matched bean for " + next2.getDeclaringClass().getName() + "." + next2.getName();
                    Log.e(PlutoConstants.PLUTO_LOG_TAG, str);
                    throw new IllegalStateException(str);
                }
                Qualifier qualifier = (Qualifier) next2.getAnnotation(Qualifier.class);
                if (qualifier == null || qualifier.filters() == null || qualifier.filters().length == 0) {
                    map = map2;
                    it = it5;
                    it2 = it6;
                    if (list2.size() > 1) {
                        String str2 = "more then one matched bean for " + next2.getDeclaringClass().getName() + "." + next2.getName() + " matched beans " + getBeanImplDescriptionInfo(list2);
                        Log.e(PlutoConstants.PLUTO_LOG_TAG, str2);
                        throw new IllegalStateException(str2);
                    }
                    beanRuntimeInfo2.after.add(list2.get(0).name);
                } else {
                    BeanProperty[] beanPropertyArrFilters = qualifier.filters();
                    map = map2;
                    ArrayList arrayList3 = new ArrayList(list2.size());
                    Iterator<BeanRuntimeInfo> it7 = list2.iterator();
                    while (it7.hasNext()) {
                        Iterator<BeanRuntimeInfo> it8 = it7;
                        BeanRuntimeInfo next3 = it7.next();
                        Iterator it9 = it5;
                        int length2 = beanPropertyArrFilters.length;
                        Iterator<Field> it10 = it6;
                        int i5 = 0;
                        while (true) {
                            if (i5 >= length2) {
                                beanPropertyArr = beanPropertyArrFilters;
                                arrayList3.add(next3);
                                break;
                            }
                            BeanProperty beanProperty = beanPropertyArrFilters[i5];
                            int i6 = length2;
                            beanPropertyArr = beanPropertyArrFilters;
                            String str3 = next3.beanInfo.properties.get(beanProperty.key());
                            if (str3 == null || !str3.equals(beanProperty.value())) {
                                break;
                            }
                            i5++;
                            length2 = i6;
                            beanPropertyArrFilters = beanPropertyArr;
                        }
                        it5 = it9;
                        it7 = it8;
                        it6 = it10;
                        beanPropertyArrFilters = beanPropertyArr;
                    }
                    it = it5;
                    it2 = it6;
                    if (arrayList3.size() == 1) {
                        beanRuntimeInfo2.after.add(((BeanRuntimeInfo) arrayList3.get(0)).name);
                    } else {
                        if (arrayList3.size() == 0 && autowired.required()) {
                            String str4 = "fail to find matched bean for " + next2.getDeclaringClass().getName() + "." + next2.getName();
                            Log.e(PlutoConstants.PLUTO_LOG_TAG, str4);
                            throw new IllegalStateException(str4);
                        }
                        if (arrayList3.size() > 1) {
                            String str5 = "more then one matched bean for " + next2.getDeclaringClass().getName() + "." + next2.getName() + " matched beans " + getBeanImplDescriptionInfo(list2);
                            Log.e(PlutoConstants.PLUTO_LOG_TAG, str5);
                            throw new IllegalStateException(str5);
                        }
                    }
                }
                map2 = map;
                it5 = it;
                it6 = it2;
            }
        }
        BeanRuntimeInfo[] beanRuntimeInfoArr = (BeanRuntimeInfo[]) arrayList2.toArray(new BeanRuntimeInfo[0]);
        SortUtils.sorts(beanRuntimeInfoArr);
        return beanRuntimeInfoArr;
    }

    public List<Field> getInjectionFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList(3);
        while (!isSystemClass(cls)) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    arrayList.add(field);
                }
            }
            cls = cls.getSuperclass();
        }
        return arrayList;
    }

    protected boolean isSystemClass(Class<?> cls) {
        if (cls == null || cls == Object.class) {
            return true;
        }
        String name = cls.getName();
        for (String str : this.systemPackagePrefixes) {
            if (name.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    protected BeanRuntimeInfo parse(BeanInfo beanInfo) {
        BeanRuntimeInfo beanRuntimeInfo = new BeanRuntimeInfo();
        beanRuntimeInfo.beanInfo = beanInfo;
        Class<?> cls = beanInfo.implType;
        if (cls == null && beanInfo.instance == null) {
            throw new IllegalStateException("implType and instance could not be null at the same time for bean " + beanInfo);
        }
        if (cls == null) {
            cls = beanInfo.instance.getClass();
        }
        beanRuntimeInfo.injectFields = getInjectionFields(cls);
        String str = beanInfo.initMethod;
        if (str != null) {
            try {
                try {
                    beanRuntimeInfo.initMethod = cls.getMethod(str, Context.class);
                } catch (Exception e2) {
                    throw new IllegalStateException("invalid init method for bean " + beanInfo, e2);
                }
            } catch (Exception unused) {
                beanRuntimeInfo.initMethod = cls.getMethod(beanInfo.initMethod, null);
            }
        }
        return beanRuntimeInfo;
    }

    public void setSystemPackagePrefixes(String[] strArr) {
        if (strArr == null) {
            return;
        }
        String[] strArr2 = new String[strArr.length];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        this.systemPackagePrefixes = strArr2;
    }
}
