package androidx.health.platform.client.proto;

import java.util.List;

/* loaded from: classes.dex */
public interface ApiOrBuilder extends MessageLiteOrBuilder {
    Method getMethods(int i2);

    int getMethodsCount();

    List<Method> getMethodsList();

    Mixin getMixins(int i2);

    int getMixinsCount();

    List<Mixin> getMixinsList();

    String getName();

    ByteString getNameBytes();

    Option getOptions(int i2);

    int getOptionsCount();

    List<Option> getOptionsList();

    SourceContext getSourceContext();

    Syntax getSyntax();

    int getSyntaxValue();

    String getVersion();

    ByteString getVersionBytes();

    boolean hasSourceContext();
}
