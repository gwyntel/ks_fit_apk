package xyz.bczl.flutter_scankit;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.util.ArrayList;
import java.util.Map;
import xyz.bczl.flutter_scankit.ScanKitAPI;

/* loaded from: classes5.dex */
public abstract /* synthetic */ class n {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList.add(0, scanKitApi.createDefaultMode());
        } catch (Throwable th) {
            arrayList = ScanKitAPI.a(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void c(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.disposeDefaultMode(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void d(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        byte[] bArr = (byte[]) arrayList2.get(0);
        Number number = (Number) arrayList2.get(1);
        Number number2 = (Number) arrayList2.get(2);
        Map<String, Object> map = (Map) arrayList2.get(3);
        Long lValueOf2 = null;
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        if (number2 != null) {
            lValueOf2 = Long.valueOf(number2.longValue());
        }
        arrayList.add(0, scanKitApi.decode(bArr, lValueOf, lValueOf2, map));
        reply.reply(arrayList);
    }

    public static /* synthetic */ void e(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        try {
            arrayList.add(0, scanKitApi.decodeBitmap((byte[]) arrayList2.get(0), (Map) arrayList2.get(1)));
        } catch (Throwable th) {
            arrayList = ScanKitAPI.a(th);
        }
        reply.reply(arrayList);
    }

    public static /* synthetic */ void f(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        String str = (String) arrayList2.get(0);
        Number number = (Number) arrayList2.get(1);
        Number number2 = (Number) arrayList2.get(2);
        Map<String, Object> map = (Map) arrayList2.get(3);
        Long lValueOf2 = null;
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        if (number2 != null) {
            lValueOf2 = Long.valueOf(number2.longValue());
        }
        arrayList.add(0, scanKitApi.encode(str, lValueOf, lValueOf2, map));
        reply.reply(arrayList);
    }

    public static /* synthetic */ void g(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.disposeCustomizedMode(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void h(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Number number2 = (Number) arrayList2.get(1);
        Map<String, Object> map = (Map) arrayList2.get(2);
        Long lValueOf2 = null;
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        if (number2 != null) {
            lValueOf2 = Long.valueOf(number2.longValue());
        }
        arrayList.add(0, scanKitApi.startScan(lValueOf, lValueOf2, map));
        reply.reply(arrayList);
    }

    public static /* synthetic */ void i(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.pauseContinuouslyScan(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void j(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.resumeContinuouslyScan(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void k(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.switchLight(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void l(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        scanKitApi.pickPhoto(lValueOf);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void m(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        arrayList.add(0, scanKitApi.getLightStatus(lValueOf));
        reply.reply(arrayList);
    }

    public static /* synthetic */ void n(ScanKitAPI.ScanKitApi scanKitApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        byte[] bArr = (byte[]) arrayList2.get(0);
        Number number = (Number) arrayList2.get(1);
        Number number2 = (Number) arrayList2.get(2);
        Map<String, Object> map = (Map) arrayList2.get(3);
        Long lValueOf2 = null;
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = ScanKitAPI.a(th);
            }
        }
        if (number2 != null) {
            lValueOf2 = Long.valueOf(number2.longValue());
        }
        arrayList.add(0, scanKitApi.decodeYUV(bArr, lValueOf, lValueOf2, map));
        reply.reply(arrayList);
    }

    public static void o(BinaryMessenger binaryMessenger, final ScanKitAPI.ScanKitApi scanKitApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.createDefaultMode", a());
        if (scanKitApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.a
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.b(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.disposeDefaultMode", a());
        if (scanKitApi != null) {
            basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.h
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.c(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel2.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.disposeCustomizedMode", a());
        if (scanKitApi != null) {
            basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.i
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.g(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel3.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.startScan", a());
        if (scanKitApi != null) {
            basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.j
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.h(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel4.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.pauseContinuouslyScan", a());
        if (scanKitApi != null) {
            basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.k
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.i(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel5.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.resumeContinuouslyScan", a());
        if (scanKitApi != null) {
            basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.l
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.j(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel6.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.switchLight", a());
        if (scanKitApi != null) {
            basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.m
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.k(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel7.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.pickPhoto", a());
        if (scanKitApi != null) {
            basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.b
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.l(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel8.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.getLightStatus", a());
        if (scanKitApi != null) {
            basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.c
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.m(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel9.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel10 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.decodeYUV", a());
        if (scanKitApi != null) {
            basicMessageChannel10.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.d
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.n(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel10.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel11 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.decode", a());
        if (scanKitApi != null) {
            basicMessageChannel11.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.e
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.d(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel11.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel12 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.decodeBitmap", a());
        if (scanKitApi != null) {
            basicMessageChannel12.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.f
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.e(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel12.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel13 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.ScanKitApi.encode", a());
        if (scanKitApi != null) {
            basicMessageChannel13.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: xyz.bczl.flutter_scankit.g
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    n.f(scanKitApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel13.setMessageHandler(null);
        }
    }
}
