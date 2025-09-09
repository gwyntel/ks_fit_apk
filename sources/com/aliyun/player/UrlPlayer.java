package com.aliyun.player;

import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.BitStreamSource;
import com.aliyun.player.source.UrlSource;

/* loaded from: classes3.dex */
public interface UrlPlayer extends IPlayer {
    void enableDowngrade(UrlSource urlSource, PlayerConfig playerConfig);

    void setDataSource(BitStreamSource bitStreamSource);

    void setDataSource(UrlSource urlSource);
}
