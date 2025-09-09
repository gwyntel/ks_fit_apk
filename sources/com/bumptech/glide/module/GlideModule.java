package com.bumptech.glide.module;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;

@Deprecated
/* loaded from: classes3.dex */
public interface GlideModule extends RegistersComponents, AppliesOptions {
    @Override // com.bumptech.glide.module.AppliesOptions
    /* synthetic */ void applyOptions(Context context, GlideBuilder glideBuilder);

    @Override // com.bumptech.glide.module.RegistersComponents
    /* synthetic */ void registerComponents(Context context, Glide glide, Registry registry);
}
