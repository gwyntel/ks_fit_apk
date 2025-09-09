package androidx.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
class MediaBrowserServiceCompatApi21 {

    static class BrowserRoot {

        /* renamed from: a, reason: collision with root package name */
        final String f4722a;

        /* renamed from: b, reason: collision with root package name */
        final Bundle f4723b;

        BrowserRoot(String str, Bundle bundle) {
            this.f4722a = str;
            this.f4723b = bundle;
        }
    }

    static class MediaBrowserServiceAdaptor extends MediaBrowserService {

        /* renamed from: a, reason: collision with root package name */
        final ServiceCompatProxy f4724a;

        MediaBrowserServiceAdaptor(Context context, ServiceCompatProxy serviceCompatProxy) {
            attachBaseContext(context);
            this.f4724a = serviceCompatProxy;
        }

        @Override // android.service.media.MediaBrowserService
        public MediaBrowserService.BrowserRoot onGetRoot(String str, int i2, Bundle bundle) {
            MediaSessionCompat.ensureClassLoader(bundle);
            BrowserRoot browserRootOnGetRoot = this.f4724a.onGetRoot(str, i2, bundle == null ? null : new Bundle(bundle));
            if (browserRootOnGetRoot == null) {
                return null;
            }
            return new MediaBrowserService.BrowserRoot(browserRootOnGetRoot.f4722a, browserRootOnGetRoot.f4723b);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
            this.f4724a.onLoadChildren(str, new ResultWrapper<>(result));
        }
    }

    static class ResultWrapper<T> {

        /* renamed from: a, reason: collision with root package name */
        MediaBrowserService.Result f4725a;

        ResultWrapper(MediaBrowserService.Result result) {
            this.f4725a = result;
        }

        List a(List list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Parcel parcel = (Parcel) it.next();
                parcel.setDataPosition(0);
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }

        public void detach() {
            this.f4725a.detach();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void sendResult(T t2) {
            if (t2 instanceof List) {
                this.f4725a.sendResult(a((List) t2));
                return;
            }
            if (!(t2 instanceof Parcel)) {
                this.f4725a.sendResult(null);
                return;
            }
            Parcel parcel = (Parcel) t2;
            parcel.setDataPosition(0);
            this.f4725a.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
            parcel.recycle();
        }
    }

    public interface ServiceCompatProxy {
        BrowserRoot onGetRoot(String str, int i2, Bundle bundle);

        void onLoadChildren(String str, ResultWrapper<List<Parcel>> resultWrapper);
    }

    private MediaBrowserServiceCompatApi21() {
    }

    public static Object createService(Context context, ServiceCompatProxy serviceCompatProxy) {
        return new MediaBrowserServiceAdaptor(context, serviceCompatProxy);
    }

    public static void notifyChildrenChanged(Object obj, String str) {
        ((MediaBrowserService) obj).notifyChildrenChanged(str);
    }

    public static IBinder onBind(Object obj, Intent intent) {
        return ((MediaBrowserService) obj).onBind(intent);
    }

    public static void onCreate(Object obj) {
        ((MediaBrowserService) obj).onCreate();
    }

    public static void setSessionToken(Object obj, Object obj2) {
        ((MediaBrowserService) obj).setSessionToken((MediaSession.Token) obj2);
    }
}
