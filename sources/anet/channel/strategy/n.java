package anet.channel.strategy;

import java.io.File;
import java.util.Comparator;

/* loaded from: classes2.dex */
final class n implements Comparator<File> {
    n() {
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(File file, File file2) {
        return (int) (file2.lastModified() - file.lastModified());
    }
}
