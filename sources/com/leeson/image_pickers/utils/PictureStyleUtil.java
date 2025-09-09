package com.leeson.image_pickers.utils;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import com.leeson.image_pickers.R;
import com.luck.picture.lib.style.AlbumWindowStyle;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import java.util.Map;

/* loaded from: classes4.dex */
public class PictureStyleUtil {
    private Context context;
    private PictureSelectorStyle selectorStyle = new PictureSelectorStyle();

    public PictureStyleUtil(Context context) {
        this.context = context;
    }

    public PictureSelectorStyle getSelectorStyle() {
        return this.selectorStyle;
    }

    public void setStyle(Map<String, Number> map) {
        Number number = map.get("a");
        int iIntValue = number != null ? number.intValue() : 255;
        Number number2 = map.get("r");
        int iIntValue2 = number2 != null ? number2.intValue() : 255;
        Number number3 = map.get("g");
        int iIntValue3 = number3 != null ? number3.intValue() : 255;
        Number number4 = map.get("b");
        int iIntValue4 = number4 != null ? number4.intValue() : 255;
        Number number5 = map.get("l");
        int iIntValue5 = number5 != null ? number5.intValue() : 255;
        int iArgb = Color.argb(iIntValue, iIntValue2, iIntValue3, iIntValue4);
        AlbumWindowStyle albumWindowStyle = this.selectorStyle.getAlbumWindowStyle();
        this.selectorStyle.setAlbumWindowStyle(albumWindowStyle);
        TitleBarStyle titleBarStyle = this.selectorStyle.getTitleBarStyle();
        titleBarStyle.setTitleBackgroundColor(iArgb);
        this.selectorStyle.setTitleBarStyle(titleBarStyle);
        BottomNavBarStyle bottomBarStyle = this.selectorStyle.getBottomBarStyle();
        bottomBarStyle.setBottomNarBarBackgroundColor(iArgb);
        bottomBarStyle.setBottomPreviewNarBarBackgroundColor(iArgb);
        this.selectorStyle.setBottomBarStyle(bottomBarStyle);
        SelectMainStyle selectMainStyle = this.selectorStyle.getSelectMainStyle();
        selectMainStyle.setStatusBarColor(iArgb);
        selectMainStyle.setSelectNumberStyle(true);
        this.selectorStyle.setSelectMainStyle(selectMainStyle);
        albumWindowStyle.setAlbumAdapterItemSelectStyle(R.drawable.num_oval_black);
        if (iIntValue5 > 178) {
            int color = ContextCompat.getColor(this.context, R.color.bar_grey);
            titleBarStyle.setTitleLeftBackResource(com.luck.picture.lib.R.drawable.ps_ic_black_back);
            titleBarStyle.setTitleTextColor(color);
            titleBarStyle.setTitleCancelTextColor(color);
            bottomBarStyle.setBottomPreviewSelectTextColor(color);
            bottomBarStyle.setBottomEditorTextColor(color);
            bottomBarStyle.setBottomOriginalTextColor(color);
            bottomBarStyle.setBottomPreviewNormalTextColor(color);
            bottomBarStyle.setBottomSelectNumTextColor(color);
            selectMainStyle.setSelectTextColor(color);
        } else {
            int color2 = ContextCompat.getColor(this.context, R.color.white);
            titleBarStyle.setTitleLeftBackResource(com.luck.picture.lib.R.drawable.ps_ic_back);
            titleBarStyle.setTitleTextColor(color2);
            titleBarStyle.setTitleCancelTextColor(color2);
            bottomBarStyle.setBottomPreviewSelectTextColor(color2);
            bottomBarStyle.setBottomEditorTextColor(color2);
            bottomBarStyle.setBottomOriginalTextColor(color2);
            bottomBarStyle.setBottomPreviewNormalTextColor(color2);
            bottomBarStyle.setBottomSelectNumTextColor(color2);
            selectMainStyle.setSelectTextColor(color2);
        }
        bottomBarStyle.setBottomSelectNumResources(R.drawable.num_oval_black_def);
        selectMainStyle.setPreviewBackgroundColor(ContextCompat.getColor(this.context, R.color.white));
        selectMainStyle.setAdapterSelectTextColor(ContextCompat.getColor(this.context, R.color.white));
        selectMainStyle.setSelectBackground(R.drawable.num_oval_black_def);
        selectMainStyle.setSelectNormalTextColor(ContextCompat.getColor(this.context, R.color.white));
        selectMainStyle.setPreviewSelectNumberStyle(true);
    }
}
