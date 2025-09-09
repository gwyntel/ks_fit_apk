package io.flutter.plugins.imagepicker;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.imagepicker.Messages;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Messages {

    public static final class CacheRetrievalError {

        @NonNull
        private String code;

        @Nullable
        private String message;

        public static final class Builder {

            @Nullable
            private String code;

            @Nullable
            private String message;

            @NonNull
            public CacheRetrievalError build() {
                CacheRetrievalError cacheRetrievalError = new CacheRetrievalError();
                cacheRetrievalError.setCode(this.code);
                cacheRetrievalError.setMessage(this.message);
                return cacheRetrievalError;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setCode(@NonNull String str) {
                this.code = str;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setMessage(@Nullable String str) {
                this.message = str;
                return this;
            }
        }

        CacheRetrievalError() {
        }

        @NonNull
        static CacheRetrievalError fromList(@NonNull ArrayList<Object> arrayList) {
            CacheRetrievalError cacheRetrievalError = new CacheRetrievalError();
            cacheRetrievalError.setCode((String) arrayList.get(0));
            cacheRetrievalError.setMessage((String) arrayList.get(1));
            return cacheRetrievalError;
        }

        @NonNull
        public String getCode() {
            return this.code;
        }

        @Nullable
        public String getMessage() {
            return this.message;
        }

        public void setCode(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"code\" is null.");
            }
            this.code = str;
        }

        public void setMessage(@Nullable String str) {
            this.message = str;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.code);
            arrayList.add(this.message);
            return arrayList;
        }
    }

    public static final class CacheRetrievalResult {

        @Nullable
        private CacheRetrievalError error;

        @NonNull
        private List<String> paths;

        @NonNull
        private CacheRetrievalType type;

        public static final class Builder {

            @Nullable
            private CacheRetrievalError error;

            @Nullable
            private List<String> paths;

            @Nullable
            private CacheRetrievalType type;

            @NonNull
            public CacheRetrievalResult build() {
                CacheRetrievalResult cacheRetrievalResult = new CacheRetrievalResult();
                cacheRetrievalResult.setType(this.type);
                cacheRetrievalResult.setError(this.error);
                cacheRetrievalResult.setPaths(this.paths);
                return cacheRetrievalResult;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setError(@Nullable CacheRetrievalError cacheRetrievalError) {
                this.error = cacheRetrievalError;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setPaths(@NonNull List<String> list) {
                this.paths = list;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setType(@NonNull CacheRetrievalType cacheRetrievalType) {
                this.type = cacheRetrievalType;
                return this;
            }
        }

        CacheRetrievalResult() {
        }

        @NonNull
        static CacheRetrievalResult fromList(@NonNull ArrayList<Object> arrayList) {
            CacheRetrievalResult cacheRetrievalResult = new CacheRetrievalResult();
            cacheRetrievalResult.setType(CacheRetrievalType.values()[((Integer) arrayList.get(0)).intValue()]);
            Object obj = arrayList.get(1);
            cacheRetrievalResult.setError(obj == null ? null : CacheRetrievalError.fromList((ArrayList) obj));
            cacheRetrievalResult.setPaths((List) arrayList.get(2));
            return cacheRetrievalResult;
        }

        @Nullable
        public CacheRetrievalError getError() {
            return this.error;
        }

        @NonNull
        public List<String> getPaths() {
            return this.paths;
        }

        @NonNull
        public CacheRetrievalType getType() {
            return this.type;
        }

        public void setError(@Nullable CacheRetrievalError cacheRetrievalError) {
            this.error = cacheRetrievalError;
        }

        public void setPaths(@NonNull List<String> list) {
            if (list == null) {
                throw new IllegalStateException("Nonnull field \"paths\" is null.");
            }
            this.paths = list;
        }

        public void setType(@NonNull CacheRetrievalType cacheRetrievalType) {
            if (cacheRetrievalType == null) {
                throw new IllegalStateException("Nonnull field \"type\" is null.");
            }
            this.type = cacheRetrievalType;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(3);
            CacheRetrievalType cacheRetrievalType = this.type;
            arrayList.add(cacheRetrievalType == null ? null : Integer.valueOf(cacheRetrievalType.index));
            CacheRetrievalError cacheRetrievalError = this.error;
            arrayList.add(cacheRetrievalError != null ? cacheRetrievalError.toList() : null);
            arrayList.add(this.paths);
            return arrayList;
        }
    }

    public enum CacheRetrievalType {
        IMAGE(0),
        VIDEO(1);

        final int index;

        CacheRetrievalType(int i2) {
            this.index = i2;
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    @interface CanIgnoreReturnValue {
    }

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    public static final class GeneralOptions {

        @NonNull
        private Boolean allowMultiple;

        @Nullable
        private Long limit;

        @NonNull
        private Boolean usePhotoPicker;

        public static final class Builder {

            @Nullable
            private Boolean allowMultiple;

            @Nullable
            private Long limit;

            @Nullable
            private Boolean usePhotoPicker;

            @NonNull
            public GeneralOptions build() {
                GeneralOptions generalOptions = new GeneralOptions();
                generalOptions.setAllowMultiple(this.allowMultiple);
                generalOptions.setUsePhotoPicker(this.usePhotoPicker);
                generalOptions.setLimit(this.limit);
                return generalOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAllowMultiple(@NonNull Boolean bool) {
                this.allowMultiple = bool;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setLimit(@Nullable Long l2) {
                this.limit = l2;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setUsePhotoPicker(@NonNull Boolean bool) {
                this.usePhotoPicker = bool;
                return this;
            }
        }

        GeneralOptions() {
        }

        @NonNull
        static GeneralOptions fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            GeneralOptions generalOptions = new GeneralOptions();
            generalOptions.setAllowMultiple((Boolean) arrayList.get(0));
            generalOptions.setUsePhotoPicker((Boolean) arrayList.get(1));
            Object obj = arrayList.get(2);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            generalOptions.setLimit(lValueOf);
            return generalOptions;
        }

        @NonNull
        public Boolean getAllowMultiple() {
            return this.allowMultiple;
        }

        @Nullable
        public Long getLimit() {
            return this.limit;
        }

        @NonNull
        public Boolean getUsePhotoPicker() {
            return this.usePhotoPicker;
        }

        public void setAllowMultiple(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"allowMultiple\" is null.");
            }
            this.allowMultiple = bool;
        }

        public void setLimit(@Nullable Long l2) {
            this.limit = l2;
        }

        public void setUsePhotoPicker(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"usePhotoPicker\" is null.");
            }
            this.usePhotoPicker = bool;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(3);
            arrayList.add(this.allowMultiple);
            arrayList.add(this.usePhotoPicker);
            arrayList.add(this.limit);
            return arrayList;
        }
    }

    public interface ImagePickerApi {

        /* renamed from: io.flutter.plugins.imagepicker.Messages$ImagePickerApi$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static MessageCodec a() {
                return ImagePickerApiCodec.INSTANCE;
            }

            public static /* synthetic */ void b(ImagePickerApi imagePickerApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) obj;
                imagePickerApi.pickImages((SourceSpecification) arrayList2.get(0), (ImageSelectionOptions) arrayList2.get(1), (GeneralOptions) arrayList2.get(2), new Result<List<String>>() { // from class: io.flutter.plugins.imagepicker.Messages.ImagePickerApi.1
                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void success(List<String> list) {
                        arrayList.add(0, list);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void c(ImagePickerApi imagePickerApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) obj;
                imagePickerApi.pickVideos((SourceSpecification) arrayList2.get(0), (VideoSelectionOptions) arrayList2.get(1), (GeneralOptions) arrayList2.get(2), new Result<List<String>>() { // from class: io.flutter.plugins.imagepicker.Messages.ImagePickerApi.2
                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void success(List<String> list) {
                        arrayList.add(0, list);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void d(ImagePickerApi imagePickerApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) obj;
                imagePickerApi.pickMedia((MediaSelectionOptions) arrayList2.get(0), (GeneralOptions) arrayList2.get(1), new Result<List<String>>() { // from class: io.flutter.plugins.imagepicker.Messages.ImagePickerApi.3
                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.imagepicker.Messages.Result
                    public void success(List<String> list) {
                        arrayList.add(0, list);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void e(ImagePickerApi imagePickerApi, Object obj, BasicMessageChannel.Reply reply) {
                ArrayList<Object> arrayList = new ArrayList<>();
                try {
                    arrayList.add(0, imagePickerApi.retrieveLostResults());
                } catch (Throwable th) {
                    arrayList = Messages.wrapError(th);
                }
                reply.reply(arrayList);
            }

            public static void f(BinaryMessenger binaryMessenger, final ImagePickerApi imagePickerApi) {
                BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.image_picker_android.ImagePickerApi.pickImages", a(), binaryMessenger.makeBackgroundTaskQueue());
                if (imagePickerApi != null) {
                    basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.imagepicker.k
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.ImagePickerApi.CC.b(imagePickerApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.image_picker_android.ImagePickerApi.pickVideos", a(), binaryMessenger.makeBackgroundTaskQueue());
                if (imagePickerApi != null) {
                    basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.imagepicker.l
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.ImagePickerApi.CC.c(imagePickerApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel2.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.image_picker_android.ImagePickerApi.pickMedia", a());
                if (imagePickerApi != null) {
                    basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.imagepicker.m
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.ImagePickerApi.CC.d(imagePickerApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel3.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.image_picker_android.ImagePickerApi.retrieveLostResults", a(), binaryMessenger.makeBackgroundTaskQueue());
                if (imagePickerApi != null) {
                    basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.imagepicker.n
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.ImagePickerApi.CC.e(imagePickerApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel4.setMessageHandler(null);
                }
            }
        }

        void pickImages(@NonNull SourceSpecification sourceSpecification, @NonNull ImageSelectionOptions imageSelectionOptions, @NonNull GeneralOptions generalOptions, @NonNull Result<List<String>> result);

        void pickMedia(@NonNull MediaSelectionOptions mediaSelectionOptions, @NonNull GeneralOptions generalOptions, @NonNull Result<List<String>> result);

        void pickVideos(@NonNull SourceSpecification sourceSpecification, @NonNull VideoSelectionOptions videoSelectionOptions, @NonNull GeneralOptions generalOptions, @NonNull Result<List<String>> result);

        @Nullable
        CacheRetrievalResult retrieveLostResults();
    }

    private static class ImagePickerApiCodec extends StandardMessageCodec {
        public static final ImagePickerApiCodec INSTANCE = new ImagePickerApiCodec();

        private ImagePickerApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            switch (b2) {
                case Byte.MIN_VALUE:
                    return CacheRetrievalError.fromList((ArrayList) readValue(byteBuffer));
                case -127:
                    return CacheRetrievalResult.fromList((ArrayList) readValue(byteBuffer));
                case -126:
                    return GeneralOptions.fromList((ArrayList) readValue(byteBuffer));
                case -125:
                    return ImageSelectionOptions.fromList((ArrayList) readValue(byteBuffer));
                case -124:
                    return MediaSelectionOptions.fromList((ArrayList) readValue(byteBuffer));
                case -123:
                    return SourceSpecification.fromList((ArrayList) readValue(byteBuffer));
                case -122:
                    return VideoSelectionOptions.fromList((ArrayList) readValue(byteBuffer));
                default:
                    return super.readValueOfType(b2, byteBuffer);
            }
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof CacheRetrievalError) {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((CacheRetrievalError) obj).toList());
                return;
            }
            if (obj instanceof CacheRetrievalResult) {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((CacheRetrievalResult) obj).toList());
                return;
            }
            if (obj instanceof GeneralOptions) {
                byteArrayOutputStream.write(130);
                writeValue(byteArrayOutputStream, ((GeneralOptions) obj).toList());
                return;
            }
            if (obj instanceof ImageSelectionOptions) {
                byteArrayOutputStream.write(131);
                writeValue(byteArrayOutputStream, ((ImageSelectionOptions) obj).toList());
                return;
            }
            if (obj instanceof MediaSelectionOptions) {
                byteArrayOutputStream.write(132);
                writeValue(byteArrayOutputStream, ((MediaSelectionOptions) obj).toList());
            } else if (obj instanceof SourceSpecification) {
                byteArrayOutputStream.write(133);
                writeValue(byteArrayOutputStream, ((SourceSpecification) obj).toList());
            } else if (!(obj instanceof VideoSelectionOptions)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(134);
                writeValue(byteArrayOutputStream, ((VideoSelectionOptions) obj).toList());
            }
        }
    }

    public static final class ImageSelectionOptions {

        @Nullable
        private Double maxHeight;

        @Nullable
        private Double maxWidth;

        @NonNull
        private Long quality;

        public static final class Builder {

            @Nullable
            private Double maxHeight;

            @Nullable
            private Double maxWidth;

            @Nullable
            private Long quality;

            @NonNull
            public ImageSelectionOptions build() {
                ImageSelectionOptions imageSelectionOptions = new ImageSelectionOptions();
                imageSelectionOptions.setMaxWidth(this.maxWidth);
                imageSelectionOptions.setMaxHeight(this.maxHeight);
                imageSelectionOptions.setQuality(this.quality);
                return imageSelectionOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setMaxHeight(@Nullable Double d2) {
                this.maxHeight = d2;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setMaxWidth(@Nullable Double d2) {
                this.maxWidth = d2;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setQuality(@NonNull Long l2) {
                this.quality = l2;
                return this;
            }
        }

        ImageSelectionOptions() {
        }

        @NonNull
        static ImageSelectionOptions fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            ImageSelectionOptions imageSelectionOptions = new ImageSelectionOptions();
            imageSelectionOptions.setMaxWidth((Double) arrayList.get(0));
            imageSelectionOptions.setMaxHeight((Double) arrayList.get(1));
            Object obj = arrayList.get(2);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            imageSelectionOptions.setQuality(lValueOf);
            return imageSelectionOptions;
        }

        @Nullable
        public Double getMaxHeight() {
            return this.maxHeight;
        }

        @Nullable
        public Double getMaxWidth() {
            return this.maxWidth;
        }

        @NonNull
        public Long getQuality() {
            return this.quality;
        }

        public void setMaxHeight(@Nullable Double d2) {
            this.maxHeight = d2;
        }

        public void setMaxWidth(@Nullable Double d2) {
            this.maxWidth = d2;
        }

        public void setQuality(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"quality\" is null.");
            }
            this.quality = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(3);
            arrayList.add(this.maxWidth);
            arrayList.add(this.maxHeight);
            arrayList.add(this.quality);
            return arrayList;
        }
    }

    public static final class MediaSelectionOptions {

        @NonNull
        private ImageSelectionOptions imageSelectionOptions;

        public static final class Builder {

            @Nullable
            private ImageSelectionOptions imageSelectionOptions;

            @NonNull
            public MediaSelectionOptions build() {
                MediaSelectionOptions mediaSelectionOptions = new MediaSelectionOptions();
                mediaSelectionOptions.setImageSelectionOptions(this.imageSelectionOptions);
                return mediaSelectionOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setImageSelectionOptions(@NonNull ImageSelectionOptions imageSelectionOptions) {
                this.imageSelectionOptions = imageSelectionOptions;
                return this;
            }
        }

        MediaSelectionOptions() {
        }

        @NonNull
        static MediaSelectionOptions fromList(@NonNull ArrayList<Object> arrayList) {
            MediaSelectionOptions mediaSelectionOptions = new MediaSelectionOptions();
            Object obj = arrayList.get(0);
            mediaSelectionOptions.setImageSelectionOptions(obj == null ? null : ImageSelectionOptions.fromList((ArrayList) obj));
            return mediaSelectionOptions;
        }

        @NonNull
        public ImageSelectionOptions getImageSelectionOptions() {
            return this.imageSelectionOptions;
        }

        public void setImageSelectionOptions(@NonNull ImageSelectionOptions imageSelectionOptions) {
            if (imageSelectionOptions == null) {
                throw new IllegalStateException("Nonnull field \"imageSelectionOptions\" is null.");
            }
            this.imageSelectionOptions = imageSelectionOptions;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            ImageSelectionOptions imageSelectionOptions = this.imageSelectionOptions;
            arrayList.add(imageSelectionOptions == null ? null : imageSelectionOptions.toList());
            return arrayList;
        }
    }

    public interface NullableResult<T> {
        void error(@NonNull Throwable th);

        void success(@Nullable T t2);
    }

    public interface Result<T> {
        void error(@NonNull Throwable th);

        void success(@NonNull T t2);
    }

    public enum SourceCamera {
        REAR(0),
        FRONT(1);

        final int index;

        SourceCamera(int i2) {
            this.index = i2;
        }
    }

    public static final class SourceSpecification {

        @Nullable
        private SourceCamera camera;

        @NonNull
        private SourceType type;

        public static final class Builder {

            @Nullable
            private SourceCamera camera;

            @Nullable
            private SourceType type;

            @NonNull
            public SourceSpecification build() {
                SourceSpecification sourceSpecification = new SourceSpecification();
                sourceSpecification.setType(this.type);
                sourceSpecification.setCamera(this.camera);
                return sourceSpecification;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setCamera(@Nullable SourceCamera sourceCamera) {
                this.camera = sourceCamera;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setType(@NonNull SourceType sourceType) {
                this.type = sourceType;
                return this;
            }
        }

        SourceSpecification() {
        }

        @NonNull
        static SourceSpecification fromList(@NonNull ArrayList<Object> arrayList) {
            SourceSpecification sourceSpecification = new SourceSpecification();
            sourceSpecification.setType(SourceType.values()[((Integer) arrayList.get(0)).intValue()]);
            Object obj = arrayList.get(1);
            sourceSpecification.setCamera(obj == null ? null : SourceCamera.values()[((Integer) obj).intValue()]);
            return sourceSpecification;
        }

        @Nullable
        public SourceCamera getCamera() {
            return this.camera;
        }

        @NonNull
        public SourceType getType() {
            return this.type;
        }

        public void setCamera(@Nullable SourceCamera sourceCamera) {
            this.camera = sourceCamera;
        }

        public void setType(@NonNull SourceType sourceType) {
            if (sourceType == null) {
                throw new IllegalStateException("Nonnull field \"type\" is null.");
            }
            this.type = sourceType;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            SourceType sourceType = this.type;
            arrayList.add(sourceType == null ? null : Integer.valueOf(sourceType.index));
            SourceCamera sourceCamera = this.camera;
            arrayList.add(sourceCamera != null ? Integer.valueOf(sourceCamera.index) : null);
            return arrayList;
        }
    }

    public enum SourceType {
        CAMERA(0),
        GALLERY(1);

        final int index;

        SourceType(int i2) {
            this.index = i2;
        }
    }

    public static final class VideoSelectionOptions {

        @Nullable
        private Long maxDurationSeconds;

        public static final class Builder {

            @Nullable
            private Long maxDurationSeconds;

            @NonNull
            public VideoSelectionOptions build() {
                VideoSelectionOptions videoSelectionOptions = new VideoSelectionOptions();
                videoSelectionOptions.setMaxDurationSeconds(this.maxDurationSeconds);
                return videoSelectionOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setMaxDurationSeconds(@Nullable Long l2) {
                this.maxDurationSeconds = l2;
                return this;
            }
        }

        @NonNull
        static VideoSelectionOptions fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            VideoSelectionOptions videoSelectionOptions = new VideoSelectionOptions();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            videoSelectionOptions.setMaxDurationSeconds(lValueOf);
            return videoSelectionOptions;
        }

        @Nullable
        public Long getMaxDurationSeconds() {
            return this.maxDurationSeconds;
        }

        public void setMaxDurationSeconds(@Nullable Long l2) {
            this.maxDurationSeconds = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(this.maxDurationSeconds);
            return arrayList;
        }
    }

    public interface VoidResult {
        void error(@NonNull Throwable th);

        void success();
    }

    @NonNull
    protected static ArrayList<Object> wrapError(@NonNull Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
        } else {
            arrayList.add(th.toString());
            arrayList.add(th.getClass().getSimpleName());
            arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        }
        return arrayList;
    }
}
