package dev.tom.cannoncore;

import com.plotsquared.core.PlotAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CannonCore extends JavaPlugin {

    public static String BASE_COMMAND_PERMISSION = "cannoncore.command.";

    @Getter
    public static CannonCore plugin;

    @Getter
    public static PlotAPI plotAPI;

    @Override
    public void onEnable() {
        plugin = this;
        PlotSquaredHook();

    }

    private void PlotSquaredHook(){
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
            System.out.println("[CannonCore]: [ERROR]: PLOTSQUARED NOT FOUND");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
