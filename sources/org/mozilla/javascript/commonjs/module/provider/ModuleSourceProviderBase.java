package org.mozilla.javascript.commonjs.module.provider;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/* loaded from: classes5.dex */
public abstract class ModuleSourceProviderBase implements ModuleSourceProvider, Serializable {
    private static final long serialVersionUID = 1;

    private static String ensureTrailingSlash(String str) {
        return str.endsWith("/") ? str : str.concat("/");
    }

    private ModuleSource loadFromPathArray(String str, Scriptable scriptable, Object obj) throws IOException {
        long uint32 = ScriptRuntime.toUint32(ScriptableObject.getProperty(scriptable, SessionDescription.ATTR_LENGTH));
        int i2 = uint32 > 2147483647L ? Integer.MAX_VALUE : (int) uint32;
        for (int i3 = 0; i3 < i2; i3++) {
            String strEnsureTrailingSlash = ensureTrailingSlash((String) ScriptableObject.getTypedProperty(scriptable, i3, String.class));
            try {
                URI uri = new URI(strEnsureTrailingSlash);
                if (!uri.isAbsolute()) {
                    uri = new File(strEnsureTrailingSlash).toURI().resolve("");
                }
                ModuleSource moduleSourceLoadFromUri = loadFromUri(uri.resolve(str), uri, obj);
                if (moduleSourceLoadFromUri != null) {
                    return moduleSourceLoadFromUri;
                }
            } catch (URISyntaxException e2) {
                throw new MalformedURLException(e2.getMessage());
            }
        }
        return null;
    }

    protected boolean entityNeedsRevalidation(Object obj) {
        return true;
    }

    protected ModuleSource loadFromFallbackLocations(String str, Object obj) throws URISyntaxException, IOException {
        return null;
    }

    protected ModuleSource loadFromPrivilegedLocations(String str, Object obj) throws URISyntaxException, IOException {
        return null;
    }

    protected abstract ModuleSource loadFromUri(URI uri, URI uri2, Object obj) throws URISyntaxException, IOException;

    @Override // org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider
    public ModuleSource loadSource(String str, Scriptable scriptable, Object obj) throws URISyntaxException, IOException {
        ModuleSource moduleSourceLoadFromPathArray;
        if (!entityNeedsRevalidation(obj)) {
            return ModuleSourceProvider.NOT_MODIFIED;
        }
        ModuleSource moduleSourceLoadFromPrivilegedLocations = loadFromPrivilegedLocations(str, obj);
        return moduleSourceLoadFromPrivilegedLocations != null ? moduleSourceLoadFromPrivilegedLocations : (scriptable == null || (moduleSourceLoadFromPathArray = loadFromPathArray(str, scriptable, obj)) == null) ? loadFromFallbackLocations(str, obj) : moduleSourceLoadFromPathArray;
    }

    @Override // org.mozilla.javascript.commonjs.module.provider.ModuleSourceProvider
    public ModuleSource loadSource(URI uri, URI uri2, Object obj) throws URISyntaxException, IOException {
        return loadFromUri(uri, uri2, obj);
    }
}
