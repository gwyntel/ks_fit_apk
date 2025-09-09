package androidx.media3.exoplayer.dash;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.dash.manifest.BaseUrl;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

@UnstableApi
/* loaded from: classes.dex */
public final class BaseUrlExclusionList {
    private final Map<Integer, Long> excludedPriorities;
    private final Map<String, Long> excludedServiceLocations;
    private final Random random;
    private final Map<List<Pair<String, Integer>>, BaseUrl> selectionsTaken;

    public BaseUrlExclusionList() {
        this(new Random());
    }

    private static <T> void addExclusion(T t2, long j2, Map<T, Long> map) {
        if (map.containsKey(t2)) {
            j2 = Math.max(j2, ((Long) Util.castNonNull(map.get(t2))).longValue());
        }
        map.put(t2, Long.valueOf(j2));
    }

    private List<BaseUrl> applyExclusions(List<BaseUrl> list) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        removeExpiredExclusions(jElapsedRealtime, this.excludedServiceLocations);
        removeExpiredExclusions(jElapsedRealtime, this.excludedPriorities);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            BaseUrl baseUrl = list.get(i2);
            if (!this.excludedServiceLocations.containsKey(baseUrl.serviceLocation) && !this.excludedPriorities.containsKey(Integer.valueOf(baseUrl.priority))) {
                arrayList.add(baseUrl);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareBaseUrl(BaseUrl baseUrl, BaseUrl baseUrl2) {
        int iCompare = Integer.compare(baseUrl.priority, baseUrl2.priority);
        return iCompare != 0 ? iCompare : baseUrl.serviceLocation.compareTo(baseUrl2.serviceLocation);
    }

    public static int getPriorityCount(List<BaseUrl> list) {
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < list.size(); i2++) {
            hashSet.add(Integer.valueOf(list.get(i2).priority));
        }
        return hashSet.size();
    }

    private static <T> void removeExpiredExclusions(long j2, Map<T, Long> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<T, Long> entry : map.entrySet()) {
            if (entry.getValue().longValue() <= j2) {
                arrayList.add(entry.getKey());
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            map.remove(arrayList.get(i2));
        }
    }

    private BaseUrl selectWeighted(List<BaseUrl> list) {
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            i2 += list.get(i3).weight;
        }
        int iNextInt = this.random.nextInt(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < list.size(); i5++) {
            BaseUrl baseUrl = list.get(i5);
            i4 += baseUrl.weight;
            if (iNextInt < i4) {
                return baseUrl;
            }
        }
        return (BaseUrl) Iterables.getLast(list);
    }

    public void exclude(BaseUrl baseUrl, long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime() + j2;
        addExclusion(baseUrl.serviceLocation, jElapsedRealtime, this.excludedServiceLocations);
        int i2 = baseUrl.priority;
        if (i2 != Integer.MIN_VALUE) {
            addExclusion(Integer.valueOf(i2), jElapsedRealtime, this.excludedPriorities);
        }
    }

    public int getPriorityCountAfterExclusion(List<BaseUrl> list) {
        HashSet hashSet = new HashSet();
        List<BaseUrl> listApplyExclusions = applyExclusions(list);
        for (int i2 = 0; i2 < listApplyExclusions.size(); i2++) {
            hashSet.add(Integer.valueOf(listApplyExclusions.get(i2).priority));
        }
        return hashSet.size();
    }

    public void reset() {
        this.excludedServiceLocations.clear();
        this.excludedPriorities.clear();
        this.selectionsTaken.clear();
    }

    @Nullable
    public BaseUrl selectBaseUrl(List<BaseUrl> list) {
        List<BaseUrl> listApplyExclusions = applyExclusions(list);
        if (listApplyExclusions.size() < 2) {
            return (BaseUrl) Iterables.getFirst(listApplyExclusions, null);
        }
        Collections.sort(listApplyExclusions, new Comparator() { // from class: androidx.media3.exoplayer.dash.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BaseUrlExclusionList.compareBaseUrl((BaseUrl) obj, (BaseUrl) obj2);
            }
        });
        ArrayList arrayList = new ArrayList();
        int i2 = listApplyExclusions.get(0).priority;
        int i3 = 0;
        while (true) {
            if (i3 >= listApplyExclusions.size()) {
                break;
            }
            BaseUrl baseUrl = listApplyExclusions.get(i3);
            if (i2 == baseUrl.priority) {
                arrayList.add(new Pair(baseUrl.serviceLocation, Integer.valueOf(baseUrl.weight)));
                i3++;
            } else if (arrayList.size() == 1) {
                return listApplyExclusions.get(0);
            }
        }
        BaseUrl baseUrl2 = this.selectionsTaken.get(arrayList);
        if (baseUrl2 != null) {
            return baseUrl2;
        }
        BaseUrl baseUrlSelectWeighted = selectWeighted(listApplyExclusions.subList(0, arrayList.size()));
        this.selectionsTaken.put(arrayList, baseUrlSelectWeighted);
        return baseUrlSelectWeighted;
    }

    BaseUrlExclusionList(Random random) {
        this.selectionsTaken = new HashMap();
        this.random = random;
        this.excludedServiceLocations = new HashMap();
        this.excludedPriorities = new HashMap();
    }
}
