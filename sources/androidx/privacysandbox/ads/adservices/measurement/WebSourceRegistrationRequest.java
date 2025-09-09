package androidx.privacysandbox.ads.adservices.measurement;

import android.net.Uri;
import android.view.InputEvent;
import androidx.annotation.RequiresApi;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiresApi(33)
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001dBK\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\fJ\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001e"}, d2 = {"Landroidx/privacysandbox/ads/adservices/measurement/WebSourceRegistrationRequest;", "", "webSourceParams", "", "Landroidx/privacysandbox/ads/adservices/measurement/WebSourceParams;", "topOriginUri", "Landroid/net/Uri;", "inputEvent", "Landroid/view/InputEvent;", "appDestination", "webDestination", "verifiedDestination", "(Ljava/util/List;Landroid/net/Uri;Landroid/view/InputEvent;Landroid/net/Uri;Landroid/net/Uri;Landroid/net/Uri;)V", "getAppDestination", "()Landroid/net/Uri;", "getInputEvent", "()Landroid/view/InputEvent;", "getTopOriginUri", "getVerifiedDestination", "getWebDestination", "getWebSourceParams", "()Ljava/util/List;", "equals", "", "other", "hashCode", "", "toString", "", "Builder", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class WebSourceRegistrationRequest {

    /* renamed from: appDestination, reason: from kotlin metadata and from toString */
    @Nullable
    private final Uri AppDestination;

    /* renamed from: inputEvent, reason: from kotlin metadata and from toString */
    @Nullable
    private final InputEvent InputEvent;

    /* renamed from: topOriginUri, reason: from kotlin metadata and from toString */
    @NotNull
    private final Uri TopOriginUri;

    /* renamed from: verifiedDestination, reason: from kotlin metadata and from toString */
    @Nullable
    private final Uri VerifiedDestination;

    /* renamed from: webDestination, reason: from kotlin metadata and from toString */
    @Nullable
    private final Uri WebDestination;

    /* renamed from: webSourceParams, reason: from kotlin metadata and from toString */
    @NotNull
    private final List<WebSourceParams> WebSourceParams;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0011\u001a\u00020\u00002\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0012\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0006R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Landroidx/privacysandbox/ads/adservices/measurement/WebSourceRegistrationRequest$Builder;", "", "webSourceParams", "", "Landroidx/privacysandbox/ads/adservices/measurement/WebSourceParams;", "topOriginUri", "Landroid/net/Uri;", "(Ljava/util/List;Landroid/net/Uri;)V", "appDestination", "inputEvent", "Landroid/view/InputEvent;", "verifiedDestination", "webDestination", "build", "Landroidx/privacysandbox/ads/adservices/measurement/WebSourceRegistrationRequest;", "setAppDestination", "setInputEvent", "setVerifiedDestination", "setWebDestination", "ads-adservices_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {

        @Nullable
        private Uri appDestination;

        @Nullable
        private InputEvent inputEvent;

        @NotNull
        private final Uri topOriginUri;

        @Nullable
        private Uri verifiedDestination;

        @Nullable
        private Uri webDestination;

        @NotNull
        private final List<WebSourceParams> webSourceParams;

        public Builder(@NotNull List<WebSourceParams> webSourceParams, @NotNull Uri topOriginUri) {
            Intrinsics.checkNotNullParameter(webSourceParams, "webSourceParams");
            Intrinsics.checkNotNullParameter(topOriginUri, "topOriginUri");
            this.webSourceParams = webSourceParams;
            this.topOriginUri = topOriginUri;
        }

        @NotNull
        public final WebSourceRegistrationRequest build() {
            return new WebSourceRegistrationRequest(this.webSourceParams, this.topOriginUri, this.inputEvent, this.appDestination, this.webDestination, this.verifiedDestination);
        }

        @NotNull
        public final Builder setAppDestination(@Nullable Uri appDestination) {
            this.appDestination = appDestination;
            return this;
        }

        @NotNull
        public final Builder setInputEvent(@NotNull InputEvent inputEvent) {
            Intrinsics.checkNotNullParameter(inputEvent, "inputEvent");
            this.inputEvent = inputEvent;
            return this;
        }

        @NotNull
        public final Builder setVerifiedDestination(@Nullable Uri verifiedDestination) {
            this.verifiedDestination = verifiedDestination;
            return this;
        }

        @NotNull
        public final Builder setWebDestination(@Nullable Uri webDestination) {
            this.webDestination = webDestination;
            return this;
        }
    }

    public WebSourceRegistrationRequest(@NotNull List<WebSourceParams> webSourceParams, @NotNull Uri topOriginUri, @Nullable InputEvent inputEvent, @Nullable Uri uri, @Nullable Uri uri2, @Nullable Uri uri3) {
        Intrinsics.checkNotNullParameter(webSourceParams, "webSourceParams");
        Intrinsics.checkNotNullParameter(topOriginUri, "topOriginUri");
        this.WebSourceParams = webSourceParams;
        this.TopOriginUri = topOriginUri;
        this.InputEvent = inputEvent;
        this.AppDestination = uri;
        this.WebDestination = uri2;
        this.VerifiedDestination = uri3;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WebSourceRegistrationRequest)) {
            return false;
        }
        WebSourceRegistrationRequest webSourceRegistrationRequest = (WebSourceRegistrationRequest) other;
        return Intrinsics.areEqual(this.WebSourceParams, webSourceRegistrationRequest.WebSourceParams) && Intrinsics.areEqual(this.WebDestination, webSourceRegistrationRequest.WebDestination) && Intrinsics.areEqual(this.AppDestination, webSourceRegistrationRequest.AppDestination) && Intrinsics.areEqual(this.TopOriginUri, webSourceRegistrationRequest.TopOriginUri) && Intrinsics.areEqual(this.InputEvent, webSourceRegistrationRequest.InputEvent) && Intrinsics.areEqual(this.VerifiedDestination, webSourceRegistrationRequest.VerifiedDestination);
    }

    @Nullable
    public final Uri getAppDestination() {
        return this.AppDestination;
    }

    @Nullable
    public final InputEvent getInputEvent() {
        return this.InputEvent;
    }

    @NotNull
    public final Uri getTopOriginUri() {
        return this.TopOriginUri;
    }

    @Nullable
    public final Uri getVerifiedDestination() {
        return this.VerifiedDestination;
    }

    @Nullable
    public final Uri getWebDestination() {
        return this.WebDestination;
    }

    @NotNull
    public final List<WebSourceParams> getWebSourceParams() {
        return this.WebSourceParams;
    }

    public int hashCode() {
        int iHashCode = (this.WebSourceParams.hashCode() * 31) + this.TopOriginUri.hashCode();
        InputEvent inputEvent = this.InputEvent;
        if (inputEvent != null) {
            iHashCode = (iHashCode * 31) + inputEvent.hashCode();
        }
        Uri uri = this.AppDestination;
        if (uri != null) {
            iHashCode = (iHashCode * 31) + uri.hashCode();
        }
        Uri uri2 = this.WebDestination;
        if (uri2 != null) {
            iHashCode = (iHashCode * 31) + uri2.hashCode();
        }
        int iHashCode2 = (iHashCode * 31) + this.TopOriginUri.hashCode();
        InputEvent inputEvent2 = this.InputEvent;
        if (inputEvent2 != null) {
            iHashCode2 = (iHashCode2 * 31) + inputEvent2.hashCode();
        }
        Uri uri3 = this.VerifiedDestination;
        return uri3 != null ? (iHashCode2 * 31) + uri3.hashCode() : iHashCode2;
    }

    @NotNull
    public String toString() {
        return "WebSourceRegistrationRequest { " + ("WebSourceParams=[" + this.WebSourceParams + "], TopOriginUri=" + this.TopOriginUri + ", InputEvent=" + this.InputEvent + ", AppDestination=" + this.AppDestination + ", WebDestination=" + this.WebDestination + ", VerifiedDestination=" + this.VerifiedDestination) + " }";
    }

    public /* synthetic */ WebSourceRegistrationRequest(List list, Uri uri, InputEvent inputEvent, Uri uri2, Uri uri3, Uri uri4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, uri, (i2 & 4) != 0 ? null : inputEvent, (i2 & 8) != 0 ? null : uri2, (i2 & 16) != 0 ? null : uri3, (i2 & 32) != 0 ? null : uri4);
    }
}
