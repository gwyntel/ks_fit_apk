package dev.fluttercommunity.plus.share;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import androidx.core.content.FileProvider;
import androidx.webkit.ProxyConfig;
import androidx.webkit.internal.AssetHelper;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.f;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\b\u0010\u001e\u001a\u00020\u0003H\u0002J\u0012\u0010\u001f\u001a\u00020\u00102\b\u0010 \u001a\u0004\u0018\u00010\u0010H\u0002J&\u0010!\u001a\u0012\u0012\u0004\u0012\u00020#0\"j\b\u0012\u0004\u0012\u00020#`$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100&H\u0002J\u0018\u0010'\u001a\u00020\u00102\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&H\u0002J\u0010\u0010)\u001a\u00020\u00192\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J \u0010*\u001a\u00020\u00192\u0006\u0010+\u001a\u00020\u00102\b\u0010,\u001a\u0004\u0018\u00010\u00102\u0006\u0010-\u001a\u00020\u001dJ@\u0010.\u001a\u00020\u00192\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100&2\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010&2\b\u0010+\u001a\u0004\u0018\u00010\u00102\b\u0010,\u001a\u0004\u0018\u00010\u00102\u0006\u0010-\u001a\u00020\u001dJ\u0018\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u0002012\u0006\u0010-\u001a\u00020\u001dH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u00062"}, d2 = {"Ldev/fluttercommunity/plus/share/Share;", "", f.X, "Landroid/content/Context;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "manager", "Ldev/fluttercommunity/plus/share/ShareSuccessManager;", "(Landroid/content/Context;Landroid/app/Activity;Ldev/fluttercommunity/plus/share/ShareSuccessManager;)V", "immutabilityIntentFlags", "", "getImmutabilityIntentFlags", "()I", "immutabilityIntentFlags$delegate", "Lkotlin/Lazy;", "providerAuthority", "", "getProviderAuthority", "()Ljava/lang/String;", "providerAuthority$delegate", "shareCacheFolder", "Ljava/io/File;", "getShareCacheFolder", "()Ljava/io/File;", "clearShareCacheFolder", "", "copyToShareCacheFolder", "file", "fileIsInShareCache", "", "getContext", "getMimeTypeBase", "mimeType", "getUrisForPaths", "Ljava/util/ArrayList;", "Landroid/net/Uri;", "Lkotlin/collections/ArrayList;", "paths", "", "reduceMimeTypes", "mimeTypes", "setActivity", "share", "text", "subject", "withResult", "shareFiles", "startActivity", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "share_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nShare.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Share.kt\ndev/fluttercommunity/plus/share/Share\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,251:1\n1855#2:252\n1855#2,2:253\n1856#2:255\n1855#2,2:256\n13579#3,2:258\n*S KotlinDebug\n*F\n+ 1 Share.kt\ndev/fluttercommunity/plus/share/Share\n*L\n144#1:252\n146#1:253,2\n144#1:255\n177#1:256,2\n235#1:258,2\n*E\n"})
/* loaded from: classes4.dex */
public final class Share {

    @Nullable
    private Activity activity;

    @NotNull
    private final Context context;

