package androidx.health.platform.client.error;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Retention(RetentionPolicy.RUNTIME)
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\b\u0002\b\u0086\u0002\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002B\u0000¨\u0006\u0003"}, d2 = {"Landroidx/health/platform/client/error/ErrorCode;", "", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public @interface ErrorCode {
    public static final int CHANGES_TOKEN_OUTDATED = 10008;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.f4332a;
    public static final int DATABASE_ERROR = 10006;
    public static final int EMPTY_PERMISSION_LIST = 10002;
    public static final int INTERNAL_ERROR = 10007;
    public static final int INVALID_OWNERSHIP = 10000;
    public static final int INVALID_PERMISSION_RATIONALE_DECLARATION = 10004;
    public static final int INVALID_UID = 10005;
    public static final int NOT_ALLOWED = 10001;
    public static final int NO_PERMISSION = 4;
    public static final int PERMISSION_NOT_DECLARED = 10003;
    public static final int PROVIDER_NEEDS_UPDATE = 3;
    public static final int PROVIDER_NOT_ENABLED = 2;
    public static final int PROVIDER_NOT_INSTALLED = 1;
    public static final int TRANSACTION_TOO_LARGE = 10010;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/health/platform/client/error/ErrorCode$Companion;", "", "()V", "CHANGES_TOKEN_OUTDATED", "", "DATABASE_ERROR", "EMPTY_PERMISSION_LIST", "INTERNAL_ERROR", "INVALID_OWNERSHIP", "INVALID_PERMISSION_RATIONALE_DECLARATION", "INVALID_UID", "NOT_ALLOWED", "NO_PERMISSION", "PERMISSION_NOT_DECLARED", "PROVIDER_NEEDS_UPDATE", "PROVIDER_NOT_ENABLED", "PROVIDER_NOT_INSTALLED", "TRANSACTION_TOO_LARGE", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public static final int CHANGES_TOKEN_OUTDATED = 10008;
        public static final int DATABASE_ERROR = 10006;
        public static final int EMPTY_PERMISSION_LIST = 10002;
        public static final int INTERNAL_ERROR = 10007;
        public static final int INVALID_OWNERSHIP = 10000;
        public static final int INVALID_PERMISSION_RATIONALE_DECLARATION = 10004;
        public static final int INVALID_UID = 10005;
        public static final int NOT_ALLOWED = 10001;
        public static final int NO_PERMISSION = 4;
        public static final int PERMISSION_NOT_DECLARED = 10003;
        public static final int PROVIDER_NEEDS_UPDATE = 3;
        public static final int PROVIDER_NOT_ENABLED = 2;
        public static final int PROVIDER_NOT_INSTALLED = 1;
        public static final int TRANSACTION_TOO_LARGE = 10010;

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ Companion f4332a = new Companion();

        private Companion() {
        }
    }
}
