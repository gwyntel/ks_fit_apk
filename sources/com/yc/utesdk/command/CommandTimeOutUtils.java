package com.yc.utesdk.command;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.log.LogUtils;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class CommandTimeOutUtils {
    private static volatile CommandTimeOutUtils instance;
    private final utendo mCountDown;
    private int commandType = 0;
    private int COMMAND_TIME_OUT_COUNT = 0;

    public class utendo extends CountDownTimer {
        public utendo(long j2, long j3) {
            super(j2, j3);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            CommandTimeOutUtils commandTimeOutUtils = CommandTimeOutUtils.this;
            commandTimeOutUtils.utendo(commandTimeOutUtils.commandType);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j2) {
        }
    }

    public CommandTimeOutUtils() {
        LogUtils.i("init CommandTimeOutUtils T=" + Thread.currentThread().getId());
        this.mCountDown = new utendo(3000L, 1000L);
    }

    public static CommandTimeOutUtils getInstance() {
        if (instance == null) {
            synchronized (CommandTimeOutUtils.class) {
                try {
                    if (instance == null) {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            instance = new CommandTimeOutUtils();
                        } else {
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: k0.a
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CommandTimeOutUtils.utendo(countDownLatch);
                                }
                            });
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                LogUtils.e("初始化中断", e2);
                            }
                        }
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public void cancelCommandTimeOut() {
        LogUtils.i("关闭命令 commandType = " + this.commandType);
        utendo utendoVar = this.mCountDown;
        if (utendoVar != null) {
            utendoVar.cancel();
        }
    }

    public int getCommandType() {
        return this.commandType;
    }

    public void setCommandTimeOut(int i2) {
        this.commandType = i2;
        LogUtils.i("开启命令 commandType=" + i2);
        utendo utendoVar = this.mCountDown;
        if (utendoVar != null) {
            utendoVar.start();
        }
    }

    public static /* synthetic */ void utendo(CountDownLatch countDownLatch) {
        if (instance == null) {
            instance = new CommandTimeOutUtils();
        }
        countDownLatch.countDown();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:260:0x085d A[Catch: all -> 0x005c, TryCatch #0 {all -> 0x005c, blocks: (B:4:0x0005, B:5:0x0037, B:7:0x0042, B:9:0x0046, B:10:0x0049, B:11:0x004c, B:13:0x0051, B:16:0x0060, B:17:0x006b, B:18:0x0076, B:19:0x007f, B:20:0x0088, B:21:0x0091, B:22:0x009a, B:23:0x00a3, B:24:0x00ac, B:25:0x00b5, B:26:0x00be, B:298:0x099e, B:27:0x00c6, B:28:0x00d1, B:29:0x00da, B:30:0x00e2, B:31:0x00ea, B:56:0x01af, B:32:0x00f1, B:33:0x00f8, B:34:0x0100, B:35:0x0109, B:36:0x0111, B:37:0x0119, B:38:0x0121, B:39:0x012a, B:40:0x0133, B:41:0x013c, B:42:0x0145, B:43:0x014e, B:44:0x0157, B:45:0x0160, B:46:0x0169, B:47:0x0172, B:48:0x017b, B:49:0x0184, B:50:0x018d, B:51:0x0193, B:52:0x0199, B:53:0x019f, B:54:0x01a5, B:55:0x01ab, B:57:0x01b4, B:58:0x01ba, B:59:0x01bf, B:60:0x01c8, B:61:0x01ce, B:62:0x01d3, B:63:0x01de, B:64:0x01e9, B:65:0x01f2, B:66:0x01fb, B:67:0x0204, B:68:0x020d, B:69:0x0216, B:70:0x021f, B:71:0x0228, B:72:0x0231, B:73:0x023a, B:74:0x0243, B:75:0x024c, B:76:0x0255, B:77:0x025e, B:78:0x0269, B:79:0x0272, B:80:0x027b, B:81:0x0281, B:82:0x0286, B:83:0x028f, B:84:0x0298, B:85:0x02a1, B:86:0x02aa, B:87:0x02af, B:88:0x02b8, B:89:0x02c1, B:90:0x02ca, B:91:0x02d3, B:92:0x02de, B:93:0x02e4, B:94:0x02e9, B:95:0x02f4, B:96:0x02fb, B:97:0x0306, B:98:0x030f, B:99:0x0315, B:100:0x031a, B:101:0x0323, B:102:0x032e, B:103:0x0334, B:104:0x0339, B:105:0x0341, B:106:0x0349, B:107:0x0351, B:108:0x0357, B:109:0x035c, B:110:0x0367, B:111:0x036d, B:112:0x0372, B:113:0x0379, B:114:0x0384, B:115:0x038c, B:116:0x0394, B:117:0x039c, B:118:0x03a7, B:119:0x03af, B:120:0x03b8, B:122:0x03c1, B:123:0x03c9, B:124:0x03d2, B:125:0x03db, B:126:0x03e4, B:127:0x03ed, B:128:0x03f6, B:129:0x03ff, B:130:0x0408, B:131:0x0411, B:132:0x041a, B:133:0x0423, B:134:0x042c, B:135:0x0435, B:136:0x043e, B:137:0x0449, B:138:0x0452, B:139:0x045b, B:140:0x0464, B:141:0x046d, B:142:0x0476, B:143:0x047f, B:144:0x0488, B:145:0x0491, B:146:0x049a, B:147:0x04a3, B:148:0x04ac, B:149:0x04b6, B:150:0x04bf, B:151:0x04c8, B:152:0x04d1, B:153:0x04da, B:154:0x04e2, B:155:0x04eb, B:156:0x04f4, B:157:0x04fd, B:158:0x0506, B:159:0x050f, B:160:0x0518, B:161:0x051e, B:162:0x0523, B:163:0x052a, B:164:0x0531, B:165:0x053a, B:166:0x0541, B:167:0x054a, B:168:0x0553, B:169:0x055c, B:170:0x0565, B:171:0x056a, B:172:0x0573, B:173:0x057c, B:174:0x0585, B:175:0x058e, B:176:0x0597, B:177:0x05a0, B:178:0x05a9, B:179:0x05b2, B:204:0x066f, B:205:0x0678, B:184:0x05c2, B:185:0x05cb, B:186:0x05d4, B:187:0x05dd, B:188:0x05e6, B:189:0x05ef, B:190:0x05f8, B:191:0x0601, B:192:0x060c, B:193:0x0617, B:194:0x0622, B:195:0x062b, B:196:0x0636, B:197:0x0641, B:198:0x0647, B:199:0x064c, B:200:0x0655, B:201:0x065c, B:202:0x0665, B:206:0x0681, B:207:0x068c, B:208:0x0697, B:209:0x06a0, B:210:0x06a9, B:211:0x06b2, B:212:0x06bb, B:213:0x06c4, B:214:0x06cd, B:215:0x06d6, B:216:0x06df, B:217:0x06e8, B:218:0x06f1, B:219:0x06f7, B:220:0x0700, B:221:0x0709, B:222:0x0712, B:223:0x071b, B:224:0x0724, B:225:0x072d, B:226:0x0736, B:227:0x073f, B:228:0x0748, B:229:0x0750, B:230:0x0758, B:231:0x0760, B:232:0x0768, B:233:0x0770, B:234:0x0778, B:235:0x0780, B:236:0x0788, B:237:0x0790, B:238:0x0798, B:239:0x07a1, B:240:0x07a9, B:241:0x07b1, B:242:0x07ba, B:243:0x07c3, B:244:0x07c9, B:245:0x07d2, B:246:0x07dc, B:247:0x07e6, B:248:0x07f0, B:249:0x07fa, B:250:0x0804, B:251:0x080e, B:252:0x0818, B:253:0x0821, B:254:0x082a, B:255:0x0833, B:256:0x083c, B:257:0x0845, B:258:0x084e, B:259:0x0857, B:260:0x085d, B:261:0x0866, B:262:0x086f, B:263:0x0878, B:264:0x0881, B:265:0x088a, B:266:0x0893, B:267:0x089c, B:268:0x08a5, B:269:0x08ae, B:270:0x08b7, B:271:0x08c0, B:272:0x08c9, B:273:0x08d2, B:274:0x08db, B:275:0x08e4, B:276:0x08ed, B:277:0x08f6, B:278:0x08ff, B:279:0x0908, B:280:0x0911, B:281:0x091a, B:282:0x0923, B:283:0x0929, B:284:0x0932, B:285:0x093b, B:286:0x0944, B:287:0x094d, B:288:0x0956, B:289:0x095e, B:290:0x0966, B:291:0x096e, B:292:0x0974, B:293:0x097c, B:294:0x0982, B:295:0x098a, B:296:0x0994, B:297:0x0999, B:299:0x09a2, B:300:0x09aa, B:301:0x09b2), top: B:306:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void utendo(int r17) {
        /*
            Method dump skipped, instructions count: 3052
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.command.CommandTimeOutUtils.utendo(int):void");
    }
}
