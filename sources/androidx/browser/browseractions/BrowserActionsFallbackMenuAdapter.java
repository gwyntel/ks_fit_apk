package androidx.browser.browseractions;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.browser.R;
import androidx.core.content.res.ResourcesCompat;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Deprecated
/* loaded from: classes.dex */
class BrowserActionsFallbackMenuAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<BrowserActionItem> mMenuItems;

    static class ViewHolderItem {

        /* renamed from: a, reason: collision with root package name */
        final ImageView f2387a;

        /* renamed from: b, reason: collision with root package name */
        final TextView f2388b;

        ViewHolderItem(ImageView imageView, TextView textView) {
            this.f2387a = imageView;
            this.f2388b = textView;
        }
    }

    BrowserActionsFallbackMenuAdapter(List list, Context context) {
        this.mMenuItems = list;
        this.mContext = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mMenuItems.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        return this.mMenuItems.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        final ViewHolderItem viewHolderItem;
        BrowserActionItem browserActionItem = this.mMenuItems.get(i2);
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.browser_actions_context_menu_row, (ViewGroup) null);
            ImageView imageView = (ImageView) view.findViewById(R.id.browser_actions_menu_item_icon);
            TextView textView = (TextView) view.findViewById(R.id.browser_actions_menu_item_text);
            if (imageView == null || textView == null) {
                throw new IllegalStateException("Browser Actions fallback UI does not contain necessary Views.");
            }
            viewHolderItem = new ViewHolderItem(imageView, textView);
            view.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolderItem) view.getTag();
        }
        final String title = browserActionItem.getTitle();
        viewHolderItem.f2388b.setText(title);
        if (browserActionItem.getIconId() != 0) {
            viewHolderItem.f2387a.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), browserActionItem.getIconId(), null));
        } else if (browserActionItem.getIconUri() != null) {
            final ListenableFuture<Bitmap> listenableFutureLoadBitmap = BrowserServiceFileProvider.loadBitmap(this.mContext.getContentResolver(), browserActionItem.getIconUri());
            listenableFutureLoadBitmap.addListener(new Runnable() { // from class: androidx.browser.browseractions.BrowserActionsFallbackMenuAdapter.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    Bitmap bitmap;
                    if (TextUtils.equals(title, viewHolderItem.f2388b.getText())) {
                        try {
                            bitmap = (Bitmap) listenableFutureLoadBitmap.get();
                        } catch (InterruptedException | ExecutionException unused) {
                            bitmap = null;
                        }
                        if (bitmap != null) {
                            viewHolderItem.f2387a.setVisibility(0);
                            viewHolderItem.f2387a.setImageBitmap(bitmap);
                        } else {
                            viewHolderItem.f2387a.setVisibility(4);
                            viewHolderItem.f2387a.setImageBitmap(null);
                        }
                    }
                }
            }, new Executor() { // from class: androidx.browser.browseractions.BrowserActionsFallbackMenuAdapter.2
                @Override // java.util.concurrent.Executor
                public void execute(@NonNull Runnable runnable) {
                    runnable.run();
                }
            });
        } else {
            viewHolderItem.f2387a.setImageBitmap(null);
            viewHolderItem.f2387a.setVisibility(4);
        }
        return view;
    }
}
