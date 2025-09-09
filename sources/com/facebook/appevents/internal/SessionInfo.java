package com.facebook.appevents.internal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;
import java.util.UUID;

/* loaded from: classes3.dex */
class SessionInfo {
    private static final String INTERRUPTION_COUNT_KEY = "com.facebook.appevents.SessionInfo.interruptionCount";
    private static final String LAST_SESSION_INFO_END_KEY = "com.facebook.appevents.SessionInfo.sessionEndTime";
    private static final String LAST_SESSION_INFO_START_KEY = "com.facebook.appevents.SessionInfo.sessionStartTime";
    private static final String SESSION_ID_KEY = "com.facebook.appevents.SessionInfo.sessionId";
    private Long diskRestoreTime;
    private int interruptionCount;
    private UUID sessionId;
    private Long sessionLastEventTime;
    private Long sessionStartTime;
    private SourceApplicationInfo sourceApplicationInfo;

    public SessionInfo(Long l2, Long l3) {
        this(l2, l3, UUID.randomUUID());
    }

    public static void clearSavedSessionFromDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.remove(LAST_SESSION_INFO_START_KEY);
        editorEdit.remove(LAST_SESSION_INFO_END_KEY);
        editorEdit.remove(INTERRUPTION_COUNT_KEY);
        editorEdit.remove(SESSION_ID_KEY);
        editorEdit.apply();
        SourceApplicationInfo.clearSavedSourceApplicationInfoFromDisk();
    }

    public static SessionInfo getStoredSessionInfo() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
        long j2 = defaultSharedPreferences.getLong(LAST_SESSION_INFO_START_KEY, 0L);
        long j3 = defaultSharedPreferences.getLong(LAST_SESSION_INFO_END_KEY, 0L);
        String string = defaultSharedPreferences.getString(SESSION_ID_KEY, null);
        if (j2 == 0 || j3 == 0 || string == null) {
            return null;
        }
        SessionInfo sessionInfo = new SessionInfo(Long.valueOf(j2), Long.valueOf(j3));
        sessionInfo.interruptionCount = defaultSharedPreferences.getInt(INTERRUPTION_COUNT_KEY, 0);
        sessionInfo.sourceApplicationInfo = SourceApplicationInfo.getStoredSourceApplicatioInfo();
        sessionInfo.diskRestoreTime = Long.valueOf(System.currentTimeMillis());
        sessionInfo.sessionId = UUID.fromString(string);
        return sessionInfo;
    }

    public long getDiskRestoreTime() {
        Long l2 = this.diskRestoreTime;
        if (l2 == null) {
            return 0L;
        }
        return l2.longValue();
    }

    public int getInterruptionCount() {
        return this.interruptionCount;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public Long getSessionLastEventTime() {
        return this.sessionLastEventTime;
    }

    public long getSessionLength() {
        Long l2;
        if (this.sessionStartTime == null || (l2 = this.sessionLastEventTime) == null) {
            return 0L;
        }
        return l2.longValue() - this.sessionStartTime.longValue();
    }

    public Long getSessionStartTime() {
        return this.sessionStartTime;
    }

    public SourceApplicationInfo getSourceApplicationInfo() {
        return this.sourceApplicationInfo;
    }

    public void incrementInterruptionCount() {
        this.interruptionCount++;
    }

    public void setSessionLastEventTime(Long l2) {
        this.sessionLastEventTime = l2;
    }

    public void setSourceApplicationInfo(SourceApplicationInfo sourceApplicationInfo) {
        this.sourceApplicationInfo = sourceApplicationInfo;
    }

    public void writeSessionToDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.putLong(LAST_SESSION_INFO_START_KEY, this.sessionStartTime.longValue());
        editorEdit.putLong(LAST_SESSION_INFO_END_KEY, this.sessionLastEventTime.longValue());
        editorEdit.putInt(INTERRUPTION_COUNT_KEY, this.interruptionCount);
        editorEdit.putString(SESSION_ID_KEY, this.sessionId.toString());
        editorEdit.apply();
        SourceApplicationInfo sourceApplicationInfo = this.sourceApplicationInfo;
        if (sourceApplicationInfo != null) {
            sourceApplicationInfo.writeSourceApplicationInfoToDisk();
        }
    }

    public SessionInfo(Long l2, Long l3, UUID uuid) {
        this.sessionStartTime = l2;
        this.sessionLastEventTime = l3;
        this.sessionId = uuid;
    }
}