    /* renamed from: immutabilityIntentFlags$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy immutabilityIntentFlags;

    @NotNull
    private final ShareSuccessManager manager;

    /* renamed from: providerAuthority$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy providerAuthority;

    public Share(@NotNull Context context, @Nullable Activity activity, @NotNull ShareSuccessManager manager) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(manager, "manager");
        this.context = context;
        this.activity = activity;
        this.manager = manager;
        this.providerAuthority = LazyKt.lazy(new Function0<String>() { // from class: dev.fluttercommunity.plus.share.Share$providerAuthority$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final String invoke() {
                return this.this$0.getContext().getPackageName() + ".flutter.share_provider";
            }
        });
        this.immutabilityIntentFlags = LazyKt.lazy(new Function0<Integer>() { // from class: dev.fluttercommunity.plus.share.Share$immutabilityIntentFlags$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Integer invoke() {
                return Integer.valueOf(DiskLruHelper.DEFAULT_MAXSIZE);
            }
        });
    }

    private final void clearShareCacheFolder() {
        File shareCacheFolder = getShareCacheFolder();
        File[] fileArrListFiles = shareCacheFolder.listFiles();
        if (!shareCacheFolder.exists() || fileArrListFiles == null || fileArrListFiles.length == 0) {
            return;
        }
        Intrinsics.checkNotNull(fileArrListFiles);
        for (File file : fileArrListFiles) {
            file.delete();
        }
        shareCacheFolder.delete();
    }

    private final File copyToShareCacheFolder(File file) throws IOException {
        File shareCacheFolder = getShareCacheFolder();
        if (!shareCacheFolder.exists()) {
            shareCacheFolder.mkdirs();
        }
        File file2 = new File(shareCacheFolder, file.getName());
        FilesKt.copyTo$default(file, file2, true, 0, 4, null);
        return file2;
    }

    private final boolean fileIsInShareCache(File file) throws IOException {
        try {
            String canonicalPath = file.getCanonicalPath();
            Intrinsics.checkNotNull(canonicalPath);
            String canonicalPath2 = getShareCacheFolder().getCanonicalPath();
            Intrinsics.checkNotNullExpressionValue(canonicalPath2, "getCanonicalPath(...)");
            return StringsKt.startsWith$default(canonicalPath, canonicalPath2, false, 2, (Object) null);
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Context getContext() {
        Activity activity = this.activity;
        if (activity == null) {
            return this.context;
        }
        Intrinsics.checkNotNull(activity);
        return activity;
    }

    private final int getImmutabilityIntentFlags() {
        return ((Number) this.immutabilityIntentFlags.getValue()).intValue();
    }

    private final String getMimeTypeBase(String mimeType) {
        if (mimeType == null || !StringsKt.contains$default((CharSequence) mimeType, (CharSequence) "/", false, 2, (Object) null)) {
            return ProxyConfig.MATCH_ALL_SCHEMES;
        }
        String strSubstring = mimeType.substring(0, StringsKt.indexOf$default((CharSequence) mimeType, "/", 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    private final String getProviderAuthority() {
        return (String) this.providerAuthority.getValue();
    }

    private final File getShareCacheFolder() {
        return new File(getContext().getCacheDir(), "share_plus");
    }

    private final ArrayList<Uri> getUrisForPaths(List<String> paths) throws IOException {
        ArrayList<Uri> arrayList = new ArrayList<>(paths.size());
        Iterator<T> it = paths.iterator();
        while (it.hasNext()) {
            File file = new File((String) it.next());
            if (fileIsInShareCache(file)) {
                throw new IOException("Shared file can not be located in '" + getShareCacheFolder().getCanonicalPath() + "'");
            }
            arrayList.add(FileProvider.getUriForFile(getContext(), getProviderAuthority(), copyToShareCacheFolder(file)));
        }
        return arrayList;
    }

    private final String reduceMimeTypes(List<String> mimeTypes) {
        int i2 = 1;
        if (mimeTypes != null ? mimeTypes.isEmpty() : true) {
            return "*/*";
        }
        Intrinsics.checkNotNull(mimeTypes);
        if (mimeTypes.size() == 1) {
            return (String) CollectionsKt.first((List) mimeTypes);
        }
        String str = (String) CollectionsKt.first((List) mimeTypes);
        int lastIndex = CollectionsKt.getLastIndex(mimeTypes);
        if (1 <= lastIndex) {
            while (true) {
                if (!Intrinsics.areEqual(str, mimeTypes.get(i2))) {
                    if (!Intrinsics.areEqual(getMimeTypeBase(str), getMimeTypeBase(mimeTypes.get(i2)))) {
                        return "*/*";
                    }
                    str = getMimeTypeBase(mimeTypes.get(i2)) + "/*";
                }
                if (i2 == lastIndex) {
                    break;
                }
                i2++;
            }
        }
        return str;
    }

    private final void startActivity(Intent intent, boolean withResult) {
        Activity activity = this.activity;
        if (activity == null) {
            intent.addFlags(268435456);
            if (withResult) {
                this.manager.unavailable();
            }
            this.context.startActivity(intent);
            return;
        }
        if (withResult) {
            Intrinsics.checkNotNull(activity);
            activity.startActivityForResult(intent, ShareSuccessManager.ACTIVITY_CODE);
        } else {
            Intrinsics.checkNotNull(activity);
            activity.startActivity(intent);
        }
    }

    public final void setActivity(@Nullable Activity activity) {
        this.activity = activity;
    }

    public final void share(@NotNull String text, @Nullable String subject, boolean withResult) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
        intent.putExtra("android.intent.extra.TEXT", text);
        if (subject != null) {
            intent.putExtra("android.intent.extra.SUBJECT", subject);
        }
        Intent intentCreateChooser = withResult ? Intent.createChooser(intent, null, PendingIntent.getBroadcast(this.context, 0, new Intent(this.context, (Class<?>) SharePlusPendingIntent.class), 134217728 | getImmutabilityIntentFlags()).getIntentSender()) : Intent.createChooser(intent, null);
        Intrinsics.checkNotNull(intentCreateChooser);
        startActivity(intentCreateChooser, withResult);
    }

    public final void shareFiles(@NotNull List<String> paths, @Nullable List<String> mimeTypes, @Nullable String text, @Nullable String subject, boolean withResult) throws IOException {
        Intrinsics.checkNotNullParameter(paths, "paths");
        clearShareCacheFolder();
        ArrayList<Uri> urisForPaths = getUrisForPaths(paths);
        Intent intent = new Intent();
        if (urisForPaths.isEmpty() && text != null && !StringsKt.isBlank(text)) {
            share(text, subject, withResult);
            return;
        }
        if (urisForPaths.size() == 1) {
            List<String> list = mimeTypes;
            String str = (list == null || list.isEmpty()) ? "*/*" : (String) CollectionsKt.first((List) mimeTypes);
            intent.setAction("android.intent.action.SEND");
            intent.setType(str);
            intent.putExtra("android.intent.extra.STREAM", (Parcelable) CollectionsKt.first((List) urisForPaths));
        } else {
            intent.setAction("android.intent.action.SEND_MULTIPLE");
            intent.setType(reduceMimeTypes(mimeTypes));
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", urisForPaths);
        }
        if (text != null) {
            intent.putExtra("android.intent.extra.TEXT", text);
        }
        if (subject != null) {
            intent.putExtra("android.intent.extra.SUBJECT", subject);
        }
        intent.addFlags(1);
        Intent intentCreateChooser = withResult ? Intent.createChooser(intent, null, PendingIntent.getBroadcast(this.context, 0, new Intent(this.context, (Class<?>) SharePlusPendingIntent.class), 134217728 | getImmutabilityIntentFlags()).getIntentSender()) : Intent.createChooser(intent, null);
        List<ResolveInfo> listQueryIntentActivities = getContext().getPackageManager().queryIntentActivities(intentCreateChooser, 65536);
        Intrinsics.checkNotNullExpressionValue(listQueryIntentActivities, "queryIntentActivities(...)");
        Iterator<T> it = listQueryIntentActivities.iterator();
        while (it.hasNext()) {
            String str2 = ((ResolveInfo) it.next()).activityInfo.packageName;
            Iterator<T> it2 = urisForPaths.iterator();
            while (it2.hasNext()) {
                getContext().grantUriPermission(str2, (Uri) it2.next(), 3);
            }
        }
        Intrinsics.checkNotNull(intentCreateChooser);
        startActivity(intentCreateChooser, withResult);
    }
}
