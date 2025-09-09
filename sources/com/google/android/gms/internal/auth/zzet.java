package com.google.android.gms.internal.auth;

import com.google.android.gms.internal.auth.zzet;
import com.google.android.gms.internal.auth.zzev;

/* loaded from: classes3.dex */
public class zzet<MessageType extends zzev<MessageType, BuilderType>, BuilderType extends zzet<MessageType, BuilderType>> extends zzdp<MessageType, BuilderType> {

    /* renamed from: a, reason: collision with root package name */
    protected zzev f13015a;
    private final zzev zzb;

    protected zzet(zzev zzevVar) {
        this.zzb = zzevVar;
        if (zzevVar.l()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.f13015a = zzevVar.c();
    }

    @Override // com.google.android.gms.internal.auth.zzdp
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzet clone() {
        zzet zzetVar = (zzet) this.zzb.m(5, null, null);
        zzetVar.f13015a = zzd();
        return zzetVar;
    }

    @Override // com.google.android.gms.internal.auth.zzfw
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public MessageType zzd() {
        if (!this.f13015a.l()) {
            return (MessageType) this.f13015a;
        }
        this.f13015a.h();
        return (MessageType) this.f13015a;
    }

    @Override // com.google.android.gms.internal.auth.zzfy
    public final /* bridge */ /* synthetic */ zzfx zze() {
        throw null;
    }
}
