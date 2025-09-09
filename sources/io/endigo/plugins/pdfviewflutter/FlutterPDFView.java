package io.endigo.plugins.pdfviewflutter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.umeng.analytics.pro.f;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class FlutterPDFView implements PlatformView, MethodChannel.MethodCallHandler {
    private final LinkHandler linkHandler;
    private final MethodChannel methodChannel;
    private final PDFView pdfView;

    FlutterPDFView(Context context, BinaryMessenger binaryMessenger, int i2, Map map) {
        PDFView.Configurator configuratorFromBytes = null;
        PDFView pDFView = new PDFView(context, null);
        this.pdfView = pDFView;
        boolean z2 = getBoolean(map, "preventLinkNavigation");
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "plugins.endigo.io/pdfview_" + i2);
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        PDFLinkHandler pDFLinkHandler = new PDFLinkHandler(context, pDFView, methodChannel, z2);
        this.linkHandler = pDFLinkHandler;
        if (map.get("filePath") != null) {
            configuratorFromBytes = pDFView.fromUri(getURI((String) map.get("filePath")));
        } else if (map.get("pdfData") != null) {
            configuratorFromBytes = pDFView.fromBytes((byte[]) map.get("pdfData"));
        }
        Object obj = map.get(TtmlNode.ATTR_TTS_BACKGROUND_COLOR);
        if (obj != null) {
            pDFView.setBackgroundColor(((Number) obj).intValue());
        }
        if (configuratorFromBytes != null) {
            configuratorFromBytes.enableSwipe(getBoolean(map, "enableSwipe")).swipeHorizontal(getBoolean(map, "swipeHorizontal")).password(getString(map, "password")).nightMode(getBoolean(map, "nightMode")).autoSpacing(getBoolean(map, "autoSpacing")).pageFling(getBoolean(map, "pageFling")).pageSnap(getBoolean(map, "pageSnap")).pageFitPolicy(getFitPolicy(map)).enableAnnotationRendering(true).linkHandler(pDFLinkHandler).enableAntialiasing(false).enableDoubletap(true).defaultPage(getInt(map, "defaultPage")).onPageChange(new OnPageChangeListener() { // from class: io.endigo.plugins.pdfviewflutter.FlutterPDFView.4
                @Override // com.github.barteksc.pdfviewer.listener.OnPageChangeListener
                public void onPageChanged(int i3, int i4) {
                    HashMap map2 = new HashMap();
                    map2.put("page", Integer.valueOf(i3));
                    map2.put(AlinkConstants.KEY_TOTAL, Integer.valueOf(i4));
                    FlutterPDFView.this.methodChannel.invokeMethod("onPageChanged", map2);
                }
            }).onError(new OnErrorListener() { // from class: io.endigo.plugins.pdfviewflutter.FlutterPDFView.3
                @Override // com.github.barteksc.pdfviewer.listener.OnErrorListener
                public void onError(Throwable th) {
                    HashMap map2 = new HashMap();
                    map2.put("error", th.toString());
                    FlutterPDFView.this.methodChannel.invokeMethod("onError", map2);
                }
            }).onPageError(new OnPageErrorListener() { // from class: io.endigo.plugins.pdfviewflutter.FlutterPDFView.2
                @Override // com.github.barteksc.pdfviewer.listener.OnPageErrorListener
                public void onPageError(int i3, Throwable th) {
                    HashMap map2 = new HashMap();
                    map2.put("page", Integer.valueOf(i3));
                    map2.put("error", th.toString());
                    FlutterPDFView.this.methodChannel.invokeMethod("onPageError", map2);
                }
            }).onRender(new OnRenderListener() { // from class: io.endigo.plugins.pdfviewflutter.FlutterPDFView.1
                @Override // com.github.barteksc.pdfviewer.listener.OnRenderListener
                public void onInitiallyRendered(int i3) {
                    HashMap map2 = new HashMap();
                    map2.put(f.f21698t, Integer.valueOf(i3));
                    FlutterPDFView.this.methodChannel.invokeMethod("onRender", map2);
                }
            }).load();
        }
    }

    private void applySettings(Map<String, Object> map) {
        for (String str : map.keySet()) {
            str.hashCode();
            switch (str) {
                case "enableSwipe":
                    this.pdfView.setSwipeEnabled(getBoolean(map, str));
                    break;
                case "preventLinkNavigation":
                    ((PDFLinkHandler) this.linkHandler).setPreventLinkNavigation(getBoolean(map, str));
                    break;
                case "pageSnap":
                    this.pdfView.setPageSnap(getBoolean(map, str));
                    break;
                case "pageFling":
                    this.pdfView.setPageFling(getBoolean(map, str));
                    break;
                case "nightMode":
                    this.pdfView.setNightMode(getBoolean(map, str));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown PDFView setting: " + str);
            }
        }
    }

    private boolean getBoolean(Map<String, Object> map, String str) {
        if (map.containsKey(str)) {
            return ((Boolean) map.get(str)).booleanValue();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.github.barteksc.pdfviewer.util.FitPolicy getFitPolicy(java.util.Map<java.lang.String, java.lang.Object> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "fitPolicy"
            java.lang.String r4 = r3.getString(r4, r0)
            int r0 = r4.hashCode()
            r1 = -1620991877(0xffffffff9f61a07b, float:-4.777835E-20)
            r2 = 1
            if (r0 == r1) goto L2f
            r1 = -191456756(0xfffffffff4969a0c, float:-9.54552E31)
            if (r0 == r1) goto L25
            r1 = 855864562(0x330374f2, float:3.0607175E-8)
            if (r0 == r1) goto L1b
            goto L39
        L1b:
            java.lang.String r0 = "FitPolicy.HEIGHT"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L39
            r4 = r2
            goto L3a
        L25:
            java.lang.String r0 = "FitPolicy.BOTH"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L39
            r4 = 2
            goto L3a
        L2f:
            java.lang.String r0 = "FitPolicy.WIDTH"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L39
            r4 = 0
            goto L3a
        L39:
            r4 = -1
        L3a:
            if (r4 == 0) goto L44
            if (r4 == r2) goto L41
            com.github.barteksc.pdfviewer.util.FitPolicy r4 = com.github.barteksc.pdfviewer.util.FitPolicy.BOTH
            return r4
        L41:
            com.github.barteksc.pdfviewer.util.FitPolicy r4 = com.github.barteksc.pdfviewer.util.FitPolicy.HEIGHT
            return r4
        L44:
            com.github.barteksc.pdfviewer.util.FitPolicy r4 = com.github.barteksc.pdfviewer.util.FitPolicy.WIDTH
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.endigo.plugins.pdfviewflutter.FlutterPDFView.getFitPolicy(java.util.Map):com.github.barteksc.pdfviewer.util.FitPolicy");
    }

    private int getInt(Map<String, Object> map, String str) {
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        return 0;
    }

    private String getString(Map<String, Object> map, String str) {
        return map.containsKey(str) ? (String) map.get(str) : "";
    }

    private Uri getURI(String str) {
        Uri uri = Uri.parse(str);
        return (uri.getScheme() == null || uri.getScheme().isEmpty()) ? Uri.fromFile(new File(str)) : uri;
    }

    private void updateSettings(MethodCall methodCall, MethodChannel.Result result) {
        applySettings((Map) methodCall.arguments);
        result.success(null);
    }

    void b(MethodChannel.Result result) {
        result.success(Integer.valueOf(this.pdfView.getCurrentPage()));
    }

    void c(MethodChannel.Result result) {
        result.success(Integer.valueOf(this.pdfView.getPageCount()));
    }

    void d(MethodCall methodCall, MethodChannel.Result result) {
        if (methodCall.argument("page") != null) {
            this.pdfView.jumpTo(((Integer) methodCall.argument("page")).intValue());
        }
        result.success(Boolean.TRUE);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public void dispose() {
        this.methodChannel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public View getView() {
        return this.pdfView;
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onFlutterViewAttached(View view) {
        io.flutter.plugin.platform.f.a(this, view);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onFlutterViewDetached() {
        io.flutter.plugin.platform.f.b(this);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onInputConnectionLocked() {
        io.flutter.plugin.platform.f.c(this);
    }

    @Override // io.flutter.plugin.platform.PlatformView
    public /* synthetic */ void onInputConnectionUnlocked() {
        io.flutter.plugin.platform.f.d(this);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "updateSettings":
                updateSettings(methodCall, result);
                break;
            case "currentPage":
                b(result);
                break;
            case "pageCount":
                c(result);
                break;
            case "setPage":
                d(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
