package androidx.exifinterface.media;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterfaceUtils;
import androidx.media3.common.MimeTypes;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class ExifInterface {
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = 1;
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = 2;
    public static final short CONTRAST_NORMAL = 0;
    public static final short CONTRAST_SOFT = 1;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    private static final Pattern DATETIME_PRIMARY_FORMAT_PATTERN;
    private static final Pattern DATETIME_SECONDARY_FORMAT_PATTERN;
    private static final int DATETIME_VALUE_STRING_LENGTH = 19;
    private static final ExifTag[] EXIF_POINTER_TAGS;
    public static final short EXPOSURE_MODE_AUTO = 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = 2;
    public static final short EXPOSURE_MODE_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_ACTION = 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = 4;
    public static final short FILE_SOURCE_DSC = 3;
    public static final short FILE_SOURCE_OTHER = 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = 1;
    public static final short FLAG_FLASH_FIRED = 1;
    public static final short FLAG_FLASH_MODE_AUTO = 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = 4;
    public static final short FORMAT_CHUNKY = 1;
    public static final short FORMAT_PLANAR = 2;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = 1;
    public static final short GAIN_CONTROL_NONE = 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    private static final Pattern GPS_TIMESTAMP_PATTERN;
    private static final byte[] IDENTIFIER_XMP_APP1;
    private static final ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = 14;
    public static final short LIGHT_SOURCE_D50 = 23;
    public static final short LIGHT_SOURCE_D55 = 20;
    public static final short LIGHT_SOURCE_D65 = 21;
    public static final short LIGHT_SOURCE_D75 = 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = 9;
    public static final short LIGHT_SOURCE_FLASH = 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = 24;
    public static final short LIGHT_SOURCE_OTHER = 255;
    public static final short LIGHT_SOURCE_SHADE = 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = 3;
    public static final short LIGHT_SOURCE_UNKNOWN = 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    public static final short METERING_MODE_AVERAGE = 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = 2;
    public static final short METERING_MODE_MULTI_SPOT = 4;
    public static final short METERING_MODE_OTHER = 255;
    public static final short METERING_MODE_PARTIAL = 6;
    public static final short METERING_MODE_PATTERN = 5;
    public static final short METERING_MODE_SPOT = 3;
    public static final short METERING_MODE_UNKNOWN = 0;
    private static final Pattern NON_ZERO_TIME_PATTERN;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS;
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int PNG_CHUNK_CRC_BYTE_LENGTH = 4;
    private static final int PNG_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = 1;
    public static final short RENDERED_PROCESS_NORMAL = 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = 3;
    public static final short RESOLUTION_UNIT_INCHES = 2;
    private static final short RW2_SIGNATURE = 85;
    public static final short SATURATION_HIGH = 0;
    public static final short SATURATION_LOW = 0;
    public static final short SATURATION_NORMAL = 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = 3;
    public static final short SENSITIVITY_TYPE_REI = 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = 6;
    public static final short SENSITIVITY_TYPE_SOS = 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = 1;
    public static final short SENSOR_TYPE_ONE_CHIP = 2;
    public static final short SENSOR_TYPE_THREE_CHIP = 4;
    public static final short SENSOR_TYPE_TRILINEAR = 7;
    public static final short SENSOR_TYPE_TWO_CHIP = 3;
    public static final short SHARPNESS_HARD = 2;
    public static final short SHARPNESS_NORMAL = 0;
    public static final short SHARPNESS_SOFT = 1;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    private static final int SKIP_BUFFER_SIZE = 8192;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = 0;
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";

    @Deprecated
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CAMERA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";

    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE;
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    private static final int WEBP_CHUNK_SIZE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_VP8X_DEFAULT_LENGTH = 10;
    private static final int WEBP_FILE_SIZE_BYTE_LENGTH = 4;
    private static final byte WEBP_VP8L_SIGNATURE = 47;

    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;

    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = 0;
    public static final short WHITE_BALANCE_MANUAL = 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = 2;

    /* renamed from: e, reason: collision with root package name */
    static final ExifTag[][] f4082e;

    /* renamed from: f, reason: collision with root package name */
    static final Charset f4083f;

    /* renamed from: g, reason: collision with root package name */
    static final byte[] f4084g;
    private static final HashMap<Integer, Integer> sExifPointerTagMap;
    private static final HashMap<Integer, ExifTag>[] sExifTagMapsForReading;
    private static final HashMap<String, ExifTag>[] sExifTagMapsForWriting;
    private static SimpleDateFormat sFormatterPrimary;
    private static SimpleDateFormat sFormatterSecondary;
    private static final HashSet<String> sTagSetForCompatibility;
    private boolean mAreThumbnailStripsConsecutive;
    private AssetManager.AssetInputStream mAssetInputStream;
    private final HashMap<String, ExifAttribute>[] mAttributes;
    private Set<Integer> mAttributesOffsets;
    private ByteOrder mExifByteOrder;
    private String mFilename;
    private boolean mHasThumbnail;
    private boolean mHasThumbnailStrips;
    private boolean mIsExifDataOnly;
    private int mMimeType;
    private boolean mModified;
    private int mOffsetToExifData;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;
    private boolean mXmpIsFromSeparateMarker;
    private static final String TAG = "ExifInterface";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final List<Integer> ROTATION_ORDER = Arrays.asList(1, 6, 3, 8);
    private static final List<Integer> FLIPPED_ROTATION_ORDER = Arrays.asList(2, 7, 4, 5);
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    private static final byte MARKER_SOI = -40;

    /* renamed from: a, reason: collision with root package name */
    static final byte[] f4078a = {-1, MARKER_SOI, -1};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, 112};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, Constants.CMD_TYPE.CMD_STATUS_REPORT};
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, Ascii.SUB, 10};
    private static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    private static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    private static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    private static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    private static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    private static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    private static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX};
    private static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());

    /* renamed from: b, reason: collision with root package name */
    static final String[] f4079b = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};

    /* renamed from: c, reason: collision with root package name */
    static final int[] f4080c = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};

    /* renamed from: d, reason: collision with root package name */
    static final byte[] f4081d = {65, 83, 67, 73, 73, 0, 0, 0};

    private static class ByteOrderedDataOutputStream extends FilterOutputStream {

        /* renamed from: a, reason: collision with root package name */
        final OutputStream f4090a;
        private ByteOrder mByteOrder;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.f4090a = outputStream;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.f4090a.write(bArr);
        }

        public void writeByte(int i2) throws IOException {
            this.f4090a.write(i2);
        }

        public void writeInt(int i2) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.f4090a.write(i2 & 255);
                this.f4090a.write((i2 >>> 8) & 255);
                this.f4090a.write((i2 >>> 16) & 255);
                this.f4090a.write((i2 >>> 24) & 255);
                return;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.f4090a.write((i2 >>> 24) & 255);
                this.f4090a.write((i2 >>> 16) & 255);
                this.f4090a.write((i2 >>> 8) & 255);
                this.f4090a.write(i2 & 255);
            }
        }

        public void writeShort(short s2) throws IOException {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.f4090a.write(s2 & 255);
                this.f4090a.write((s2 >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.f4090a.write((s2 >>> 8) & 255);
                this.f4090a.write(s2 & 255);
            }
        }

        public void writeUnsignedInt(long j2) throws IOException {
            if (j2 > 4294967295L) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 32-bit unsigned integer");
            }
            writeInt((int) j2);
        }

        public void writeUnsignedShort(int i2) throws IOException {
            if (i2 > 65535) {
                throw new IllegalArgumentException("val is larger than the maximum value of a 16-bit unsigned integer");
            }
            writeShort((short) i2);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            this.f4090a.write(bArr, i2, i3);
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        ExifAttribute(int i2, int i3, byte[] bArr) {
            this(i2, i3, -1L, bArr);
        }

        public static ExifAttribute createByte(String str) {
            if (str.length() == 1 && str.charAt(0) >= '0' && str.charAt(0) <= '1') {
                return new ExifAttribute(1, 1, new byte[]{(byte) (str.charAt(0) - '0')});
            }
            byte[] bytes = str.getBytes(ExifInterface.f4083f);
            return new ExifAttribute(1, bytes.length, bytes);
        }

        public static ExifAttribute createDouble(double[] dArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[12] * dArr.length]);
            byteBufferWrap.order(byteOrder);
            for (double d2 : dArr) {
                byteBufferWrap.putDouble(d2);
            }
            return new ExifAttribute(12, dArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createSLong(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[9] * iArr.length]);
            byteBufferWrap.order(byteOrder);
            for (int i2 : iArr) {
                byteBufferWrap.putInt(i2);
            }
            return new ExifAttribute(9, iArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createSRational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[10] * rationalArr.length]);
            byteBufferWrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                byteBufferWrap.putInt((int) rational.numerator);
                byteBufferWrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(10, rationalArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createString(String str) {
            byte[] bytes = (str + (char) 0).getBytes(ExifInterface.f4083f);
            return new ExifAttribute(2, bytes.length, bytes);
        }

        public static ExifAttribute createULong(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[4] * jArr.length]);
            byteBufferWrap.order(byteOrder);
            for (long j2 : jArr) {
                byteBufferWrap.putInt((int) j2);
            }
            return new ExifAttribute(4, jArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createURational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[5] * rationalArr.length]);
            byteBufferWrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                byteBufferWrap.putInt((int) rational.numerator);
                byteBufferWrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(5, rationalArr.length, byteBufferWrap.array());
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.f4080c[3] * iArr.length]);
            byteBufferWrap.order(byteOrder);
            for (int i2 : iArr) {
                byteBufferWrap.putShort((short) i2);
            }
            return new ExifAttribute(3, iArr.length, byteBufferWrap.array());
        }

        /* JADX WARN: Not initialized variable reg: 5, insn: 0x0030: MOVE (r4 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]) (LINE:49), block:B:17:0x0030 */
        /* JADX WARN: Removed duplicated region for block: B:162:0x018f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.Object a(java.nio.ByteOrder r12) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 436
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.ExifAttribute.a(java.nio.ByteOrder):java.lang.Object");
        }

        public double getDoubleValue(ByteOrder byteOrder) throws Throwable {
            Object objA = a(byteOrder);
            if (objA == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            }
            if (objA instanceof String) {
                return Double.parseDouble((String) objA);
            }
            if (objA instanceof long[]) {
                if (((long[]) objA).length == 1) {
                    return r5[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (objA instanceof int[]) {
                if (((int[]) objA).length == 1) {
                    return r5[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (objA instanceof double[]) {
                double[] dArr = (double[]) objA;
                if (dArr.length == 1) {
                    return dArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (!(objA instanceof Rational[])) {
                throw new NumberFormatException("Couldn't find a double value");
            }
            Rational[] rationalArr = (Rational[]) objA;
            if (rationalArr.length == 1) {
                return rationalArr[0].calculate();
            }
            throw new NumberFormatException("There are more than one component");
        }

        public int getIntValue(ByteOrder byteOrder) throws Throwable {
            Object objA = a(byteOrder);
            if (objA == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            }
            if (objA instanceof String) {
                return Integer.parseInt((String) objA);
            }
            if (objA instanceof long[]) {
                long[] jArr = (long[]) objA;
                if (jArr.length == 1) {
                    return (int) jArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (!(objA instanceof int[])) {
                throw new NumberFormatException("Couldn't find a integer value");
            }
            int[] iArr = (int[]) objA;
            if (iArr.length == 1) {
                return iArr[0];
            }
            throw new NumberFormatException("There are more than one component");
        }

        public String getStringValue(ByteOrder byteOrder) throws Throwable {
            Object objA = a(byteOrder);
            if (objA == null) {
                return null;
            }
            if (objA instanceof String) {
                return (String) objA;
            }
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            if (objA instanceof long[]) {
                long[] jArr = (long[]) objA;
                while (i2 < jArr.length) {
                    sb.append(jArr[i2]);
                    i2++;
                    if (i2 != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (objA instanceof int[]) {
                int[] iArr = (int[]) objA;
                while (i2 < iArr.length) {
                    sb.append(iArr[i2]);
                    i2++;
                    if (i2 != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (objA instanceof double[]) {
                double[] dArr = (double[]) objA;
                while (i2 < dArr.length) {
                    sb.append(dArr[i2]);
                    i2++;
                    if (i2 != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (!(objA instanceof Rational[])) {
                return null;
            }
            Rational[] rationalArr = (Rational[]) objA;
            while (i2 < rationalArr.length) {
                sb.append(rationalArr[i2].numerator);
                sb.append(IOUtils.DIR_SEPARATOR_UNIX);
                sb.append(rationalArr[i2].denominator);
                i2++;
                if (i2 != rationalArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        public int size() {
            return ExifInterface.f4080c[this.format] * this.numberOfComponents;
        }

        public String toString() {
            return "(" + ExifInterface.f4079b[this.format] + ", data length:" + this.bytes.length + ")";
        }

        ExifAttribute(int i2, int i3, long j2, byte[] bArr) {
            this.format = i2;
            this.numberOfComponents = i3;
            this.bytesOffset = j2;
            this.bytes = bArr;
        }

        public static ExifAttribute createDouble(double d2, ByteOrder byteOrder) {
            return createDouble(new double[]{d2}, byteOrder);
        }

        public static ExifAttribute createSLong(int i2, ByteOrder byteOrder) {
            return createSLong(new int[]{i2}, byteOrder);
        }

        public static ExifAttribute createULong(long j2, ByteOrder byteOrder) {
            return createULong(new long[]{j2}, byteOrder);
        }

        public static ExifAttribute createUShort(int i2, ByteOrder byteOrder) {
            return createUShort(new int[]{i2}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational rational, ByteOrder byteOrder) {
            return createSRational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createURational(Rational rational, ByteOrder byteOrder) {
            return createURational(new Rational[]{rational}, byteOrder);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ExifStreamType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface IfdType {
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        Rational(double d2) {
            this((long) (d2 * 10000.0d), 10000L);
        }

        public double calculate() {
            return this.numerator / this.denominator;
        }

        public String toString() {
            return this.numerator + "/" + this.denominator;
        }

        Rational(long j2, long j3) {
            if (j3 == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = j2;
                this.denominator = j3;
            }
        }
    }

    static {
        ExifTag[] exifTagArr = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, KeyType.QUERY_FRK_DEVICE_ERASE_COMMAND, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, KeyType.QUERY_BRIGHT_SCREEN_PARAM, 2), new ExifTag(TAG_MAKE, KeyType.QUERY_HEART_RATE_PARAM, 2), new ExifTag(TAG_MODEL, KeyType.SEND_MENSTRUAL_START_DAY, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, KeyType.QUERY_MENSTRUAL_DATA_FOR_MONTH, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, KeyType.SET_AI_WATCH_STATUS, 3), new ExifTag(TAG_ROWS_PER_STRIP, KeyType.SET_AI_WATCH_ENABLE, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, KeyType.QUERY_AI_WATCH_ENABLE, 3, 4), new ExifTag(TAG_X_RESOLUTION, KeyType.SET_AI_AGENT_TYPE, 5), new ExifTag(TAG_Y_RESOLUTION, KeyType.SYNC_AI_AGENT_MEMO_TO_DEVICE, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, KeyType.SET_LARGE_MODE_TYPE, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, TypedValues.AttributesType.TYPE_PIVOT_TARGET, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag(TAG_RW2_ISO, 23, 3), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, 7), new ExifTag(TAG_XMP, TypedValues.TransitionType.TYPE_DURATION, 1)};
        IFD_TIFF_TAGS = exifTagArr;
        ExifTag[] exifTagArr2 = {new ExifTag(TAG_EXPOSURE_TIME, 33434, 5), new ExifTag(TAG_F_NUMBER, 33437, 5), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag(TAG_SPECTRAL_SENSITIVITY, 34852, 2), new ExifTag(TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, 3), new ExifTag(TAG_OECF, 34856, 7), new ExifTag(TAG_SENSITIVITY_TYPE, 34864, 3), new ExifTag(TAG_STANDARD_OUTPUT_SENSITIVITY, 34865, 4), new ExifTag(TAG_RECOMMENDED_EXPOSURE_INDEX, 34866, 4), new ExifTag(TAG_ISO_SPEED, 34867, 4), new ExifTag(TAG_ISO_SPEED_LATITUDE_YYY, 34868, 4), new ExifTag(TAG_ISO_SPEED_LATITUDE_ZZZ, 34869, 4), new ExifTag(TAG_EXIF_VERSION, 36864, 2), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag(TAG_OFFSET_TIME, 36880, 2), new ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, 2), new ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, 2), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag(TAG_APERTURE_VALUE, 37378, 5), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, 5), new ExifTag(TAG_METERING_MODE, 37383, 3), new ExifTag(TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(TAG_FLASH, 37385, 3), new ExifTag(TAG_FOCAL_LENGTH, 37386, 5), new ExifTag(TAG_SUBJECT_AREA, 37396, 3), new ExifTag(TAG_MAKER_NOTE, 37500, 7), new ExifTag(TAG_USER_COMMENT, 37510, 7), new ExifTag(TAG_SUBSEC_TIME, 37520, 2), new ExifTag(TAG_SUBSEC_TIME_ORIGINAL, 37521, 2), new ExifTag(TAG_SUBSEC_TIME_DIGITIZED, 37522, 2), new ExifTag(TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag(TAG_COLOR_SPACE, 40961, 3), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag(TAG_RELATED_SOUND_FILE, 40964, 2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_FLASH_ENERGY, 41483, 5), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag(TAG_SUBJECT_LOCATION, 41492, 3), new ExifTag(TAG_EXPOSURE_INDEX, 41493, 5), new ExifTag(TAG_SENSING_METHOD, 41495, 3), new ExifTag(TAG_FILE_SOURCE, 41728, 7), new ExifTag(TAG_SCENE_TYPE, 41729, 7), new ExifTag(TAG_CFA_PATTERN, 41730, 7), new ExifTag(TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag(TAG_EXPOSURE_MODE, 41986, 3), new ExifTag(TAG_WHITE_BALANCE, 41987, 3), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag(TAG_GAIN_CONTROL, 41991, 3), new ExifTag(TAG_CONTRAST, 41992, 3), new ExifTag(TAG_SATURATION, 41993, 3), new ExifTag(TAG_SHARPNESS, 41994, 3), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, 2), new ExifTag("CameraOwnerName", 42032, 2), new ExifTag(TAG_BODY_SERIAL_NUMBER, 42033, 2), new ExifTag(TAG_LENS_SPECIFICATION, 42034, 5), new ExifTag(TAG_LENS_MAKE, 42035, 2), new ExifTag(TAG_LENS_MODEL, 42036, 2), new ExifTag(TAG_GAMMA, 42240, 5), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_EXIF_TAGS = exifTagArr2;
        ExifTag[] exifTagArr3 = {new ExifTag(TAG_GPS_VERSION_ID, 0, 1), new ExifTag(TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(TAG_GPS_LATITUDE, 2, 5, 10), new ExifTag(TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(TAG_GPS_LONGITUDE, 4, 5, 10), new ExifTag(TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(TAG_GPS_ALTITUDE, 6, 5), new ExifTag(TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(TAG_GPS_SATELLITES, 8, 2), new ExifTag(TAG_GPS_STATUS, 9, 2), new ExifTag(TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag(TAG_GPS_DOP, 11, 5), new ExifTag(TAG_GPS_SPEED_REF, 12, 2), new ExifTag(TAG_GPS_SPEED, 13, 5), new ExifTag(TAG_GPS_TRACK_REF, 14, 2), new ExifTag(TAG_GPS_TRACK, 15, 5), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag(TAG_GPS_MAP_DATUM, 18, 2), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag(TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(TAG_GPS_DEST_BEARING, 24, 5), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag(TAG_GPS_DATESTAMP, 29, 2), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, 3), new ExifTag(TAG_GPS_H_POSITIONING_ERROR, 31, 5)};
        IFD_GPS_TAGS = exifTagArr3;
        ExifTag[] exifTagArr4 = {new ExifTag(TAG_INTEROPERABILITY_INDEX, 1, 2)};
        IFD_INTEROPERABILITY_TAGS = exifTagArr4;
        ExifTag[] exifTagArr5 = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, KeyType.QUERY_FRK_DEVICE_ERASE_COMMAND, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, KeyType.QUERY_BRIGHT_SCREEN_PARAM, 2), new ExifTag(TAG_MAKE, KeyType.QUERY_HEART_RATE_PARAM, 2), new ExifTag(TAG_MODEL, KeyType.SEND_MENSTRUAL_START_DAY, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_THUMBNAIL_ORIENTATION, KeyType.QUERY_MENSTRUAL_DATA_FOR_MONTH, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, KeyType.SET_AI_WATCH_STATUS, 3), new ExifTag(TAG_ROWS_PER_STRIP, KeyType.SET_AI_WATCH_ENABLE, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, KeyType.QUERY_AI_WATCH_ENABLE, 3, 4), new ExifTag(TAG_X_RESOLUTION, KeyType.SET_AI_AGENT_TYPE, 5), new ExifTag(TAG_Y_RESOLUTION, KeyType.SYNC_AI_AGENT_MEMO_TO_DEVICE, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, KeyType.SET_LARGE_MODE_TYPE, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, TypedValues.AttributesType.TYPE_PIVOT_TARGET, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_THUMBNAIL_TAGS = exifTagArr5;
        TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, 3);
        ExifTag[] exifTagArr6 = {new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 4), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 4)};
        ORF_MAKER_NOTE_TAGS = exifTagArr6;
        ExifTag[] exifTagArr7 = {new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, 4)};
        ORF_CAMERA_SETTINGS_TAGS = exifTagArr7;
        ExifTag[] exifTagArr8 = {new ExifTag(TAG_ORF_ASPECT_FRAME, 4371, 3)};
        ORF_IMAGE_PROCESSING_TAGS = exifTagArr8;
        ExifTag[] exifTagArr9 = {new ExifTag(TAG_COLOR_SPACE, 55, 3)};
        PEF_TAGS = exifTagArr9;
        ExifTag[][] exifTagArr10 = {exifTagArr, exifTagArr2, exifTagArr3, exifTagArr4, exifTagArr5, exifTagArr, exifTagArr6, exifTagArr7, exifTagArr8, exifTagArr9};
        f4082e = exifTagArr10;
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 1), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 1)};
        sExifTagMapsForReading = new HashMap[exifTagArr10.length];
        sExifTagMapsForWriting = new HashMap[exifTagArr10.length];
        sTagSetForCompatibility = new HashSet<>(Arrays.asList(TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
        sExifPointerTagMap = new HashMap<>();
        Charset charsetForName = Charset.forName("US-ASCII");
        f4083f = charsetForName;
        f4084g = "Exif\u0000\u0000".getBytes(charsetForName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(charsetForName);
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", locale);
        sFormatterPrimary = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss, locale);
        sFormatterSecondary = simpleDateFormat2;
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
        int i2 = 0;
        while (true) {
            ExifTag[][] exifTagArr11 = f4082e;
            if (i2 >= exifTagArr11.length) {
                HashMap<Integer, Integer> map = sExifPointerTagMap;
                ExifTag[] exifTagArr12 = EXIF_POINTER_TAGS;
                map.put(Integer.valueOf(exifTagArr12[0].number), 5);
                map.put(Integer.valueOf(exifTagArr12[1].number), 1);
                map.put(Integer.valueOf(exifTagArr12[2].number), 2);
                map.put(Integer.valueOf(exifTagArr12[3].number), 3);
                map.put(Integer.valueOf(exifTagArr12[4].number), 7);
                map.put(Integer.valueOf(exifTagArr12[5].number), 8);
                NON_ZERO_TIME_PATTERN = Pattern.compile(".*[1-9].*");
                GPS_TIMESTAMP_PATTERN = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
                DATETIME_PRIMARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
                DATETIME_SECONDARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
                return;
            }
            sExifTagMapsForReading[i2] = new HashMap<>();
            sExifTagMapsForWriting[i2] = new HashMap<>();
            for (ExifTag exifTag : exifTagArr11[i2]) {
                sExifTagMapsForReading[i2].put(Integer.valueOf(exifTag.number), exifTag);
                sExifTagMapsForWriting[i2].put(exifTag.name, exifTag);
            }
            i2++;
        }
    }

    public ExifInterface(@NonNull File file) throws Throwable {
        ExifTag[][] exifTagArr = f4082e;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (file == null) {
            throw new NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    private void addDefaultValuesForCompatibility() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    private String convertDecimalDegree(double d2) {
        long j2 = (long) d2;
        double d3 = d2 - j2;
        long j3 = (long) (d3 * 60.0d);
        return j2 + "/1," + j3 + "/1," + Math.round((d3 - (j3 / 60.0d)) * 3600.0d * 1.0E7d) + "/10000000";
    }

    private static double convertRationalLatLonToDouble(String str, String str2) {
        try {
            String[] strArrSplit = str.split(",", -1);
            String[] strArrSplit2 = strArrSplit[0].split("/", -1);
            double d2 = Double.parseDouble(strArrSplit2[0].trim()) / Double.parseDouble(strArrSplit2[1].trim());
            String[] strArrSplit3 = strArrSplit[1].split("/", -1);
            double d3 = Double.parseDouble(strArrSplit3[0].trim()) / Double.parseDouble(strArrSplit3[1].trim());
            String[] strArrSplit4 = strArrSplit[2].split("/", -1);
            double d4 = d2 + (d3 / 60.0d) + ((Double.parseDouble(strArrSplit4[0].trim()) / Double.parseDouble(strArrSplit4[1].trim())) / 3600.0d);
            if (!str2.equals(LATITUDE_SOUTH) && !str2.equals(LONGITUDE_WEST)) {
                if (!str2.equals("N") && !str2.equals(LONGITUDE_EAST)) {
                    throw new IllegalArgumentException();
                }
                return d4;
            }
            return -d4;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            throw new IllegalArgumentException();
        }
    }

    private void copyChunksUpToGivenChunkType(ByteOrderedDataInputStream byteOrderedDataInputStream, ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr, byte[] bArr2) throws IOException {
        while (true) {
            byte[] bArr3 = new byte[4];
            byteOrderedDataInputStream.readFully(bArr3);
            copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr3);
            if (Arrays.equals(bArr3, bArr)) {
                return;
            }
            if (bArr2 != null && Arrays.equals(bArr3, bArr2)) {
                return;
            }
        }
    }

    private void copyWebPChunk(ByteOrderedDataInputStream byteOrderedDataInputStream, ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr) throws IOException {
        int i2 = byteOrderedDataInputStream.readInt();
        byteOrderedDataOutputStream.write(bArr);
        byteOrderedDataOutputStream.writeInt(i2);
        if (i2 % 2 == 1) {
            i2++;
        }
        ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream, i2);
    }

    @Nullable
    private ExifAttribute getExifAttribute(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if (TAG_ISO_SPEED_RATINGS.equals(str)) {
            if (DEBUG) {
                Log.d(TAG, "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str = TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        for (int i2 = 0; i2 < f4082e.length; i2++) {
            ExifAttribute exifAttribute = this.mAttributes[i2].get(str);
            if (exifAttribute != null) {
                return exifAttribute;
            }
        }
        return null;
    }

    private void getHeifAttributes(final SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        String strExtractMetadata;
        String strExtractMetadata2;
        String strExtractMetadata3;
        if (Build.VERSION.SDK_INT < 28) {
            throw new UnsupportedOperationException("Reading EXIF from HEIF files is supported from SDK 28 and above");
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                ExifInterfaceUtils.Api23Impl.a(mediaMetadataRetriever, new MediaDataSource() { // from class: androidx.exifinterface.media.ExifInterface.1

                    /* renamed from: a, reason: collision with root package name */
                    long f4085a;

                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                    }

                    @Override // android.media.MediaDataSource
                    public long getSize() throws IOException {
                        return -1L;
                    }

                    @Override // android.media.MediaDataSource
                    public int readAt(long j2, byte[] bArr, int i2, int i3) throws IOException {
                        if (i3 == 0) {
                            return 0;
                        }
                        if (j2 < 0) {
                            return -1;
                        }
                        try {
                            long j3 = this.f4085a;
                            if (j3 != j2) {
                                if (j3 >= 0 && j2 >= j3 + seekableByteOrderedDataInputStream.available()) {
                                    return -1;
                                }
                                seekableByteOrderedDataInputStream.seek(j2);
                                this.f4085a = j2;
                            }
                            if (i3 > seekableByteOrderedDataInputStream.available()) {
                                i3 = seekableByteOrderedDataInputStream.available();
                            }
                            int i4 = seekableByteOrderedDataInputStream.read(bArr, i2, i3);
                            if (i4 >= 0) {
                                this.f4085a += i4;
                                return i4;
                            }
                        } catch (IOException unused) {
                        }
                        this.f4085a = -1L;
                        return -1;
                    }
                });
                String strExtractMetadata4 = mediaMetadataRetriever.extractMetadata(33);
                String strExtractMetadata5 = mediaMetadataRetriever.extractMetadata(34);
                String strExtractMetadata6 = mediaMetadataRetriever.extractMetadata(26);
                String strExtractMetadata7 = mediaMetadataRetriever.extractMetadata(17);
                if ("yes".equals(strExtractMetadata6)) {
                    strExtractMetadata = mediaMetadataRetriever.extractMetadata(29);
                    strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(30);
                    strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(31);
                } else if ("yes".equals(strExtractMetadata7)) {
                    strExtractMetadata = mediaMetadataRetriever.extractMetadata(18);
                    strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(19);
                    strExtractMetadata3 = mediaMetadataRetriever.extractMetadata(24);
                } else {
                    strExtractMetadata = null;
                    strExtractMetadata2 = null;
                    strExtractMetadata3 = null;
                }
                if (strExtractMetadata != null) {
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createUShort(Integer.parseInt(strExtractMetadata), this.mExifByteOrder));
                }
                if (strExtractMetadata2 != null) {
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createUShort(Integer.parseInt(strExtractMetadata2), this.mExifByteOrder));
                }
                if (strExtractMetadata3 != null) {
                    int i2 = Integer.parseInt(strExtractMetadata3);
                    this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(i2 != 90 ? i2 != 180 ? i2 != 270 ? 1 : 8 : 3 : 6, this.mExifByteOrder));
                }
                if (strExtractMetadata4 != null && strExtractMetadata5 != null) {
                    int i3 = Integer.parseInt(strExtractMetadata4);
                    int i4 = Integer.parseInt(strExtractMetadata5);
                    if (i4 <= 6) {
                        throw new IOException("Invalid exif length");
                    }
                    seekableByteOrderedDataInputStream.seek(i3);
                    byte[] bArr = new byte[6];
                    seekableByteOrderedDataInputStream.readFully(bArr);
                    int i5 = i3 + 6;
                    int i6 = i4 - 6;
                    if (!Arrays.equals(bArr, f4084g)) {
                        throw new IOException("Invalid identifier");
                    }
                    byte[] bArr2 = new byte[i6];
                    seekableByteOrderedDataInputStream.readFully(bArr2);
                    this.mOffsetToExifData = i5;
                    readExifSegment(bArr2, 0);
                }
                if (DEBUG) {
                    Log.d(TAG, "Heif meta: " + strExtractMetadata + "x" + strExtractMetadata2 + ", rotation " + strExtractMetadata3);
                }
                mediaMetadataRetriever.release();
            } catch (RuntimeException unused) {
                throw new UnsupportedOperationException("Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported.");
            }
        } catch (Throwable th) {
            mediaMetadataRetriever.release();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0186, code lost:
    
        r22.setByteOrder(r21.mExifByteOrder);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x018b, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0170 A[LOOP:0: B:10:0x0037->B:59:0x0170, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0177 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getJpegAttributes(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r22, int r23, int r24) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 522
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getJpegAttributes(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    private int getMimeType(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (isJpegFormat(bArr)) {
            return 4;
        }
        if (isRafFormat(bArr)) {
            return 9;
        }
        if (isHeifFormat(bArr)) {
            return 12;
        }
        if (isOrfFormat(bArr)) {
            return 7;
        }
        if (isRw2Format(bArr)) {
            return 10;
        }
        if (isPngFormat(bArr)) {
            return 13;
        }
        return isWebpFormat(bArr) ? 14 : 0;
    }

    private void getOrfAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        int i2;
        int i3;
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (exifAttribute != null) {
            SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
            seekableByteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
            byte[] bArr = ORF_MAKER_NOTE_HEADER_1;
            byte[] bArr2 = new byte[bArr.length];
            seekableByteOrderedDataInputStream2.readFully(bArr2);
            seekableByteOrderedDataInputStream2.seek(0L);
            byte[] bArr3 = ORF_MAKER_NOTE_HEADER_2;
            byte[] bArr4 = new byte[bArr3.length];
            seekableByteOrderedDataInputStream2.readFully(bArr4);
            if (Arrays.equals(bArr2, bArr)) {
                seekableByteOrderedDataInputStream2.seek(8L);
            } else if (Arrays.equals(bArr4, bArr3)) {
                seekableByteOrderedDataInputStream2.seek(12L);
            }
            readImageFileDirectory(seekableByteOrderedDataInputStream2, 6);
            ExifAttribute exifAttribute2 = this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute exifAttribute3 = this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (exifAttribute2 != null && exifAttribute3 != null) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, exifAttribute2);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, exifAttribute3);
            }
            ExifAttribute exifAttribute4 = this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (exifAttribute4 != null) {
                int[] iArr = (int[]) exifAttribute4.a(this.mExifByteOrder);
                if (iArr == null || iArr.length != 4) {
                    Log.w(TAG, "Invalid aspect frame values. frame=" + Arrays.toString(iArr));
                    return;
                }
                int i4 = iArr[2];
                int i5 = iArr[0];
                if (i4 <= i5 || (i2 = iArr[3]) <= (i3 = iArr[1])) {
                    return;
                }
                int i6 = (i4 - i5) + 1;
                int i7 = (i2 - i3) + 1;
                if (i6 < i7) {
                    int i8 = i6 + i7;
                    i7 = i8 - i7;
                    i6 = i8 - i7;
                }
                ExifAttribute exifAttributeCreateUShort = ExifAttribute.createUShort(i6, this.mExifByteOrder);
                ExifAttribute exifAttributeCreateUShort2 = ExifAttribute.createUShort(i7, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttributeCreateUShort);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttributeCreateUShort2);
            }
        }
    }

    private void getPngAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d(TAG, "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        byte[] bArr = PNG_SIGNATURE;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length;
        while (true) {
            try {
                int i2 = byteOrderedDataInputStream.readInt();
                byte[] bArr2 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr2);
                int i3 = length + 8;
                if (i3 == 16 && !Arrays.equals(bArr2, PNG_CHUNK_TYPE_IHDR)) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_IEND)) {
                    return;
                }
                if (Arrays.equals(bArr2, PNG_CHUNK_TYPE_EXIF)) {
                    byte[] bArr3 = new byte[i2];
                    byteOrderedDataInputStream.readFully(bArr3);
                    int i4 = byteOrderedDataInputStream.readInt();
                    CRC32 crc32 = new CRC32();
                    crc32.update(bArr2);
                    crc32.update(bArr3);
                    if (((int) crc32.getValue()) == i4) {
                        this.mOffsetToExifData = i3;
                        readExifSegment(bArr3, 0);
                        validateImages();
                        setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                        return;
                    }
                    throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + i4 + ", calculated CRC value: " + crc32.getValue());
                }
                int i5 = i2 + 4;
                byteOrderedDataInputStream.skipFully(i5);
                length = i3 + i5;
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        boolean z2 = DEBUG;
        if (z2) {
            Log.d(TAG, "getRafAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.skipFully(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[4];
        byteOrderedDataInputStream.readFully(bArr);
        byteOrderedDataInputStream.readFully(bArr2);
        byteOrderedDataInputStream.readFully(bArr3);
        int i2 = ByteBuffer.wrap(bArr).getInt();
        int i3 = ByteBuffer.wrap(bArr2).getInt();
        int i4 = ByteBuffer.wrap(bArr3).getInt();
        byte[] bArr4 = new byte[i3];
        byteOrderedDataInputStream.skipFully(i2 - byteOrderedDataInputStream.position());
        byteOrderedDataInputStream.readFully(bArr4);
        getJpegAttributes(new ByteOrderedDataInputStream(bArr4), i2, 5);
        byteOrderedDataInputStream.skipFully(i4 - byteOrderedDataInputStream.position());
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        int i5 = byteOrderedDataInputStream.readInt();
        if (z2) {
            Log.d(TAG, "numberOfDirectoryEntry: " + i5);
        }
        for (int i6 = 0; i6 < i5; i6++) {
            int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int unsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (unsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short s2 = byteOrderedDataInputStream.readShort();
                short s3 = byteOrderedDataInputStream.readShort();
                ExifAttribute exifAttributeCreateUShort = ExifAttribute.createUShort(s2, this.mExifByteOrder);
                ExifAttribute exifAttributeCreateUShort2 = ExifAttribute.createUShort(s3, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttributeCreateUShort);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttributeCreateUShort2);
                if (DEBUG) {
                    Log.d(TAG, "Updated to length: " + ((int) s2) + ", width: " + ((int) s3));
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipFully(unsignedShort2);
        }
    }

    private void getRawAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        ExifAttribute exifAttribute;
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 0);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 5);
        updateImageSizeValues(seekableByteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType != 8 || (exifAttribute = this.mAttributes[1].get(TAG_MAKER_NOTE)) == null) {
            return;
        }
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream2 = new SeekableByteOrderedDataInputStream(exifAttribute.bytes);
        seekableByteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
        seekableByteOrderedDataInputStream2.skipFully(6);
        readImageFileDirectory(seekableByteOrderedDataInputStream2, 9);
        ExifAttribute exifAttribute2 = this.mAttributes[9].get(TAG_COLOR_SPACE);
        if (exifAttribute2 != null) {
            this.mAttributes[1].put(TAG_COLOR_SPACE, exifAttribute2);
        }
    }

    private void getRw2Attributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d(TAG, "getRw2Attributes starting with: " + seekableByteOrderedDataInputStream);
        }
        getRawAttributes(seekableByteOrderedDataInputStream);
        ExifAttribute exifAttribute = this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW);
        if (exifAttribute != null) {
            getJpegAttributes(new ByteOrderedDataInputStream(exifAttribute.bytes), (int) exifAttribute.bytesOffset, 5);
        }
        ExifAttribute exifAttribute2 = this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifAttribute3 = this.mAttributes[1].get(TAG_PHOTOGRAPHIC_SENSITIVITY);
        if (exifAttribute2 == null || exifAttribute3 != null) {
            return;
        }
        this.mAttributes[1].put(TAG_PHOTOGRAPHIC_SENSITIVITY, exifAttribute2);
    }

    private void getStandaloneAttributes(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream) throws IOException {
        byte[] bArr = f4084g;
        seekableByteOrderedDataInputStream.skipFully(bArr.length);
        byte[] bArr2 = new byte[seekableByteOrderedDataInputStream.available()];
        seekableByteOrderedDataInputStream.readFully(bArr2);
        this.mOffsetToExifData = bArr.length;
        readExifSegment(bArr2, 0);
    }

    private void getWebpAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        if (DEBUG) {
            Log.d(TAG, "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        byteOrderedDataInputStream.skipFully(WEBP_SIGNATURE_1.length);
        int i2 = byteOrderedDataInputStream.readInt() + 8;
        byte[] bArr = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr.length);
        int length = bArr.length + 8;
        while (true) {
            try {
                byte[] bArr2 = new byte[4];
                byteOrderedDataInputStream.readFully(bArr2);
                int i3 = byteOrderedDataInputStream.readInt();
                int i4 = length + 8;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr2)) {
                    byte[] bArr3 = new byte[i3];
                    byteOrderedDataInputStream.readFully(bArr3);
                    this.mOffsetToExifData = i4;
                    readExifSegment(bArr3, 0);
                    setThumbnailData(new ByteOrderedDataInputStream(bArr3));
                    return;
                }
                if (i3 % 2 == 1) {
                    i3++;
                }
                length = i4 + i3;
                if (length == i2) {
                    return;
                }
                if (length > i2) {
                    throw new IOException("Encountered WebP file with invalid chunk size");
                }
                byteOrderedDataInputStream.skipFully(i3);
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private static Pair<Integer, Integer> guessDataFormat(String str) throws NumberFormatException {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",", -1);
            Pair<Integer, Integer> pairGuessDataFormat = guessDataFormat(strArrSplit[0]);
            if (((Integer) pairGuessDataFormat.first).intValue() == 2) {
                return pairGuessDataFormat;
            }
            for (int i2 = 1; i2 < strArrSplit.length; i2++) {
                Pair<Integer, Integer> pairGuessDataFormat2 = guessDataFormat(strArrSplit[i2]);
                int iIntValue = (((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.first) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.first)) ? ((Integer) pairGuessDataFormat.first).intValue() : -1;
                int iIntValue2 = (((Integer) pairGuessDataFormat.second).intValue() == -1 || !(((Integer) pairGuessDataFormat2.first).equals(pairGuessDataFormat.second) || ((Integer) pairGuessDataFormat2.second).equals(pairGuessDataFormat.second))) ? -1 : ((Integer) pairGuessDataFormat.second).intValue();
                if (iIntValue == -1 && iIntValue2 == -1) {
                    return new Pair<>(2, -1);
                }
                if (iIntValue == -1) {
                    pairGuessDataFormat = new Pair<>(Integer.valueOf(iIntValue2), -1);
                } else if (iIntValue2 == -1) {
                    pairGuessDataFormat = new Pair<>(Integer.valueOf(iIntValue), -1);
                }
            }
            return pairGuessDataFormat;
        }
        if (!str.contains("/")) {
            try {
                try {
                    long j2 = Long.parseLong(str);
                    return (j2 < 0 || j2 > 65535) ? j2 < 0 ? new Pair<>(9, -1) : new Pair<>(4, -1) : new Pair<>(3, 4);
                } catch (NumberFormatException unused) {
                    Double.parseDouble(str);
                    return new Pair<>(12, -1);
                }
            } catch (NumberFormatException unused2) {
                return new Pair<>(2, -1);
            }
        }
        String[] strArrSplit2 = str.split("/", -1);
        if (strArrSplit2.length == 2) {
            try {
                long j3 = (long) Double.parseDouble(strArrSplit2[0]);
                long j4 = (long) Double.parseDouble(strArrSplit2[1]);
                if (j3 >= 0 && j4 >= 0) {
                    if (j3 <= 2147483647L && j4 <= 2147483647L) {
                        return new Pair<>(10, 5);
                    }
                    return new Pair<>(5, -1);
                }
                return new Pair<>(10, -1);
            } catch (NumberFormatException unused3) {
            }
        }
        return new Pair<>(2, -1);
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap map) throws Throwable {
        ExifAttribute exifAttribute = (ExifAttribute) map.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        if (this.mMimeType == 7) {
            intValue += this.mOrfMakerNoteOffset;
        }
        if (intValue > 0 && intValue2 > 0) {
            this.mHasThumbnail = true;
            if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                byte[] bArr = new byte[intValue2];
                byteOrderedDataInputStream.skipFully(intValue);
                byteOrderedDataInputStream.readFully(bArr);
                this.mThumbnailBytes = bArr;
            }
            this.mThumbnailOffset = intValue;
            this.mThumbnailLength = intValue2;
        }
        if (DEBUG) {
            Log.d(TAG, "Setting thumbnail attributes with offset: " + intValue + ", length: " + intValue2);
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap map) throws IOException {
        byte[] bArr;
        ExifAttribute exifAttribute = (ExifAttribute) map.get(TAG_STRIP_OFFSETS);
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get(TAG_STRIP_BYTE_COUNTS);
        if (exifAttribute == null || exifAttribute2 == null) {
            return;
        }
        long[] jArrC = ExifInterfaceUtils.c(exifAttribute.a(this.mExifByteOrder));
        long[] jArrC2 = ExifInterfaceUtils.c(exifAttribute2.a(this.mExifByteOrder));
        if (jArrC == null || jArrC.length == 0) {
            Log.w(TAG, "stripOffsets should not be null or have zero length.");
            return;
        }
        if (jArrC2 == null || jArrC2.length == 0) {
            Log.w(TAG, "stripByteCounts should not be null or have zero length.");
            return;
        }
        if (jArrC.length != jArrC2.length) {
            Log.w(TAG, "stripOffsets and stripByteCounts should have same length.");
            return;
        }
        long j2 = 0;
        for (long j3 : jArrC2) {
            j2 += j3;
        }
        int i2 = (int) j2;
        byte[] bArr2 = new byte[i2];
        this.mAreThumbnailStripsConsecutive = true;
        this.mHasThumbnailStrips = true;
        this.mHasThumbnail = true;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < jArrC.length) {
            int i6 = (int) jArrC[i3];
            int i7 = (int) jArrC2[i3];
            if (i3 < jArrC.length - 1) {
                bArr = bArr2;
                if (i6 + i7 != jArrC[i3 + 1]) {
                    this.mAreThumbnailStripsConsecutive = false;
                }
            } else {
                bArr = bArr2;
            }
            int i8 = i6 - i4;
            if (i8 < 0) {
                Log.d(TAG, "Invalid strip offset value");
                return;
            }
            try {
                byteOrderedDataInputStream.skipFully(i8);
                int i9 = i4 + i8;
                byte[] bArr3 = new byte[i7];
                try {
                    byteOrderedDataInputStream.readFully(bArr3);
                    i4 = i9 + i7;
                    byte[] bArr4 = bArr;
                    System.arraycopy(bArr3, 0, bArr4, i5, i7);
                    i5 += i7;
                    i3++;
                    bArr2 = bArr4;
                } catch (EOFException unused) {
                    Log.d(TAG, "Failed to read " + i7 + " bytes.");
                    return;
                }
            } catch (EOFException unused2) {
                Log.d(TAG, "Failed to skip " + i8 + " bytes.");
                return;
            }
        }
        this.mThumbnailBytes = bArr2;
        if (this.mAreThumbnailStripsConsecutive) {
            this.mThumbnailOffset = (int) jArrC[0];
            this.mThumbnailLength = i2;
        }
    }

    private void initForFilename(String str) throws Throwable {
        if (str == null) {
            throw new NullPointerException("filename cannot be null");
        }
        FileInputStream fileInputStream = null;
        this.mAssetInputStream = null;
        this.mFilename = str;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                if (isSeekableFD(fileInputStream2.getFD())) {
                    this.mSeekableFileDescriptor = fileInputStream2.getFD();
                } else {
                    this.mSeekableFileDescriptor = null;
                }
                loadAttributes(fileInputStream2);
                ExifInterfaceUtils.b(fileInputStream2);
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                ExifInterfaceUtils.b(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static boolean isExifDataOnly(BufferedInputStream bufferedInputStream) throws IOException {
        byte[] bArr = f4084g;
        bufferedInputStream.mark(bArr.length);
        byte[] bArr2 = new byte[bArr.length];
        bufferedInputStream.read(bArr2);
        bufferedInputStream.reset();
        int i2 = 0;
        while (true) {
            byte[] bArr3 = f4084g;
            if (i2 >= bArr3.length) {
                return true;
            }
            if (bArr2[i2] != bArr3[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean isHeifFormat(byte[] bArr) throws Throwable {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        long length;
        byte[] bArr2;
        long j2;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            try {
                byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
                try {
                    length = byteOrderedDataInputStream.readInt();
                    bArr2 = new byte[4];
                    byteOrderedDataInputStream.readFully(bArr2);
                } catch (Exception e2) {
                    e = e2;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (DEBUG) {
                        Log.d(TAG, "Exception parsing HEIF file type box.", e);
                    }
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
        if (!Arrays.equals(bArr2, HEIF_TYPE_FTYP)) {
            byteOrderedDataInputStream.close();
            return false;
        }
        if (length == 1) {
            length = byteOrderedDataInputStream.readLong();
            j2 = 16;
            if (length < 16) {
                byteOrderedDataInputStream.close();
                return false;
            }
        } else {
            j2 = 8;
        }
        if (length > bArr.length) {
            length = bArr.length;
        }
        long j3 = length - j2;
        if (j3 < 8) {
            byteOrderedDataInputStream.close();
            return false;
        }
        byte[] bArr3 = new byte[4];
        boolean z2 = false;
        boolean z3 = false;
        for (long j4 = 0; j4 < j3 / 4; j4++) {
            try {
                byteOrderedDataInputStream.readFully(bArr3);
                if (j4 != 1) {
                    if (Arrays.equals(bArr3, HEIF_BRAND_MIF1)) {
                        z2 = true;
                    } else if (Arrays.equals(bArr3, HEIF_BRAND_HEIC)) {
                        z3 = true;
                    }
                    if (z2 && z3) {
                        byteOrderedDataInputStream.close();
                        return true;
                    }
                }
            } catch (EOFException unused) {
                byteOrderedDataInputStream.close();
                return false;
            }
        }
        byteOrderedDataInputStream.close();
        return false;
    }

    private static boolean isJpegFormat(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = f4078a;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean isOrfFormat(byte[] bArr) throws Throwable {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            ByteOrder byteOrder = readByteOrder(byteOrderedDataInputStream);
            this.mExifByteOrder = byteOrder;
            byteOrderedDataInputStream.setByteOrder(byteOrder);
            short s2 = byteOrderedDataInputStream.readShort();
            boolean z2 = s2 == 20306 || s2 == 21330;
            byteOrderedDataInputStream.close();
            return z2;
        } catch (Exception unused2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private boolean isPngFormat(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = PNG_SIGNATURE;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean isRafFormat(byte[] bArr) throws IOException {
        byte[] bytes = RAF_SIGNATURE.getBytes(Charset.defaultCharset());
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if (bArr[i2] != bytes[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRw2Format(byte[] bArr) throws Throwable {
        ByteOrderedDataInputStream byteOrderedDataInputStream;
        ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            ByteOrder byteOrder = readByteOrder(byteOrderedDataInputStream);
            this.mExifByteOrder = byteOrder;
            byteOrderedDataInputStream.setByteOrder(byteOrder);
            boolean z2 = byteOrderedDataInputStream.readShort() == 85;
            byteOrderedDataInputStream.close();
            return z2;
        } catch (Exception unused2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private static boolean isSeekableFD(FileDescriptor fileDescriptor) {
        try {
            ExifInterfaceUtils.Api21Impl.c(fileDescriptor, 0L, OsConstants.SEEK_CUR);
            return true;
        } catch (Exception unused) {
            if (!DEBUG) {
                return false;
            }
            Log.d(TAG, "The file descriptor for the given input is not seekable");
            return false;
        }
    }

    private boolean isSupportedDataType(HashMap map) throws IOException {
        ExifAttribute exifAttribute;
        int intValue;
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get(TAG_BITS_PER_SAMPLE);
        if (exifAttribute2 != null) {
            int[] iArr = (int[]) exifAttribute2.a(this.mExifByteOrder);
            int[] iArr2 = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr2, iArr)) {
                return true;
            }
            if (this.mMimeType == 3 && (exifAttribute = (ExifAttribute) map.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, iArr2)))) {
                return true;
            }
        }
        if (!DEBUG) {
            return false;
        }
        Log.d(TAG, "Unsupported data type value");
        return false;
    }

    private static boolean isSupportedFormatForSavingAttributes(int i2) {
        return i2 == 4 || i2 == 13 || i2 == 14;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static boolean isSupportedMimeType(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("mimeType shouldn't be null");
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        char c2 = 65535;
        switch (lowerCase.hashCode()) {
            case -1875291391:
                if (lowerCase.equals("image/x-fuji-raf")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1635437028:
                if (lowerCase.equals("image/x-samsung-srw")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1594371159:
                if (lowerCase.equals("image/x-sony-arw")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1487464693:
                if (lowerCase.equals(MimeTypes.IMAGE_HEIC)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1487464690:
                if (lowerCase.equals(MimeTypes.IMAGE_HEIF)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1487394660:
                if (lowerCase.equals("image/jpeg")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1487018032:
                if (lowerCase.equals(MimeTypes.IMAGE_WEBP)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1423313290:
                if (lowerCase.equals("image/x-adobe-dng")) {
                    c2 = 7;
                    break;
                }
                break;
            case -985160897:
                if (lowerCase.equals("image/x-panasonic-rw2")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -879258763:
                if (lowerCase.equals("image/png")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -332763809:
                if (lowerCase.equals("image/x-pentax-pef")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 1378106698:
                if (lowerCase.equals("image/x-olympus-orf")) {
                    c2 = 11;
                    break;
                }
                break;
            case 2099152104:
                if (lowerCase.equals("image/x-nikon-nef")) {
                    c2 = '\f';
                    break;
                }
                break;
            case 2099152524:
                if (lowerCase.equals("image/x-nikon-nrw")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case 2111234748:
                if (lowerCase.equals("image/x-canon-cr2")) {
                    c2 = 14;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
                return true;
            default:
                return false;
        }
    }

    private boolean isThumbnail(HashMap map) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) map.get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute) map.get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            return false;
        }
        return exifAttribute.getIntValue(this.mExifByteOrder) <= 512 && exifAttribute2.getIntValue(this.mExifByteOrder) <= 512;
    }

    private boolean isWebpFormat(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = WEBP_SIGNATURE_1;
            if (i2 >= bArr2.length) {
                int i3 = 0;
                while (true) {
                    byte[] bArr3 = WEBP_SIGNATURE_2;
                    if (i3 >= bArr3.length) {
                        return true;
                    }
                    if (bArr[WEBP_SIGNATURE_1.length + i3 + 4] != bArr3[i3]) {
                        return false;
                    }
                    i3++;
                }
            } else {
                if (bArr[i2] != bArr2[i2]) {
                    return false;
                }
                i2++;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x009f A[Catch: all -> 0x0015, TRY_LEAVE, TryCatch #0 {all -> 0x0015, blocks: (B:4:0x0004, B:6:0x0009, B:13:0x001e, B:15:0x0022, B:16:0x0030, B:18:0x0038, B:20:0x0041, B:31:0x0061, B:21:0x0045, B:23:0x004b, B:26:0x0052, B:29:0x005a, B:30:0x005e, B:32:0x006b, B:34:0x0075, B:37:0x007d, B:40:0x0085, B:43:0x008d, B:47:0x009b, B:49:0x009f), top: B:60:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadAttributes(@androidx.annotation.NonNull java.io.InputStream r5) {
        /*
            r4 = this;
            if (r5 == 0) goto Lb8
            r0 = 0
            r1 = r0
        L4:
            androidx.exifinterface.media.ExifInterface$ExifTag[][] r2 = androidx.exifinterface.media.ExifInterface.f4082e     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            int r2 = r2.length     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            if (r1 >= r2) goto L1e
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r2 = r4.mAttributes     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            java.util.HashMap r3 = new java.util.HashMap     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r3.<init>()     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r2[r1] = r3     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            int r1 = r1 + 1
            goto L4
        L15:
            r5 = move-exception
            goto Lad
        L18:
            r5 = move-exception
            goto L9b
        L1b:
            r5 = move-exception
            goto L9b
        L1e:
            boolean r1 = r4.mIsExifDataOnly     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            if (r1 != 0) goto L30
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r2 = 5000(0x1388, float:7.006E-42)
            r1.<init>(r5, r2)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            int r5 = r4.getMimeType(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r4.mMimeType = r5     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r5 = r1
        L30:
            int r1 = r4.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            boolean r1 = shouldSupportSeek(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            if (r1 == 0) goto L6b
            androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream r0 = new androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            boolean r5 = r4.mIsExifDataOnly     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            if (r5 == 0) goto L45
            r4.getStandaloneAttributes(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L61
        L45:
            int r5 = r4.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r1 = 12
            if (r5 != r1) goto L4f
            r4.getHeifAttributes(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L61
        L4f:
            r1 = 7
            if (r5 != r1) goto L56
            r4.getOrfAttributes(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L61
        L56:
            r1 = 10
            if (r5 != r1) goto L5e
            r4.getRw2Attributes(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L61
        L5e:
            r4.getRawAttributes(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
        L61:
            int r5 = r4.mOffsetToExifData     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            long r1 = (long) r5     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r0.seek(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r4.setThumbnailData(r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L90
        L6b:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r1 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            int r5 = r4.mMimeType     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            r2 = 4
            if (r5 != r2) goto L79
            r4.getJpegAttributes(r1, r0, r0)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L90
        L79:
            r0 = 13
            if (r5 != r0) goto L81
            r4.getPngAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L90
        L81:
            r0 = 9
            if (r5 != r0) goto L89
            r4.getRafAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
            goto L90
        L89:
            r0 = 14
            if (r5 != r0) goto L90
            r4.getWebpAttributes(r1)     // Catch: java.lang.Throwable -> L15 java.lang.UnsupportedOperationException -> L18 java.io.IOException -> L1b
        L90:
            r4.addDefaultValuesForCompatibility()
            boolean r5 = androidx.exifinterface.media.ExifInterface.DEBUG
            if (r5 == 0) goto Lac
        L97:
            r4.printAttributes()
            goto Lac
        L9b:
            boolean r0 = androidx.exifinterface.media.ExifInterface.DEBUG     // Catch: java.lang.Throwable -> L15
            if (r0 == 0) goto La6
            java.lang.String r1 = "ExifInterface"
            java.lang.String r2 = "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface."
            android.util.Log.w(r1, r2, r5)     // Catch: java.lang.Throwable -> L15
        La6:
            r4.addDefaultValuesForCompatibility()
            if (r0 == 0) goto Lac
            goto L97
        Lac:
            return
        Lad:
            r4.addDefaultValuesForCompatibility()
            boolean r0 = androidx.exifinterface.media.ExifInterface.DEBUG
            if (r0 == 0) goto Lb7
            r4.printAttributes()
        Lb7:
            throw r5
        Lb8:
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.String r0 = "inputstream shouldn't be null"
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.loadAttributes(java.io.InputStream):void");
    }

    private static Long parseDateTime(@Nullable String str, @Nullable String str2, @Nullable String str3) throws NumberFormatException {
        if (str != null && NON_ZERO_TIME_PATTERN.matcher(str).matches()) {
            ParsePosition parsePosition = new ParsePosition(0);
            try {
                Date date = sFormatterPrimary.parse(str, parsePosition);
                if (date == null && (date = sFormatterSecondary.parse(str, parsePosition)) == null) {
                    return null;
                }
                long time = date.getTime();
                if (str3 != null) {
                    int i2 = 1;
                    String strSubstring = str3.substring(0, 1);
                    int i3 = Integer.parseInt(str3.substring(1, 3));
                    int i4 = Integer.parseInt(str3.substring(4, 6));
                    if ((MqttTopic.SINGLE_LEVEL_WILDCARD.equals(strSubstring) || com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(strSubstring)) && ":".equals(str3.substring(3, 4)) && i3 <= 14) {
                        int i5 = ((i3 * 60) + i4) * 60000;
                        if (!com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(strSubstring)) {
                            i2 = -1;
                        }
                        time += i5 * i2;
                    }
                }
                if (str2 != null) {
                    time += ExifInterfaceUtils.f(str2);
                }
                return Long.valueOf(time);
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        ByteOrder byteOrder = readByteOrder(byteOrderedDataInputStream);
        this.mExifByteOrder = byteOrder;
        byteOrderedDataInputStream.setByteOrder(byteOrder);
        int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        int i2 = this.mMimeType;
        if (i2 != 7 && i2 != 10 && unsignedShort != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(unsignedShort));
        }
        int i3 = byteOrderedDataInputStream.readInt();
        if (i3 < 8) {
            throw new IOException("Invalid first Ifd offset: " + i3);
        }
        int i4 = i3 - 8;
        if (i4 > 0) {
            byteOrderedDataInputStream.skipFully(i4);
        }
    }

    private void printAttributes() {
        for (int i2 = 0; i2 < this.mAttributes.length; i2++) {
            Log.d(TAG, "The size of tag group[" + i2 + "]: " + this.mAttributes[i2].size());
            for (Map.Entry<String, ExifAttribute> entry : this.mAttributes[i2].entrySet()) {
                ExifAttribute value = entry.getValue();
                Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + value.toString() + ", tagValue: '" + value.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        short s2 = byteOrderedDataInputStream.readShort();
        if (s2 == 18761) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (s2 == 19789) {
            if (DEBUG) {
                Log.d(TAG, "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        }
        throw new IOException("Invalid byte order: " + Integer.toHexString(s2));
    }

    private void readExifSegment(byte[] bArr, int i2) throws IOException {
        SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream = new SeekableByteOrderedDataInputStream(bArr);
        parseTiffHeaders(seekableByteOrderedDataInputStream);
        readImageFileDirectory(seekableByteOrderedDataInputStream, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0243  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readImageFileDirectory(androidx.exifinterface.media.ExifInterface.SeekableByteOrderedDataInputStream r29, int r30) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 985
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.readImageFileDirectory(androidx.exifinterface.media.ExifInterface$SeekableByteOrderedDataInputStream, int):void");
    }

    private void removeAttribute(String str) {
        for (int i2 = 0; i2 < f4082e.length; i2++) {
            this.mAttributes[i2].remove(str);
        }
    }

    private void replaceInvalidTags(int i2, String str, String str2) {
        if (this.mAttributes[i2].isEmpty() || this.mAttributes[i2].get(str) == null) {
            return;
        }
        HashMap<String, ExifAttribute> map = this.mAttributes[i2];
        map.put(str2, map.get(str));
        this.mAttributes[i2].remove(str);
    }

    private void retrieveJpegImageSize(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i2) throws Throwable {
        ExifAttribute exifAttribute = this.mAttributes[i2].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = this.mAttributes[i2].get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            ExifAttribute exifAttribute3 = this.mAttributes[i2].get(TAG_JPEG_INTERCHANGE_FORMAT);
            ExifAttribute exifAttribute4 = this.mAttributes[i2].get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            if (exifAttribute3 == null || exifAttribute4 == null) {
                return;
            }
            int intValue = exifAttribute3.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute3.getIntValue(this.mExifByteOrder);
            seekableByteOrderedDataInputStream.seek(intValue);
            byte[] bArr = new byte[intValue2];
            seekableByteOrderedDataInputStream.readFully(bArr);
            getJpegAttributes(new ByteOrderedDataInputStream(bArr), intValue, i2);
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (byteOrderedDataInputStream.readByte() != -1) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-1);
        if (byteOrderedDataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-40);
        ExifAttribute exifAttributeRemove = (getAttribute(TAG_XMP) == null || !this.mXmpIsFromSeparateMarker) ? null : this.mAttributes[0].remove(TAG_XMP);
        byteOrderedDataOutputStream.writeByte(-1);
        byteOrderedDataOutputStream.writeByte(-31);
        writeExifSegment(byteOrderedDataOutputStream);
        if (exifAttributeRemove != null) {
            this.mAttributes[0].put(TAG_XMP, exifAttributeRemove);
        }
        byte[] bArr = new byte[4096];
        while (byteOrderedDataInputStream.readByte() == -1) {
            byte b2 = byteOrderedDataInputStream.readByte();
            if (b2 == -39 || b2 == -38) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b2);
                ExifInterfaceUtils.d(byteOrderedDataInputStream, byteOrderedDataOutputStream);
                return;
            }
            if (b2 != -31) {
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b2);
                int unsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                byteOrderedDataOutputStream.writeUnsignedShort(unsignedShort);
                int i2 = unsignedShort - 2;
                if (i2 < 0) {
                    throw new IOException("Invalid length");
                }
                while (i2 > 0) {
                    int i3 = byteOrderedDataInputStream.read(bArr, 0, Math.min(i2, 4096));
                    if (i3 >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, i3);
                        i2 -= i3;
                    }
                }
            } else {
                int unsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
                int i4 = unsignedShort2 - 2;
                if (i4 < 0) {
                    throw new IOException("Invalid length");
                }
                byte[] bArr2 = new byte[6];
                if (i4 >= 6) {
                    byteOrderedDataInputStream.readFully(bArr2);
                    if (Arrays.equals(bArr2, f4084g)) {
                        byteOrderedDataInputStream.skipFully(unsignedShort2 - 8);
                    }
                }
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(b2);
                byteOrderedDataOutputStream.writeUnsignedShort(unsignedShort2);
                if (i4 >= 6) {
                    i4 = unsignedShort2 - 8;
                    byteOrderedDataOutputStream.write(bArr2);
                }
                while (i4 > 0) {
                    int i5 = byteOrderedDataInputStream.read(bArr, 0, Math.min(i4, 4096));
                    if (i5 >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, i5);
                        i4 -= i5;
                    }
                }
            }
        }
        throw new IOException("Invalid marker");
    }

    private void savePngAttributes(InputStream inputStream, OutputStream outputStream) throws Throwable {
        if (DEBUG) {
            Log.d(TAG, "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream, PNG_SIGNATURE.length);
        if (this.mOffsetToExifData == 0) {
            int i2 = byteOrderedDataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(i2);
            ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream, i2 + 8);
        } else {
            ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream, (r2 - r7.length) - 8);
            byteOrderedDataInputStream.skipFully(byteOrderedDataInputStream.readInt() + 8);
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, byteOrder);
                writeExifSegment(byteOrderedDataOutputStream2);
                byte[] byteArray = ((ByteArrayOutputStream) byteOrderedDataOutputStream2.f4090a).toByteArray();
                byteOrderedDataOutputStream.write(byteArray);
                CRC32 crc32 = new CRC32();
                crc32.update(byteArray, 4, byteArray.length - 4);
                byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
                ExifInterfaceUtils.b(byteArrayOutputStream2);
                ExifInterfaceUtils.d(byteOrderedDataInputStream, byteOrderedDataOutputStream);
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = byteArrayOutputStream2;
                ExifInterfaceUtils.b(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) throws Throwable {
        int i2;
        int i3;
        int i4;
        boolean z2;
        if (DEBUG) {
            Log.d(TAG, "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(inputStream, byteOrder);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        byte[] bArr = WEBP_SIGNATURE_1;
        ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr.length);
        byte[] bArr2 = WEBP_SIGNATURE_2;
        byteOrderedDataInputStream.skipFully(bArr2.length + 4);
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new ByteOrderedDataOutputStream(byteArrayOutputStream2, byteOrder);
                    int i5 = this.mOffsetToExifData;
                    if (i5 != 0) {
                        ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream2, (i5 - ((bArr.length + 4) + bArr2.length)) - 8);
                        byteOrderedDataInputStream.skipFully(4);
                        int i6 = byteOrderedDataInputStream.readInt();
                        if (i6 % 2 != 0) {
                            i6++;
                        }
                        byteOrderedDataInputStream.skipFully(i6);
                        writeExifSegment(byteOrderedDataOutputStream2);
                    } else {
                        byte[] bArr3 = new byte[4];
                        byteOrderedDataInputStream.readFully(bArr3);
                        byte[] bArr4 = WEBP_CHUNK_TYPE_VP8X;
                        boolean z3 = true;
                        if (Arrays.equals(bArr3, bArr4)) {
                            int i7 = byteOrderedDataInputStream.readInt();
                            byte[] bArr5 = new byte[i7 % 2 == 1 ? i7 + 1 : i7];
                            byteOrderedDataInputStream.readFully(bArr5);
                            byte b2 = (byte) (8 | bArr5[0]);
                            bArr5[0] = b2;
                            boolean z4 = ((b2 >> 1) & 1) == 1;
                            byteOrderedDataOutputStream2.write(bArr4);
                            byteOrderedDataOutputStream2.writeInt(i7);
                            byteOrderedDataOutputStream2.write(bArr5);
                            if (z4) {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_ANIM, null);
                                while (true) {
                                    byte[] bArr6 = new byte[4];
                                    try {
                                        byteOrderedDataInputStream.readFully(bArr6);
                                        z2 = !Arrays.equals(bArr6, WEBP_CHUNK_TYPE_ANMF);
                                    } catch (EOFException unused) {
                                        z2 = true;
                                    }
                                    if (z2) {
                                        break;
                                    } else {
                                        copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream2, bArr6);
                                    }
                                }
                                writeExifSegment(byteOrderedDataOutputStream2);
                            } else {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        } else {
                            byte[] bArr7 = WEBP_CHUNK_TYPE_VP8;
                            if (Arrays.equals(bArr3, bArr7) || Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                int i8 = byteOrderedDataInputStream.readInt();
                                int i9 = i8 % 2 == 1 ? i8 + 1 : i8;
                                byte[] bArr8 = new byte[3];
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataInputStream.readFully(bArr8);
                                    byte[] bArr9 = new byte[3];
                                    byteOrderedDataInputStream.readFully(bArr9);
                                    if (!Arrays.equals(WEBP_VP8_SIGNATURE, bArr9)) {
                                        throw new IOException("Error checking VP8 signature");
                                    }
                                    int i10 = byteOrderedDataInputStream.readInt();
                                    i4 = (i10 << 2) >> 18;
                                    i9 -= 10;
                                    i2 = (i10 << 18) >> 18;
                                    i3 = i10;
                                    z3 = false;
                                } else if (!Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                    i2 = 0;
                                    z3 = false;
                                    i3 = 0;
                                    i4 = 0;
                                } else {
                                    if (byteOrderedDataInputStream.readByte() != 47) {
                                        throw new IOException("Error checking VP8L signature");
                                    }
                                    i3 = byteOrderedDataInputStream.readInt();
                                    i2 = (i3 & 16383) + 1;
                                    i4 = ((i3 & 268419072) >>> 14) + 1;
                                    if ((i3 & 268435456) == 0) {
                                        z3 = false;
                                    }
                                    i9 -= 5;
                                }
                                byteOrderedDataOutputStream2.write(bArr4);
                                byteOrderedDataOutputStream2.writeInt(10);
                                byte[] bArr10 = new byte[10];
                                if (z3) {
                                    bArr10[0] = (byte) (bArr10[0] | 16);
                                }
                                bArr10[0] = (byte) (bArr10[0] | 8);
                                int i11 = i2 - 1;
                                int i12 = i4 - 1;
                                bArr10[4] = (byte) i11;
                                bArr10[5] = (byte) (i11 >> 8);
                                bArr10[6] = (byte) (i11 >> 16);
                                bArr10[7] = (byte) i12;
                                bArr10[8] = (byte) (i12 >> 8);
                                bArr10[9] = (byte) (i12 >> 16);
                                byteOrderedDataOutputStream2.write(bArr10);
                                byteOrderedDataOutputStream2.write(bArr3);
                                byteOrderedDataOutputStream2.writeInt(i8);
                                if (Arrays.equals(bArr3, bArr7)) {
                                    byteOrderedDataOutputStream2.write(bArr8);
                                    byteOrderedDataOutputStream2.write(WEBP_VP8_SIGNATURE);
                                    byteOrderedDataOutputStream2.writeInt(i3);
                                } else if (Arrays.equals(bArr3, WEBP_CHUNK_TYPE_VP8L)) {
                                    byteOrderedDataOutputStream2.write(47);
                                    byteOrderedDataOutputStream2.writeInt(i3);
                                }
                                ExifInterfaceUtils.e(byteOrderedDataInputStream, byteOrderedDataOutputStream2, i9);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        }
                    }
                    ExifInterfaceUtils.d(byteOrderedDataInputStream, byteOrderedDataOutputStream2);
                    int size = byteArrayOutputStream2.size();
                    byte[] bArr11 = WEBP_SIGNATURE_2;
                    byteOrderedDataOutputStream.writeInt(size + bArr11.length);
                    byteOrderedDataOutputStream.write(bArr11);
                    byteArrayOutputStream2.writeTo(byteOrderedDataOutputStream);
                    ExifInterfaceUtils.b(byteArrayOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    throw new IOException("Failed to save WebP file", e);
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    ExifInterfaceUtils.b(byteArrayOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) throws Throwable {
        HashMap<String, ExifAttribute> map = this.mAttributes[4];
        ExifAttribute exifAttribute = map.get(TAG_COMPRESSION);
        if (exifAttribute == null) {
            this.mThumbnailCompression = 6;
            handleThumbnailFromJfif(byteOrderedDataInputStream, map);
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        this.mThumbnailCompression = intValue;
        if (intValue != 1) {
            if (intValue == 6) {
                handleThumbnailFromJfif(byteOrderedDataInputStream, map);
                return;
            } else if (intValue != 7) {
                return;
            }
        }
        if (isSupportedDataType(map)) {
            handleThumbnailFromStrips(byteOrderedDataInputStream, map);
        }
    }

    private static boolean shouldSupportSeek(int i2) {
        return (i2 == 4 || i2 == 9 || i2 == 13 || i2 == 14) ? false : true;
    }

    private void swapBasedOnImageSize(int i2, int i3) throws Throwable {
        if (this.mAttributes[i2].isEmpty() || this.mAttributes[i3].isEmpty()) {
            if (DEBUG) {
                Log.d(TAG, "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        ExifAttribute exifAttribute = this.mAttributes[i2].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = this.mAttributes[i2].get(TAG_IMAGE_WIDTH);
        ExifAttribute exifAttribute3 = this.mAttributes[i3].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute4 = this.mAttributes[i3].get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            if (DEBUG) {
                Log.d(TAG, "First image does not contain valid size information");
                return;
            }
            return;
        }
        if (exifAttribute3 == null || exifAttribute4 == null) {
            if (DEBUG) {
                Log.d(TAG, "Second image does not contain valid size information");
                return;
            }
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
        int intValue4 = exifAttribute4.getIntValue(this.mExifByteOrder);
        if (intValue >= intValue3 || intValue2 >= intValue4) {
            return;
        }
        HashMap<String, ExifAttribute>[] mapArr = this.mAttributes;
        HashMap<String, ExifAttribute> map = mapArr[i2];
        mapArr[i2] = mapArr[i3];
        mapArr[i3] = map;
    }

    private void updateImageSizeValues(SeekableByteOrderedDataInputStream seekableByteOrderedDataInputStream, int i2) throws Throwable {
        ExifAttribute exifAttributeCreateUShort;
        ExifAttribute exifAttributeCreateUShort2;
        ExifAttribute exifAttribute = this.mAttributes[i2].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute exifAttribute2 = this.mAttributes[i2].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute exifAttribute3 = this.mAttributes[i2].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute exifAttribute4 = this.mAttributes[i2].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute exifAttribute5 = this.mAttributes[i2].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (exifAttribute == null) {
            if (exifAttribute2 == null || exifAttribute3 == null || exifAttribute4 == null || exifAttribute5 == null) {
                retrieveJpegImageSize(seekableByteOrderedDataInputStream, i2);
                return;
            }
            int intValue = exifAttribute2.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute4.getIntValue(this.mExifByteOrder);
            int intValue3 = exifAttribute5.getIntValue(this.mExifByteOrder);
            int intValue4 = exifAttribute3.getIntValue(this.mExifByteOrder);
            if (intValue2 <= intValue || intValue3 <= intValue4) {
                return;
            }
            ExifAttribute exifAttributeCreateUShort3 = ExifAttribute.createUShort(intValue2 - intValue, this.mExifByteOrder);
            ExifAttribute exifAttributeCreateUShort4 = ExifAttribute.createUShort(intValue3 - intValue4, this.mExifByteOrder);
            this.mAttributes[i2].put(TAG_IMAGE_LENGTH, exifAttributeCreateUShort3);
            this.mAttributes[i2].put(TAG_IMAGE_WIDTH, exifAttributeCreateUShort4);
            return;
        }
        if (exifAttribute.format == 5) {
            Rational[] rationalArr = (Rational[]) exifAttribute.a(this.mExifByteOrder);
            if (rationalArr == null || rationalArr.length != 2) {
                Log.w(TAG, "Invalid crop size values. cropSize=" + Arrays.toString(rationalArr));
                return;
            }
            exifAttributeCreateUShort = ExifAttribute.createURational(rationalArr[0], this.mExifByteOrder);
            exifAttributeCreateUShort2 = ExifAttribute.createURational(rationalArr[1], this.mExifByteOrder);
        } else {
            int[] iArr = (int[]) exifAttribute.a(this.mExifByteOrder);
            if (iArr == null || iArr.length != 2) {
                Log.w(TAG, "Invalid crop size values. cropSize=" + Arrays.toString(iArr));
                return;
            }
            exifAttributeCreateUShort = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
            exifAttributeCreateUShort2 = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
        }
        this.mAttributes[i2].put(TAG_IMAGE_WIDTH, exifAttributeCreateUShort);
        this.mAttributes[i2].put(TAG_IMAGE_LENGTH, exifAttributeCreateUShort2);
    }

    private void validateImages() throws Throwable {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute exifAttribute2 = this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (exifAttribute != null && exifAttribute2 != null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap<String, ExifAttribute>[] mapArr = this.mAttributes;
            mapArr[4] = mapArr[5];
            mapArr[5] = new HashMap<>();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(4, TAG_ORIENTATION, TAG_THUMBNAIL_ORIENTATION);
        replaceInvalidTags(4, TAG_IMAGE_LENGTH, TAG_THUMBNAIL_IMAGE_LENGTH);
        replaceInvalidTags(4, TAG_IMAGE_WIDTH, TAG_THUMBNAIL_IMAGE_WIDTH);
    }

    private int writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream) throws IOException {
        ExifTag[][] exifTagArr = f4082e;
        int[] iArr = new int[exifTagArr.length];
        int[] iArr2 = new int[exifTagArr.length];
        for (ExifTag exifTag : EXIF_POINTER_TAGS) {
            removeAttribute(exifTag.name);
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                removeAttribute(TAG_STRIP_OFFSETS);
                removeAttribute(TAG_STRIP_BYTE_COUNTS);
            } else {
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT);
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            }
        }
        for (int i2 = 0; i2 < f4082e.length; i2++) {
            for (Object obj : this.mAttributes[i2].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[i2].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, ExifAttribute.createUShort(0, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_STRIP_BYTE_COUNTS, ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, ExifAttribute.createULong(0L, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        for (int i3 = 0; i3 < f4082e.length; i3++) {
            Iterator<Map.Entry<String, ExifAttribute>> it = this.mAttributes[i3].entrySet().iterator();
            int i4 = 0;
            while (it.hasNext()) {
                int size = it.next().getValue().size();
                if (size > 4) {
                    i4 += size;
                }
            }
            iArr2[i3] = iArr2[i3] + i4;
        }
        int size2 = 8;
        for (int i5 = 0; i5 < f4082e.length; i5++) {
            if (!this.mAttributes[i5].isEmpty()) {
                iArr[i5] = size2;
                size2 += (this.mAttributes[i5].size() * 12) + 6 + iArr2[i5];
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, ExifAttribute.createUShort(size2, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, ExifAttribute.createULong(size2, this.mExifByteOrder));
            }
            this.mThumbnailOffset = size2;
            size2 += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            size2 += 8;
        }
        if (DEBUG) {
            for (int i6 = 0; i6 < f4082e.length; i6++) {
                Log.d(TAG, String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i6), Integer.valueOf(iArr[i6]), Integer.valueOf(this.mAttributes[i6].size()), Integer.valueOf(iArr2[i6]), Integer.valueOf(size2)));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(iArr[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(iArr[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(iArr[3], this.mExifByteOrder));
        }
        int i7 = this.mMimeType;
        if (i7 == 4) {
            if (size2 > 65535) {
                throw new IllegalStateException("Size of exif data (" + size2 + " bytes) exceeds the max size of a JPEG APP1 segment (65536 bytes)");
            }
            byteOrderedDataOutputStream.writeUnsignedShort(size2);
            byteOrderedDataOutputStream.write(f4084g);
        } else if (i7 == 13) {
            byteOrderedDataOutputStream.writeInt(size2);
            byteOrderedDataOutputStream.write(PNG_CHUNK_TYPE_EXIF);
        } else if (i7 == 14) {
            byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
            byteOrderedDataOutputStream.writeInt(size2);
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
        byteOrderedDataOutputStream.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (int i8 = 0; i8 < f4082e.length; i8++) {
            if (!this.mAttributes[i8].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[i8].size());
                int size3 = iArr[i8] + 2 + (this.mAttributes[i8].size() * 12) + 4;
                for (Map.Entry<String, ExifAttribute> entry2 : this.mAttributes[i8].entrySet()) {
                    int i9 = sExifTagMapsForWriting[i8].get(entry2.getKey()).number;
                    ExifAttribute value = entry2.getValue();
                    int size4 = value.size();
                    byteOrderedDataOutputStream.writeUnsignedShort(i9);
                    byteOrderedDataOutputStream.writeUnsignedShort(value.format);
                    byteOrderedDataOutputStream.writeInt(value.numberOfComponents);
                    if (size4 > 4) {
                        byteOrderedDataOutputStream.writeUnsignedInt(size3);
                        size3 += size4;
                    } else {
                        byteOrderedDataOutputStream.write(value.bytes);
                        if (size4 < 4) {
                            while (size4 < 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                size4++;
                            }
                        }
                    }
                }
                if (i8 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0L);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt(iArr[4]);
                }
                Iterator<Map.Entry<String, ExifAttribute>> it2 = this.mAttributes[i8].entrySet().iterator();
                while (it2.hasNext()) {
                    byte[] bArr = it2.next().getValue().bytes;
                    if (bArr.length > 4) {
                        byteOrderedDataOutputStream.write(bArr, 0, bArr.length);
                    }
                }
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && size2 % 2 == 1) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        return size2;
    }

    public void flipHorizontally() throws NumberFormatException {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 2;
                break;
            case 2:
                break;
            case 3:
                i2 = 4;
                break;
            case 4:
                i2 = 3;
                break;
            case 5:
                i2 = 6;
                break;
            case 6:
                i2 = 5;
                break;
            case 7:
                i2 = 8;
                break;
            case 8:
                i2 = 7;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public void flipVertically() throws NumberFormatException {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 4;
                break;
            case 2:
                i2 = 3;
                break;
            case 3:
                i2 = 2;
                break;
            case 4:
                break;
            case 5:
                i2 = 8;
                break;
            case 6:
                i2 = 7;
                break;
            case 7:
                i2 = 6;
                break;
            case 8:
                i2 = 5;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public double getAltitude(double d2) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d2;
        }
        return attributeDouble * (attributeInt != 1 ? 1 : -1);
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            if (str.equals(TAG_GPS_TIMESTAMP)) {
                int i2 = exifAttribute.format;
                if (i2 != 5 && i2 != 10) {
                    Log.w(TAG, "GPS Timestamp format is not rational. format=" + exifAttribute.format);
                    return null;
                }
                Rational[] rationalArr = (Rational[]) exifAttribute.a(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 3) {
                    Log.w(TAG, "Invalid GPS Timestamp array. array=" + Arrays.toString(rationalArr));
                    return null;
                }
                Rational rational = rationalArr[0];
                Integer numValueOf = Integer.valueOf((int) (rational.numerator / rational.denominator));
                Rational rational2 = rationalArr[1];
                Integer numValueOf2 = Integer.valueOf((int) (rational2.numerator / rational2.denominator));
                Rational rational3 = rationalArr[2];
                return String.format("%02d:%02d:%02d", numValueOf, numValueOf2, Integer.valueOf((int) (rational3.numerator / rational3.denominator)));
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    @Nullable
    public byte[] getAttributeBytes(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return exifAttribute.bytes;
        }
        return null;
    }

    public double getAttributeDouble(@NonNull String str, double d2) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return d2;
        }
        try {
            return exifAttribute.getDoubleValue(this.mExifByteOrder);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    public int getAttributeInt(@NonNull String str, int i2) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return i2;
        }
        try {
            return exifAttribute.getIntValue(this.mExifByteOrder);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    @Nullable
    public long[] getAttributeRange(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return new long[]{exifAttribute.bytesOffset, exifAttribute.bytes.length};
        }
        return null;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute(TAG_SUBSEC_TIME_DIGITIZED), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute(TAG_SUBSEC_TIME_ORIGINAL), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    @Nullable
    @SuppressLint({"AutoBoxing"})
    public Long getGpsDateTime() {
        String attribute = getAttribute(TAG_GPS_DATESTAMP);
        String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute != null && attribute2 != null) {
            Pattern pattern = NON_ZERO_TIME_PATTERN;
            if (pattern.matcher(attribute).matches() || pattern.matcher(attribute2).matches()) {
                String str = attribute + ' ' + attribute2;
                ParsePosition parsePosition = new ParsePosition(0);
                try {
                    Date date = sFormatterPrimary.parse(str, parsePosition);
                    if (date == null && (date = sFormatterSecondary.parse(str, parsePosition)) == null) {
                        return null;
                    }
                    return Long.valueOf(date.getTime());
                } catch (IllegalArgumentException unused) {
                }
            }
        }
        return null;
    }

    @Deprecated
    public boolean getLatLong(float[] fArr) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        fArr[0] = (float) latLong[0];
        fArr[1] = (float) latLong[1];
        return true;
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 8:
                return KeyType.QUERY_BRIGHT_SCREEN_PARAM;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    @Nullable
    public byte[] getThumbnail() {
        int i2 = this.mThumbnailCompression;
        if (i2 == 6 || i2 == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    @Nullable
    public Bitmap getThumbnailBitmap() throws Throwable {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        int i2 = this.mThumbnailCompression;
        if (i2 == 6 || i2 == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (i2 == 1) {
            int length = this.mThumbnailBytes.length / 3;
            int[] iArr = new int[length];
            for (int i3 = 0; i3 < length; i3++) {
                byte[] bArr = this.mThumbnailBytes;
                int i4 = i3 * 3;
                iArr[i3] = (bArr[i4] << 16) + (bArr[i4 + 1] << 8) + bArr[i4 + 2];
            }
            ExifAttribute exifAttribute = this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_LENGTH);
            ExifAttribute exifAttribute2 = this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_WIDTH);
            if (exifAttribute != null && exifAttribute2 != null) {
                return Bitmap.createBitmap(iArr, exifAttribute2.getIntValue(this.mExifByteOrder), exifAttribute.getIntValue(this.mExifByteOrder), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0095  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getThumbnailBytes() throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.String r0 = "ExifInterface"
            boolean r1 = r8.mHasThumbnail
            r2 = 0
            if (r1 != 0) goto L8
            return r2
        L8:
            byte[] r1 = r8.mThumbnailBytes
            if (r1 == 0) goto Ld
            return r1
        Ld:
            android.content.res.AssetManager$AssetInputStream r1 = r8.mAssetInputStream     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            if (r1 == 0) goto L2f
            boolean r3 = r1.markSupported()     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L21
            if (r3 == 0) goto L26
            r1.reset()     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L21
        L1a:
            r3 = r2
            goto L58
        L1c:
            r0 = move-exception
            r3 = r2
        L1e:
            r2 = r1
            goto L90
        L21:
            r3 = move-exception
            r4 = r3
            r3 = r2
            goto L82
        L26:
            java.lang.String r3 = "Cannot read thumbnail from inputstream without mark/reset support"
            android.util.Log.d(r0, r3)     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L21
            androidx.exifinterface.media.ExifInterfaceUtils.b(r1)
            return r2
        L2f:
            java.lang.String r1 = r8.mFilename     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            if (r1 == 0) goto L43
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            java.lang.String r3 = r8.mFilename     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            goto L1a
        L3b:
            r0 = move-exception
            r3 = r2
            goto L90
        L3e:
            r3 = move-exception
            r1 = r2
            r4 = r3
            r3 = r1
            goto L82
        L43:
            java.io.FileDescriptor r1 = r8.mSeekableFileDescriptor     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            java.io.FileDescriptor r1 = androidx.exifinterface.media.ExifInterfaceUtils.Api21Impl.b(r1)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            int r3 = android.system.OsConstants.SEEK_SET     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r4 = 0
            androidx.exifinterface.media.ExifInterfaceUtils.Api21Impl.c(r1, r4, r3)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7e
            r7 = r3
            r3 = r1
            r1 = r7
        L58:
            androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r4 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r5 = r8.mThumbnailOffset     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r6 = r8.mOffsetToExifData     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r5 = r5 + r6
            r4.skipFully(r5)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            int r5 = r8.mThumbnailLength     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r4.readFully(r5)     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            r8.mThumbnailBytes = r5     // Catch: java.lang.Throwable -> L77 java.lang.Exception -> L79
            androidx.exifinterface.media.ExifInterfaceUtils.b(r1)
            if (r3 == 0) goto L76
            androidx.exifinterface.media.ExifInterfaceUtils.a(r3)
        L76:
            return r5
        L77:
            r0 = move-exception
            goto L1e
        L79:
            r4 = move-exception
            goto L82
        L7b:
            r0 = move-exception
            r3 = r1
            goto L90
        L7e:
            r3 = move-exception
            r4 = r3
            r3 = r1
            r1 = r2
        L82:
            java.lang.String r5 = "Encountered exception while getting thumbnail"
            android.util.Log.d(r0, r5, r4)     // Catch: java.lang.Throwable -> L77
            androidx.exifinterface.media.ExifInterfaceUtils.b(r1)
            if (r3 == 0) goto L8f
            androidx.exifinterface.media.ExifInterfaceUtils.a(r3)
        L8f:
            return r2
        L90:
            androidx.exifinterface.media.ExifInterfaceUtils.b(r2)
            if (r3 == 0) goto L98
            androidx.exifinterface.media.ExifInterfaceUtils.a(r3)
        L98:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getThumbnailBytes():byte[]");
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        if (!this.mHasThumbnail) {
            return null;
        }
        if (!this.mHasThumbnailStrips || this.mAreThumbnailStripsConsecutive) {
            return new long[]{this.mThumbnailOffset + this.mOffsetToExifData, this.mThumbnailLength};
        }
        return null;
    }

    public boolean hasAttribute(@NonNull String str) {
        return getExifAttribute(str) != null;
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean isFlipped() {
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        return attributeInt == 2 || attributeInt == 7 || attributeInt == 4 || attributeInt == 5;
    }

    public boolean isThumbnailCompressed() {
        if (!this.mHasThumbnail) {
            return false;
        }
        int i2 = this.mThumbnailCompression;
        return i2 == 6 || i2 == 7;
    }

    public void resetOrientation() throws NumberFormatException {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int i2) throws NumberFormatException {
        if (i2 % 90 != 0) {
            throw new IllegalArgumentException("degree should be a multiple of 90");
        }
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        List<Integer> list = ROTATION_ORDER;
        if (list.contains(Integer.valueOf(attributeInt))) {
            int iIndexOf = (list.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
            iIntValue = list.get(iIndexOf + (iIndexOf < 0 ? 4 : 0)).intValue();
        } else {
            List<Integer> list2 = FLIPPED_ROTATION_ORDER;
            if (list2.contains(Integer.valueOf(attributeInt))) {
                int iIndexOf2 = (list2.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
                iIntValue = list2.get(iIndexOf2 + (iIndexOf2 < 0 ? 4 : 0)).intValue();
            }
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(iIntValue));
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x00f1 A[Catch: all -> 0x0101, Exception -> 0x0104, TryCatch #20 {Exception -> 0x0104, all -> 0x0101, blocks: (B:68:0x00ed, B:70:0x00f1, B:77:0x010e, B:76:0x0106), top: B:120:0x00ed }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0106 A[Catch: all -> 0x0101, Exception -> 0x0104, TryCatch #20 {Exception -> 0x0104, all -> 0x0101, blocks: (B:68:0x00ed, B:70:0x00f1, B:77:0x010e, B:76:0x0106), top: B:120:0x00ed }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void saveAttributes() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.saveAttributes():void");
    }

    public void setAltitude(double d2) throws NumberFormatException {
        String str = d2 >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new Rational(Math.abs(d2)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, str);
    }

    public void setAttribute(@NonNull String str, @Nullable String str2) throws NumberFormatException {
        ExifTag exifTag;
        int i2;
        int i3;
        String str3 = str;
        String strReplaceAll = str2;
        if (str3 == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if ((TAG_DATETIME.equals(str3) || TAG_DATETIME_ORIGINAL.equals(str3) || TAG_DATETIME_DIGITIZED.equals(str3)) && strReplaceAll != null) {
            boolean zFind = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
            boolean zFind2 = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(strReplaceAll).find();
            if (str2.length() != 19 || (!zFind && !zFind2)) {
                Log.w(TAG, "Invalid value for " + str3 + " : " + strReplaceAll);
                return;
            }
            if (zFind2) {
                strReplaceAll = strReplaceAll.replaceAll(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER, ":");
            }
        }
        if (TAG_ISO_SPEED_RATINGS.equals(str3)) {
            if (DEBUG) {
                Log.d(TAG, "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
            }
            str3 = TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        int i4 = 2;
        int i5 = 1;
        if (strReplaceAll != null && sTagSetForCompatibility.contains(str3)) {
            if (str3.equals(TAG_GPS_TIMESTAMP)) {
                Matcher matcher = GPS_TIMESTAMP_PATTERN.matcher(strReplaceAll);
                if (!matcher.find()) {
                    Log.w(TAG, "Invalid value for " + str3 + " : " + strReplaceAll);
                    return;
                }
                strReplaceAll = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            } else {
                try {
                    strReplaceAll = new Rational(Double.parseDouble(strReplaceAll)).toString();
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Invalid value for " + str3 + " : " + strReplaceAll);
                    return;
                }
            }
        }
        int i6 = 0;
        int i7 = 0;
        while (i7 < f4082e.length) {
            if ((i7 != 4 || this.mHasThumbnail) && (exifTag = sExifTagMapsForWriting[i7].get(str3)) != null) {
                if (strReplaceAll != null) {
                    Pair<Integer, Integer> pairGuessDataFormat = guessDataFormat(strReplaceAll);
                    int i8 = -1;
                    if (exifTag.primaryFormat == ((Integer) pairGuessDataFormat.first).intValue() || exifTag.primaryFormat == ((Integer) pairGuessDataFormat.second).intValue()) {
                        i2 = exifTag.primaryFormat;
                    } else {
                        int i9 = exifTag.secondaryFormat;
                        if (i9 == -1 || !(i9 == ((Integer) pairGuessDataFormat.first).intValue() || exifTag.secondaryFormat == ((Integer) pairGuessDataFormat.second).intValue())) {
                            int i10 = exifTag.primaryFormat;
                            if (i10 == i5 || i10 == 7 || i10 == i4) {
                                i2 = i10;
                            } else if (DEBUG) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Given tag (");
                                sb.append(str3);
                                sb.append(") value didn't match with one of expected formats: ");
                                String[] strArr = f4079b;
                                sb.append(strArr[exifTag.primaryFormat]);
                                sb.append(exifTag.secondaryFormat == -1 ? "" : ", " + strArr[exifTag.secondaryFormat]);
                                sb.append(" (guess: ");
                                sb.append(strArr[((Integer) pairGuessDataFormat.first).intValue()]);
                                sb.append(((Integer) pairGuessDataFormat.second).intValue() != -1 ? ", " + strArr[((Integer) pairGuessDataFormat.second).intValue()] : "");
                                sb.append(")");
                                Log.d(TAG, sb.toString());
                            }
                        } else {
                            i2 = exifTag.secondaryFormat;
                        }
                    }
                    switch (i2) {
                        case 1:
                            i3 = i5;
                            this.mAttributes[i7].put(str3, ExifAttribute.createByte(strReplaceAll));
                            break;
                        case 2:
                        case 7:
                            i3 = i5;
                            this.mAttributes[i7].put(str3, ExifAttribute.createString(strReplaceAll));
                            break;
                        case 3:
                            i3 = i5;
                            String[] strArrSplit = strReplaceAll.split(",", -1);
                            int[] iArr = new int[strArrSplit.length];
                            for (int i11 = 0; i11 < strArrSplit.length; i11++) {
                                iArr[i11] = Integer.parseInt(strArrSplit[i11]);
                            }
                            this.mAttributes[i7].put(str3, ExifAttribute.createUShort(iArr, this.mExifByteOrder));
                            break;
                        case 4:
                            i3 = i5;
                            String[] strArrSplit2 = strReplaceAll.split(",", -1);
                            long[] jArr = new long[strArrSplit2.length];
                            for (int i12 = 0; i12 < strArrSplit2.length; i12++) {
                                jArr[i12] = Long.parseLong(strArrSplit2[i12]);
                            }
                            this.mAttributes[i7].put(str3, ExifAttribute.createULong(jArr, this.mExifByteOrder));
                            break;
                        case 5:
                            String[] strArrSplit3 = strReplaceAll.split(",", -1);
                            Rational[] rationalArr = new Rational[strArrSplit3.length];
                            int i13 = 0;
                            while (i13 < strArrSplit3.length) {
                                String[] strArrSplit4 = strArrSplit3[i13].split("/", i8);
                                rationalArr[i13] = new Rational((long) Double.parseDouble(strArrSplit4[0]), (long) Double.parseDouble(strArrSplit4[1]));
                                i13++;
                                i8 = -1;
                            }
                            i3 = 1;
                            this.mAttributes[i7].put(str3, ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                            break;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            if (DEBUG) {
                                Log.d(TAG, "Data format isn't one of expected formats: " + i2);
                                break;
                            }
                            break;
                        case 9:
                            String[] strArrSplit5 = strReplaceAll.split(",", -1);
                            int[] iArr2 = new int[strArrSplit5.length];
                            for (int i14 = 0; i14 < strArrSplit5.length; i14++) {
                                iArr2[i14] = Integer.parseInt(strArrSplit5[i14]);
                            }
                            this.mAttributes[i7].put(str3, ExifAttribute.createSLong(iArr2, this.mExifByteOrder));
                            i3 = 1;
                            break;
                        case 10:
                            String[] strArrSplit6 = strReplaceAll.split(",", -1);
                            Rational[] rationalArr2 = new Rational[strArrSplit6.length];
                            int i15 = i6;
                            while (i15 < strArrSplit6.length) {
                                String[] strArrSplit7 = strArrSplit6[i15].split("/", -1);
                                rationalArr2[i15] = new Rational((long) Double.parseDouble(strArrSplit7[i6]), (long) Double.parseDouble(strArrSplit7[i5]));
                                i15++;
                                strArrSplit6 = strArrSplit6;
                                i6 = 0;
                                i5 = 1;
                            }
                            this.mAttributes[i7].put(str3, ExifAttribute.createSRational(rationalArr2, this.mExifByteOrder));
                            i3 = 1;
                            break;
                        case 12:
                            String[] strArrSplit8 = strReplaceAll.split(",", -1);
                            double[] dArr = new double[strArrSplit8.length];
                            for (int i16 = i6; i16 < strArrSplit8.length; i16++) {
                                dArr[i16] = Double.parseDouble(strArrSplit8[i16]);
                            }
                            this.mAttributes[i7].put(str3, ExifAttribute.createDouble(dArr, this.mExifByteOrder));
                            break;
                    }
                } else {
                    this.mAttributes[i7].remove(str3);
                }
                i3 = i5;
            } else {
                i3 = i5;
            }
            i7++;
            i5 = i3;
            i4 = 2;
            i6 = 0;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDateTime(@NonNull Long l2) throws NumberFormatException {
        if (l2 == null) {
            throw new NullPointerException("Timestamp should not be null.");
        }
        if (l2.longValue() < 0) {
            throw new IllegalArgumentException("Timestamp should a positive value.");
        }
        String string = Long.toString(l2.longValue() % 1000);
        for (int length = string.length(); length < 3; length++) {
            string = "0" + string;
        }
        setAttribute(TAG_DATETIME, sFormatterPrimary.format(new Date(l2.longValue())));
        setAttribute(TAG_SUBSEC_TIME, string);
    }

    public void setGpsInfo(Location location) throws NumberFormatException {
        if (location == null) {
            return;
        }
        setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
        setLatLong(location.getLatitude(), location.getLongitude());
        setAltitude(location.getAltitude());
        setAttribute(TAG_GPS_SPEED_REF, "K");
        setAttribute(TAG_GPS_SPEED, new Rational((location.getSpeed() * TimeUnit.HOURS.toSeconds(1L)) / 1000.0f).toString());
        String[] strArrSplit = sFormatterPrimary.format(new Date(location.getTime())).split("\\s+", -1);
        setAttribute(TAG_GPS_DATESTAMP, strArrSplit[0]);
        setAttribute(TAG_GPS_TIMESTAMP, strArrSplit[1]);
    }

    public void setLatLong(double d2, double d3) throws NumberFormatException {
        if (d2 < -90.0d || d2 > 90.0d || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Latitude value " + d2 + " is not valid.");
        }
        if (d3 < -180.0d || d3 > 180.0d || Double.isNaN(d3)) {
            throw new IllegalArgumentException("Longitude value " + d3 + " is not valid.");
        }
        setAttribute(TAG_GPS_LATITUDE_REF, d2 >= 0.0d ? "N" : LATITUDE_SOUTH);
        setAttribute(TAG_GPS_LATITUDE, convertDecimalDegree(Math.abs(d2)));
        setAttribute(TAG_GPS_LONGITUDE_REF, d3 >= 0.0d ? LONGITUDE_EAST : LONGITUDE_WEST);
        setAttribute(TAG_GPS_LONGITUDE, convertDecimalDegree(Math.abs(d3)));
    }

    private static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        public static final int LENGTH_UNSET = -1;

        /* renamed from: a, reason: collision with root package name */
        protected final DataInputStream f4088a;

        /* renamed from: b, reason: collision with root package name */
        protected int f4089b;
        private ByteOrder mByteOrder;
        private int mLength;
        private byte[] mSkipBuffer;

        ByteOrderedDataInputStream(byte[] bArr) {
            this(new ByteArrayInputStream(bArr), ByteOrder.BIG_ENDIAN);
            this.mLength = bArr.length;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.f4088a.available();
        }

        public int length() {
            return this.mLength;
        }

        @Override // java.io.InputStream
        public void mark(int i2) {
            throw new UnsupportedOperationException("Mark is currently unsupported");
        }

        public int position() {
            return this.f4089b;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            this.f4089b++;
            return this.f4088a.read();
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.f4089b++;
            return this.f4088a.readBoolean();
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            this.f4089b++;
            int i2 = this.f4088a.read();
            if (i2 >= 0) {
                return (byte) i2;
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            this.f4089b += 2;
            return this.f4088a.readChar();
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i2, int i3) throws IOException {
            this.f4089b += i3;
            this.f4088a.readFully(bArr, i2, i3);
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            this.f4089b += 4;
            int i2 = this.f4088a.read();
            int i3 = this.f4088a.read();
            int i4 = this.f4088a.read();
            int i5 = this.f4088a.read();
            if ((i2 | i3 | i4 | i5) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i5 << 24) + (i4 << 16) + (i3 << 8) + i2;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i2 << 24) + (i3 << 16) + (i4 << 8) + i5;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            this.f4089b += 8;
            int i2 = this.f4088a.read();
            int i3 = this.f4088a.read();
            int i4 = this.f4088a.read();
            int i5 = this.f4088a.read();
            int i6 = this.f4088a.read();
            int i7 = this.f4088a.read();
            int i8 = this.f4088a.read();
            int i9 = this.f4088a.read();
            if ((i2 | i3 | i4 | i5 | i6 | i7 | i8 | i9) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i9 << 56) + (i8 << 48) + (i7 << 40) + (i6 << 32) + (i5 << 24) + (i4 << 16) + (i3 << 8) + i2;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i2 << 56) + (i3 << 48) + (i4 << 40) + (i5 << 32) + (i6 << 24) + (i7 << 16) + (i8 << 8) + i9;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            this.f4089b += 2;
            int i2 = this.f4088a.read();
            int i3 = this.f4088a.read();
            if ((i2 | i3) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (short) ((i3 << 8) + i2);
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (short) ((i2 << 8) + i3);
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            this.f4089b += 2;
            return this.f4088a.readUTF();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.f4089b++;
            return this.f4088a.readUnsignedByte();
        }

        public long readUnsignedInt() throws IOException {
            return readInt() & 4294967295L;
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            this.f4089b += 2;
            int i2 = this.f4088a.read();
            int i3 = this.f4088a.read();
            if ((i2 | i3) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                return (i3 << 8) + i2;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return (i2 << 8) + i3;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.InputStream
        public void reset() {
            throw new UnsupportedOperationException("Reset is currently unsupported");
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.DataInput
        public int skipBytes(int i2) throws IOException {
            throw new UnsupportedOperationException("skipBytes is currently unsupported");
        }

        public void skipFully(int i2) throws IOException {
            int i3 = 0;
            while (i3 < i2) {
                int i4 = i2 - i3;
                int iSkip = (int) this.f4088a.skip(i4);
                if (iSkip <= 0) {
                    if (this.mSkipBuffer == null) {
                        this.mSkipBuffer = new byte[8192];
                    }
                    iSkip = this.f4088a.read(this.mSkipBuffer, 0, Math.min(8192, i4));
                    if (iSkip == -1) {
                        throw new EOFException("Reached EOF while skipping " + i2 + " bytes.");
                    }
                }
                i3 += iSkip;
            }
            this.f4089b += i3;
        }

        ByteOrderedDataInputStream(InputStream inputStream) {
            this(inputStream, ByteOrder.BIG_ENDIAN);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            int i4 = this.f4088a.read(bArr, i2, i3);
            this.f4089b += i4;
            return i4;
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.f4089b += bArr.length;
            this.f4088a.readFully(bArr);
        }

        ByteOrderedDataInputStream(InputStream inputStream, ByteOrder byteOrder) {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.f4088a = dataInputStream;
            dataInputStream.mark(0);
            this.f4089b = 0;
            this.mByteOrder = byteOrder;
            this.mLength = inputStream instanceof ByteOrderedDataInputStream ? ((ByteOrderedDataInputStream) inputStream).length() : -1;
        }
    }

    private static class SeekableByteOrderedDataInputStream extends ByteOrderedDataInputStream {
        SeekableByteOrderedDataInputStream(byte[] bArr) {
            super(bArr);
            this.f4088a.mark(Integer.MAX_VALUE);
        }

        public void seek(long j2) throws IOException {
            int i2 = this.f4089b;
            if (i2 > j2) {
                this.f4089b = 0;
                this.f4088a.reset();
            } else {
                j2 -= i2;
            }
            skipFully((int) j2);
        }

        SeekableByteOrderedDataInputStream(InputStream inputStream) {
            super(inputStream);
            if (inputStream.markSupported()) {
                this.f4088a.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }
    }

    @Nullable
    public double[] getLatLong() {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (attribute == null || attribute2 == null || attribute3 == null || attribute4 == null) {
            return null;
        }
        try {
            return new double[]{convertRationalLatLonToDouble(attribute, attribute2), convertRationalLatLonToDouble(attribute3, attribute4)};
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "Latitude/longitude values are not parsable. " + String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", attribute, attribute2, attribute3, attribute4));
            return null;
        }
    }

    static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        ExifTag(String str, int i2, int i3) {
            this.name = str;
            this.number = i2;
            this.primaryFormat = i3;
            this.secondaryFormat = -1;
        }

        boolean a(int i2) {
            int i3;
            int i4 = this.primaryFormat;
            if (i4 == 7 || i2 == 7 || i4 == i2 || (i3 = this.secondaryFormat) == i2) {
                return true;
            }
            if ((i4 == 4 || i3 == 4) && i2 == 3) {
                return true;
            }
            if ((i4 == 9 || i3 == 9) && i2 == 8) {
                return true;
            }
            return (i4 == 12 || i3 == 12) && i2 == 11;
        }

        ExifTag(String str, int i2, int i3, int i4) {
            this.name = str;
            this.number = i2;
            this.primaryFormat = i3;
            this.secondaryFormat = i4;
        }
    }

    public ExifInterface(@NonNull String str) throws Throwable {
        ExifTag[][] exifTagArr = f4082e;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (str != null) {
            initForFilename(str);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    public ExifInterface(@NonNull FileDescriptor fileDescriptor) throws Throwable {
        boolean z2;
        FileInputStream fileInputStream;
        Throwable th;
        ExifTag[][] exifTagArr = f4082e;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (fileDescriptor != null) {
            this.mAssetInputStream = null;
            this.mFilename = null;
            if (isSeekableFD(fileDescriptor)) {
                this.mSeekableFileDescriptor = fileDescriptor;
                try {
                    fileDescriptor = ExifInterfaceUtils.Api21Impl.b(fileDescriptor);
                    z2 = true;
                } catch (Exception e2) {
                    throw new IOException("Failed to duplicate file descriptor", e2);
                }
            } else {
                this.mSeekableFileDescriptor = null;
                z2 = false;
            }
            try {
                fileInputStream = new FileInputStream(fileDescriptor);
            } catch (Throwable th2) {
                fileInputStream = null;
                th = th2;
            }
            try {
                loadAttributes(fileInputStream);
                ExifInterfaceUtils.b(fileInputStream);
                if (z2) {
                    ExifInterfaceUtils.a(fileDescriptor);
                    return;
                }
                return;
            } catch (Throwable th3) {
                th = th3;
                ExifInterfaceUtils.b(fileInputStream);
                if (z2) {
                    ExifInterfaceUtils.a(fileDescriptor);
                }
                throw th;
            }
        }
        throw new NullPointerException("fileDescriptor cannot be null");
    }

    public ExifInterface(@NonNull InputStream inputStream) throws IOException {
        this(inputStream, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ExifInterface(@androidx.annotation.NonNull java.io.InputStream r4, int r5) throws java.io.IOException {
        /*
            r3 = this;
            r3.<init>()
            androidx.exifinterface.media.ExifInterface$ExifTag[][] r0 = androidx.exifinterface.media.ExifInterface.f4082e
            int r1 = r0.length
            java.util.HashMap[] r1 = new java.util.HashMap[r1]
            r3.mAttributes = r1
            java.util.HashSet r1 = new java.util.HashSet
            int r0 = r0.length
            r1.<init>(r0)
            r3.mAttributesOffsets = r1
            java.nio.ByteOrder r0 = java.nio.ByteOrder.BIG_ENDIAN
            r3.mExifByteOrder = r0
            if (r4 == 0) goto L6a
            r0 = 0
            r3.mFilename = r0
            r1 = 1
            if (r5 != r1) goto L3c
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream
            byte[] r2 = androidx.exifinterface.media.ExifInterface.f4084g
            int r2 = r2.length
            r5.<init>(r4, r2)
            boolean r4 = isExifDataOnly(r5)
            if (r4 != 0) goto L34
            java.lang.String r4 = "ExifInterface"
            java.lang.String r5 = "Given data does not follow the structure of an Exif-only data."
            android.util.Log.w(r4, r5)
            return
        L34:
            r3.mIsExifDataOnly = r1
            r3.mAssetInputStream = r0
            r3.mSeekableFileDescriptor = r0
            r4 = r5
            goto L66
        L3c:
            boolean r5 = r4 instanceof android.content.res.AssetManager.AssetInputStream
            if (r5 == 0) goto L48
            r5 = r4
            android.content.res.AssetManager$AssetInputStream r5 = (android.content.res.AssetManager.AssetInputStream) r5
            r3.mAssetInputStream = r5
            r3.mSeekableFileDescriptor = r0
            goto L66
        L48:
            boolean r5 = r4 instanceof java.io.FileInputStream
            if (r5 == 0) goto L62
            r5 = r4
            java.io.FileInputStream r5 = (java.io.FileInputStream) r5
            java.io.FileDescriptor r1 = r5.getFD()
            boolean r1 = isSeekableFD(r1)
            if (r1 == 0) goto L62
            r3.mAssetInputStream = r0
            java.io.FileDescriptor r5 = r5.getFD()
            r3.mSeekableFileDescriptor = r5
            goto L66
        L62:
            r3.mAssetInputStream = r0
            r3.mSeekableFileDescriptor = r0
        L66:
            r3.loadAttributes(r4)
            return
        L6a:
            java.lang.NullPointerException r4 = new java.lang.NullPointerException
            java.lang.String r5 = "inputStream cannot be null"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.<init>(java.io.InputStream, int):void");
    }
}
