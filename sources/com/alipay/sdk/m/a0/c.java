package com.alipay.sdk.m.a0;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class c implements FileFilter {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b f9185a;

    public c(b bVar) {
        this.f9185a = bVar;
    }

    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return Pattern.matches("cpu[0-9]+", file.getName());
    }
}
