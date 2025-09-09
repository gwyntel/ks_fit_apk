package org.joda.time.tz;

import com.xiaomi.mipush.sdk.Constants;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.joda.time.DateTimeZone;

/* loaded from: classes5.dex */
public final class FixedDateTimeZone extends DateTimeZone {
    private static final long serialVersionUID = -3513011772763289092L;
    private final String iNameKey;
    private final int iStandardOffset;
    private final int iWallOffset;

    public FixedDateTimeZone(String str, String str2, int i2, int i3) {
        super(str);
        this.iNameKey = str2;
        this.iWallOffset = i2;
        this.iStandardOffset = i3;
    }

    @Override // org.joda.time.DateTimeZone
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedDateTimeZone)) {
            return false;
        }
        FixedDateTimeZone fixedDateTimeZone = (FixedDateTimeZone) obj;
        return getID().equals(fixedDateTimeZone.getID()) && this.iStandardOffset == fixedDateTimeZone.iStandardOffset && this.iWallOffset == fixedDateTimeZone.iWallOffset;
    }

    @Override // org.joda.time.DateTimeZone
    public String getNameKey(long j2) {
        return this.iNameKey;
    }

    @Override // org.joda.time.DateTimeZone
    public int getOffset(long j2) {
        return this.iWallOffset;
    }

    @Override // org.joda.time.DateTimeZone
    public int getOffsetFromLocal(long j2) {
        return this.iWallOffset;
    }

    @Override // org.joda.time.DateTimeZone
    public int getStandardOffset(long j2) {
        return this.iStandardOffset;
    }

    @Override // org.joda.time.DateTimeZone
    public int hashCode() {
        return getID().hashCode() + (this.iStandardOffset * 37) + (this.iWallOffset * 31);
    }

    @Override // org.joda.time.DateTimeZone
    public boolean isFixed() {
        return true;
    }

    @Override // org.joda.time.DateTimeZone
    public long nextTransition(long j2) {
        return j2;
    }

    @Override // org.joda.time.DateTimeZone
    public long previousTransition(long j2) {
        return j2;
    }

    @Override // org.joda.time.DateTimeZone
    public TimeZone toTimeZone() {
        String id = getID();
        if (id.length() != 6 || (!id.startsWith(MqttTopic.SINGLE_LEVEL_WILDCARD) && !id.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER))) {
            return new SimpleTimeZone(this.iWallOffset, getID());
        }
        return TimeZone.getTimeZone("GMT" + getID());
    }
}
