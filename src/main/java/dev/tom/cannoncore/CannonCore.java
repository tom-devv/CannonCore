package dev.tom.cannoncore;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.plot.Plot;
import dev.tom.cannoncore.commands.CannonCoreCommands;
import dev.tom.cannoncore.config.ChatMessages;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.items.NodeStickItem;
import dev.tom.cannoncore.listeners.Block36Events;
import dev.tom.cannoncore.listeners.BoneEvent;
import dev.tom.cannoncore.listeners.NodeListeners;
import dev.tom.cannoncore.listeners.ProtectionBlockEvent;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.magicsand.MagicsandType;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class CannonCore extends JavaPlugin {

    public static String BASE_COMMAND_PERMISSION = "cannoncore.command.";
    public static String NBT_IDENTIFIER = "cannoncore";

    @Getter
    public static long currentTick = 0;

    @Getter
    public static Reflections reflection = new Reflections("dev.tom.cannoncore");

    @Getter
    public static boolean sakuraEnabled = false;

    @Getter
    public static CannonCore core;

    @Getter
    public static PlotAPI plotAPI;

    @Getter
    public static FeaturesConfig featuresConfig;

    @Getter
    public static ChatMessages chatMessages;

    @Override
    public void onEnable() {
        core = this;
        tickRunner();
        PlotSquaredHook();
        SakuraHook();
        configs();
        MagicsandType.initializeMagicsandTypes();
        new NodeStickItem();
        new MagicsandManager(this);
        new CannonCoreCommands();
        new Block36Events(this);
        new ProtectionBlockEvent(this);
        new BoneEvent(this);
        new NodeListeners();

    }

    private void tickRunner(){

        new BukkitRunnable(){
            @Override
            public void run() {
                currentTick++;
            }
        }.runTaskTimer(this, 0, 1);

    }

    private void PlotSquaredHook(){
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
            System.out.println("[CannonCore]: [ERROR]: PLOTSQUARED NOT FOUND");
        }
        plotAPI = new PlotAPI();
    }

    private void SakuraHook(){
        if(Bukkit.getServer().getName().equalsIgnoreCase("sakura")){
            System.out.println("[CannonCore]: [INFO]: Sakura detected");
            sakuraEnabled = true;
        }

    }

    private void configs(){
        File file = new File(getDataFolder(), "config");
        file.mkdirs();
        featuresConfig = new FeaturesConfig(file, "feature-settings");
        chatMessages = new ChatMessages(file, "chat-messages");
        featuresConfig.initialize();
        chatMessages.initialize();

    }

    @Override
    public void onDisable() {
        // Avoid concurrent modification exception as deactiveMagicsand modifies the map
        Map<Plot, HashMap<Location, Magicsand>> clonedMap = new HashMap<>(MagicsandManager.activePlotMagicSands);
        clonedMap.forEach((plot, map) -> {
            map.forEach((location, magicsand) -> {
                MagicsandManager.deactivateMagicsand(location);
            });
        });
    }

}
