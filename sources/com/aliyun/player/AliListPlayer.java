package com.aliyun.player;

import com.aliyun.player.source.PlayAuthInfo;
import com.aliyun.player.source.StsInfo;

/* loaded from: classes3.dex */
public interface AliListPlayer extends UrlListPlayer, AliPlayer {
    void addVid(String str, String str2);

    boolean moveTo(String str, PlayAuthInfo playAuthInfo);

    boolean moveTo(String str, StsInfo stsInfo);

    boolean moveToNext(PlayAuthInfo playAuthInfo);

    boolean moveToNext(StsInfo stsInfo);

    boolean moveToNextWithPrerendered(PlayAuthInfo playAuthInfo);

    boolean moveToNextWithPrerendered(StsInfo stsInfo);

    boolean moveToPrev(PlayAuthInfo playAuthInfo);

    boolean moveToPrev(StsInfo stsInfo);

    void setDefinition(String str);
}
