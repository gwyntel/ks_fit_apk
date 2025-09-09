package anet.channel.e;

import android.content.SharedPreferences;
import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.l;

/* loaded from: classes2.dex */
final class c implements IStrategyListener {
    c() {
    }

    @Override // anet.channel.strategy.IStrategyListener
    public void onStrategyUpdated(l.d dVar) {
        String str;
        if (dVar == null || dVar.f7044b == null) {
            return;
        }
        int i2 = 0;
        loop0: while (true) {
            l.b[] bVarArr = dVar.f7044b;
            if (i2 >= bVarArr.length) {
                return;
            }
            l.b bVar = bVarArr[i2];
            str = bVar.f7029a;
            l.a[] aVarArr = bVar.f7036h;
            if (aVarArr != null && aVarArr.length > 0) {
                for (l.a aVar : aVarArr) {
                    String str2 = aVar.f7022b;
                    if (ConnType.HTTP3.equals(str2) || ConnType.HTTP3_PLAIN.equals(str2)) {
                        break loop0;
                    }
                }
            }
            i2++;
        }
        if (!str.equals(a.f6752b)) {
            String unused = a.f6752b = str;
            SharedPreferences.Editor editorEdit = a.f6756f.edit();
            editorEdit.putString("http3_detector_host", a.f6752b);
            editorEdit.apply();
        }
        a.a(NetworkStatusHelper.getStatus());
    }
}
