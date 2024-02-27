package dev.tom.cannoncore.config;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ChatMessages extends AnnotatedConfig {

    public ChatMessages(File parentDirectory, String name) {super(parentDirectory, name);}

    @ConfigField(path = "message.help")
    public List<String> helpmessage = Arrays.asList("&c&lCommands &8»",

            "&8» &f/Tntfill &cFills Dispensers With Tnt Around You",
            "&8» &f/Tntunfill &cUnfills Dispensers Around You",
            "&8» &f/Fire &cPresses The Last Button You Pressed",
            "&8» &f/Lever &cFlicks The Last Lever You Flicked",
            "&8» &f/Ms &cGives You A Magicsand Block",
            "&8» &f/Refill &cReplaces Sandstone With Magic Sand Around You",
            "&8» &f/Cl &cGives You A 5x5 Chunk Loader",
            "&8» &f/Ca &cClears Sand In A 50x50 Area Around You",
            "&8» &f/Ce &cClears Entities In A 50x50 Area Around You",
            "&8» &f/Clock &cCreates A Redstone Clock",
            "&8» &f/Walls &cGenerates Walls",
            "&8» &f/Upload &cUpload Schematics",
            "&8» &f/Tickstick &cCounts Ticks",
            "&8» &f/Xray &cSee Through Cannon",
            "&8» &f/Platform &cGenerates A Platform",
            "&8» &f/bh &cStops You From Opening Dispensers",
            "&8» &f/BudCheck &cShows Budded Dispensers",
            "&8» &f/Wire &cHelps Make Cannon Powers",
            "&l-------------------------",
            "&c&lItems &8»",
            "&8» &fBone &cRemoves Sandstacks",
            "&8» &fStick &cNode Counter",
            "&8» &fGold Block &cShows What Entities Hit It",
            "&8» &fEmerald &cSafe Block");

    @ConfigField(path = "message.discord")
    public List<String> discordmessage = Arrays.asList(" &c&lCannoning&f&lMC &8» &f Join The Discord Here : https://discord.gg/qypVs3EK ");

    @ConfigField(path = "message.bone.receive")
    public List<String> bonereceive = Arrays.asList("&7You have received a bone");

    @ConfigField(path = "message.unfill")
    public List<String> unfill = Arrays.asList("&7You have unfilled dispensers");

    @ConfigField(path = "message.tntfill")
    public List<String> tntfill = Arrays.asList("&7You have filled dispensers");

    @ConfigField(path = "message.node.receive")
    public List<String> nodereceive = Arrays.asList("&7You have received a node");

    }

