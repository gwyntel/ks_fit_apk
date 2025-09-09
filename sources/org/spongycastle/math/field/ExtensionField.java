package org.spongycastle.math.field;

/* loaded from: classes5.dex */
public interface ExtensionField extends FiniteField {
    int getDegree();

    FiniteField getSubfield();
}
