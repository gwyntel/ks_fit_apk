package io.flutter.plugins.webviewflutter;

import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
class DisplayListenerProxy {
    private static final String TAG = "DisplayListenerProxy";
    private ArrayList<DisplayManager.DisplayListener> listenersBeforeWebView;

    DisplayListenerProxy() {
    }

    private static ArrayList<DisplayManager.DisplayListener> yoinkDisplayListeners(DisplayManager displayManager) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 28) {
            return new ArrayList<>();
        }
        try {
            Field declaredField = DisplayManager.class.getDeclaredField("mGlobal");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(displayManager);
            Field declaredField2 = obj.getClass().getDeclaredField("mDisplayListeners");
            declaredField2.setAccessible(true);
            ArrayList arrayList = (ArrayList) declaredField2.get(obj);
            ArrayList<DisplayManager.DisplayListener> arrayList2 = new ArrayList<>();
            Iterator it = arrayList.iterator();
            Field field = null;
            while (it.hasNext()) {
                Object next = it.next();
                if (field == null) {
                    field = next.getClass().getField("mListener");
                    field.setAccessible(true);
                }
                arrayList2.add((DisplayManager.DisplayListener) field.get(next));
            }
            return arrayList2;
        } catch (IllegalAccessException e2) {
            e = e2;
            Log.w(TAG, "Could not extract WebView's display listeners. " + e);
            return new ArrayList<>();
        } catch (NoSuchFieldException e3) {
            e = e3;
            Log.w(TAG, "Could not extract WebView's display listeners. " + e);
            return new ArrayList<>();
        }
    }

    void onPostWebViewInitialization(final DisplayManager displayManager) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        final ArrayList<DisplayManager.DisplayListener> arrayListYoinkDisplayListeners = yoinkDisplayListeners(displayManager);
        arrayListYoinkDisplayListeners.removeAll(this.listenersBeforeWebView);
        if (arrayListYoinkDisplayListeners.isEmpty()) {
            return;
        }
        Iterator<DisplayManager.DisplayListener> it = arrayListYoinkDisplayListeners.iterator();
        while (it.hasNext()) {
            displayManager.unregisterDisplayListener(it.next());
            displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: io.flutter.plugins.webviewflutter.DisplayListenerProxy.1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i2) {
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayAdded(i2);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i2) {
                    if (displayManager.getDisplay(i2) == null) {
                        return;
                    }
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayChanged(i2);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i2) {
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayRemoved(i2);
                    }
                }
            }, null);
        }
    }

    void onPreWebViewInitialization(DisplayManager displayManager) {
        this.listenersBeforeWebView = yoinkDisplayListeners(displayManager);
    }
}
