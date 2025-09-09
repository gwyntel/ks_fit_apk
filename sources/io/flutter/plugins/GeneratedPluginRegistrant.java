package io.flutter.plugins;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import cachet.plugins.health.HealthPlugin;
import com.aboutyou.dart_packages.sign_in_with_apple.SignInWithApplePlugin;
import com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin;
import com.alternadom.wifiiot.WifiIotPlugin;
import com.amap.flutter.location.AMapFlutterLocationPlugin;
import com.baseflow.geocoding.GeocodingPlugin;
import com.baseflow.geolocator.GeolocatorPlugin;
import com.baseflow.permissionhandler.PermissionHandlerPlugin;
import com.chgocn.flutter_android_util.FlutterAndroidUtilPlugin;
import com.crazecoder.flutterbugly.FlutterBuglyPlugin;
import com.example.flutter_social_share.FlutterSocialSharePlugin;
import com.example.imagegallerysaver.ImageGallerySaverPlugin;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import com.fluttercandies.image_editor.ImageEditorPlugin;
import com.kineapps.flutterarchive.FlutterArchivePlugin;
import com.kingsmith.aliiot.AliiotPlugin;
import com.kingsmith.es11.Es11Plugin;
import com.kingsmith.ksfit.flutter_launcher.FlutterLauncherPlugin;
import com.kingsmith.miot.MiotPlugin;
import com.leeson.image_pickers.ImagePickersPlugin;
import com.lib.flutter_blue_plus.FlutterBluePlusPlugin;
import com.syncfusion.flutter.pdfviewer.SyncfusionFlutterPdfViewerPlugin;
import com.tekartik.sqflite.SqflitePlugin;
import com.umeng.message.UmengPushSdkPlugin;
import com.umeng.umeng_common_sdk.UmengCommonSdkPlugin;
import com.zaihui.installplugin.InstallPlugin;
import dev.fluttercommunity.plus.androidintent.AndroidIntentPlugin;
import dev.fluttercommunity.plus.connectivity.ConnectivityPlugin;
import dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin;
import dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin;
import dev.fluttercommunity.plus.share.SharePlusPlugin;
import dev.fluttercommunity.plus.wakelock.WakelockPlusPlugin;
import dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin;
import es.antonborri.home_widget.HomeWidgetPlugin;
import io.endigo.plugins.pdfviewflutter.PDFViewFlutterPlugin;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin;
import io.flutter.plugins.googlesignin.GoogleSignInPlugin;
import io.flutter.plugins.imagepicker.ImagePickerPlugin;
import io.flutter.plugins.pathprovider.PathProviderPlugin;
import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;
import io.flutter.plugins.urllauncher.UrlLauncherPlugin;
import io.flutter.plugins.videoplayer.VideoPlayerPlugin;
import io.flutter.plugins.webviewflutter.WebViewFlutterPlugin;
import io.github.ponnamkarthik.toast.fluttertoast.FlutterToastPlugin;
import io.github.v7lin.alipay_kit.AlipayKitPlugin;
import me.andisemler.nfc_in_flutter.NfcInFlutterPlugin;
import net.nfet.flutter.printing.PrintingPlugin;
import top.huic.perfect_volume_control.perfect_volume_control.PerfectVolumeControlPlugin;
import vn.hunghd.flutter.plugins.imagecropper.ImageCropperPlugin;
import xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin;
import xyz.bczl.flutter_scankit.FlutterScanKitPlugin;
import xyz.luan.audioplayers.AudioplayersPlugin;

@Keep
/* loaded from: classes4.dex */
public final class GeneratedPluginRegistrant {
    private static final String TAG = "GeneratedPluginRegistrant";

