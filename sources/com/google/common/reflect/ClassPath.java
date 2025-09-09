package com.google.common.reflect;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.common.reflect.ClassPath;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import org.apache.commons.io.IOUtils;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ClassPath {
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private final ImmutableSet<ResourceInfo> resources;
    private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();

    public static final class ClassInfo extends ResourceInfo {
        private final String className;

        ClassInfo(File file, String str, ClassLoader classLoader) {
            super(file, str, classLoader);
            this.className = ClassPath.b(str);
        }

        public String getName() {
            return this.className;
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int iLastIndexOf = this.className.lastIndexOf(36);
            if (iLastIndexOf != -1) {
                return CharMatcher.inRange('0', '9').trimLeadingFrom(this.className.substring(iLastIndexOf + 1));
            }
            String packageName = getPackageName();
            return packageName.isEmpty() ? this.className : this.className.substring(packageName.length() + 1);
        }

        public boolean isTopLevel() {
            return this.className.indexOf(36) == -1;
        }

        public Class<?> load() {
            try {
                return this.f14723a.loadClass(this.className);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // com.google.common.reflect.ClassPath.ResourceInfo
        public String toString() {
            return this.className;
        }
    }

    static final class LocationInfo {

        /* renamed from: a, reason: collision with root package name */
        final File f14722a;
        private final ClassLoader classloader;

        LocationInfo(File file, ClassLoader classLoader) {
            this.f14722a = (File) Preconditions.checkNotNull(file);
            this.classloader = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        private void scan(File file, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        scanDirectory(file, builder);
                    } else {
                        scanJar(file, set, builder);
                    }
                }
            } catch (SecurityException e2) {
                ClassPath.logger.warning("Cannot access " + file + ": " + e2);
            }
        }

        private void scanDirectory(File file, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            HashSet hashSet = new HashSet();
            hashSet.add(file.getCanonicalFile());
            scanDirectory(file, "", hashSet, builder);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void scanJar(File file, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    UnmodifiableIterator it = ClassPath.e(file, jarFile.getManifest()).iterator();
                    while (it.hasNext()) {
                        File file2 = (File) it.next();
                        if (set.add(file2.getCanonicalFile())) {
                            scan(file2, set, builder);
                        }
                    }
                    scanJarFile(jarFile, builder);
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                } catch (Throwable th) {
                    try {
                        jarFile.close();
                    } catch (IOException unused2) {
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            }
        }

        private void scanJarFile(JarFile jarFile, ImmutableSet.Builder<ResourceInfo> builder) {
            Enumeration<JarEntry> enumerationEntries = jarFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                JarEntry jarEntryNextElement = enumerationEntries.nextElement();
                if (!jarEntryNextElement.isDirectory() && !jarEntryNextElement.getName().equals("META-INF/MANIFEST.MF")) {
                    builder.add((ImmutableSet.Builder<ResourceInfo>) ResourceInfo.a(new File(jarFile.getName()), jarEntryNextElement.getName(), this.classloader));
                }
            }
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof LocationInfo)) {
                return false;
            }
            LocationInfo locationInfo = (LocationInfo) obj;
            return this.f14722a.equals(locationInfo.f14722a) && this.classloader.equals(locationInfo.classloader);
        }

        public final File file() {
            return this.f14722a;
        }

        public int hashCode() {
            return this.f14722a.hashCode();
        }

        public ImmutableSet<ResourceInfo> scanResources() throws IOException {
            return scanResources(new HashSet());
        }

        public String toString() {
            return this.f14722a.toString();
        }

        public ImmutableSet<ResourceInfo> scanResources(Set<File> set) throws IOException {
            ImmutableSet.Builder<ResourceInfo> builder = ImmutableSet.builder();
            set.add(this.f14722a);
            scan(this.f14722a, set, builder);
            return builder.build();
        }

        private void scanDirectory(File file, String str, Set<File> set, ImmutableSet.Builder<ResourceInfo> builder) throws IOException {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                ClassPath.logger.warning("Cannot read directory " + file);
                return;
            }
            for (File file2 : fileArrListFiles) {
                String name = file2.getName();
                if (file2.isDirectory()) {
                    File canonicalFile = file2.getCanonicalFile();
                    if (set.add(canonicalFile)) {
                        scanDirectory(canonicalFile, str + name + "/", set, builder);
                        set.remove(canonicalFile);
                    }
                } else {
                    String str2 = str + name;
                    if (!str2.equals("META-INF/MANIFEST.MF")) {
                        builder.add((ImmutableSet.Builder<ResourceInfo>) ResourceInfo.a(file2, str2, this.classloader));
                    }
                }
            }
        }
    }

    public static class ResourceInfo {

        /* renamed from: a, reason: collision with root package name */
        final ClassLoader f14723a;
        private final File file;
        private final String resourceName;

        ResourceInfo(File file, String str, ClassLoader classLoader) {
            this.file = (File) Preconditions.checkNotNull(file);
            this.resourceName = (String) Preconditions.checkNotNull(str);
            this.f14723a = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        static ResourceInfo a(File file, String str, ClassLoader classLoader) {
            return str.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION) ? new ClassInfo(file, str, classLoader) : new ResourceInfo(file, str, classLoader);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(url(), charset);
        }

        public boolean equals(@CheckForNull Object obj) {
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo resourceInfo = (ResourceInfo) obj;
            return this.resourceName.equals(resourceInfo.resourceName) && this.f14723a == resourceInfo.f14723a;
        }

        public final String getResourceName() {
            return this.resourceName;
        }

        public int hashCode() {
            return this.resourceName.hashCode();
        }

        public String toString() {
            return this.resourceName;
        }

        public final URL url() {
            URL resource = this.f14723a.getResource(this.resourceName);
            if (resource != null) {
                return resource;
            }
            throw new NoSuchElementException(this.resourceName);
        }
    }

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.resources = immutableSet;
    }

    static String b(String str) {
        return str.substring(0, str.length() - 6).replace(IOUtils.DIR_SEPARATOR_UNIX, '.');
    }

    static ImmutableMap c(ClassLoader classLoader) {
        LinkedHashMap linkedHashMapNewLinkedHashMap = Maps.newLinkedHashMap();
        ClassLoader parent = classLoader.getParent();
        if (parent != null) {
            linkedHashMapNewLinkedHashMap.putAll(c(parent));
        }
        UnmodifiableIterator<URL> it = getClassLoaderUrls(classLoader).iterator();
        while (it.hasNext()) {
            URL next = it.next();
            if (next.getProtocol().equals("file")) {
                File fileH = h(next);
                if (!linkedHashMapNewLinkedHashMap.containsKey(fileH)) {
                    linkedHashMapNewLinkedHashMap.put(fileH, classLoader);
                }
            }
        }
        return ImmutableMap.copyOf((Map) linkedHashMapNewLinkedHashMap);
    }

    static URL d(File file, String str) {
        return new URL(file.toURI().toURL(), str);
    }

    static ImmutableSet e(File file, Manifest manifest) {
        if (manifest == null) {
            return ImmutableSet.of();
        }
        ImmutableSet.Builder builder = ImmutableSet.builder();
        String value = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
        if (value != null) {
            for (String str : CLASS_PATH_ATTRIBUTE_SEPARATOR.split(value)) {
                try {
                    URL urlD = d(file, str);
                    if (urlD.getProtocol().equals("file")) {
                        builder.add((ImmutableSet.Builder) h(urlD));
                    }
                } catch (MalformedURLException unused) {
                    logger.warning("Invalid Class-Path entry: " + str);
                }
            }
        }
        return builder.build();
    }

    static ImmutableSet f(ClassLoader classLoader) {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator it = c(classLoader).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            builder.add((ImmutableSet.Builder) new LocationInfo((File) entry.getKey(), (ClassLoader) entry.getValue()));
        }
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ClassPath from(ClassLoader classLoader) throws IOException {
        ImmutableSet immutableSetF = f(classLoader);
        HashSet hashSet = new HashSet();
        UnmodifiableIterator it = immutableSetF.iterator();
        while (it.hasNext()) {
            hashSet.add(((LocationInfo) it.next()).file());
        }
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator it2 = immutableSetF.iterator();
        while (it2.hasNext()) {
            builder.addAll((Iterable) ((LocationInfo) it2.next()).scanResources(hashSet));
        }
        return new ClassPath(builder.build());
    }

    static ImmutableList g() {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (String str : Splitter.on(StandardSystemProperty.PATH_SEPARATOR.value()).split(StandardSystemProperty.JAVA_CLASS_PATH.value())) {
            try {
                try {
                    builder.add((ImmutableList.Builder) new File(str).toURI().toURL());
                } catch (SecurityException unused) {
                    builder.add((ImmutableList.Builder) new URL("file", (String) null, new File(str).getAbsolutePath()));
                }
            } catch (MalformedURLException e2) {
                logger.log(Level.WARNING, "malformed classpath entry: " + str, (Throwable) e2);
            }
        }
        return builder.build();
    }

    private static ImmutableList<URL> getClassLoaderUrls(ClassLoader classLoader) {
        return classLoader instanceof URLClassLoader ? ImmutableList.copyOf(((URLClassLoader) classLoader).getURLs()) : classLoader.equals(ClassLoader.getSystemClassLoader()) ? g() : ImmutableList.of();
    }

    static File h(URL url) {
        Preconditions.checkArgument(url.getProtocol().equals("file"));
        try {
            return new File(url.toURI());
        } catch (URISyntaxException unused) {
            return new File(url.getPath());
        }
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(new Predicate() { // from class: com.google.common.reflect.a
            @Override // com.google.common.base.Predicate
            public final boolean apply(Object obj) {
                return ((ClassPath.ClassInfo) obj).isTopLevel();
            }
        }).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String str) {
        Preconditions.checkNotNull(str);
        String str2 = str + '.';
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getName().startsWith(str2)) {
                builder.add((ImmutableSet.Builder) next);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String str) {
        Preconditions.checkNotNull(str);
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getPackageName().equals(str)) {
                builder.add((ImmutableSet.Builder) next);
            }
        }
        return builder.build();
    }
}
