package com.facebook.appevents;

import android.content.Context;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.Utility;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/* loaded from: classes3.dex */
class AppEventStore {
    private static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
    private static final String TAG = "com.facebook.appevents.AppEventStore";

    private static class MovedClassObjectInputStream extends ObjectInputStream {
        private static final String ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair$SerializationProxyV1";
        private static final String APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME = "com.facebook.appevents.AppEventsLogger$AppEvent$SerializationProxyV1";

        public MovedClassObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override // java.io.ObjectInputStream
        protected ObjectStreamClass readClassDescriptor() throws ClassNotFoundException, IOException {
            ObjectStreamClass classDescriptor = super.readClassDescriptor();
            return classDescriptor.getName().equals(ACCESS_TOKEN_APP_ID_PAIR_SERIALIZATION_PROXY_V1_CLASS_NAME) ? ObjectStreamClass.lookup(AccessTokenAppIdPair.SerializationProxyV1.class) : classDescriptor.getName().equals(APP_EVENT_SERIALIZATION_PROXY_V1_CLASS_NAME) ? ObjectStreamClass.lookup(AppEvent.SerializationProxyV1.class) : classDescriptor;
        }
    }

    AppEventStore() {
    }

    public static synchronized void persistEvents(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
        try {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents andClearStore = readAndClearStore();
            if (andClearStore.containsKey(accessTokenAppIdPair)) {
                andClearStore.get(accessTokenAppIdPair).addAll(sessionEventsState.getEventsToPersist());
            } else {
                andClearStore.addEvents(accessTokenAppIdPair, sessionEventsState.getEventsToPersist());
            }
            saveEventsToDisk(andClearStore);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0090 A[Catch: all -> 0x002e, TRY_LEAVE, TryCatch #7 {, blocks: (B:4:0x0003, B:8:0x0021, B:9:0x0024, B:44:0x0090, B:14:0x0032, B:32:0x0065, B:33:0x0068, B:37:0x007a, B:36:0x0073, B:26:0x004f, B:27:0x0052, B:30:0x005d, B:31:0x0061, B:38:0x007b, B:39:0x007e, B:42:0x0089), top: B:59:0x0003, inners: #1, #2, #6, #8 }] */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.lang.Exception, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r4v13, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized com.facebook.appevents.PersistedEvents readAndClearStore() {
        /*
            java.lang.Class<com.facebook.appevents.AppEventStore> r0 = com.facebook.appevents.AppEventStore.class
            monitor-enter(r0)
            com.facebook.appevents.internal.AppEventUtility.assertIsNotMainThread()     // Catch: java.lang.Throwable -> L2e
            android.content.Context r1 = com.facebook.FacebookSdk.getApplicationContext()     // Catch: java.lang.Throwable -> L2e
            r2 = 0
            java.lang.String r3 = "AppEventsLogger.persistedevents"
            java.io.FileInputStream r3 = r1.openFileInput(r3)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43 java.io.FileNotFoundException -> L46
            com.facebook.appevents.AppEventStore$MovedClassObjectInputStream r4 = new com.facebook.appevents.AppEventStore$MovedClassObjectInputStream     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43 java.io.FileNotFoundException -> L46
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43 java.io.FileNotFoundException -> L46
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43 java.io.FileNotFoundException -> L46
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43 java.io.FileNotFoundException -> L46
            java.lang.Object r3 = r4.readObject()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d java.io.FileNotFoundException -> L7b
            com.facebook.appevents.PersistedEvents r3 = (com.facebook.appevents.PersistedEvents) r3     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d java.io.FileNotFoundException -> L7b
            com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r2 = "AppEventsLogger.persistedevents"
            java.io.File r1 = r1.getFileStreamPath(r2)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L31
            r1.delete()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L31
            goto L39
        L2e:
            r1 = move-exception
            goto L97
        L31:
            r1 = move-exception
            java.lang.String r2 = com.facebook.appevents.AppEventStore.TAG     // Catch: java.lang.Throwable -> L2e
            java.lang.String r4 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r2, r4, r1)     // Catch: java.lang.Throwable -> L2e
        L39:
            r2 = r3
            goto L8e
        L3b:
            r2 = move-exception
            goto L65
        L3d:
            r3 = move-exception
            goto L48
        L3f:
            r3 = move-exception
            r4 = r2
            r2 = r3
            goto L65
        L43:
            r3 = move-exception
            r4 = r2
            goto L48
        L46:
            r4 = r2
            goto L7b
        L48:
            java.lang.String r5 = com.facebook.appevents.AppEventStore.TAG     // Catch: java.lang.Throwable -> L3b
            java.lang.String r6 = "Got unexpected exception while reading events: "
            android.util.Log.w(r5, r6, r3)     // Catch: java.lang.Throwable -> L3b
            com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r3 = "AppEventsLogger.persistedevents"
            java.io.File r1 = r1.getFileStreamPath(r3)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L5c
            r1.delete()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L5c
            goto L8e
        L5c:
            r1 = move-exception
            java.lang.String r3 = com.facebook.appevents.AppEventStore.TAG     // Catch: java.lang.Throwable -> L2e
            java.lang.String r4 = "Got unexpected exception when removing events file: "
        L61:
            android.util.Log.w(r3, r4, r1)     // Catch: java.lang.Throwable -> L2e
            goto L8e
        L65:
            com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r3 = "AppEventsLogger.persistedevents"
            java.io.File r1 = r1.getFileStreamPath(r3)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L72
            r1.delete()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L72
            goto L7a
        L72:
            r1 = move-exception
            java.lang.String r3 = com.facebook.appevents.AppEventStore.TAG     // Catch: java.lang.Throwable -> L2e
            java.lang.String r4 = "Got unexpected exception when removing events file: "
            android.util.Log.w(r3, r4, r1)     // Catch: java.lang.Throwable -> L2e
        L7a:
            throw r2     // Catch: java.lang.Throwable -> L2e
        L7b:
            com.facebook.internal.Utility.closeQuietly(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r3 = "AppEventsLogger.persistedevents"
            java.io.File r1 = r1.getFileStreamPath(r3)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L88
            r1.delete()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L88
            goto L8e
        L88:
            r1 = move-exception
            java.lang.String r3 = com.facebook.appevents.AppEventStore.TAG     // Catch: java.lang.Throwable -> L2e
            java.lang.String r4 = "Got unexpected exception when removing events file: "
            goto L61
        L8e:
            if (r2 != 0) goto L95
            com.facebook.appevents.PersistedEvents r2 = new com.facebook.appevents.PersistedEvents     // Catch: java.lang.Throwable -> L2e
            r2.<init>()     // Catch: java.lang.Throwable -> L2e
        L95:
            monitor-exit(r0)
            return r2
        L97:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L2e
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventStore.readAndClearStore():com.facebook.appevents.PersistedEvents");
    }

    private static void saveEventsToDisk(PersistedEvents persistedEvents) throws Throwable {
        Context applicationContext = FacebookSdk.getApplicationContext();
        ObjectOutputStream objectOutputStream = null;
        try {
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new BufferedOutputStream(applicationContext.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
                try {
                    objectOutputStream2.writeObject(persistedEvents);
                    Utility.closeQuietly(objectOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    objectOutputStream = objectOutputStream2;
                    Log.w(TAG, "Got unexpected exception while persisting events: ", e);
                    try {
                        applicationContext.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                    } catch (Exception unused) {
                    }
                    Utility.closeQuietly(objectOutputStream);
                } catch (Throwable th) {
                    th = th;
                    objectOutputStream = objectOutputStream2;
                    Utility.closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static synchronized void persistEvents(AppEventCollection appEventCollection) {
        try {
            AppEventUtility.assertIsNotMainThread();
            PersistedEvents andClearStore = readAndClearStore();
            for (AccessTokenAppIdPair accessTokenAppIdPair : appEventCollection.keySet()) {
                andClearStore.addEvents(accessTokenAppIdPair, appEventCollection.get(accessTokenAppIdPair).getEventsToPersist());
            }
            saveEventsToDisk(andClearStore);
        } catch (Throwable th) {
            throw th;
        }
    }
}
