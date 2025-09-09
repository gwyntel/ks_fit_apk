package com.google.common.util.concurrent;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

@J2ktIncompatible
@ElementTypesAreNonnullByDefault
@GwtIncompatible
/* loaded from: classes3.dex */
public final class ServiceManager implements ServiceManagerBridge {
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;
    private static final LazyLogger logger = new LazyLogger(ServiceManager.class);
    private static final ListenerCallQueue.Event<Listener> HEALTHY_EVENT = new ListenerCallQueue.Event<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.1
        public String toString() {
            return "healthy()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Listener listener) {
            listener.healthy();
        }
    };
    private static final ListenerCallQueue.Event<Listener> STOPPED_EVENT = new ListenerCallQueue.Event<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.2
        public String toString() {
            return "stopped()";
        }

        @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
        public void call(Listener listener) {
            listener.stopped();
        }
    };

    private static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    private static final class FailedService extends Throwable {
        FailedService(Service service) {
            super(service.toString(), service.failureCause(), false, false);
        }
    }

    public static abstract class Listener {
        public void failure(Service service) {
        }

        public void healthy() {
        }

        public void stopped() {
        }
    }

    private static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void c() throws Exception {
            f();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void d() throws Exception {
            g();
        }
    }

    private static final class ServiceListener extends Service.Listener {

        /* renamed from: a, reason: collision with root package name */
        final Service f14904a;

        /* renamed from: b, reason: collision with root package name */
        final WeakReference f14905b;

        ServiceListener(Service service, WeakReference weakReference) {
            this.f14904a = service;
            this.f14905b = weakReference;
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void failed(Service.State state, Throwable th) throws Exception {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.f14905b.get();
            if (serviceManagerState != null) {
                if ((!(this.f14904a instanceof NoOpService)) & (state != Service.State.STARTING)) {
                    ServiceManager.logger.a().log(Level.SEVERE, "Service " + this.f14904a + " has failed in the " + state + " state.", th);
                }
                serviceManagerState.n(this.f14904a, state, Service.State.FAILED);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void running() throws Exception {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.f14905b.get();
            if (serviceManagerState != null) {
                serviceManagerState.n(this.f14904a, Service.State.STARTING, Service.State.RUNNING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void starting() throws Exception {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.f14905b.get();
            if (serviceManagerState != null) {
                serviceManagerState.n(this.f14904a, Service.State.NEW, Service.State.STARTING);
                if (this.f14904a instanceof NoOpService) {
                    return;
                }
                ServiceManager.logger.a().log(Level.FINE, "Starting {0}.", this.f14904a);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void stopping(Service.State state) throws Exception {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.f14905b.get();
            if (serviceManagerState != null) {
                serviceManagerState.n(this.f14904a, state, Service.State.STOPPING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void terminated(Service.State state) throws Exception {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.f14905b.get();
            if (serviceManagerState != null) {
                if (!(this.f14904a instanceof NoOpService)) {
                    ServiceManager.logger.a().log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.f14904a, state});
                }
                serviceManagerState.n(this.f14904a, state, Service.State.TERMINATED);
            }
        }
    }

    private static final class ServiceManagerState {

        /* renamed from: a, reason: collision with root package name */
        final Monitor f14906a = new Monitor();

        /* renamed from: b, reason: collision with root package name */
        final SetMultimap f14907b;

        /* renamed from: c, reason: collision with root package name */
        final Multiset f14908c;

        /* renamed from: d, reason: collision with root package name */
        final IdentityHashMap f14909d;

        /* renamed from: e, reason: collision with root package name */
        boolean f14910e;

        /* renamed from: f, reason: collision with root package name */
        boolean f14911f;

        /* renamed from: g, reason: collision with root package name */
        final int f14912g;

        /* renamed from: h, reason: collision with root package name */
        final Monitor.Guard f14913h;

        /* renamed from: i, reason: collision with root package name */
        final Monitor.Guard f14914i;

        /* renamed from: j, reason: collision with root package name */
        final ListenerCallQueue f14915j;

        final class AwaitHealthGuard extends Monitor.Guard {
            AwaitHealthGuard() {
                super(ServiceManagerState.this.f14906a);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                int iCount = ServiceManagerState.this.f14908c.count(Service.State.RUNNING);
                ServiceManagerState serviceManagerState = ServiceManagerState.this;
                return iCount == serviceManagerState.f14912g || serviceManagerState.f14908c.contains(Service.State.STOPPING) || ServiceManagerState.this.f14908c.contains(Service.State.TERMINATED) || ServiceManagerState.this.f14908c.contains(Service.State.FAILED);
            }
        }

        final class StoppedGuard extends Monitor.Guard {
            StoppedGuard() {
                super(ServiceManagerState.this.f14906a);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return ServiceManagerState.this.f14908c.count(Service.State.TERMINATED) + ServiceManagerState.this.f14908c.count(Service.State.FAILED) == ServiceManagerState.this.f14912g;
            }
        }

        ServiceManagerState(ImmutableCollection immutableCollection) {
            SetMultimap setMultimapBuild = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
            this.f14907b = setMultimapBuild;
            this.f14908c = setMultimapBuild.keys();
            this.f14909d = new IdentityHashMap();
            this.f14913h = new AwaitHealthGuard();
            this.f14914i = new StoppedGuard();
            this.f14915j = new ListenerCallQueue();
            this.f14912g = immutableCollection.size();
            setMultimapBuild.putAll(Service.State.NEW, immutableCollection);
        }

        void a(Listener listener, Executor executor) {
            this.f14915j.addListener(listener, executor);
        }

        void b() {
            this.f14906a.enterWhenUninterruptibly(this.f14913h);
            try {
                f();
            } finally {
                this.f14906a.leave();
            }
        }

        void c(long j2, TimeUnit timeUnit) {
            this.f14906a.enter();
            try {
                if (this.f14906a.waitForUninterruptibly(this.f14913h, j2, timeUnit)) {
                    f();
                    return;
                }
                throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.f14907b, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
            } finally {
                this.f14906a.leave();
            }
        }

        void d() {
            this.f14906a.enterWhenUninterruptibly(this.f14914i);
            this.f14906a.leave();
        }

        void e(long j2, TimeUnit timeUnit) {
            this.f14906a.enter();
            try {
                if (this.f14906a.waitForUninterruptibly(this.f14914i, j2, timeUnit)) {
                    return;
                }
                throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.f14907b, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
            } finally {
                this.f14906a.leave();
            }
        }

        void f() {
            Multiset multiset = this.f14908c;
            Service.State state = Service.State.RUNNING;
            if (multiset.count(state) != this.f14912g) {
                IllegalStateException illegalStateException = new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.f14907b, Predicates.not(Predicates.equalTo(state))));
                Iterator it = this.f14907b.get((SetMultimap) Service.State.FAILED).iterator();
                while (it.hasNext()) {
                    illegalStateException.addSuppressed(new FailedService((Service) it.next()));
                }
                throw illegalStateException;
            }
        }

        void g() throws Exception {
            Preconditions.checkState(!this.f14906a.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            this.f14915j.dispatch();
        }

        void h(final Service service) {
            this.f14915j.enqueue(new ListenerCallQueue.Event<Listener>(this) { // from class: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.2

                /* renamed from: b, reason: collision with root package name */
                final /* synthetic */ ServiceManagerState f14918b;

                {
                    this.f14918b = this;
                }

                public String toString() {
                    return "failed({service=" + service + "})";
                }

                @Override // com.google.common.util.concurrent.ListenerCallQueue.Event
                public void call(Listener listener) {
                    listener.failure(service);
                }
            });
        }

        void i() {
            this.f14915j.enqueue(ServiceManager.HEALTHY_EVENT);
        }

        void j() {
            this.f14915j.enqueue(ServiceManager.STOPPED_EVENT);
        }

        void k() {
            this.f14906a.enter();
            try {
                if (!this.f14911f) {
                    this.f14910e = true;
                    return;
                }
                ArrayList arrayListNewArrayList = Lists.newArrayList();
                UnmodifiableIterator it = l().values().iterator();
                while (it.hasNext()) {
                    Service service = (Service) it.next();
                    if (service.state() != Service.State.NEW) {
                        arrayListNewArrayList.add(service);
                    }
                }
                throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + arrayListNewArrayList);
            } finally {
                this.f14906a.leave();
            }
        }

        ImmutableSetMultimap l() {
            ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.f14906a.enter();
            try {
                for (Map.Entry entry : this.f14907b.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put(entry);
                    }
                }
                this.f14906a.leave();
                return builder.build();
            } catch (Throwable th) {
                this.f14906a.leave();
                throw th;
            }
        }

        ImmutableMap m() {
            this.f14906a.enter();
            try {
                ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(this.f14909d.size());
                for (Map.Entry entry : this.f14909d.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopwatch = (Stopwatch) entry.getValue();
                    if (!stopwatch.isRunning() && !(service instanceof NoOpService)) {
                        arrayListNewArrayListWithCapacity.add(Maps.immutableEntry(service, Long.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                this.f14906a.leave();
                Collections.sort(arrayListNewArrayListWithCapacity, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>() { // from class: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.1
                    @Override // com.google.common.base.Function
                    public Long apply(Map.Entry<Service, Long> entry2) {
                        return entry2.getValue();
                    }
                }));
                return ImmutableMap.copyOf(arrayListNewArrayListWithCapacity);
            } catch (Throwable th) {
                this.f14906a.leave();
                throw th;
            }
        }

        void n(Service service, Service.State state, Service.State state2) throws Exception {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.f14906a.enter();
            try {
                this.f14911f = true;
                if (!this.f14910e) {
                    this.f14906a.leave();
                    g();
                    return;
                }
                Preconditions.checkState(this.f14907b.remove(state, service), "Service %s not at the expected location in the state map %s", service, state);
                Preconditions.checkState(this.f14907b.put(state2, service), "Service %s in the state map unexpectedly at %s", service, state2);
                Stopwatch stopwatchCreateStarted = (Stopwatch) this.f14909d.get(service);
                if (stopwatchCreateStarted == null) {
                    stopwatchCreateStarted = Stopwatch.createStarted();
                    this.f14909d.put(service, stopwatchCreateStarted);
                }
                Service.State state3 = Service.State.RUNNING;
                if (state2.compareTo(state3) >= 0 && stopwatchCreateStarted.isRunning()) {
                    stopwatchCreateStarted.stop();
                    if (!(service instanceof NoOpService)) {
                        ServiceManager.logger.a().log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatchCreateStarted});
                    }
                }
                Service.State state4 = Service.State.FAILED;
                if (state2 == state4) {
                    h(service);
                }
                if (this.f14908c.count(state3) == this.f14912g) {
                    i();
                } else if (this.f14908c.count(Service.State.TERMINATED) + this.f14908c.count(state4) == this.f14912g) {
                    j();
                }
                this.f14906a.leave();
                g();
            } catch (Throwable th) {
                this.f14906a.leave();
                g();
                throw th;
            }
        }

        void o(Service service) {
            this.f14906a.enter();
            try {
                if (((Stopwatch) this.f14909d.get(service)) == null) {
                    this.f14909d.put(service, Stopwatch.createStarted());
                }
            } finally {
                this.f14906a.leave();
            }
        }
    }

    public ServiceManager(Iterable<? extends Service> iterable) {
        ImmutableList<Service> immutableListCopyOf = ImmutableList.copyOf(iterable);
        if (immutableListCopyOf.isEmpty()) {
            logger.a().log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", (Throwable) new EmptyServiceManagerWarning());
            immutableListCopyOf = ImmutableList.of(new NoOpService());
        }
        ServiceManagerState serviceManagerState = new ServiceManagerState(immutableListCopyOf);
        this.state = serviceManagerState;
        this.services = immutableListCopyOf;
        WeakReference weakReference = new WeakReference(serviceManagerState);
        UnmodifiableIterator<Service> it = immutableListCopyOf.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            next.addListener(new ServiceListener(next, weakReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(next.state() == Service.State.NEW, "Can only manage NEW services, %s", next);
        }
        this.state.k();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.a(listener, executor);
    }

    public void awaitHealthy() {
        this.state.b();
    }

    public void awaitStopped() {
        this.state.d();
    }

    public boolean isHealthy() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            if (!it.next().isRunning()) {
                return false;
            }
        }
        return true;
    }

    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            Preconditions.checkState(it.next().state() == Service.State.NEW, "Not all services are NEW, cannot start %s", this);
        }
        UnmodifiableIterator<Service> it2 = this.services.iterator();
        while (it2.hasNext()) {
            Service next = it2.next();
            try {
                this.state.o(next);
                next.startAsync();
            } catch (IllegalStateException e2) {
                logger.a().log(Level.WARNING, "Unable to start Service " + next, (Throwable) e2);
            }
        }
        return this;
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.m();
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            it.next().stopAsync();
        }
        return this;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Class<?>) ServiceManager.class).add(TmpConstant.DEVICE_MODEL_SERVICES, Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }

    public void awaitHealthy(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.state.c(j2, timeUnit);
    }

    public void awaitStopped(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.state.e(j2, timeUnit);
    }

    @Override // com.google.common.util.concurrent.ServiceManagerBridge
    public ImmutableSetMultimap<Service.State, Service> servicesByState() {
        return this.state.l();
    }
}
