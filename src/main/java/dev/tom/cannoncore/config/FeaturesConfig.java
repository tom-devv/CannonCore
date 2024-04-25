package dev.tom.cannoncore.config;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import dev.splityosis.configsystem.configsystem.ConfigHeader;
import dev.tom.cannoncore.Util;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

    @ConfigField(path = "budchecker.radius")
    public Integer bradius = 50;

    @ConfigField(path = "xray.radius")
    public Integer xradius = 50;


    @ConfigField(path = "void.block")
    public ItemStack voidblock = Util.createItemStack(Material.CALCITE, 1, "&e&lVoid Block", Arrays.asList(
            "&7Entities get removed upon hitting the block"));

    @ConfigField(path = "debugblock.block")
    public ItemStack debugblock = Util.createItemStack(Material.GOLD_BLOCK, 1, "&e&lDebug Block", Arrays.asList(
            "&7Place to count the entities that hit it"));

}
