package com.yc.utesdk.watchface.bean.acts;

import java.util.List;

/* loaded from: classes4.dex */
public class WatchFaceInfo {
    public static final int NETWORK_DOMESTIC_LIVE = 1;
    public static final int NETWORK_DOMESTIC_TEST = 2;
    public static final int NETWORK_FOREIGN_LIVE = 3;
    public static final int NETWORK_FOREIGN_TEST = 4;
    public static final int WATCH_FACE_CURRENT = 1;
    public static final int WATCH_FACE_GOOGLE_PRESET = 16;
    public static final int WATCH_FACE_PHOTO = 32;
    public static final int WATCH_FACE_PRESET = 2;
    public static final int WATCH_FACE_SUPPORT_CUSTOMIZATION = 8;
    public static final int WATCH_FACE_SUPPORT_DELETION = 4;
    private int serverMode;
    private List<WatchFace> workFaceList;

    public static class WatchFace {
        private String id;
        private int type;
        private String version;

        public String getId() {
            return this.id;
        }

        public int getType() {
            return this.type;
        }

        public String getVersion() {
            return this.version;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setType(int i2) {
            this.type = i2;
        }

        public void setVersion(String str) {
            this.version = str;
        }
    }

    public int getServerMode() {
        return this.serverMode;
    }

    public List<WatchFace> getWorkFaceList() {
        return this.workFaceList;
    }

    public void setServerMode(int i2) {
        this.serverMode = i2;
    }

    public void setWorkFaceList(List<WatchFace> list) {
        this.workFaceList = list;
    }
}
