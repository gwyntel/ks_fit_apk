package io.endigo.plugins.pdfviewflutter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes4.dex */
public class PDFLinkHandler implements LinkHandler {

    /* renamed from: a, reason: collision with root package name */
    PDFView f25035a;

    /* renamed from: b, reason: collision with root package name */
    Context f25036b;

    /* renamed from: c, reason: collision with root package name */
    MethodChannel f25037c;

    /* renamed from: d, reason: collision with root package name */
    boolean f25038d;

    public PDFLinkHandler(Context context, PDFView pDFView, MethodChannel methodChannel, boolean z2) {
        this.f25036b = context;
        this.f25035a = pDFView;
        this.f25037c = methodChannel;
        this.f25038d = z2;
    }

    private void handlePage(int i2) {
        this.f25035a.jumpTo(i2);
    }

    private void handleUri(String str) {
        if (!this.f25038d) {
            Uri uri = Uri.parse(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(uri);
            intent.addFlags(268435456);
            intent.addFlags(32768);
            if (intent.resolveActivity(this.f25036b.getPackageManager()) != null) {
                this.f25036b.startActivity(intent, null);
            }
        }
        onLinkHandler(str);
    }

    private void onLinkHandler(String str) {
        this.f25037c.invokeMethod("onLinkHandler", str);
    }

    @Override // com.github.barteksc.pdfviewer.link.LinkHandler
    public void handleLinkEvent(LinkTapEvent linkTapEvent) {
        String uri = linkTapEvent.getLink().getUri();
        Integer destPageIdx = linkTapEvent.getLink().getDestPageIdx();
        if (uri != null && !uri.isEmpty()) {
            handleUri(uri);
        } else if (destPageIdx != null) {
            handlePage(destPageIdx.intValue());
        }
    }

    public void setPreventLinkNavigation(boolean z2) {
        this.f25038d = z2;
    }
}
