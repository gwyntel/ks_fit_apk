package com.google.firebase.components;

import android.util.Log;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.dynamicloading.ComponentLoader;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class ComponentRuntime implements ComponentContainer, ComponentLoader {
    private static final Provider<Set<Object>> EMPTY_PROVIDER = new Provider() { // from class: com.google.firebase.components.j
        @Override // com.google.firebase.inject.Provider
        public final Object get() {
            return Collections.emptySet();
        }
    };
    private final ComponentRegistrarProcessor componentRegistrarProcessor;
    private final Map<Component<?>, Provider<?>> components;
    private final AtomicReference<Boolean> eagerComponentsInitializedWith;
    private final EventBus eventBus;
    private final Map<Qualified<?>, Provider<?>> lazyInstanceMap;
    private final Map<Qualified<?>, LazySet<?>> lazySetMap;
    private Set<String> processedCoroutineDispatcherInterfaces;
    private final List<Provider<ComponentRegistrar>> unprocessedRegistrarProviders;

    public static final class Builder {
        private final Executor defaultExecutor;
        private final List<Provider<ComponentRegistrar>> lazyRegistrars = new ArrayList();
        private final List<Component<?>> additionalComponents = new ArrayList();
        private ComponentRegistrarProcessor componentRegistrarProcessor = ComponentRegistrarProcessor.NOOP;

        Builder(Executor executor) {
            this.defaultExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ ComponentRegistrar lambda$addComponentRegistrar$0(ComponentRegistrar componentRegistrar) {
            return componentRegistrar;
        }

        @CanIgnoreReturnValue
        public Builder addComponent(Component<?> component) {
            this.additionalComponents.add(component);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addComponentRegistrar(final ComponentRegistrar componentRegistrar) {
            this.lazyRegistrars.add(new Provider() { // from class: com.google.firebase.components.n
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return ComponentRuntime.Builder.lambda$addComponentRegistrar$0(componentRegistrar);
                }
            });
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addLazyComponentRegistrars(Collection<Provider<ComponentRegistrar>> collection) {
            this.lazyRegistrars.addAll(collection);
            return this;
        }

        public ComponentRuntime build() {
            return new ComponentRuntime(this.defaultExecutor, this.lazyRegistrars, this.additionalComponents, this.componentRegistrarProcessor);
        }

        @CanIgnoreReturnValue
        public Builder setProcessor(ComponentRegistrarProcessor componentRegistrarProcessor) {
            this.componentRegistrarProcessor = componentRegistrarProcessor;
            return this;
        }
    }

    public static Builder builder(Executor executor) {
        return new Builder(executor);
    }

    private void discoverComponents(List<Component<?>> list) {
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator<Provider<ComponentRegistrar>> it = this.unprocessedRegistrarProviders.iterator();
            while (it.hasNext()) {
                try {
                    ComponentRegistrar componentRegistrar = it.next().get();
                    if (componentRegistrar != null) {
                        list.addAll(this.componentRegistrarProcessor.processRegistrar(componentRegistrar));
                        it.remove();
                    }
                } catch (InvalidRegistrarException e2) {
                    it.remove();
                    Log.w("ComponentDiscovery", "Invalid component registrar.", e2);
                }
            }
            Iterator<Component<?>> it2 = list.iterator();
            while (it2.hasNext()) {
                Object[] array = it2.next().getProvidedInterfaces().toArray();
                int length = array.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        Object obj = array[i2];
                        if (obj.toString().contains("kotlinx.coroutines.CoroutineDispatcher")) {
                            if (this.processedCoroutineDispatcherInterfaces.contains(obj.toString())) {
                                it2.remove();
                                break;
                            }
                            this.processedCoroutineDispatcherInterfaces.add(obj.toString());
                        }
                        i2++;
                    }
                }
            }
            if (this.components.isEmpty()) {
                CycleDetector.a(list);
            } else {
                ArrayList arrayList2 = new ArrayList(this.components.keySet());
                arrayList2.addAll(list);
                CycleDetector.a(arrayList2);
            }
            for (final Component<?> component : list) {
                this.components.put(component, new Lazy(new Provider() { // from class: com.google.firebase.components.k
                    @Override // com.google.firebase.inject.Provider
                    public final Object get() {
                        return this.f15000a.lambda$discoverComponents$0(component);
                    }
                }));
            }
            arrayList.addAll(processInstanceComponents(list));
            arrayList.addAll(processSetComponents());
            processDependencies();
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            ((Runnable) it3.next()).run();
        }
        maybeInitializeEagerComponents();
    }

    private void doInitializeEagerComponents(Map<Component<?>, Provider<?>> map, boolean z2) {
        for (Map.Entry<Component<?>, Provider<?>> entry : map.entrySet()) {
            Component<?> key = entry.getKey();
            Provider<?> value = entry.getValue();
            if (key.isAlwaysEager() || (key.isEagerInDefaultApp() && z2)) {
                value.get();
            }
        }
        this.eventBus.b();
    }

    private static <T> List<T> iterableToList(Iterable<T> iterable) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$discoverComponents$0(Component component) {
        return component.getFactory().create(new RestrictedComponentContainer(component, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ComponentRegistrar lambda$toProviders$1(ComponentRegistrar componentRegistrar) {
        return componentRegistrar;
    }

    private void maybeInitializeEagerComponents() {
        Boolean bool = this.eagerComponentsInitializedWith.get();
        if (bool != null) {
            doInitializeEagerComponents(this.components, bool.booleanValue());
        }
    }

    private void processDependencies() {
        for (Component<?> component : this.components.keySet()) {
            for (Dependency dependency : component.getDependencies()) {
                if (dependency.isSet() && !this.lazySetMap.containsKey(dependency.getInterface())) {
                    this.lazySetMap.put(dependency.getInterface(), LazySet.b(Collections.emptySet()));
                } else if (this.lazyInstanceMap.containsKey(dependency.getInterface())) {
                    continue;
                } else {
                    if (dependency.isRequired()) {
                        throw new MissingDependencyException(String.format("Unsatisfied dependency for component %s: %s", component, dependency.getInterface()));
                    }
                    if (!dependency.isSet()) {
                        this.lazyInstanceMap.put(dependency.getInterface(), OptionalProvider.d());
                    }
                }
            }
        }
    }

    private List<Runnable> processInstanceComponents(List<Component<?>> list) {
        ArrayList arrayList = new ArrayList();
        for (Component<?> component : list) {
            if (component.isValue()) {
                final Provider<?> provider = this.components.get(component);
                for (Qualified<? super Object> qualified : component.getProvidedInterfaces()) {
                    if (this.lazyInstanceMap.containsKey(qualified)) {
                        final OptionalProvider optionalProvider = (OptionalProvider) this.lazyInstanceMap.get(qualified);
                        arrayList.add(new Runnable() { // from class: com.google.firebase.components.l
                            @Override // java.lang.Runnable
                            public final void run() {
                                optionalProvider.f(provider);
                            }
                        });
                    } else {
                        this.lazyInstanceMap.put(qualified, provider);
                    }
                }
            }
        }
        return arrayList;
    }

    private List<Runnable> processSetComponents() {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        for (Map.Entry<Component<?>, Provider<?>> entry : this.components.entrySet()) {
            Component<?> key = entry.getKey();
            if (!key.isValue()) {
                Provider<?> value = entry.getValue();
                for (Qualified<? super Object> qualified : key.getProvidedInterfaces()) {
                    if (!map.containsKey(qualified)) {
                        map.put(qualified, new HashSet());
                    }
                    ((Set) map.get(qualified)).add(value);
                }
            }
        }
        for (Map.Entry entry2 : map.entrySet()) {
            if (this.lazySetMap.containsKey(entry2.getKey())) {
                final LazySet<?> lazySet = this.lazySetMap.get(entry2.getKey());
                for (final Provider provider : (Set) entry2.getValue()) {
                    arrayList.add(new Runnable() { // from class: com.google.firebase.components.m
                        @Override // java.lang.Runnable
                        public final void run() {
                            lazySet.a(provider);
                        }
                    });
                }
            } else {
                this.lazySetMap.put((Qualified) entry2.getKey(), LazySet.b((Collection) entry2.getValue()));
            }
        }
        return arrayList;
    }

    private static Iterable<Provider<ComponentRegistrar>> toProviders(Iterable<ComponentRegistrar> iterable) {
        ArrayList arrayList = new ArrayList();
        for (final ComponentRegistrar componentRegistrar : iterable) {
            arrayList.add(new Provider() { // from class: com.google.firebase.components.i
                @Override // com.google.firebase.inject.Provider
                public final Object get() {
                    return ComponentRuntime.lambda$toProviders$1(componentRegistrar);
                }
            });
        }
        return arrayList;
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Object get(Qualified qualified) {
        return f.a(this, qualified);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Deferred getDeferred(Class cls) {
        return f.c(this, cls);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Provider getProvider(Class cls) {
        return f.d(this, cls);
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    @VisibleForTesting
    public void initializeAllComponentsForTests() {
        Iterator<Provider<?>> it = this.components.values().iterator();
        while (it.hasNext()) {
            it.next().get();
        }
    }

    public void initializeEagerComponents(boolean z2) {
        HashMap map;
        if (androidx.lifecycle.e.a(this.eagerComponentsInitializedWith, null, Boolean.valueOf(z2))) {
            synchronized (this) {
                map = new HashMap(this.components);
            }
            doInitializeEagerComponents(map, z2);
        }
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Set setOf(Qualified qualified) {
        return f.e(this, qualified);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Provider setOfProvider(Class cls) {
        return f.g(this, cls);
    }

    @Deprecated
    public ComponentRuntime(Executor executor, Iterable<ComponentRegistrar> iterable, Component<?>... componentArr) {
        this(executor, toProviders(iterable), Arrays.asList(componentArr), ComponentRegistrarProcessor.NOOP);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Object get(Class cls) {
        return f.b(this, cls);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public <T> Deferred<T> getDeferred(Qualified<T> qualified) {
        Provider<T> provider = getProvider(qualified);
        return provider == null ? OptionalProvider.d() : provider instanceof OptionalProvider ? (OptionalProvider) provider : OptionalProvider.e(provider);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public synchronized <T> Provider<T> getProvider(Qualified<T> qualified) {
        Preconditions.checkNotNull(qualified, "Null interface requested.");
        return (Provider) this.lazyInstanceMap.get(qualified);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public /* synthetic */ Set setOf(Class cls) {
        return f.f(this, cls);
    }

    @Override // com.google.firebase.components.ComponentContainer
    public synchronized <T> Provider<Set<T>> setOfProvider(Qualified<T> qualified) {
        LazySet<?> lazySet = this.lazySetMap.get(qualified);
        if (lazySet != null) {
            return lazySet;
        }
        return (Provider<Set<T>>) EMPTY_PROVIDER;
    }

    private ComponentRuntime(Executor executor, Iterable<Provider<ComponentRegistrar>> iterable, Collection<Component<?>> collection, ComponentRegistrarProcessor componentRegistrarProcessor) {
        this.components = new HashMap();
        this.lazyInstanceMap = new HashMap();
        this.lazySetMap = new HashMap();
        this.processedCoroutineDispatcherInterfaces = new HashSet();
        this.eagerComponentsInitializedWith = new AtomicReference<>();
        EventBus eventBus = new EventBus(executor);
        this.eventBus = eventBus;
        this.componentRegistrarProcessor = componentRegistrarProcessor;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Component.of(eventBus, (Class<EventBus>) EventBus.class, (Class<? super EventBus>[]) new Class[]{Subscriber.class, Publisher.class}));
        arrayList.add(Component.of(this, (Class<ComponentRuntime>) ComponentLoader.class, (Class<? super ComponentRuntime>[]) new Class[0]));
        for (Component<?> component : collection) {
            if (component != null) {
                arrayList.add(component);
            }
        }
        this.unprocessedRegistrarProviders = iterableToList(iterable);
        discoverComponents(arrayList);
    }

    @Override // com.google.firebase.dynamicloading.ComponentLoader
    public void discoverComponents() {
        synchronized (this) {
            try {
                if (this.unprocessedRegistrarProviders.isEmpty()) {
                    return;
                }
                discoverComponents(new ArrayList());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
