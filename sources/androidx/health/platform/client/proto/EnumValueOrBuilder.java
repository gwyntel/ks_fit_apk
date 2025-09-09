package androidx.health.platform.client.proto;

import java.util.List;

/* loaded from: classes.dex */
public interface EnumValueOrBuilder extends MessageLiteOrBuilder {
    String getName();

    ByteString getNameBytes();

    int getNumber();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();
}
