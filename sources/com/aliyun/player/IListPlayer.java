package com.aliyun.player;

/* loaded from: classes3.dex */
public interface IListPlayer extends IPlayer {

    public enum MultiBitratesMode {
        MultiBitratesMode_Default(0),
        MultiBitratesMode_FCPrio(1),
        MultiBitratesMode_FC_AND_SMOOTH(2),
        MultiBitratesMode_SmoothPrio(3);

        private int mValue;

        MultiBitratesMode(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum SceneType {
        SCENE_NONE(-1),
        SCENE_VERY_SHORT(0),
        SCENE_SHORT(1),
        SCENE_MIDDLE(2),
        SCENE_LONG(3);

        private int mValue;

        SceneType(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public enum StrategyType {
        STRATEGY_DYNAMIC_PRELOAD_DURATION(1);

        private int mValue;

        StrategyType(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    MultiBitratesMode GetMultiBitratesMode();

    void SetMultiBitratesMode(MultiBitratesMode multiBitratesMode);

    void clear();

    void enablePreloadStrategy(StrategyType strategyType, boolean z2);

    String getCurrentUid();

    int getMaxPreloadMemorySizeMB();

    void removeSource(String str);

    void setMaxPreloadMemorySizeMB(int i2);

    void setPreloadCount(int i2);

    void setPreloadCount(int i2, int i3);

    void setPreloadScene(SceneType sceneType);

    void setPreloadStrategy(StrategyType strategyType, String str);
}