    public static void registerWith(@NonNull FlutterEngine flutterEngine) {
        try {
            flutterEngine.getPlugins().add(new AliiotPlugin());
        } catch (Exception e2) {
            Log.e(TAG, "Error registering plugin aliiot, com.kingsmith.aliiot.AliiotPlugin", e2);
        }
        try {
            flutterEngine.getPlugins().add(new AlipayKitPlugin());
        } catch (Exception e3) {
            Log.e(TAG, "Error registering plugin alipay_kit_android, io.github.v7lin.alipay_kit.AlipayKitPlugin", e3);
        }
        try {
            flutterEngine.getPlugins().add(new AMapFlutterLocationPlugin());
        } catch (Exception e4) {
            Log.e(TAG, "Error registering plugin amap_flutter_location, com.amap.flutter.location.AMapFlutterLocationPlugin", e4);
        }
        try {
            flutterEngine.getPlugins().add(new AndroidIntentPlugin());
        } catch (Exception e5) {
            Log.e(TAG, "Error registering plugin android_intent_plus, dev.fluttercommunity.plus.androidintent.AndroidIntentPlugin", e5);
        }
        try {
            flutterEngine.getPlugins().add(new AudioplayersPlugin());
        } catch (Exception e6) {
            Log.e(TAG, "Error registering plugin audioplayers_android, xyz.luan.audioplayers.AudioplayersPlugin", e6);
        }
        try {
            flutterEngine.getPlugins().add(new ConnectivityPlugin());
        } catch (Exception e7) {
            Log.e(TAG, "Error registering plugin connectivity_plus, dev.fluttercommunity.plus.connectivity.ConnectivityPlugin", e7);
        }
        try {
            flutterEngine.getPlugins().add(new DeviceInfoPlusPlugin());
        } catch (Exception e8) {
            Log.e(TAG, "Error registering plugin device_info_plus, dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin", e8);
        }
        try {
            flutterEngine.getPlugins().add(new Es11Plugin());
        } catch (Exception e9) {
            Log.e(TAG, "Error registering plugin es11, com.kingsmith.es11.Es11Plugin", e9);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterAliplayerPlugin());
        } catch (Exception e10) {
            Log.e(TAG, "Error registering plugin flutter_aliplayer, com.alibaba.fplayer.flutter_aliplayer.FlutterAliplayerPlugin", e10);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterAndroidUtilPlugin());
        } catch (Exception e11) {
            Log.e(TAG, "Error registering plugin flutter_android_util, com.chgocn.flutter_android_util.FlutterAndroidUtilPlugin", e11);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterArchivePlugin());
        } catch (Exception e12) {
            Log.e(TAG, "Error registering plugin flutter_archive, com.kineapps.flutterarchive.FlutterArchivePlugin", e12);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterBluePlusPlugin());
        } catch (Exception e13) {
            Log.e(TAG, "Error registering plugin flutter_blue_plus, com.lib.flutter_blue_plus.FlutterBluePlusPlugin", e13);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterBuglyPlugin());
        } catch (Exception e14) {
            Log.e(TAG, "Error registering plugin flutter_bugly, com.crazecoder.flutterbugly.FlutterBuglyPlugin", e14);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterHealthKitPlugin());
        } catch (Exception e15) {
            Log.e(TAG, "Error registering plugin flutter_health_kit, xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin", e15);
        }
        try {
            flutterEngine.getPlugins().add(new ImageCompressPlugin());
        } catch (Exception e16) {
            Log.e(TAG, "Error registering plugin flutter_image_compress, com.fluttercandies.flutter_image_compress.ImageCompressPlugin", e16);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterLauncherPlugin());
        } catch (Exception e17) {
            Log.e(TAG, "Error registering plugin flutter_launcher, com.kingsmith.ksfit.flutter_launcher.FlutterLauncherPlugin", e17);
        }
        try {
            flutterEngine.getPlugins().add(new PDFViewFlutterPlugin());
        } catch (Exception e18) {
            Log.e(TAG, "Error registering plugin flutter_pdfview, io.endigo.plugins.pdfviewflutter.PDFViewFlutterPlugin", e18);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterAndroidLifecyclePlugin());
        } catch (Exception e19) {
            Log.e(TAG, "Error registering plugin flutter_plugin_android_lifecycle, io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin", e19);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterScanKitPlugin());
        } catch (Exception e20) {
            Log.e(TAG, "Error registering plugin flutter_scankit, xyz.bczl.flutter_scankit.FlutterScanKitPlugin", e20);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterSocialSharePlugin());
        } catch (Exception e21) {
            Log.e(TAG, "Error registering plugin flutter_social_share_plugin, com.example.flutter_social_share.FlutterSocialSharePlugin", e21);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterToastPlugin());
        } catch (Exception e22) {
            Log.e(TAG, "Error registering plugin fluttertoast, io.github.ponnamkarthik.toast.fluttertoast.FlutterToastPlugin", e22);
        }
        try {
            flutterEngine.getPlugins().add(new GeocodingPlugin());
        } catch (Exception e23) {
            Log.e(TAG, "Error registering plugin geocoding_android, com.baseflow.geocoding.GeocodingPlugin", e23);
        }
        try {
            flutterEngine.getPlugins().add(new GeolocatorPlugin());
        } catch (Exception e24) {
            Log.e(TAG, "Error registering plugin geolocator_android, com.baseflow.geolocator.GeolocatorPlugin", e24);
        }
        try {
            flutterEngine.getPlugins().add(new GoogleSignInPlugin());
        } catch (Exception e25) {
            Log.e(TAG, "Error registering plugin google_sign_in_android, io.flutter.plugins.googlesignin.GoogleSignInPlugin", e25);
        }
        try {
            flutterEngine.getPlugins().add(new HealthPlugin());
        } catch (Exception e26) {
            Log.e(TAG, "Error registering plugin health, cachet.plugins.health.HealthPlugin", e26);
        }
        try {
            flutterEngine.getPlugins().add(new HomeWidgetPlugin());
        } catch (Exception e27) {
            Log.e(TAG, "Error registering plugin home_widget, es.antonborri.home_widget.HomeWidgetPlugin", e27);
        }
        try {
            flutterEngine.getPlugins().add(new ImageCropperPlugin());
        } catch (Exception e28) {
            Log.e(TAG, "Error registering plugin image_cropper, vn.hunghd.flutter.plugins.imagecropper.ImageCropperPlugin", e28);
        }
        try {
            flutterEngine.getPlugins().add(new ImageEditorPlugin());
        } catch (Exception e29) {
            Log.e(TAG, "Error registering plugin image_editor_common, com.fluttercandies.image_editor.ImageEditorPlugin", e29);
        }
        try {
            flutterEngine.getPlugins().add(new ImageGallerySaverPlugin());
        } catch (Exception e30) {
            Log.e(TAG, "Error registering plugin image_gallery_saver, com.example.imagegallerysaver.ImageGallerySaverPlugin", e30);
        }
        try {
            flutterEngine.getPlugins().add(new ImagePickerPlugin());
        } catch (Exception e31) {
            Log.e(TAG, "Error registering plugin image_picker_android, io.flutter.plugins.imagepicker.ImagePickerPlugin", e31);
        }
        try {
            flutterEngine.getPlugins().add(new ImagePickersPlugin());
        } catch (Exception e32) {
            Log.e(TAG, "Error registering plugin image_pickers, com.leeson.image_pickers.ImagePickersPlugin", e32);
        }
        try {
            flutterEngine.getPlugins().add(new InstallPlugin());
        } catch (Exception e33) {
            Log.e(TAG, "Error registering plugin install_plugin, com.zaihui.installplugin.InstallPlugin", e33);
        }
        try {
            flutterEngine.getPlugins().add(new MiotPlugin());
        } catch (Exception e34) {
            Log.e(TAG, "Error registering plugin miot, com.kingsmith.miot.MiotPlugin", e34);
        }
        try {
            flutterEngine.getPlugins().add(new NfcInFlutterPlugin());
        } catch (Exception e35) {
            Log.e(TAG, "Error registering plugin nfc_in_flutter, me.andisemler.nfc_in_flutter.NfcInFlutterPlugin", e35);
        }
        try {
            flutterEngine.getPlugins().add(new PackageInfoPlugin());
        } catch (Exception e36) {
            Log.e(TAG, "Error registering plugin package_info_plus, dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin", e36);
        }
        try {
            flutterEngine.getPlugins().add(new PathProviderPlugin());
        } catch (Exception e37) {
            Log.e(TAG, "Error registering plugin path_provider_android, io.flutter.plugins.pathprovider.PathProviderPlugin", e37);
        }
        try {
            flutterEngine.getPlugins().add(new PerfectVolumeControlPlugin());
        } catch (Exception e38) {
            Log.e(TAG, "Error registering plugin perfect_volume_control, top.huic.perfect_volume_control.perfect_volume_control.PerfectVolumeControlPlugin", e38);
        }
        try {
            flutterEngine.getPlugins().add(new PermissionHandlerPlugin());
        } catch (Exception e39) {
            Log.e(TAG, "Error registering plugin permission_handler_android, com.baseflow.permissionhandler.PermissionHandlerPlugin", e39);
        }
        try {
            flutterEngine.getPlugins().add(new PrintingPlugin());
        } catch (Exception e40) {
            Log.e(TAG, "Error registering plugin printing, net.nfet.flutter.printing.PrintingPlugin", e40);
        }
        try {
            flutterEngine.getPlugins().add(new SharePlusPlugin());
        } catch (Exception e41) {
            Log.e(TAG, "Error registering plugin share_plus, dev.fluttercommunity.plus.share.SharePlusPlugin", e41);
        }
        try {
            flutterEngine.getPlugins().add(new SharedPreferencesPlugin());
        } catch (Exception e42) {
            Log.e(TAG, "Error registering plugin shared_preferences_android, io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin", e42);
        }
        try {
            flutterEngine.getPlugins().add(new SignInWithApplePlugin());
        } catch (Exception e43) {
            Log.e(TAG, "Error registering plugin sign_in_with_apple, com.aboutyou.dart_packages.sign_in_with_apple.SignInWithApplePlugin", e43);
        }
        try {
            flutterEngine.getPlugins().add(new SqflitePlugin());
        } catch (Exception e44) {
            Log.e(TAG, "Error registering plugin sqflite, com.tekartik.sqflite.SqflitePlugin", e44);
        }
        try {
            flutterEngine.getPlugins().add(new SyncfusionFlutterPdfViewerPlugin());
        } catch (Exception e45) {
            Log.e(TAG, "Error registering plugin syncfusion_flutter_pdfviewer, com.syncfusion.flutter.pdfviewer.SyncfusionFlutterPdfViewerPlugin", e45);
        }
        try {
            flutterEngine.getPlugins().add(new UmengCommonSdkPlugin());
        } catch (Exception e46) {
            Log.e(TAG, "Error registering plugin umeng_common_sdk, com.umeng.umeng_common_sdk.UmengCommonSdkPlugin", e46);
        }
        try {
            flutterEngine.getPlugins().add(new UmengPushSdkPlugin());
        } catch (Exception e47) {
            Log.e(TAG, "Error registering plugin umeng_push_sdk, com.umeng.message.UmengPushSdkPlugin", e47);
        }
        try {
            flutterEngine.getPlugins().add(new UrlLauncherPlugin());
        } catch (Exception e48) {
            Log.e(TAG, "Error registering plugin url_launcher_android, io.flutter.plugins.urllauncher.UrlLauncherPlugin", e48);
        }
        try {
            flutterEngine.getPlugins().add(new VideoPlayerPlugin());
        } catch (Exception e49) {
            Log.e(TAG, "Error registering plugin video_player_android, io.flutter.plugins.videoplayer.VideoPlayerPlugin", e49);
        }
        try {
            flutterEngine.getPlugins().add(new WakelockPlusPlugin());
        } catch (Exception e50) {
            Log.e(TAG, "Error registering plugin wakelock_plus, dev.fluttercommunity.plus.wakelock.WakelockPlusPlugin", e50);
        }
        try {
            flutterEngine.getPlugins().add(new WebViewFlutterPlugin());
        } catch (Exception e51) {
            Log.e(TAG, "Error registering plugin webview_flutter_android, io.flutter.plugins.webviewflutter.WebViewFlutterPlugin", e51);
        }
        try {
            flutterEngine.getPlugins().add(new WifiIotPlugin());
        } catch (Exception e52) {
            Log.e(TAG, "Error registering plugin wifi_iot, com.alternadom.wifiiot.WifiIotPlugin", e52);
        }
        try {
            flutterEngine.getPlugins().add(new WifiScanPlugin());
        } catch (Exception e53) {
            Log.e(TAG, "Error registering plugin wifi_scan, dev.flutternetwork.wifi.wifi_scan.WifiScanPlugin", e53);
        }
    }
}
