package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ForwardingSet;
import java.util.Set;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class InvalidatableSet<E> extends ForwardingSet<E> {
    private final Set<E> delegate;
    private final Supplier<String> errorMessage;
    private final Supplier<Boolean> validator;

    private InvalidatableSet(Set<E> set, Supplier<Boolean> supplier, Supplier<String> supplier2) {
        this.delegate = set;
        this.validator = supplier;
        this.errorMessage = supplier2;
    }

    public static final <E> InvalidatableSet<E> of(Set<E> set, Supplier<Boolean> supplier, Supplier<String> supplier2) {
        return new InvalidatableSet<>((Set) Preconditions.checkNotNull(set), (Supplier) Preconditions.checkNotNull(supplier), (Supplier) Preconditions.checkNotNull(supplier2));
    }

    private void validate() {
        if (!this.validator.get().booleanValue()) {
            throw new IllegalStateException(this.errorMessage.get());
        }
    }

    @Override // com.google.common.collect.ForwardingSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.delegate.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
    public Set delegate() {
        validate();
        return this.delegate;
    }
}
