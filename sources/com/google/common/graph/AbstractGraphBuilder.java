package com.google.common.graph;

import com.google.common.base.Optional;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class AbstractGraphBuilder<N> {

    /* renamed from: a, reason: collision with root package name */
    final boolean f14494a;

    /* renamed from: b, reason: collision with root package name */
    boolean f14495b = false;

    /* renamed from: c, reason: collision with root package name */
    ElementOrder f14496c = ElementOrder.insertion();

    /* renamed from: d, reason: collision with root package name */
    ElementOrder f14497d = ElementOrder.unordered();

    /* renamed from: e, reason: collision with root package name */
    Optional f14498e = Optional.absent();

    AbstractGraphBuilder(boolean z2) {
        this.f14494a = z2;
    }
}
