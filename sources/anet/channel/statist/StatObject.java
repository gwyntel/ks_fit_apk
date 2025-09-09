package anet.channel.statist;

import java.io.Serializable;

/* loaded from: classes2.dex */
public abstract class StatObject implements Serializable {
    public boolean beforeCommit() {
        return true;
    }
}
