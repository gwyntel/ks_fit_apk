package androidx.constraintlayout.core;

import androidx.constraintlayout.core.Pools;

/* loaded from: classes.dex */
public class Cache {

    /* renamed from: a, reason: collision with root package name */
    Pools.Pool f2554a = new Pools.SimplePool(256);

    /* renamed from: b, reason: collision with root package name */
    Pools.Pool f2555b = new Pools.SimplePool(256);

    /* renamed from: c, reason: collision with root package name */
    Pools.Pool f2556c = new Pools.SimplePool(256);

    /* renamed from: d, reason: collision with root package name */
    SolverVariable[] f2557d = new SolverVariable[32];
}
