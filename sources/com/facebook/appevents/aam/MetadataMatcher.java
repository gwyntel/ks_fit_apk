package com.facebook.appevents.aam;

import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
final class MetadataMatcher {
    private static final int MAX_INDICATOR_LENGTH = 100;
    private static final String TAG = "com.facebook.appevents.aam.MetadataMatcher";

    MetadataMatcher() {
    }

    static List<String> getCurrentViewIndicators(View view) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add(ViewHierarchy.getHintOfView(view));
        Object tag = view.getTag();
        if (tag != null) {
            arrayList.add(tag.toString());
        }
        CharSequence contentDescription = view.getContentDescription();
        if (contentDescription != null) {
            arrayList.add(contentDescription.toString());
        }
        try {
            if (view.getId() != -1) {
                String[] strArrSplit = view.getResources().getResourceName(view.getId()).split("/");
                if (strArrSplit.length == 2) {
                    arrayList.add(strArrSplit[1]);
                }
            }
        } catch (Resources.NotFoundException unused) {
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str : arrayList) {
            if (!str.isEmpty() && str.length() <= 100) {
                arrayList2.add(str.toLowerCase());
            }
        }
        return arrayList2;
    }

    static List<String> getTextIndicators(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof EditText) {
            return arrayList;
        }
        if (!(view instanceof TextView)) {
            Iterator<View> it = ViewHierarchy.getChildrenOfView(view).iterator();
            while (it.hasNext()) {
                arrayList.addAll(getTextIndicators(it.next()));
            }
            return arrayList;
        }
        String string = ((TextView) view).getText().toString();
        if (!string.isEmpty() && string.length() < 100) {
            arrayList.add(string.toLowerCase());
        }
        return arrayList;
    }

    static boolean matchIndicator(List<String> list, List<String> list2) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (matchIndicator(it.next(), list2)) {
                return true;
            }
        }
        return false;
    }

    static boolean matchValue(String str, String str2) {
        return str.matches(str2);
    }

    static boolean matchIndicator(String str, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return true;
            }
        }
        return false;
    }
}
