package org.android.agoo.control;

import com.taobao.accs.utl.ALog;
import java.util.ArrayList;
import java.util.Iterator;
import org.android.agoo.common.MsgDO;

/* loaded from: classes5.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AgooFactory f26526a;

    c(AgooFactory agooFactory) {
        this.f26526a = agooFactory;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList<MsgDO> arrayListB = this.f26526a.messageService.b();
        if (arrayListB == null || arrayListB.size() <= 0) {
            return;
        }
        ALog.e("AgooFactory", "reportCacheMsg", "size", Integer.valueOf(arrayListB.size()));
        Iterator<MsgDO> it = arrayListB.iterator();
        while (it.hasNext()) {
            MsgDO next = it.next();
            if (next != null) {
                next.isFromCache = true;
                this.f26526a.notifyManager.report(next, null);
            }
        }
    }
}
