package com.heytap.msp.push.mode;

/* loaded from: classes3.dex */
public class NotificationSortMessage implements Comparable<NotificationSortMessage> {
    private int autoDelete;
    private String group;
    private int importantLevel;
    private boolean isMcs;
    private String messageId;
    private int notifyId;
    private long postTime;
    private String statisticData;

    public NotificationSortMessage(String str, int i2, int i3, boolean z2, long j2, int i4, String str2) {
        this(str, i2, i3, z2, j2, str2);
        this.notifyId = i4;
    }

    public int getAutoDelete() {
        return this.autoDelete;
    }

    public String getGroup() {
        return this.group;
    }

    public int getImportantLevel() {
        return this.importantLevel;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public int getNotifyId() {
        return this.notifyId;
    }

    public long getPostTime() {
        return this.postTime;
    }

    public String getStatisticData() {
        return this.statisticData;
    }

    public boolean isMcs() {
        return this.isMcs;
    }

    public void setGroup(String str) {
        this.group = str;
    }

    @Override // java.lang.Comparable
    public int compareTo(NotificationSortMessage notificationSortMessage) {
        if (notificationSortMessage.getPostTime() < this.postTime) {
            return 1;
        }
        return notificationSortMessage.getPostTime() == this.postTime ? 0 : -1;
    }

    public NotificationSortMessage(String str, int i2, int i3, boolean z2, long j2, String str2) {
        this.messageId = str;
        this.importantLevel = i2;
        this.autoDelete = i3;
        this.isMcs = z2;
        this.postTime = j2;
        this.statisticData = str2;
    }
}
