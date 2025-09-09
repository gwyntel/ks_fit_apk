package meshprovisioner.configuration.bean;

import android.content.Context;
import android.util.Pair;
import com.alibaba.ailabs.iot.mesh.R;

/* loaded from: classes5.dex */
public class SceneRegisterStatus {
    public short currentScene;
    public byte[] scenes;
    public int status;

    public SceneRegisterStatus(int i2, short s2, byte[] bArr) {
        this.status = i2;
        this.currentScene = s2;
        this.scenes = bArr;
    }

    public short getCurrentScene() {
        return this.currentScene;
    }

    public byte[] getScenes() {
        return this.scenes;
    }

    public int getStatus() {
        return this.status;
    }

    public Pair<Integer, Object> parseStatus(Context context) {
        int i2 = this.status;
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? new Pair<>(-52, context.getString(R.string.status_scene_reserved)) : new Pair<>(-51, context.getString(R.string.status_scene_not_found)) : new Pair<>(-50, context.getString(R.string.status_scene_register_full)) : new Pair<>(0, Boolean.TRUE);
    }

    public void setCurrentScene(short s2) {
        this.currentScene = s2;
    }

    public void setScenes(byte[] bArr) {
        this.scenes = bArr;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }
}
