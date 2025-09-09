package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class KeyFrames {
    private static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    private static final String CUSTOM_METHOD = "CustomMethod";
    private static final String TAG = "KeyFrames";
    public static final int UNSET = -1;

    /* renamed from: a, reason: collision with root package name */
    static HashMap f3002a;
    private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap<>();

    static {
        HashMap map = new HashMap();
        f3002a = map;
        try {
            map.put("KeyAttribute", KeyAttributes.class.getConstructor(null));
            f3002a.put("KeyPosition", KeyPosition.class.getConstructor(null));
            f3002a.put("KeyCycle", KeyCycle.class.getConstructor(null));
            f3002a.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(null));
            f3002a.put("KeyTrigger", KeyTrigger.class.getConstructor(null));
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "unable to load", e2);
        }
    }

    public KeyFrames() {
    }

    public void addAllFrames(MotionController motionController) {
        ArrayList<Key> arrayList = this.mFramesMap.get(-1);
        if (arrayList != null) {
            motionController.a(arrayList);
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList<Key> arrayList = this.mFramesMap.get(Integer.valueOf(motionController.f3032c));
        if (arrayList != null) {
            motionController.a(arrayList);
        }
        ArrayList<Key> arrayList2 = this.mFramesMap.get(-1);
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            while (it.hasNext()) {
                Key next = it.next();
                if (next.a(((ConstraintLayout.LayoutParams) motionController.f3031b.getLayoutParams()).constraintTag)) {
                    motionController.addKey(next);
                }
            }
        }
    }

    public void addKey(Key key) {
        if (!this.mFramesMap.containsKey(Integer.valueOf(key.f2998b))) {
            this.mFramesMap.put(Integer.valueOf(key.f2998b), new ArrayList<>());
        }
        ArrayList<Key> arrayList = this.mFramesMap.get(Integer.valueOf(key.f2998b));
        if (arrayList != null) {
            arrayList.add(key);
        }
    }

    public ArrayList<Key> getKeyFramesForView(int i2) {
        return this.mFramesMap.get(Integer.valueOf(i2));
    }

    public Set<Integer> getKeys() {
        return this.mFramesMap.keySet();
    }

    public KeyFrames(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Exception e2;
        Key key;
        Constructor constructor;
        HashMap map;
        HashMap map2;
        try {
            int eventType = xmlPullParser.getEventType();
            Key key2 = null;
            while (eventType != 1) {
                if (eventType != 2) {
                    if (eventType == 3 && ViewTransition.KEY_FRAME_SET_TAG.equals(xmlPullParser.getName())) {
                        return;
                    }
                } else {
                    String name = xmlPullParser.getName();
                    if (f3002a.containsKey(name)) {
                        try {
                            constructor = (Constructor) f3002a.get(name);
                        } catch (Exception e3) {
                            Key key3 = key2;
                            e2 = e3;
                            key = key3;
                        }
                        if (constructor != null) {
                            key = (Key) constructor.newInstance(null);
                            try {
                                key.load(context, Xml.asAttributeSet(xmlPullParser));
                                addKey(key);
                            } catch (Exception e4) {
                                e2 = e4;
                                Log.e(TAG, "unable to create ", e2);
                                key2 = key;
                                eventType = xmlPullParser.next();
                            }
                            key2 = key;
                        } else {
                            throw new NullPointerException("Keymaker for " + name + " not found");
                        }
                    } else if (name.equalsIgnoreCase("CustomAttribute")) {
                        if (key2 != null && (map2 = key2.f3001e) != null) {
                            ConstraintAttribute.parse(context, xmlPullParser, map2);
                        }
                    } else if (name.equalsIgnoreCase("CustomMethod") && key2 != null && (map = key2.f3001e) != null) {
                        ConstraintAttribute.parse(context, xmlPullParser, map);
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        } catch (XmlPullParserException e6) {
            e6.printStackTrace();
        }
    }
}
