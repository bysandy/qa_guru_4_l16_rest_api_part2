package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/authorisation.properties"
})

public interface AuthorisationConfig extends Config{
    @Config.Key("email.username")
    String emailUsername();

    @Config.Key("email.password")
    String emailPassword();
}
