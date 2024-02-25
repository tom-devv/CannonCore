package dev.tom.cannoncore;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.BlockTypeMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.*;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@UtilityClass
public class Util {

    private static final java.util.regex.Pattern HEX_PATTERN = java.util.regex.Pattern.compile("&(#\\w{6})");
    public static final Set<BlockType> magicsandBlockTypes = MagicsandManager.magicsandSpawnBlocks.stream().map((material) -> BlockTypes.get(material.name().toLowerCase())).collect(Collectors.toSet());

    private static void sendMessage(CommandSender to, String message) {
        to.sendMessage(colorize(message));
    }

    public static void sendMessage(CommandSender to, List<String> message) {
        message.forEach((s) -> {
            sendMessage(to, s);
        });
    }

    public static String[] colorize(String... str) {
        for (int i = 0; i < str.length; i++) {
            str[i] = colorize(str[i]);
        }
        return str;
    }

    public static String colorize(String str) {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', str));
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.valueOf(matcher.group(1)).toString());
        }

        return org.bukkit.ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static List<String> colorize(List<String> lst) {
        List<String> newList = new ArrayList();
        lst.forEach((s) -> {
            newList.add(colorize(s));
        });
        return newList;
    }

    public static List<String> replaceList(List<String> lst, String from, String to) {
        List<String> newList = new ArrayList();
        lst.forEach((s) -> {
            newList.add(s.replace(from, to));
        });
        return newList;
    }

    public static void sendMessage (Player player, String message) {
        if (!message.isEmpty())
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(Player player, List<String> messageList) {
        for (String message : messageList)
            if (!message.isEmpty())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void setBlock(Block block, Material material){
        setBlock(block.getLocation(), material);
    }

    public static void setBlock(Location location, Material material){
        try (EditSession session = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()))) {
            session.setBlock(BukkitAdapter.asBlockVector(location), BlockTypes.get(material.name().toLowerCase()));
        };
    }

    public static void setBlocks(Set<Block> blocks, Material material, Location location){
        try (EditSession session = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()))) {
            Set<BlockVector3> blockVector3s = blocks.stream().map((block) -> BukkitAdapter.asBlockVector(block.getLocation())).collect(Collectors.toSet());
            session.setBlocks(blockVector3s, BlockTypes.get(material.name().toLowerCase()));
        };
    }

    public static void clearSandLike(Location start) {
        Region region = new CuboidRegion(BukkitAdapter.adapt(start.getWorld()), BukkitAdapter.asBlockVector(start), BukkitAdapter.asBlockVector(start.clone().add(0, -64 - start.getBlockY(), 0)));
        replaceBlocks(region, MagicsandManager.magicsandSpawnBlocks, Material.AIR);
    }

    public static void replaceBlocks(Region region, Set<Material> from, Material to) {
        try (EditSession session = WorldEdit.getInstance().newEditSession(region.getWorld())) {
            Mask mask = new BlockTypeMask(session, from.stream().map((material) -> BlockTypes.get(material.name().toLowerCase())).collect(Collectors.toSet()));
            session.replaceBlocks(region, mask, BlockTypes.get(to.name().toLowerCase()));
        };
    }

    public static void replaceBlockType(Region region, Set<BlockType> type, Material to){
        try (EditSession session = WorldEdit.getInstance().newEditSession(region.getWorld())) {
            Mask mask = new BlockTypeMask(session, type);
            session.replaceBlocks(region, mask, BlockTypes.get(to.name().toLowerCase()));
        };
    }

}
