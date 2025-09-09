package com.kingsmith.xiaojin;

import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u00038FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/kingsmith/xiaojin/LanguageType;", "", bc.N, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getLanguage", "()Ljava/lang/String;", "setLanguage", "(Ljava/lang/String;)V", "CHINESE", ViewHierarchyConstants.ENGLISH, "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LanguageType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ LanguageType[] $VALUES;
    public static final LanguageType CHINESE = new LanguageType("CHINESE", 0, "zh");
    public static final LanguageType ENGLISH = new LanguageType(ViewHierarchyConstants.ENGLISH, 1, "en");

    @Nullable
    private String language;

    private static final /* synthetic */ LanguageType[] $values() {
        return new LanguageType[]{CHINESE, ENGLISH};
    }

    static {
        LanguageType[] languageTypeArr$values = $values();
        $VALUES = languageTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(languageTypeArr$values);
    }

    private LanguageType(String str, int i2, String str2) {
        this.language = str2;
    }

    @NotNull
    public static EnumEntries<LanguageType> getEntries() {
        return $ENTRIES;
    }

    public static LanguageType valueOf(String str) {
        return (LanguageType) Enum.valueOf(LanguageType.class, str);
    }

    public static LanguageType[] values() {
        return (LanguageType[]) $VALUES.clone();
    }

    @Nullable
    public final String getLanguage() {
        String str = this.language;
        return str == null ? "" : str;
    }

    public final void setLanguage(@Nullable String str) {
        this.language = str;
    }
}
