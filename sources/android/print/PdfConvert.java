package android.print;

import android.content.Context;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PrintDocumentAdapter;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RequiresApi(api = 19)
/* loaded from: classes.dex */
public class PdfConvert {

    public interface Result {
        void onError(String str);

        void onSuccess(File file);
    }

    public static void print(final Context context, final PrintDocumentAdapter printDocumentAdapter, PrintAttributes printAttributes, final Result result) {
        printDocumentAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() { // from class: android.print.PdfConvert.1
            @Override // android.print.PrintDocumentAdapter.LayoutResultCallback
            public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z2) throws IOException {
                try {
                    final File fileCreateTempFile = File.createTempFile("printing", "pdf", context.getCacheDir());
                    try {
                        printDocumentAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, ParcelFileDescriptor.open(fileCreateTempFile, 805306368), new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() { // from class: android.print.PdfConvert.1.1
                            @Override // android.print.PrintDocumentAdapter.WriteResultCallback
                            public void onWriteFinished(PageRange[] pageRangeArr) {
                                super.onWriteFinished(pageRangeArr);
                                if (pageRangeArr.length == 0) {
                                    if (!fileCreateTempFile.delete()) {
                                        Log.e("PDF", "Unable to delete temporary file");
                                    }
                                    result.onError("No page created");
                                }
                                result.onSuccess(fileCreateTempFile);
                                if (fileCreateTempFile.delete()) {
                                    return;
                                }
                                Log.e("PDF", "Unable to delete temporary file");
                            }
                        });
                    } catch (FileNotFoundException e2) {
                        if (!fileCreateTempFile.delete()) {
                            Log.e("PDF", "Unable to delete temporary file");
                        }
                        result.onError(e2.getMessage());
                    }
                } catch (IOException e3) {
                    result.onError(e3.getMessage());
                }
            }
        }, null);
    }

    public static byte[] readFile(File file) throws IOException {
        byte[] bArr = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            if (fileInputStream.read(bArr) == -1) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
            fileInputStream.close();
            return bArr;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
