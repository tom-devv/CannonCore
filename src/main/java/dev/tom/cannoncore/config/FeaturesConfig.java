package dev.tom.cannoncore.config;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import dev.splityosis.configsystem.configsystem.ConfigHeader;
import lombok.Getter;

import java.io.File;

@ConfigHeader(header = "Configure settings related to CannonCore features")
public class FeaturesConfig extends AnnotatedConfig {
    public FeaturesConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    /*
    TNTFill
     */
    @Getter
    @ConfigField(path = "features.tntfill.max-radius")
    public static int maxRadius = 10;
}
