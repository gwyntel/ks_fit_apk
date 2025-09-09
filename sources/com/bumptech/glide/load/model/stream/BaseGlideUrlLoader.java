package com.bumptech.glide.load.model.stream;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseGlideUrlLoader<Model> implements ModelLoader<Model, InputStream> {
    private final ModelLoader<GlideUrl, InputStream> concreteLoader;

    @Nullable
    private final ModelCache<Model, GlideUrl> modelCache;

    private static List<Key> getAlternateKeys(Collection<String> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(new GlideUrl(it.next()));
        }
        return arrayList;
    }

    protected List a(Object obj, int i2, int i3, Options options) {
        return Collections.emptyList();
    }

    protected Headers b(Object obj, int i2, int i3, Options options) {
        return Headers.DEFAULT;
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    @Nullable
    public ModelLoader.LoadData<InputStream> buildLoadData(@NonNull Model model, int i2, int i3, @NonNull Options options) {
        ModelCache<Model, GlideUrl> modelCache = this.modelCache;
        GlideUrl glideUrl = modelCache != null ? modelCache.get(model, i2, i3) : null;
        if (glideUrl == null) {
            String strC = c(model, i2, i3, options);
            if (TextUtils.isEmpty(strC)) {
                return null;
            }
            GlideUrl glideUrl2 = new GlideUrl(strC, b(model, i2, i3, options));
            ModelCache<Model, GlideUrl> modelCache2 = this.modelCache;
            if (modelCache2 != null) {
                modelCache2.put(model, i2, i3, glideUrl2);
            }
            glideUrl = glideUrl2;
        }
        List listA = a(model, i2, i3, options);
        ModelLoader.LoadData<InputStream> loadDataBuildLoadData = this.concreteLoader.buildLoadData(glideUrl, i2, i3, options);
        return (loadDataBuildLoadData == null || listA.isEmpty()) ? loadDataBuildLoadData : new ModelLoader.LoadData<>(loadDataBuildLoadData.sourceKey, getAlternateKeys(listA), loadDataBuildLoadData.fetcher);
    }

    protected abstract String c(Object obj, int i2, int i3, Options options);
}
