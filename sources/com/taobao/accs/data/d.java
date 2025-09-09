package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.antibrush.AntiBrush;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.flowcontrol.FlowControl;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.ut.monitor.TrafficsMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.p;
import com.taobao.accs.utl.s;
import com.umeng.message.common.inter.ITagManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    public int f20138b;

    /* renamed from: c, reason: collision with root package name */
    protected TrafficsMonitor f20139c;

    /* renamed from: d, reason: collision with root package name */
    public FlowControl f20140d;

    /* renamed from: e, reason: collision with root package name */
    public AntiBrush f20141e;

    /* renamed from: i, reason: collision with root package name */
    private Context f20145i;

    /* renamed from: j, reason: collision with root package name */
    private com.taobao.accs.ut.a.d f20146j;

    /* renamed from: k, reason: collision with root package name */
    private Message f20147k;

    /* renamed from: l, reason: collision with root package name */
    private com.taobao.accs.net.a f20148l;

    /* renamed from: m, reason: collision with root package name */
    private String f20149m;

    /* renamed from: g, reason: collision with root package name */
    private ConcurrentMap<Message.Id, Message> f20143g = new ConcurrentHashMap();

    /* renamed from: a, reason: collision with root package name */
    public ConcurrentMap<String, ScheduledFuture<?>> f20137a = new ConcurrentHashMap();

    /* renamed from: h, reason: collision with root package name */
    private boolean f20144h = false;

    /* renamed from: f, reason: collision with root package name */
    public String f20142f = "";

    /* renamed from: n, reason: collision with root package name */
    private LinkedHashMap<String, String> f20150n = new LinkedHashMap<String, String>() { // from class: com.taobao.accs.data.MessageHandler$1
        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, String> entry) {
            return size() > 50;
        }
    };

    /* renamed from: o, reason: collision with root package name */
    private Map<String, a> f20151o = new HashMap();

    /* renamed from: p, reason: collision with root package name */
    private Runnable f20152p = new f(this);

    public d(Context context, com.taobao.accs.net.a aVar) throws IOException {
        String str;
        this.f20149m = "MsgRecv_";
        this.f20145i = context;
        this.f20148l = aVar;
        this.f20139c = new TrafficsMonitor(context);
        this.f20140d = new FlowControl(this.f20145i);
        this.f20141e = new AntiBrush(this.f20145i);
        if (aVar == null) {
            str = this.f20149m;
        } else {
            str = this.f20149m + aVar.f20203m;
        }
        this.f20149m = str;
        i();
        h();
    }

    private boolean b(int i2) {
        return i2 == -1 || i2 == -9 || i2 == -10 || i2 == -11;
    }

    private void i() throws IOException {
        try {
            File file = new File(this.f20145i.getDir("accs", 0), "message" + this.f20148l.i());
            if (!file.exists()) {
                ALog.d(this.f20149m, "message file not exist", new Object[0]);
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    return;
                }
                this.f20150n.put(line, line);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void j() throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.f20145i.getDir("accs", 0), "message" + this.f20148l.i()));
            fileWriter.write("");
            Iterator<String> it = this.f20150n.keySet().iterator();
            while (it.hasNext()) {
                fileWriter.append((CharSequence) it.next()).append((CharSequence) IOUtils.LINE_SEPARATOR_WINDOWS);
            }
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void a(byte[] bArr) throws IOException {
        a(bArr, (String) null);
    }

    public boolean c() {
        return this.f20144h;
    }

    public int d() {
        return this.f20143g.size();
    }

    public Collection<Message> e() {
        return this.f20143g.values();
    }

    public Set<Message.Id> f() {
        return this.f20143g.keySet();
    }

    public com.taobao.accs.ut.a.d g() {
        return this.f20146j;
    }

    public void h() {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(this.f20152p);
        } catch (Throwable th) {
            ALog.e(this.f20149m, "restoreTraffics", th, new Object[0]);
        }
    }

    private boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f20150n.containsKey(str);
    }

    private void d(String str) throws IOException {
        if (TextUtils.isEmpty(str) || this.f20150n.containsKey(str)) {
            return;
        }
        this.f20150n.put(str, str);
        j();
    }

    public void a(byte[] bArr, String str) throws IOException {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i(this.f20149m, "onMessage", "host", str);
        }
        s sVar = new s(bArr);
        try {
            int iA = sVar.a();
            int i2 = (iA & 240) >> 4;
            ALog.Level level = ALog.Level.D;
            if (ALog.isPrintLog(level)) {
                ALog.d(this.f20149m, "version:" + i2, new Object[0]);
            }
            int i3 = iA & 15;
            if (ALog.isPrintLog(level)) {
                ALog.d(this.f20149m, "compress:" + i3, new Object[0]);
            }
            sVar.a();
            int iB = sVar.b();
            if (ALog.isPrintLog(level)) {
                ALog.d(this.f20149m, "totalLen:" + iB, new Object[0]);
            }
            int i4 = 0;
            while (i4 < iB) {
                int iB2 = sVar.b();
                int i5 = i4 + 2;
                if (iB2 <= 0) {
                    throw new IOException("data format error");
                }
                byte[] bArr2 = new byte[iB2];
                sVar.read(bArr2);
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.d(this.f20149m, "buf len:" + iB2, new Object[0]);
                }
                i4 = i5 + iB2;
                a(i3, bArr2, str, i2);
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public void b() {
        ALog.d(this.f20149m, "onRcvPing", new Object[0]);
        synchronized (d.class) {
            this.f20144h = false;
        }
    }

    private Intent c(Message message) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(message.f20108m);
        intent.putExtra("command", message.command);
        intent.putExtra(Constants.KEY_SERVICE_ID, message.serviceId);
        intent.putExtra(Constants.KEY_USER_ID, message.userinfo);
        Integer num = message.command;
        if (num != null && num.intValue() == 100) {
            intent.putExtra(Constants.KEY_DATA_ID, message.cunstomDataId);
        }
        NetPerformanceMonitor netPerformanceMonitor = message.G;
        if (netPerformanceMonitor != null) {
            intent.putExtra(Constants.KEY_MONIROT, netPerformanceMonitor);
        }
        return intent;
    }

    public void b(Message message) {
        if (this.f20143g.keySet().size() > 0) {
            Iterator<Message.Id> it = this.f20143g.keySet().iterator();
            while (it.hasNext()) {
                Message message2 = this.f20143g.get(it.next());
                if (message2 != null && message2.command != null && message2.getPackageName().equals(message.getPackageName())) {
                    switch (message.command.intValue()) {
                        case 1:
                        case 2:
                            if (message2.command.intValue() == 1 || message2.command.intValue() == 2) {
                                message2.isCancel = true;
                                break;
                            }
                            break;
                        case 3:
                        case 4:
                            if (message2.command.intValue() == 3 || message2.command.intValue() == 4) {
                                message2.isCancel = true;
                                break;
                            }
                            break;
                        case 5:
                        case 6:
                            if (message2.command.intValue() == 5 || message2.command.intValue() == 6) {
                                message2.isCancel = true;
                                break;
                            }
                            break;
                    }
                }
                if (message2 != null && message2.isCancel) {
                    ALog.e(this.f20149m, "cancelControlMessage", "command", message2.command);
                }
            }
        }
    }

    public Message b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f20143g.remove(new Message.Id(0, str));
    }

    private void b(Message message, int i2) {
        if (message == null) {
            return;
        }
        String strJ = UtilityImpl.j(this.f20145i);
        String str = System.currentTimeMillis() + "";
        boolean z2 = i2 == 200;
        int iIntValue = message.command.intValue();
        if (iIntValue == 1) {
            com.taobao.accs.ut.a.a aVar = new com.taobao.accs.ut.a.a();
            aVar.f20285a = strJ;
            aVar.f20286b = str;
            aVar.f20287c = z2;
            aVar.a(i2);
            aVar.a();
            return;
        }
        if (iIntValue != 3) {
            return;
        }
        com.taobao.accs.ut.a.b bVar = new com.taobao.accs.ut.a.b();
        bVar.f20291a = strJ;
        bVar.f20292b = str;
        bVar.f20293c = z2;
        bVar.f20295e = message.userinfo;
        bVar.a(i2);
        bVar.a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02bb A[Catch: Exception -> 0x0280, TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x0280, blocks: (B:89:0x0279, B:107:0x02bb), top: B:257:0x0279 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0300 A[Catch: Exception -> 0x02f7, TryCatch #6 {Exception -> 0x02f7, blocks: (B:111:0x02f0, B:116:0x031f, B:123:0x0372, B:125:0x0378, B:127:0x0388, B:129:0x038e, B:131:0x03a8, B:136:0x03c9, B:139:0x03cf, B:141:0x03da, B:143:0x03e4, B:145:0x03ef, B:147:0x03f7, B:148:0x040d, B:150:0x0411, B:151:0x0414, B:153:0x0430, B:155:0x0434, B:157:0x043a, B:159:0x0446, B:161:0x046b, B:163:0x0473, B:167:0x0483, B:169:0x0492, B:171:0x04b5, B:173:0x04bd, B:115:0x0300, B:121:0x0345), top: B:256:0x022f }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0372 A[Catch: Exception -> 0x02f7, TryCatch #6 {Exception -> 0x02f7, blocks: (B:111:0x02f0, B:116:0x031f, B:123:0x0372, B:125:0x0378, B:127:0x0388, B:129:0x038e, B:131:0x03a8, B:136:0x03c9, B:139:0x03cf, B:141:0x03da, B:143:0x03e4, B:145:0x03ef, B:147:0x03f7, B:148:0x040d, B:150:0x0411, B:151:0x0414, B:153:0x0430, B:155:0x0434, B:157:0x043a, B:159:0x0446, B:161:0x046b, B:163:0x0473, B:167:0x0483, B:169:0x0492, B:171:0x04b5, B:173:0x04bd, B:115:0x0300, B:121:0x0345), top: B:256:0x022f }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x03c9 A[Catch: Exception -> 0x02f7, TryCatch #6 {Exception -> 0x02f7, blocks: (B:111:0x02f0, B:116:0x031f, B:123:0x0372, B:125:0x0378, B:127:0x0388, B:129:0x038e, B:131:0x03a8, B:136:0x03c9, B:139:0x03cf, B:141:0x03da, B:143:0x03e4, B:145:0x03ef, B:147:0x03f7, B:148:0x040d, B:150:0x0411, B:151:0x0414, B:153:0x0430, B:155:0x0434, B:157:0x043a, B:159:0x0446, B:161:0x046b, B:163:0x0473, B:167:0x0483, B:169:0x0492, B:171:0x04b5, B:173:0x04bd, B:115:0x0300, B:121:0x0345), top: B:256:0x022f }] */
    /* JADX WARN: Removed duplicated region for block: B:277:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x023a  */
    /* JADX WARN: Type inference failed for: r2v122 */
    /* JADX WARN: Type inference failed for: r2v46, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v48 */
    /* JADX WARN: Type inference failed for: r2v49 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v64 */
    /* JADX WARN: Type inference failed for: r3v65 */
    /* JADX WARN: Type inference failed for: r3v66 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v76 */
    /* JADX WARN: Type inference failed for: r52v0, types: [com.taobao.accs.data.d] */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v33 */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v43 */
    /* JADX WARN: Type inference failed for: r6v44 */
    /* JADX WARN: Type inference failed for: r6v45 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(int r53, byte[] r54, java.lang.String r55, int r56) throws org.json.JSONException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 1941
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.d.a(int, byte[], java.lang.String, int):void");
    }

    private byte[] a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int i2 = gZIPInputStream.read(bArr);
                    if (i2 <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    gZIPInputStream.close();
                    byteArrayOutputStream.close();
                } catch (Exception unused) {
                }
                return byteArray;
            } catch (Throwable th) {
                try {
                    gZIPInputStream.close();
                    byteArrayOutputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (Exception e2) {
            ALog.e(this.f20149m, "uncompress data error " + e2.toString(), new Object[0]);
            com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", this.f20138b + " uncompress data error " + e2.toString());
            try {
                gZIPInputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception unused3) {
            }
            return null;
        }
    }

    private void a(Message message, byte[] bArr, byte[] bArr2, String str) {
        JSONArray jSONArray;
        int i2 = -8;
        try {
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr));
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.d(this.f20149m, "handleControlMessage parse", "json", jSONObject.toString());
                }
                i2 = message.command.intValue() == 100 ? 200 : jSONObject.getInt("code");
                if (i2 == 200) {
                    int iIntValue = message.command.intValue();
                    if (iIntValue == 1) {
                        UtilityImpl.c(Constants.SP_FILE_NAME, this.f20145i);
                        try {
                            this.f20148l.j().a(this.f20145i.getPackageName());
                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                            this.f20142f = p.a(jSONObject2, Constants.KEY_DEVICE_TOKEN, null);
                            if (jSONObject2 != null && (jSONArray = jSONObject2.getJSONArray(Constants.KEY_PACKAGE_NAMES)) != null) {
                                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                                    String string = jSONArray.getString(i3);
                                    if (UtilityImpl.a(this.f20145i, string)) {
                                        this.f20148l.j().a(message.f20108m);
                                    } else {
                                        ALog.d(this.f20149m, "unbind app", "pkg", string);
                                        com.taobao.accs.net.a aVar = this.f20148l;
                                        aVar.b(Message.buildUnbindApp(aVar.b((String) null), string), true);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            ALog.i(this.f20149m, "no token/invalid app", th);
                        }
                    } else if (iIntValue == 2) {
                        this.f20148l.j().b(message.f20108m);
                    } else if (iIntValue == 3) {
                        this.f20148l.j().a(message.f20108m, message.userinfo);
                    } else if (iIntValue != 4) {
                        if (iIntValue == 100 && (this.f20148l instanceof com.taobao.accs.net.k) && "4|sal|accs-iot".equals(message.f20104i)) {
                            ((com.taobao.accs.net.k) this.f20148l).a(jSONObject);
                        }
                    } else {
                        this.f20148l.j().e(message.f20108m);
                    }
                } else if (message.command.intValue() == 3 && i2 == 300) {
                    this.f20148l.j().b(message.f20108m);
                }
            } catch (Throwable th2) {
                th = th2;
                ALog.e(this.f20149m, "handleControlMessage", th, new Object[0]);
                com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", this.f20138b + th.toString());
                a(message, i2, null, bArr, null);
                a(new TrafficsMonitor.a(message.serviceId, GlobalAppRuntimeInfo.isAppBackground(), str, bArr2.length));
            }
        } catch (Throwable th3) {
            th = th3;
        }
        a(message, i2, null, bArr, null);
        a(new TrafficsMonitor.a(message.serviceId, GlobalAppRuntimeInfo.isAppBackground(), str, bArr2.length));
    }

    private Map<Integer, String> a(s sVar) {
        HashMap map = null;
        if (sVar == null) {
            return null;
        }
        try {
            int iB = sVar.b();
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.d(this.f20149m, "extHeaderLen:" + iB, new Object[0]);
            }
            int i2 = 0;
            while (i2 < iB) {
                int iB2 = sVar.b();
                int i3 = (64512 & iB2) >> 10;
                int i4 = iB2 & 1023;
                String strA = sVar.a(i4);
                i2 = i2 + 2 + i4;
                if (map == null) {
                    map = new HashMap();
                }
                map.put(Integer.valueOf(i3), strA);
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.d(this.f20149m, "", "extHeaderType", Integer.valueOf(i3), "value", strA);
                }
            }
        } catch (Exception e2) {
            ALog.e(this.f20149m, "parseExtHeader", e2, new Object[0]);
        }
        return map;
    }

    public void a(Message message, int i2) {
        a(message, i2, null, null, null);
    }

    public void a(Message message, int i2, Map<Integer, String> map) {
        a(message, i2, null, null, map);
    }

    public void a(Message message, int i2, Message.ReqType reqType, byte[] bArr, Map<Integer, String> map) {
        if (message.command != null && message.getType() >= 0 && message.getType() != 2) {
            String str = message.cunstomDataId;
            if (str != null) {
                this.f20137a.remove(str);
            }
            Message.ReqType reqType2 = null;
            if (this.f20141e.checkAntiBrush(message.host, map)) {
                i2 = ErrorCode.SERVIER_ANTI_BRUSH;
                reqType = null;
                bArr = null;
                map = null;
            }
            int iA = this.f20140d.a(map, message.serviceId);
            if (iA != 0) {
                i2 = iA == 2 ? ErrorCode.SERVIER_HIGH_LIMIT : iA == 3 ? ErrorCode.SERVIER_HIGH_LIMIT_BRUSH : ErrorCode.SERVIER_LOW_LIMIT;
                bArr = null;
                map = null;
            } else {
                reqType2 = reqType;
            }
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.d(this.f20149m, "onResult", "command", message.command, "erorcode", Integer.valueOf(i2));
            }
            if (message.command.intValue() == 102) {
                return;
            }
            if (!message.isCancel) {
                if (b(i2) && message.command.intValue() != 100 && message.retryTimes <= Message.CONTROL_MAX_RETRY_TIMES) {
                    message.startSendTime = System.currentTimeMillis();
                    int i3 = message.retryTimes + 1;
                    message.retryTimes = i3;
                    ALog.d(this.f20149m, "onResult", "retryTimes", Integer.valueOf(i3));
                    this.f20148l.b(message, true);
                    a(message, i2, true);
                } else {
                    a(message, i2, false);
                    Intent intentC = c(message);
                    intentC.putExtra("errorCode", i2);
                    Message.ReqType reqTypeValueOf = Message.ReqType.valueOf(3 & (message.f20101f >> 13));
                    if (reqType2 == Message.ReqType.RES || reqTypeValueOf == Message.ReqType.REQ) {
                        intentC.putExtra(Constants.KEY_SEND_TYPE, "res");
                    }
                    if (i2 == 200) {
                        intentC.putExtra("data", bArr);
                    }
                    intentC.putExtra("appKey", this.f20148l.f20192b);
                    intentC.putExtra(Constants.KEY_CONFIG_TAG, this.f20148l.f20203m);
                    a(map, intentC);
                    g.a().b(this.f20145i, intentC);
                    if (!TextUtils.isEmpty(message.serviceId)) {
                        UTMini.getInstance().commitEvent(66001, "MsgToBuss0", "commandId=" + message.command, "serviceId=" + message.serviceId + " errorCode=" + i2 + " dataId=" + message.dataId, 221);
                        StringBuilder sb = new StringBuilder();
                        sb.append("1commandId=");
                        sb.append(message.command);
                        sb.append("serviceId=");
                        sb.append(message.serviceId);
                        com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb.toString(), 0.0d);
                    }
                }
            } else {
                ALog.e(this.f20149m, "onResult message is cancel", "command", message.command);
                a(message, i2, true);
            }
            b(message, i2);
            return;
        }
        ALog.d(this.f20149m, "onError, skip ping/ack", new Object[0]);
    }

    private void a(Message message, int i2, boolean z2) {
        boolean z3;
        try {
            NetPerformanceMonitor netPermanceMonitor = message.getNetPermanceMonitor();
            if (netPermanceMonitor == null) {
                return;
            }
            netPermanceMonitor.onToBizDate();
            URL url = message.host;
            String string = url == null ? null : url.toString();
            if (i2 == 200) {
                if (message.retryTimes > 0) {
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, "succ", 0.0d);
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, "succ_" + message.retryTimes, 0.0d);
                } else {
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQUEST, string);
                }
                z3 = true;
            } else {
                if (message.retryTimes > 0) {
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, "fail＿" + i2, 0.0d);
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, ITagManager.FAIL, 0.0d);
                } else if (i2 != -13) {
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQUEST, string, UtilityImpl.a(i2), this.f20138b + message.serviceId + message.timeout);
                }
                netPermanceMonitor.setFailReason(i2);
                z3 = false;
            }
            netPermanceMonitor.setRet(z3);
            if (z2) {
                if (message.isCancel) {
                    netPermanceMonitor.setRet(false);
                    netPermanceMonitor.setFailReason("msg cancel");
                }
                AppMonitor.getInstance().commitStat(message.getNetPermanceMonitor());
            }
        } catch (Throwable th) {
            ALog.e(this.f20149m, "monitorPerf", th, new Object[0]);
        }
    }

    public void a() {
        ALog.d(this.f20149m, "onSendPing", new Object[0]);
        synchronized (d.class) {
            this.f20144h = true;
        }
    }

    public void a(Message message) {
        String str;
        Message message2 = this.f20147k;
        if (message2 != null && (str = message.cunstomDataId) != null && message.serviceId != null && message2.cunstomDataId.equals(str) && this.f20147k.serviceId.equals(message.serviceId)) {
            UTMini.getInstance().commitEvent(66001, "SEND_REPEAT", message.serviceId, message.cunstomDataId, Long.valueOf(Thread.currentThread().getId()));
        }
        if (message.getType() == -1 || message.getType() == 2 || message.isAck) {
            return;
        }
        this.f20143g.put(message.getMsgId(), message);
    }

    public void a(int i2) {
        this.f20144h = false;
        Message.Id[] idArr = (Message.Id[]) this.f20143g.keySet().toArray(new Message.Id[0]);
        if (idArr.length > 0) {
            ALog.d(this.f20149m, "onNetworkFail", new Object[0]);
            for (Message.Id id : idArr) {
                Message messageRemove = this.f20143g.remove(id);
                if (messageRemove != null) {
                    a(messageRemove, i2);
                }
            }
        }
    }

    public Message a(String str) {
        return this.f20143g.get(new Message.Id(0, str));
    }

    private byte[] a(String str, Map<Integer, String> map, byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    int i2 = Integer.parseInt(map.get(17));
                    int i3 = Integer.parseInt(map.get(16));
                    if (i3 <= 1) {
                        throw new RuntimeException("burstNums <= 1");
                    }
                    if (i2 >= 0 && i2 < i3) {
                        String str2 = map.get(18);
                        long j2 = 0;
                        try {
                            String str3 = map.get(15);
                            if (!TextUtils.isEmpty(str3)) {
                                j2 = Long.parseLong(str3);
                            }
                        } catch (Throwable th) {
                            ALog.w(this.f20149m, "putBurstMessage", th, new Object[0]);
                        }
                        a aVar = this.f20151o.get(str);
                        if (aVar == null) {
                            if (ALog.isPrintLog(ALog.Level.I)) {
                                ALog.i(this.f20149m, "putBurstMessage", Constants.KEY_DATA_ID, str, "burstLength", Integer.valueOf(i3));
                            }
                            aVar = new a(str, i3, str2);
                            aVar.a(j2);
                            this.f20151o.put(str, aVar);
                        }
                        return aVar.a(i2, i3, bArr);
                    }
                    throw new RuntimeException(String.format("burstNums:%s burstIndex:%s", Integer.valueOf(i3), Integer.valueOf(i2)));
                }
            } catch (Throwable th2) {
                ALog.w(this.f20149m, "putBurstMessage", th2, new Object[0]);
                return null;
            }
        }
        throw new RuntimeException("burstLength == 0");
    }

    private void a(Map<Integer, String> map, Intent intent) {
        if (map == null || intent == null) {
            return;
        }
        intent.putExtra(TaoBaseService.ExtraInfo.EXT_HEADER, (HashMap) map);
    }

    private void a(Intent intent, String str, String str2, short s2) {
        if (intent != null) {
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("source", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                intent.putExtra("target", str2);
            }
            intent.putExtra(Constants.KEY_FLAGS, s2);
        }
    }

    private void a(String str, String str2) {
        com.taobao.accs.ut.a.e eVar = new com.taobao.accs.ut.a.e();
        eVar.f20320a = UtilityImpl.j(this.f20145i);
        eVar.f20322c = str;
        eVar.f20323d = "" + System.currentTimeMillis();
        eVar.f20325f = "";
        eVar.f20324e = str2;
        eVar.f20321b = "";
        eVar.a();
    }

    public void a(TrafficsMonitor.a aVar) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new e(this, aVar));
        } catch (Throwable th) {
            ALog.e(this.f20149m, "addTrafficsInfo", th, new Object[0]);
        }
    }
}
