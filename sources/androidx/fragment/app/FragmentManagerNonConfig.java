package androidx.fragment.app;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public class FragmentManagerNonConfig {

    @Nullable
    private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;

    @Nullable
    private final Collection<Fragment> mFragments;

    @Nullable
    private final Map<String, ViewModelStore> mViewModelStores;

    FragmentManagerNonConfig(Collection collection, Map map, Map map2) {
        this.mFragments = collection;
        this.mChildNonConfigs = map;
        this.mViewModelStores = map2;
    }

    Map a() {
        return this.mChildNonConfigs;
    }

    Collection b() {
        return this.mFragments;
    }

    Map c() {
        return this.mViewModelStores;
    }
}
