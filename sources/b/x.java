package b;

import aisscanner.ScanResult;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class x implements Comparator<ScanResult> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ y f7572a;

    public x(y yVar) {
        this.f7572a = yVar;
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.getRssi() - scanResult.getRssi();
    }
}
