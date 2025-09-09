package meshprovisioner.models;

import a.a.a.a.b.m.a;
import com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity;
import com.aliyun.alink.linksdk.channel.gateway.api.GatewayErrorCode;
import com.heytap.mcssdk.constant.MessageConstant;
import com.umeng.analytics.pro.q;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SigModelParser {
    public static final short CONFIGURATION_CLIENT = 1;
    public static final short CONFIGURATION_SERVER = 0;
    public static final short GENERIC_ADMIN_PROPERTY_SERVER = 4113;
    public static final short GENERIC_BATTERY_CLIENT = 4109;
    public static final short GENERIC_BATTERY_SERVER = 4108;
    public static final short GENERIC_CLIENT_PROPERTY_SERVER = 4116;
    public static final short GENERIC_DEFAULT_TRANSITION_TIME_CLIENT = 4101;
    public static final short GENERIC_DEFAULT_TRANSITION_TIME_SERVER = 4100;
    public static final short GENERIC_LEVEL_CLIENT = 4099;
    public static final short GENERIC_LEVEL_SERVER = 4098;
    public static final short GENERIC_LOCATION_CLIENT = 4112;
    public static final short GENERIC_LOCATION_SERVER = 4110;
    public static final short GENERIC_LOCATION_SETUP_SERVER = 4111;
    public static final short GENERIC_MANUFACTURER_PROPERTY_SERVER = 4114;
    public static final short GENERIC_ON_OFF_CLIENT = 4097;
    public static final short GENERIC_ON_OFF_SERVER = 4096;
    public static final short GENERIC_POWER_LEVEL_CLIENT = 4107;
    public static final short GENERIC_POWER_LEVEL_SERVER = 4105;
    public static final short GENERIC_POWER_LEVEL_SETUP_SERVER = 4106;
    public static final short GENERIC_POWER_ON_OFF_CLIENT = 4104;
    public static final short GENERIC_POWER_ON_OFF_SERVER = 4102;
    public static final short GENERIC_POWER_ON_OFF_SETUP_SERVER = 4103;
    public static final short GENERIC_PROPERTY_CLIENT = 4117;
    public static final short GENERIC_USER_PROPERTY_SERVER = 4115;
    public static final short HEALTH_CLIENT_MODEL = 3;
    public static final short HEALTH_SERVER_MODEL = 2;
    public static final short LIGHT_CTL_CLIENT = 4869;
    public static final short LIGHT_CTL_SERVER = 4867;
    public static final short LIGHT_CTL_SETUP_SERVER = 4868;
    public static final short LIGHT_CTL_TEMPERATURE_SERVER = 4870;
    public static final short LIGHT_HSL_CLIENT = 4873;
    public static final short LIGHT_HSL_HUE_SERVER = 4874;
    public static final short LIGHT_HSL_SATURATION_SERVER = 4875;
    public static final short LIGHT_HSL_SERVER = 4871;
    public static final short LIGHT_HSL_SETUP_SERVER = 4872;
    public static final short LIGHT_LC_CLIENT = 4881;
    public static final short LIGHT_LC_SERVER = 4879;
    public static final short LIGHT_LC_SETUP_SERVER = 4880;
    public static final short LIGHT_LIGHTNESS_CLIENT = 4866;
    public static final short LIGHT_LIGHTNESS_SERVER = 4864;
    public static final short LIGHT_LIGHTNESS_SETUP_SERVER = 4865;
    public static final short LIGHT_XYL_CLIENT = 4878;
    public static final short LIGHT_XYL_SERVER = 4876;
    public static final short LIGHT_XYL_SETUP_SERVER = 4877;
    public static final short SCENE_CLIENT = 4613;
    public static final short SCENE_SERVER = 4611;
    public static final short SCENE_SETUP_SERVER = 4612;
    public static final short SCHEDULER_CLIENT = 4616;
    public static final short SCHEDULER_SERVER = 4614;
    public static final short SCHEDULER_SETUP_SERVER = 4615;
    public static final short SENSOR_CLIENT = 4354;
    public static final short SENSOR_SERVER = 4352;
    public static final short SENSOR_SETUP_SERVER = 4353;
    public static final String TAG = "SigModelParser";
    public static final short TIME_CLIENT = 4610;
    public static final short TIME_SERVER = 4608;
    public static final short TIME_SETUP_SERVER = 4609;

    public static SigModel getSigModel(int i2) {
        if (i2 == 0) {
            return new ConfigurationServerModel(i2);
        }
        if (i2 == 1) {
            return new ConfigurationClientModel(i2);
        }
        if (i2 == 2) {
            return new HealthServerModel(i2);
        }
        if (i2 == 3) {
            return new HealthClientModel(i2);
        }
        switch (i2) {
            case 4096:
                return new GenericOnOffServerModel(i2);
            case 4097:
                return new GenericOnOffClientModel(i2);
            case 4098:
                return new GenericLevelServerModel(i2);
            case 4099:
                return new GenericLevelClientModel(i2);
            case 4100:
                return new GenericDefaultTransitionTimeServer(i2);
            case 4101:
                return new GenericDefaultTransitionTimeClient(i2);
            case 4102:
                return new GenericPowerOnOffServer(i2);
            case 4103:
                return new GenericPowerOnOffSetupServer(i2);
            case q.a.f21886h /* 4104 */:
                return new GenericPowerOnOffClient(i2);
            case 4105:
                return new GenericPowerLevelServer(i2);
            case 4106:
                return new GenericPowerLevelSetupServer(i2);
            case 4107:
                return new GenericPowerLevelClient(i2);
            case MessageConstant.MessageType.MESSAGE_REVOKE /* 4108 */:
                return new GenericBatteryServer(i2);
            case 4109:
                return new GenericBatteryClient(i2);
            case GatewayErrorCode.ERROR_INVOKE_PARAMS_INVALID /* 4110 */:
                return new GenericLocationServer(i2);
            case 4111:
                return new GenericLocationSetupServer(i2);
            case 4112:
                return new GenericLocationClient(i2);
            case 4113:
                return new GenericAdminPropertyServer(i2);
            case 4114:
                return new GenericManufacturerPropertyServer(i2);
            case 4115:
                return new GenericUserPropertyServer(i2);
            case 4116:
                return new GenericClientPropertyServer(i2);
            case 4117:
                return new GenericPropertyClient(i2);
            default:
                switch (i2) {
                    case q.a.f21889k /* 4352 */:
                        return new SensorServer(i2);
                    case q.a.f21890l /* 4353 */:
                        return new SensorSetupServer(i2);
                    case q.a.f21891m /* 4354 */:
                        return new SensorClient(i2);
                    default:
                        switch (i2) {
                            case 4608:
                                return new TimeServer(i2);
                            case 4609:
                                return new TimeSetupServer(i2);
                            case BaseAppCompatActivity.sUiHidNavigationBarFlags /* 4610 */:
                                return new TimeClient(i2);
                            case 4611:
                                return new SceneServer(i2);
                            case 4612:
                                return new SceneSetupServer(i2);
                            case 4613:
                                return new SceneClient(i2);
                            case 4614:
                                return new SchedulerServer(i2);
                            case 4615:
                                return new SchedulerSetupServer(i2);
                            case 4616:
                                return new SchedulerClient(i2);
                            default:
                                switch (i2) {
                                    case 4864:
                                        return new LightLightnessServer(i2);
                                    case 4865:
                                        return new LightLightnessSetupServer(i2);
                                    case 4866:
                                        return new LightLightnessClient(i2);
                                    case 4867:
                                        return new LightCtlServer(i2);
                                    case 4868:
                                        return new LightCtlSetupServer(i2);
                                    case 4869:
                                        return new LightCtlClient(i2);
                                    case 4870:
                                        return new LightCtlTemperatureServer(i2);
                                    case 4871:
                                        return new LightHslServer(i2);
                                    case 4872:
                                        return new LightHslSetupServer(i2);
                                    case 4873:
                                        return new LightHslClient(i2);
                                    case 4874:
                                        return new LightHslHueServer(i2);
                                    case 4875:
                                        return new LightHslSaturationServer(i2);
                                    case 4876:
                                        return new LightXylServer(i2);
                                    case 4877:
                                        return new LightXylSetupServer(i2);
                                    case 4878:
                                        return new LightXylClient(i2);
                                    case 4879:
                                        return new LightLcServer(i2);
                                    case 4880:
                                        return new LightLcSetupServer(i2);
                                    case 4881:
                                        return new LightLightnessClient(i2);
                                    default:
                                        a.a(TAG, "Model ID: " + String.format(Locale.US, "%04X", Integer.valueOf(i2)));
                                        return null;
                                }
                        }
                }
        }
    }
}
