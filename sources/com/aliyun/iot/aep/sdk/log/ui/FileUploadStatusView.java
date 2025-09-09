package com.aliyun.iot.aep.sdk.log.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.aliyun.iot.aep.sdk.log.R;
import java.io.File;

/* loaded from: classes3.dex */
public class FileUploadStatusView extends ViewGroup {
    private Context mContext;
    private File mFile;
    private View mView;

    public FileUploadStatusView(Context context) {
        super(context);
        this.mContext = context;
        this.mView = View.inflate(context, R.layout.fileupload_status_layout, null);
    }

    public void attachFile(File file) {
        this.mFile = file;
        ((TextView) this.mView.findViewById(R.id.uploadFileName)).setText(file.getName());
    }

    public View getContentView() {
        return this.mView;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
    }

    public void setProgress(int i2) {
        ((TextView) this.mView.findViewById(R.id.uploadFileProgress)).setText(Integer.toString(i2) + "%");
        if (i2 == 100) {
            this.mView.findViewById(R.id.uploadFileComplete).setVisibility(0);
        }
    }
}
