package dev.tom.cannoncore.config;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import dev.splityosis.configsystem.configsystem.ConfigHeader;
import lombok.Getter;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

    /*
    Protection Blocks
     */
    @Getter
    @ConfigField(path = "features.protection-blocks.types")
    public static List<String> materials = Arrays.asList("BEDROCK", "EMERALD");


    @Getter
    @ConfigField(path = "features.magicsand.max-per-plot")
    public static int magicsandMaxPerPlot = 300;
}
