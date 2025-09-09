package com.alibaba.ailabs.iot.aisbase.plugin.basic;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ICommandActionListener;
import com.alibaba.ailabs.iot.aisbase.callback.ICommandSendListener;
import com.alibaba.ailabs.iot.aisbase.plugin.IPlugin;

/* loaded from: classes2.dex */
public interface IBasicPlugin extends IPlugin {
    void getManufacturerSpecificData(IActionListener<byte[]> iActionListener);

    void sendCommand(byte[] bArr, ICommandSendListener iCommandSendListener);

    void setCommandActionListener(ICommandActionListener iCommandActionListener);
}
