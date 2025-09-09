package io.flutter.plugins.videoplayer;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public class Messages {

    public interface AndroidVideoPlayerApi {
        @NonNull
        TextureMessage create(@NonNull CreateMessage createMessage);

        void dispose(@NonNull TextureMessage textureMessage);

        void initialize();

        void pause(@NonNull TextureMessage textureMessage);

        void play(@NonNull TextureMessage textureMessage);

        @NonNull
        PositionMessage position(@NonNull TextureMessage textureMessage);

        void seekTo(@NonNull PositionMessage positionMessage);

        void setLooping(@NonNull LoopingMessage loopingMessage);

        void setMixWithOthers(@NonNull MixWithOthersMessage mixWithOthersMessage);

        void setPlaybackSpeed(@NonNull PlaybackSpeedMessage playbackSpeedMessage);

        void setVolume(@NonNull VolumeMessage volumeMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AndroidVideoPlayerApiCodec extends StandardMessageCodec {
        public static final AndroidVideoPlayerApiCodec INSTANCE = new AndroidVideoPlayerApiCodec();

        private AndroidVideoPlayerApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            switch (b2) {
                case Byte.MIN_VALUE:
                    return CreateMessage.fromList((ArrayList) readValue(byteBuffer));
                case -127:
                    return LoopingMessage.fromList((ArrayList) readValue(byteBuffer));
                case -126:
                    return MixWithOthersMessage.fromList((ArrayList) readValue(byteBuffer));
                case -125:
                    return PlaybackSpeedMessage.fromList((ArrayList) readValue(byteBuffer));
                case -124:
                    return PositionMessage.fromList((ArrayList) readValue(byteBuffer));
                case -123:
                    return TextureMessage.fromList((ArrayList) readValue(byteBuffer));
                case -122:
                    return VolumeMessage.fromList((ArrayList) readValue(byteBuffer));
                default:
                    return super.readValueOfType(b2, byteBuffer);
            }
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof CreateMessage) {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((CreateMessage) obj).toList());
                return;
            }
            if (obj instanceof LoopingMessage) {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((LoopingMessage) obj).toList());
                return;
            }
            if (obj instanceof MixWithOthersMessage) {
                byteArrayOutputStream.write(130);
                writeValue(byteArrayOutputStream, ((MixWithOthersMessage) obj).toList());
                return;
            }
            if (obj instanceof PlaybackSpeedMessage) {
                byteArrayOutputStream.write(131);
                writeValue(byteArrayOutputStream, ((PlaybackSpeedMessage) obj).toList());
                return;
            }
            if (obj instanceof PositionMessage) {
                byteArrayOutputStream.write(132);
                writeValue(byteArrayOutputStream, ((PositionMessage) obj).toList());
            } else if (obj instanceof TextureMessage) {
                byteArrayOutputStream.write(133);
                writeValue(byteArrayOutputStream, ((TextureMessage) obj).toList());
            } else if (!(obj instanceof VolumeMessage)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(134);
                writeValue(byteArrayOutputStream, ((VolumeMessage) obj).toList());
            }
        }
    }

    public static final class CreateMessage {

        @Nullable
        private String asset;

        @Nullable
        private String formatHint;

        @NonNull
        private Map<String, String> httpHeaders;

        @Nullable
        private String packageName;

        @Nullable
        private String uri;

        public static final class Builder {

            @Nullable
            private String asset;

            @Nullable
            private String formatHint;

            @Nullable
            private Map<String, String> httpHeaders;

            @Nullable
            private String packageName;

            @Nullable
            private String uri;

            @NonNull
            public CreateMessage build() {
                CreateMessage createMessage = new CreateMessage();
                createMessage.setAsset(this.asset);
                createMessage.setUri(this.uri);
                createMessage.setPackageName(this.packageName);
                createMessage.setFormatHint(this.formatHint);
                createMessage.setHttpHeaders(this.httpHeaders);
                return createMessage;
            }

            @NonNull
            public Builder setAsset(@Nullable String str) {
                this.asset = str;
                return this;
            }

            @NonNull
            public Builder setFormatHint(@Nullable String str) {
                this.formatHint = str;
                return this;
            }

            @NonNull
            public Builder setHttpHeaders(@NonNull Map<String, String> map) {
                this.httpHeaders = map;
                return this;
            }

            @NonNull
            public Builder setPackageName(@Nullable String str) {
                this.packageName = str;
                return this;
            }

            @NonNull
            public Builder setUri(@Nullable String str) {
                this.uri = str;
                return this;
            }
        }

        CreateMessage() {
        }

        @NonNull
        static CreateMessage fromList(@NonNull ArrayList<Object> arrayList) {
            CreateMessage createMessage = new CreateMessage();
            createMessage.setAsset((String) arrayList.get(0));
            createMessage.setUri((String) arrayList.get(1));
            createMessage.setPackageName((String) arrayList.get(2));
            createMessage.setFormatHint((String) arrayList.get(3));
            createMessage.setHttpHeaders((Map) arrayList.get(4));
            return createMessage;
        }

        @Nullable
        public String getAsset() {
            return this.asset;
        }

        @Nullable
        public String getFormatHint() {
            return this.formatHint;
        }

        @NonNull
        public Map<String, String> getHttpHeaders() {
            return this.httpHeaders;
        }

        @Nullable
        public String getPackageName() {
            return this.packageName;
        }

        @Nullable
        public String getUri() {
            return this.uri;
        }

        public void setAsset(@Nullable String str) {
            this.asset = str;
        }

        public void setFormatHint(@Nullable String str) {
            this.formatHint = str;
        }

        public void setHttpHeaders(@NonNull Map<String, String> map) {
            if (map == null) {
                throw new IllegalStateException("Nonnull field \"httpHeaders\" is null.");
            }
            this.httpHeaders = map;
        }

        public void setPackageName(@Nullable String str) {
            this.packageName = str;
        }

        public void setUri(@Nullable String str) {
            this.uri = str;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(5);
            arrayList.add(this.asset);
            arrayList.add(this.uri);
            arrayList.add(this.packageName);
            arrayList.add(this.formatHint);
            arrayList.add(this.httpHeaders);
            return arrayList;
        }
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

    public static final class LoopingMessage {

        @NonNull
        private Boolean isLooping;

        @NonNull
        private Long textureId;

        public static final class Builder {

            @Nullable
            private Boolean isLooping;

            @Nullable
            private Long textureId;

            @NonNull
            public LoopingMessage build() {
                LoopingMessage loopingMessage = new LoopingMessage();
                loopingMessage.setTextureId(this.textureId);
                loopingMessage.setIsLooping(this.isLooping);
                return loopingMessage;
            }

            @NonNull
            public Builder setIsLooping(@NonNull Boolean bool) {
                this.isLooping = bool;
                return this;
            }

            @NonNull
            public Builder setTextureId(@NonNull Long l2) {
                this.textureId = l2;
                return this;
            }
        }

        LoopingMessage() {
        }

        @NonNull
        static LoopingMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            LoopingMessage loopingMessage = new LoopingMessage();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            loopingMessage.setTextureId(lValueOf);
            loopingMessage.setIsLooping((Boolean) arrayList.get(1));
            return loopingMessage;
        }

        @NonNull
        public Boolean getIsLooping() {
            return this.isLooping;
        }

        @NonNull
        public Long getTextureId() {
            return this.textureId;
        }

        public void setIsLooping(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"isLooping\" is null.");
            }
            this.isLooping = bool;
        }

        public void setTextureId(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"textureId\" is null.");
            }
            this.textureId = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.textureId);
            arrayList.add(this.isLooping);
            return arrayList;
        }
    }

    public static final class MixWithOthersMessage {

        @NonNull
        private Boolean mixWithOthers;

        public static final class Builder {

            @Nullable
            private Boolean mixWithOthers;

            @NonNull
            public MixWithOthersMessage build() {
                MixWithOthersMessage mixWithOthersMessage = new MixWithOthersMessage();
                mixWithOthersMessage.setMixWithOthers(this.mixWithOthers);
                return mixWithOthersMessage;
            }

            @NonNull
            public Builder setMixWithOthers(@NonNull Boolean bool) {
                this.mixWithOthers = bool;
                return this;
            }
        }

        MixWithOthersMessage() {
        }

        @NonNull
        static MixWithOthersMessage fromList(@NonNull ArrayList<Object> arrayList) {
            MixWithOthersMessage mixWithOthersMessage = new MixWithOthersMessage();
            mixWithOthersMessage.setMixWithOthers((Boolean) arrayList.get(0));
            return mixWithOthersMessage;
        }

        @NonNull
        public Boolean getMixWithOthers() {
            return this.mixWithOthers;
        }

        public void setMixWithOthers(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"mixWithOthers\" is null.");
            }
            this.mixWithOthers = bool;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(this.mixWithOthers);
            return arrayList;
        }
    }

    public static final class PlaybackSpeedMessage {

        @NonNull
        private Double speed;

        @NonNull
        private Long textureId;

        public static final class Builder {

            @Nullable
            private Double speed;

            @Nullable
            private Long textureId;

            @NonNull
            public PlaybackSpeedMessage build() {
                PlaybackSpeedMessage playbackSpeedMessage = new PlaybackSpeedMessage();
                playbackSpeedMessage.setTextureId(this.textureId);
                playbackSpeedMessage.setSpeed(this.speed);
                return playbackSpeedMessage;
            }

            @NonNull
            public Builder setSpeed(@NonNull Double d2) {
                this.speed = d2;
                return this;
            }

            @NonNull
            public Builder setTextureId(@NonNull Long l2) {
                this.textureId = l2;
                return this;
            }
        }

        PlaybackSpeedMessage() {
        }

        @NonNull
        static PlaybackSpeedMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            PlaybackSpeedMessage playbackSpeedMessage = new PlaybackSpeedMessage();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            playbackSpeedMessage.setTextureId(lValueOf);
            playbackSpeedMessage.setSpeed((Double) arrayList.get(1));
            return playbackSpeedMessage;
        }

        @NonNull
        public Double getSpeed() {
            return this.speed;
        }

        @NonNull
        public Long getTextureId() {
            return this.textureId;
        }

        public void setSpeed(@NonNull Double d2) {
            if (d2 == null) {
                throw new IllegalStateException("Nonnull field \"speed\" is null.");
            }
            this.speed = d2;
        }

        public void setTextureId(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"textureId\" is null.");
            }
            this.textureId = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.textureId);
            arrayList.add(this.speed);
            return arrayList;
        }
    }

    public static final class PositionMessage {

        @NonNull
        private Long position;

        @NonNull
        private Long textureId;

        public static final class Builder {

            @Nullable
            private Long position;

            @Nullable
            private Long textureId;

            @NonNull
            public PositionMessage build() {
                PositionMessage positionMessage = new PositionMessage();
                positionMessage.setTextureId(this.textureId);
                positionMessage.setPosition(this.position);
                return positionMessage;
            }

            @NonNull
            public Builder setPosition(@NonNull Long l2) {
                this.position = l2;
                return this;
            }

            @NonNull
            public Builder setTextureId(@NonNull Long l2) {
                this.textureId = l2;
                return this;
            }
        }

        PositionMessage() {
        }

        @NonNull
        static PositionMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            PositionMessage positionMessage = new PositionMessage();
            Object obj = arrayList.get(0);
            Long lValueOf2 = null;
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            positionMessage.setTextureId(lValueOf);
            Object obj2 = arrayList.get(1);
            if (obj2 != null) {
                lValueOf2 = Long.valueOf(obj2 instanceof Integer ? ((Integer) obj2).intValue() : ((Long) obj2).longValue());
            }
            positionMessage.setPosition(lValueOf2);
            return positionMessage;
        }

        @NonNull
        public Long getPosition() {
            return this.position;
        }

        @NonNull
        public Long getTextureId() {
            return this.textureId;
        }

        public void setPosition(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"position\" is null.");
            }
            this.position = l2;
        }

        public void setTextureId(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"textureId\" is null.");
            }
            this.textureId = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.textureId);
            arrayList.add(this.position);
            return arrayList;
        }
    }

    public static final class TextureMessage {

        @NonNull
        private Long textureId;

        public static final class Builder {

            @Nullable
            private Long textureId;

            @NonNull
            public TextureMessage build() {
                TextureMessage textureMessage = new TextureMessage();
                textureMessage.setTextureId(this.textureId);
                return textureMessage;
            }

            @NonNull
            public Builder setTextureId(@NonNull Long l2) {
                this.textureId = l2;
                return this;
            }
        }

        TextureMessage() {
        }

        @NonNull
        static TextureMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            TextureMessage textureMessage = new TextureMessage();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            textureMessage.setTextureId(lValueOf);
            return textureMessage;
        }

        @NonNull
        public Long getTextureId() {
            return this.textureId;
        }

        public void setTextureId(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"textureId\" is null.");
            }
            this.textureId = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(this.textureId);
            return arrayList;
        }
    }

    public static final class VolumeMessage {

        @NonNull
        private Long textureId;

        @NonNull
        private Double volume;

        public static final class Builder {

            @Nullable
            private Long textureId;

            @Nullable
            private Double volume;

            @NonNull
            public VolumeMessage build() {
                VolumeMessage volumeMessage = new VolumeMessage();
                volumeMessage.setTextureId(this.textureId);
                volumeMessage.setVolume(this.volume);
                return volumeMessage;
            }

            @NonNull
            public Builder setTextureId(@NonNull Long l2) {
                this.textureId = l2;
                return this;
            }

            @NonNull
            public Builder setVolume(@NonNull Double d2) {
                this.volume = d2;
                return this;
            }
        }

        VolumeMessage() {
        }

        @NonNull
        static VolumeMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            VolumeMessage volumeMessage = new VolumeMessage();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            volumeMessage.setTextureId(lValueOf);
            volumeMessage.setVolume((Double) arrayList.get(1));
            return volumeMessage;
        }

        @NonNull
        public Long getTextureId() {
            return this.textureId;
        }

        @NonNull
        public Double getVolume() {
            return this.volume;
        }

        public void setTextureId(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"textureId\" is null.");
            }
            this.textureId = l2;
        }

        public void setVolume(@NonNull Double d2) {
            if (d2 == null) {
                throw new IllegalStateException("Nonnull field \"volume\" is null.");
            }
            this.volume = d2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.textureId);
            arrayList.add(this.volume);
            return arrayList;
        }
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
