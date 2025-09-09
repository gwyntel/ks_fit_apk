package com.example.imagegallerysaver;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J&\u0010\u0011\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0001`\u0013R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lcom/example/imagegallerysaver/SaveResultModel;", "", "isSuccess", "", "filePath", "", "errorMessage", "(ZLjava/lang/String;Ljava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "setErrorMessage", "(Ljava/lang/String;)V", "getFilePath", "setFilePath", "()Z", "setSuccess", "(Z)V", "toHashMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "image_gallery_saver_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SaveResultModel {

    @Nullable
    private String errorMessage;

    @Nullable
    private String filePath;
    private boolean isSuccess;

    public SaveResultModel(boolean z2, @Nullable String str, @Nullable String str2) {
        this.isSuccess = z2;
        this.filePath = str;
        this.errorMessage = str2;
    }

    @Nullable
    public final String getErrorMessage() {
        return this.errorMessage;
    }

    @Nullable
    public final String getFilePath() {
        return this.filePath;
    }

    /* renamed from: isSuccess, reason: from getter */
    public final boolean getIsSuccess() {
        return this.isSuccess;
    }

    public final void setErrorMessage(@Nullable String str) {
        this.errorMessage = str;
    }

    public final void setFilePath(@Nullable String str) {
        this.filePath = str;
    }

    public final void setSuccess(boolean z2) {
        this.isSuccess = z2;
    }

    @NotNull
    public final HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isSuccess", Boolean.valueOf(this.isSuccess));
        map.put("filePath", this.filePath);
        map.put("errorMessage", this.errorMessage);
        return map;
    }

    public /* synthetic */ SaveResultModel(boolean z2, String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z2, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2);
    }
}
