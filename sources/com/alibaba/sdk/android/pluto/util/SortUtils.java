package com.alibaba.sdk.android.pluto.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SortUtils {

    public static class SortInfo implements Serializable {
        private static final long serialVersionUID = 3959903664223165585L;
        public List<String> after;
        public Boolean afterAll;
        public List<String> before;
        public Boolean beforeAll;
        public String name;
    }

    private static void normalize(String str, String str2, Set<String> set, Map<String, Set<String>> map) {
        Set<String> set2 = map.get(str2);
        if (set2 == null || set2.contains(str)) {
            return;
        }
        for (String str3 : set2) {
            if (set.add(str3)) {
                normalize(str, str3, set, map);
            }
        }
    }

    public static void sorts(SortInfo[] sortInfoArr) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int i2 = 0;
        for (SortInfo sortInfo : sortInfoArr) {
            List<String> list = sortInfo.before;
            if (list != null) {
                for (String str : list) {
                    Set hashSet = (Set) map.get(str);
                    if (hashSet == null) {
                        hashSet = new HashSet();
                        map.put(str, hashSet);
                    }
                    hashSet.add(sortInfo.name);
                }
            }
            Set hashSet2 = (Set) map.get(sortInfo.name);
            if (hashSet2 == null) {
                hashSet2 = new HashSet();
                map.put(sortInfo.name, hashSet2);
            }
            List<String> list2 = sortInfo.after;
            if (list2 != null) {
                hashSet2.addAll(list2);
            }
            map2.put(sortInfo.name, sortInfo);
        }
        HashSet hashSet3 = new HashSet();
        for (SortInfo sortInfo2 : sortInfoArr) {
            String str2 = sortInfo2.name;
            normalize(str2, str2, hashSet3, map);
            ((Set) map.get(sortInfo2.name)).addAll(hashSet3);
            hashSet3.clear();
        }
        HashSet hashSet4 = new HashSet();
        HashSet hashSet5 = new HashSet();
        for (SortInfo sortInfo3 : sortInfoArr) {
            String str3 = sortInfo3.name;
            Boolean bool = sortInfo3.beforeAll;
            if (bool == null || !bool.booleanValue()) {
                Boolean bool2 = sortInfo3.afterAll;
                if (bool2 != null && bool2.booleanValue()) {
                    hashSet5.add(str3);
                    for (Map.Entry entry : map.entrySet()) {
                        if (((Set) entry.getValue()).contains(str3)) {
                            hashSet5.add(entry.getKey());
                        }
                    }
                }
            } else {
                hashSet4.add(str3);
                hashSet4.addAll((Set) map.get(str3));
            }
        }
        for (Map.Entry entry2 : map.entrySet()) {
            if (!hashSet4.contains(entry2.getKey())) {
                ((Set) entry2.getValue()).addAll(hashSet4);
            }
        }
        HashSet hashSet6 = new HashSet(map.keySet());
        hashSet6.removeAll(hashSet5);
        Iterator it = hashSet5.iterator();
        while (it.hasNext()) {
            ((Set) map.get((String) it.next())).addAll(hashSet6);
        }
        HashSet hashSet7 = new HashSet();
        HashSet hashSet8 = new HashSet();
        ArrayList arrayList = new ArrayList(sortInfoArr.length);
        while (map.size() > 0) {
            Iterator it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry3 = (Map.Entry) it2.next();
                ((Set) entry3.getValue()).removeAll(hashSet8);
                ((Set) entry3.getValue()).retainAll(map.keySet());
                if (((Set) entry3.getValue()).size() == 0) {
                    hashSet7.add(entry3.getKey());
                    arrayList.add(entry3.getKey());
                    it2.remove();
                }
            }
            if (hashSet7.size() == 0) {
                throw new IllegalStateException("Circular found for " + map);
            }
            hashSet8.clear();
            hashSet8.addAll(hashSet7);
            hashSet7.clear();
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            SortInfo sortInfo4 = (SortInfo) map2.get((String) it3.next());
            if (sortInfo4 != null) {
                sortInfoArr[i2] = sortInfo4;
                i2++;
            }
        }
    }
}
