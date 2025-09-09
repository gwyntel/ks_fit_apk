package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(23)
/* loaded from: classes.dex */
class MediaBrowserCompatApi23 {

    interface ItemCallback {
        void onError(@NonNull String str);

        void onItemLoaded(Parcel parcel);
    }

    static class ItemCallbackProxy<T extends ItemCallback> extends MediaBrowser.ItemCallback {

        /* renamed from: a, reason: collision with root package name */
        protected final ItemCallback f1791a;

        public ItemCallbackProxy(T t2) {
            this.f1791a = t2;
        }

        @Override // android.media.browse.MediaBrowser.ItemCallback
        public void onError(@NonNull String str) {
            this.f1791a.onError(str);
        }

        @Override // android.media.browse.MediaBrowser.ItemCallback
        public void onItemLoaded(MediaBrowser.MediaItem mediaItem) {
            if (mediaItem == null) {
                this.f1791a.onItemLoaded(null);
                return;
            }
            Parcel parcelObtain = Parcel.obtain();
            mediaItem.writeToParcel(parcelObtain, 0);
            this.f1791a.onItemLoaded(parcelObtain);
        }
    }

    private MediaBrowserCompatApi23() {
    }

    public static Object createItemCallback(ItemCallback itemCallback) {
        return new ItemCallbackProxy(itemCallback);
    }

    public static void getItem(Object obj, String str, Object obj2) {
        ((MediaBrowser) obj).getItem(str, (MediaBrowser.ItemCallback) obj2);
    }
}
