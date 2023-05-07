package dev.tom.cannoncore;

import com.plotsquared.core.PlotAPI;
import dev.tom.cannoncore.commands.CannonCoreCommands;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CannonCore extends JavaPlugin {

    public static String BASE_COMMAND_PERMISSION = "cannoncore.command.";

    @Getter
    public static CannonCore plugin;

    @Getter
    public static PlotAPI plotAPI;

    @Getter
    public static FeaturesConfig featuresConfig;

    @Override
    public void onEnable() {
        plugin = this;
        PlotSquaredHook();
        configs();
        new CannonCoreCommands(this);
    }

    private void PlotSquaredHook(){
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
            System.out.println("[CannonCore]: [ERROR]: PLOTSQUARED NOT FOUND");
        }
    }

    private void configs(){
        File file = new File(getDataFolder(), "config");
        file.mkdirs();
        featuresConfig = new FeaturesConfig(file, "feature-settings");
        featuresConfig.initialize();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
