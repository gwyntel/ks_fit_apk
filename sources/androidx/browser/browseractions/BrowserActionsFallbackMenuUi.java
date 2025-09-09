package androidx.browser.browseractions;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.browser.R;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.widget.TextViewCompat;
import androidx.webkit.internal.AssetHelper;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
class BrowserActionsFallbackMenuUi implements AdapterView.OnItemClickListener {
    private static final String TAG = "BrowserActionskMenuUi";

    /* renamed from: a, reason: collision with root package name */
    final Context f2391a;

    /* renamed from: b, reason: collision with root package name */
    final Uri f2392b;

    /* renamed from: c, reason: collision with root package name */
    BrowserActionsFallMenuUiListener f2393c;

    @Nullable
    private BrowserActionsFallbackMenuDialog mBrowserActionsDialog;
    private final List<BrowserActionItem> mMenuItems;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @VisibleForTesting
    interface BrowserActionsFallMenuUiListener {
        void onMenuShown(View view);
    }

    BrowserActionsFallbackMenuUi(Context context, Uri uri, List list) {
        this.f2391a = context;
        this.f2392b = uri;
        this.mMenuItems = buildFallbackMenuItemList(list);
    }

    private Runnable buildCopyAction() {
        return new Runnable() { // from class: androidx.browser.browseractions.BrowserActionsFallbackMenuUi.1
            @Override // java.lang.Runnable
            public void run() {
                ((ClipboardManager) BrowserActionsFallbackMenuUi.this.f2391a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("url", BrowserActionsFallbackMenuUi.this.f2392b.toString()));
                Toast.makeText(BrowserActionsFallbackMenuUi.this.f2391a, BrowserActionsFallbackMenuUi.this.f2391a.getString(R.string.copy_toast_msg), 0).show();
            }
        };
    }

    @NonNull
    private List<BrowserActionItem> buildFallbackMenuItemList(List<BrowserActionItem> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BrowserActionItem(this.f2391a.getString(R.string.fallback_menu_item_open_in_browser), buildOpenInBrowserAction()));
        arrayList.add(new BrowserActionItem(this.f2391a.getString(R.string.fallback_menu_item_copy_link), buildCopyAction()));
        arrayList.add(new BrowserActionItem(this.f2391a.getString(R.string.fallback_menu_item_share_link), buildShareAction()));
        arrayList.addAll(list);
        return arrayList;
    }

    private PendingIntent buildOpenInBrowserAction() {
        return PendingIntent.getActivity(this.f2391a, 0, new Intent("android.intent.action.VIEW", this.f2392b), AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
    }

    private PendingIntent buildShareAction() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", this.f2392b.toString());
        intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
        return PendingIntent.getActivity(this.f2391a, 0, intent, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
    }

    private BrowserActionsFallbackMenuView initMenuView(View view) {
        BrowserActionsFallbackMenuView browserActionsFallbackMenuView = (BrowserActionsFallbackMenuView) view.findViewById(R.id.browser_actions_menu_view);
        final TextView textView = (TextView) view.findViewById(R.id.browser_actions_header_text);
        textView.setText(this.f2392b.toString());
        textView.setOnClickListener(new View.OnClickListener() { // from class: androidx.browser.browseractions.BrowserActionsFallbackMenuUi.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TextViewCompat.getMaxLines(textView) == Integer.MAX_VALUE) {
                    textView.setMaxLines(1);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                } else {
                    textView.setMaxLines(Integer.MAX_VALUE);
                    textView.setEllipsize(null);
                }
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.browser_actions_menu_items);
        listView.setAdapter((ListAdapter) new BrowserActionsFallbackMenuAdapter(this.mMenuItems, this.f2391a));
        listView.setOnItemClickListener(this);
        return browserActionsFallbackMenuView;
    }

    public void displayMenu() {
        final View viewInflate = LayoutInflater.from(this.f2391a).inflate(R.layout.browser_actions_context_menu_page, (ViewGroup) null);
        BrowserActionsFallbackMenuDialog browserActionsFallbackMenuDialog = new BrowserActionsFallbackMenuDialog(this.f2391a, initMenuView(viewInflate));
        this.mBrowserActionsDialog = browserActionsFallbackMenuDialog;
        browserActionsFallbackMenuDialog.setContentView(viewInflate);
        if (this.f2393c != null) {
            this.mBrowserActionsDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: androidx.browser.browseractions.BrowserActionsFallbackMenuUi.2
                @Override // android.content.DialogInterface.OnShowListener
                public void onShow(DialogInterface dialogInterface) {
                    BrowserActionsFallMenuUiListener browserActionsFallMenuUiListener = BrowserActionsFallbackMenuUi.this.f2393c;
                    if (browserActionsFallMenuUiListener == null) {
                        Log.e(BrowserActionsFallbackMenuUi.TAG, "Cannot trigger menu item listener, it is null");
                    } else {
                        browserActionsFallMenuUiListener.onMenuShown(viewInflate);
                    }
                }
            });
        }
        this.mBrowserActionsDialog.show();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) throws PendingIntent.CanceledException {
        BrowserActionItem browserActionItem = this.mMenuItems.get(i2);
        if (browserActionItem.getAction() != null) {
            try {
                browserActionItem.getAction().send();
            } catch (PendingIntent.CanceledException e2) {
                Log.e(TAG, "Failed to send custom item action", e2);
            }
        } else if (browserActionItem.a() != null) {
            browserActionItem.a().run();
        }
        BrowserActionsFallbackMenuDialog browserActionsFallbackMenuDialog = this.mBrowserActionsDialog;
        if (browserActionsFallbackMenuDialog == null) {
            Log.e(TAG, "Cannot dismiss dialog, it has already been dismissed.");
        } else {
            browserActionsFallbackMenuDialog.dismiss();
        }
    }
}
