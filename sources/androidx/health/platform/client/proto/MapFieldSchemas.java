package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
final class MapFieldSchemas {
    private static final MapFieldSchema FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final MapFieldSchema LITE_SCHEMA = new MapFieldSchemaLite();

    static MapFieldSchema a() {
        return FULL_SCHEMA;
    }

    static MapFieldSchema b() {
        return LITE_SCHEMA;
    }

    private static MapFieldSchema loadSchemaForFullRuntime() {
        try {
            return (MapFieldSchema) Class.forName("androidx.health.platform.client.proto.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
            return null;
        }
    }
}
