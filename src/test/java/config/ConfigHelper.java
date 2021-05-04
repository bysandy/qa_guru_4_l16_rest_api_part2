package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {
    public static String getEmailUsername(){
        return getAuthorisationConfig().emailUsername();
    }

    public static String getEmailPassword(){
        return getAuthorisationConfig().emailPassword();
    }

    public static String getWebUrl() {
        return getWebConfig().webUrl();
    }

    public static String getWebRemoteDriver() {
        // https://%s:%s@selenoid.autotests.cloud/wd/hub/
        return String.format(System.getProperty("web.remote.driver"),
                getWebConfig().webRemoteDriverUser(),
                getWebConfig().webRemoteDriverPassword());
    }

    public static boolean isRemoteWebDriver() {
        return System.getProperty("web.remote.driver") != null;
    }


    private static AuthorisationConfig getAuthorisationConfig(){
        return ConfigFactory.newInstance().create(
                AuthorisationConfig.class, System.getProperties());
    }

    private static WebConfig getWebConfig() {
        return ConfigFactory.newInstance().create(WebConfig.class, System.getProperties());
    }
}
