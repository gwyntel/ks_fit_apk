package com.xiaomi.push;

import java.io.File;
import java.io.FileFilter;

/* loaded from: classes4.dex */
class y implements FileFilter {
    y() {
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return file.isDirectory();
    }
}
