package dev.tom.cannoncore;

import com.plotsquared.core.PlotAPI;
import dev.tom.cannoncore.commands.CannonCoreCommands;
import dev.tom.cannoncore.config.ChatMessages;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.listeners.Block36Events;
import dev.tom.cannoncore.listeners.BoneEvent;
import dev.tom.cannoncore.listeners.ProtectionBlockEvent;
import dev.tom.cannoncore.magicsand.MagicsandType;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.File;

public final class CannonCore extends JavaPlugin {

    public static String BASE_COMMAND_PERMISSION = "cannoncore.command.";
    public static String NBT_IDENTIFIER = "cannoncore";

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
        PlotSquaredHook();
        SakuraHook();
        configs();
        MagicsandType.initializeMagicsandTypes();
        new MagicsandManager(this);
        new CannonCoreCommands();
        new Block36Events(this);
        new ProtectionBlockEvent(this);
        new BoneEvent(this);

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
        MagicsandManager.activePlotMagicSands.forEach((plot, map) -> {
            map.forEach((location, magicsand) -> {
                magicsand.deactivate();
            });
        });
    }

    public static Reflections getReflection() {
        return reflection;
    }
}
