package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
class ActivityChooserModel extends DataSetObservable {
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;

    /* renamed from: d, reason: collision with root package name */
    static final String f2219d = "ActivityChooserModel";

    /* renamed from: a, reason: collision with root package name */
    final Context f2220a;

    /* renamed from: b, reason: collision with root package name */
    final String f2221b;
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private Intent mIntent;
    private static final Object sRegistryLock = new Object();
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private final Object mInstanceLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private ActivitySorter mActivitySorter = new DefaultSorter();
    private int mHistoryMaxSize = 50;

    /* renamed from: c, reason: collision with root package name */
    boolean f2222c = true;
    private boolean mReadShareHistoryCalled = false;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && ActivityResolveInfo.class == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            return "[resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal(this.weight) + "]";
        }

        @Override // java.lang.Comparable
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    private static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        DefaultSorter() {
        }

        @Override // androidx.appcompat.widget.ActivityChooserModel.ActivitySorter
        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ActivityResolveInfo activityResolveInfo = list.get(i2);
                activityResolveInfo.weight = 0.0f;
                ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
                map.put(new ComponentName(activityInfo.packageName, activityInfo.name), activityResolveInfo);
            }
            float f2 = 1.0f;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f2;
                    f2 *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j2, float f2) {
            this(ComponentName.unflattenFromString(str), j2, f2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || HistoricalRecord.class != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int iHashCode = componentName == null ? 0 : componentName.hashCode();
            long j2 = this.time;
            return ((((iHashCode + 31) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal(this.weight) + "]";
        }

        public HistoricalRecord(ComponentName componentName, long j2, float f2) {
            this.activity = componentName;
            this.time = j2;
            this.weight = f2;
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Object... objArr) throws IOException {
            List list = (List) objArr[0];
            String str = (String) objArr[1];
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = ActivityChooserModel.this.f2220a.openFileOutput(str, 0);
                XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                try {
                    try {
                        try {
                            try {
                                xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                                xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                                xmlSerializerNewSerializer.startTag(null, "historical-records");
                                int size = list.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    HistoricalRecord historicalRecord = (HistoricalRecord) list.remove(0);
                                    xmlSerializerNewSerializer.startTag(null, "historical-record");
                                    xmlSerializerNewSerializer.attribute(null, PushConstants.INTENT_ACTIVITY_NAME, historicalRecord.activity.flattenToString());
                                    xmlSerializerNewSerializer.attribute(null, "time", String.valueOf(historicalRecord.time));
                                    xmlSerializerNewSerializer.attribute(null, "weight", String.valueOf(historicalRecord.weight));
                                    xmlSerializerNewSerializer.endTag(null, "historical-record");
                                }
                                xmlSerializerNewSerializer.endTag(null, "historical-records");
                                xmlSerializerNewSerializer.endDocument();
                                ActivityChooserModel.this.f2222c = true;
                            } catch (IllegalArgumentException e2) {
                                Log.e(ActivityChooserModel.f2219d, "Error writing historical record file: " + ActivityChooserModel.this.f2221b, e2);
                                ActivityChooserModel.this.f2222c = true;
                                if (fileOutputStreamOpenFileOutput != null) {
                                }
                            }
                        } catch (IllegalStateException e3) {
                            Log.e(ActivityChooserModel.f2219d, "Error writing historical record file: " + ActivityChooserModel.this.f2221b, e3);
                            ActivityChooserModel.this.f2222c = true;
                            if (fileOutputStreamOpenFileOutput != null) {
                            }
                        }
                    } catch (IOException e4) {
                        Log.e(ActivityChooserModel.f2219d, "Error writing historical record file: " + ActivityChooserModel.this.f2221b, e4);
                        ActivityChooserModel.this.f2222c = true;
                        if (fileOutputStreamOpenFileOutput != null) {
                        }
                    }
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    ActivityChooserModel.this.f2222c = true;
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                Log.e(ActivityChooserModel.f2219d, "Error writing historical record file: " + str, e5);
                return null;
            }
        }
    }

    private ActivityChooserModel(Context context, String str) {
        this.f2220a = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.f2221b = str;
            return;
        }
        this.f2221b = str + HISTORY_FILE_EXTENSION;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean zAdd = this.mHistoricalRecords.add(historicalRecord);
        if (zAdd) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return zAdd;
    }

    private void ensureConsistentState() {
        boolean zLoadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (zLoadActivitiesIfNeeded) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            try {
                Map<String, ActivityChooserModel> map = sDataModelRegistry;
                activityChooserModel = map.get(str);
                if (activityChooserModel == null) {
                    activityChooserModel = new ActivityChooserModel(context, str);
                    map.put(str, activityChooserModel);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return activityChooserModel;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> listQueryIntentActivities = this.f2220a.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = listQueryIntentActivities.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mActivities.add(new ActivityResolveInfo(listQueryIntentActivities.get(i2)));
        }
        return true;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (TextUtils.isEmpty(this.f2221b)) {
                return;
            }
            new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.f2221b);
        }
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        for (int i2 = 0; i2 < size; i2++) {
            this.mHistoricalRecords.remove(0);
        }
    }

    private boolean readHistoricalDataIfNeeded() throws IOException {
        if (!this.f2222c || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.f2221b)) {
            return false;
        }
        this.f2222c = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readHistoricalDataImpl() throws java.io.IOException {
        /*
            r10 = this;
            java.lang.String r0 = "Error reading historical recrod file: "
            android.content.Context r1 = r10.f2220a     // Catch: java.io.FileNotFoundException -> Lc5
            java.lang.String r2 = r10.f2221b     // Catch: java.io.FileNotFoundException -> Lc5
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch: java.io.FileNotFoundException -> Lc5
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r3 = "UTF-8"
            r2.setInput(r1, r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            r3 = 0
        L14:
            r4 = 1
            if (r3 == r4) goto L27
            r5 = 2
            if (r3 == r5) goto L27
            int r3 = r2.next()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            goto L14
        L1f:
            r0 = move-exception
            goto Lbf
        L22:
            r2 = move-exception
            goto L8c
        L24:
            r2 = move-exception
            goto La5
        L27:
            java.lang.String r3 = "historical-records"
            java.lang.String r5 = r2.getName()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            boolean r3 = r3.equals(r5)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            if (r3 == 0) goto L84
            java.util.List<androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord> r3 = r10.mHistoricalRecords     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            r3.clear()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
        L38:
            int r5 = r2.next()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            if (r5 != r4) goto L45
            if (r1 == 0) goto Lbe
        L40:
            r1.close()     // Catch: java.io.IOException -> Lbe
            goto Lbe
        L45:
            r6 = 3
            if (r5 == r6) goto L38
            r6 = 4
            if (r5 != r6) goto L4c
            goto L38
        L4c:
            java.lang.String r5 = r2.getName()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r6 = "historical-record"
            boolean r5 = r6.equals(r5)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            if (r5 == 0) goto L7c
            java.lang.String r5 = "activity"
            r6 = 0
            java.lang.String r5 = r2.getAttributeValue(r6, r5)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r7 = "time"
            java.lang.String r7 = r2.getAttributeValue(r6, r7)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            long r7 = java.lang.Long.parseLong(r7)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r9 = "weight"
            java.lang.String r6 = r2.getAttributeValue(r6, r9)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            float r6 = java.lang.Float.parseFloat(r6)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord r9 = new androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            r9.<init>(r5, r7, r6)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            r3.add(r9)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            goto L38
        L7c:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r3 = "Share records file not well-formed."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            throw r2     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
        L84:
            org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            java.lang.String r3 = "Share records file does not start with historical-records tag."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
            throw r2     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22 org.xmlpull.v1.XmlPullParserException -> L24
        L8c:
            java.lang.String r3 = androidx.appcompat.widget.ActivityChooserModel.f2219d     // Catch: java.lang.Throwable -> L1f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
            r4.<init>()     // Catch: java.lang.Throwable -> L1f
            r4.append(r0)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r0 = r10.f2221b     // Catch: java.lang.Throwable -> L1f
            r4.append(r0)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L1f
            android.util.Log.e(r3, r0, r2)     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto Lbe
            goto L40
        La5:
            java.lang.String r3 = androidx.appcompat.widget.ActivityChooserModel.f2219d     // Catch: java.lang.Throwable -> L1f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
            r4.<init>()     // Catch: java.lang.Throwable -> L1f
            r4.append(r0)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r0 = r10.f2221b     // Catch: java.lang.Throwable -> L1f
            r4.append(r0)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L1f
            android.util.Log.e(r3, r0, r2)     // Catch: java.lang.Throwable -> L1f
            if (r1 == 0) goto Lbe
            goto L40
        Lbe:
            return
        Lbf:
            if (r1 == 0) goto Lc4
            r1.close()     // Catch: java.io.IOException -> Lc4
        Lc4:
            throw r0
        Lc5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    public Intent chooseActivity(int i2) {
        synchronized (this.mInstanceLock) {
            try {
                if (this.mIntent == null) {
                    return null;
                }
                ensureConsistentState();
                ActivityInfo activityInfo = this.mActivities.get(i2).resolveInfo.activityInfo;
                ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.name);
                Intent intent = new Intent(this.mIntent);
                intent.setComponent(componentName);
                if (this.mActivityChoserModelPolicy != null) {
                    if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(intent))) {
                        return null;
                    }
                }
                addHistoricalRecord(new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f));
                return intent;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ResolveInfo getActivity(int i2) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(i2).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            try {
                ensureConsistentState();
                List<ActivityResolveInfo> list = this.mActivities;
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (list.get(i2).resolveInfo == resolveInfo) {
                        return i2;
                    }
                }
                return -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            try {
                ensureConsistentState();
                if (this.mActivities.isEmpty()) {
                    return null;
                }
                return this.mActivities.get(0).resolveInfo;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getHistoryMaxSize() {
        int i2;
        synchronized (this.mInstanceLock) {
            i2 = this.mHistoryMaxSize;
        }
        return i2;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public void setActivitySorter(ActivitySorter activitySorter) {
        synchronized (this.mInstanceLock) {
            try {
                if (this.mActivitySorter == activitySorter) {
                    return;
                }
                this.mActivitySorter = activitySorter;
                if (sortActivitiesIfNeeded()) {
                    notifyChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setDefaultActivity(int i2) {
        synchronized (this.mInstanceLock) {
            try {
                ensureConsistentState();
                ActivityResolveInfo activityResolveInfo = this.mActivities.get(i2);
                ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
                float f2 = activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : 1.0f;
                ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
                addHistoricalRecord(new HistoricalRecord(new ComponentName(activityInfo.packageName, activityInfo.name), System.currentTimeMillis(), f2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setHistoryMaxSize(int i2) {
        synchronized (this.mInstanceLock) {
            try {
                if (this.mHistoryMaxSize == i2) {
                    return;
                }
                this.mHistoryMaxSize = i2;
                pruneExcessiveHistoricalRecordsIfNeeded();
                if (sortActivitiesIfNeeded()) {
                    notifyChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            try {
                if (this.mIntent == intent) {
                    return;
                }
                this.mIntent = intent;
                this.mReloadActivities = true;
                ensureConsistentState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }
}
