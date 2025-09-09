package com.github.barteksc.pdfviewer.link;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;

/* loaded from: classes3.dex */
public class DefaultLinkHandler implements LinkHandler {
    private static final String TAG = "DefaultLinkHandler";
    private PDFView pdfView;

    public DefaultLinkHandler(PDFView pDFView) {
        this.pdfView = pDFView;
    }

    private void handlePage(int i2) {
        this.pdfView.jumpTo(i2);
    }

    private void handleUri(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        Context context = this.pdfView.getContext();
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return;
        }
        Log.w(TAG, "No activity found for URI: " + str);
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
}
