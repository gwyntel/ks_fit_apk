package meshprovisioner.configuration.bean;

import android.content.Context;
import android.util.Pair;
import com.alibaba.ailabs.iot.mesh.R;

/* loaded from: classes5.dex */
public class SceneStatus {
    public short currentScene;
    public int status;
    public Short targetScene;

    public SceneStatus(int i2, short s2, Short sh) {
        this.status = i2;
        this.currentScene = s2;
        this.targetScene = sh;
    }

    public static String parseStatusMessage(Context context, int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? context.getString(R.string.status_scene_reserved) : context.getString(R.string.status_scene_not_found) : context.getString(R.string.status_scene_register_full) : context.getString(R.string.model_subscription_success);
    }

    public short getCurrentScene() {
        return this.currentScene;
    }

    public int getStatus() {
        return this.status;
    }

    public Short getTargetScene() {
        return this.targetScene;
    }

    public Pair<Integer, Object> parseStatus(Context context) {
        int i2 = this.status;
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? new Pair<>(-52, context.getString(R.string.status_scene_reserved)) : new Pair<>(-51, context.getString(R.string.status_scene_not_found)) : new Pair<>(-50, context.getString(R.string.status_scene_register_full)) : new Pair<>(0, Boolean.TRUE);
    }

    public void setCurrentScene(short s2) {
        this.currentScene = s2;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setTargetScene(Short sh) {
        this.targetScene = sh;
    }
}
