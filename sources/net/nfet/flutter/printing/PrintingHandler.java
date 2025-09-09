package net.nfet.flutter.printing;

import android.content.Context;
import android.print.PrintAttributes;
import androidx.annotation.NonNull;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class PrintingHandler implements MethodChannel.MethodCallHandler {
    private final MethodChannel channel;
    private final Context context;

    PrintingHandler(Context context, MethodChannel methodChannel) {
        this.context = context;
        this.channel = methodChannel;
    }

    void a(PrintingJob printingJob, boolean z2, String str) {
        HashMap map = new HashMap();
        map.put("completed", Boolean.valueOf(z2));
        map.put("error", str);
        map.put("job", Integer.valueOf(printingJob.f26041a));
        this.channel.invokeMethod("onCompleted", map);
    }

    void b(PrintingJob printingJob, String str) {
        HashMap map = new HashMap();
        map.put("error", str);
        map.put("job", Integer.valueOf(printingJob.f26041a));
        this.channel.invokeMethod("onHtmlError", map);
    }

    void c(PrintingJob printingJob, byte[] bArr) {
        HashMap map = new HashMap();
        map.put("doc", bArr);
        map.put("job", Integer.valueOf(printingJob.f26041a));
        this.channel.invokeMethod("onHtmlRendered", map);
    }

    void d(final PrintingJob printingJob, Double d2, double d3, double d4, double d5, double d6, double d7) {
        HashMap map = new HashMap();
        map.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, d2);
        map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, Double.valueOf(d3));
        map.put("marginLeft", Double.valueOf(d4));
        map.put("marginTop", Double.valueOf(d5));
        map.put("marginRight", Double.valueOf(d6));
        map.put("marginBottom", Double.valueOf(d7));
        map.put("job", Integer.valueOf(printingJob.f26041a));
        this.channel.invokeMethod("onLayout", map, new MethodChannel.Result() { // from class: net.nfet.flutter.printing.PrintingHandler.1
            @Override // io.flutter.plugin.common.MethodChannel.Result
            public void error(@NonNull String str, String str2, Object obj) {
                printingJob.k(str2);
            }

            @Override // io.flutter.plugin.common.MethodChannel.Result
            public void notImplemented() {
                printingJob.k("notImplemented");
            }

            @Override // io.flutter.plugin.common.MethodChannel.Result
            public void success(Object obj) {
                if (obj instanceof byte[]) {
                    printingJob.q((byte[]) obj);
                } else {
                    printingJob.k("Unknown data received");
                }
            }
        });
    }

    void e(PrintingJob printingJob, String str) {
        HashMap map = new HashMap();
        map.put("job", Integer.valueOf(printingJob.f26041a));
        if (str != null) {
            map.put("error", str);
        }
        this.channel.invokeMethod("onPageRasterEnd", map);
    }

    void f(PrintingJob printingJob, byte[] bArr, int i2, int i3) {
        HashMap map = new HashMap();
        map.put("image", bArr);
        map.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, Integer.valueOf(i2));
        map.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, Integer.valueOf(i3));
        map.put("job", Integer.valueOf(printingJob.f26041a));
        this.channel.invokeMethod("onPageRasterized", map);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) throws IOException {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "printPdf":
                new PrintingJob(this.context, this, ((Integer) methodCall.argument("job")).intValue()).n((String) methodCall.argument("name"), (Double) methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY), (Double) methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY));
                result.success(1);
                break;
            case "convertHtml":
                Double d2 = (Double) methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
                Double d3 = (Double) methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
                Double d4 = (Double) methodCall.argument("marginLeft");
                Double d5 = (Double) methodCall.argument("marginTop");
                Double d6 = (Double) methodCall.argument("marginRight");
                Double d7 = (Double) methodCall.argument("marginBottom");
                PrintingJob printingJob = new PrintingJob(this.context, this, ((Integer) methodCall.argument("job")).intValue());
                PrintAttributes.Margins margins = new PrintAttributes.Margins(Double.valueOf(d4.doubleValue() * 1000.0d).intValue(), Double.valueOf((d5.doubleValue() * 1000.0d) / 72.0d).intValue(), Double.valueOf((d6.doubleValue() * 1000.0d) / 72.0d).intValue(), Double.valueOf((d7.doubleValue() * 1000.0d) / 72.0d).intValue());
                printingJob.l((String) methodCall.argument("html"), new PrintAttributes.MediaSize("flutter_printing", "Provided size", Double.valueOf((d2.doubleValue() * 1000.0d) / 72.0d).intValue(), Double.valueOf((d3.doubleValue() * 1000.0d) / 72.0d).intValue()), margins, (String) methodCall.argument("baseUrl"));
                result.success(1);
                break;
            case "sharePdf":
                PrintingJob.r(this.context, (byte[]) methodCall.argument("doc"), (String) methodCall.argument("name"), (String) methodCall.argument("subject"), (String) methodCall.argument("body"), (ArrayList) methodCall.argument("emails"));
                result.success(1);
                break;
            case "rasterPdf":
                new PrintingJob(this.context, this, ((Integer) methodCall.argument("job")).intValue()).p((byte[]) methodCall.argument("doc"), (ArrayList) methodCall.argument(com.umeng.analytics.pro.f.f21698t), (Double) methodCall.argument("scale"));
                result.success(1);
                break;
            case "printingInfo":
                result.success(PrintingJob.o());
                break;
            case "cancelJob":
                new PrintingJob(this.context, this, ((Integer) methodCall.argument("job")).intValue()).k(null);
                result.success(1);
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
