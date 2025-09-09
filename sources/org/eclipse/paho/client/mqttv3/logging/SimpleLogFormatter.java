package org.eclipse.paho.client.mqttv3.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/* loaded from: classes5.dex */
public class SimpleLogFormatter extends Formatter {
    private static final String LS = System.getProperty("line.separator");

    public static String left(String str, int i2, char c2) {
        if (str.length() >= i2) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i2);
        stringBuffer.append(str);
        int length = i2 - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c2);
        }
    }

    @Override // java.util.logging.Formatter
    public String format(LogRecord logRecord) throws Throwable {
        String string;
        StringWriter stringWriter;
        PrintWriter printWriter;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(logRecord.getLevel().getName());
        stringBuffer.append("\t");
        stringBuffer.append(MessageFormat.format("{0, date, yy-MM-dd} {0, time, kk:mm:ss.SSSS} ", new Date(logRecord.getMillis())) + "\t");
        String sourceClassName = logRecord.getSourceClassName();
        if (sourceClassName != null) {
            int length = sourceClassName.length();
            if (length > 20) {
                string = logRecord.getSourceClassName().substring(length - 19);
            } else {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(sourceClassName);
                stringBuffer2.append(new char[]{' '}, 0, 1);
                string = stringBuffer2.toString();
            }
        } else {
            string = "";
        }
        stringBuffer.append(string);
        stringBuffer.append("\t");
        stringBuffer.append(" ");
        stringBuffer.append(left(logRecord.getSourceMethodName(), 23, ' '));
        stringBuffer.append("\t");
        stringBuffer.append(logRecord.getThreadID());
        stringBuffer.append("\t");
        stringBuffer.append(formatMessage(logRecord));
        stringBuffer.append(LS);
        if (logRecord.getThrown() != null) {
            stringBuffer.append("Throwable occurred: ");
            Throwable thrown = logRecord.getThrown();
            PrintWriter printWriter2 = null;
            try {
                stringWriter = new StringWriter();
                printWriter = new PrintWriter(stringWriter);
            } catch (Throwable th) {
                th = th;
            }
            try {
                thrown.printStackTrace(printWriter);
                stringBuffer.append(stringWriter.toString());
                try {
                    printWriter.close();
                } catch (Exception unused) {
                }
            } catch (Throwable th2) {
                th = th2;
                printWriter2 = printWriter;
                if (printWriter2 != null) {
                    try {
                        printWriter2.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        }
        return stringBuffer.toString();
    }
}
