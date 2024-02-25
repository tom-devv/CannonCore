package dev.tom.cannoncore.magicsand;

import com.plotsquared.core.configuration.caption.StaticCaption;
import com.plotsquared.core.plot.Plot;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.items.MagicsandItem;
import dev.tom.cannoncore.objects.CannonPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;

@Getter
public class MagicsandManager implements Listener {

    @Getter
    public static Set<Material> magicsandSpawnBlocks = new HashSet<>();
    public static Set<Material> magicsandInactiveBlocks = new HashSet<>();
    public static Map<Plot, HashMap<Location, Magicsand>> activePlotMagicSands = new HashMap<>();

    public MagicsandManager(CannonCore plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onMagicsandPlace(BlockPlaceEvent e){
        MagicsandItem magicsandItem = MagicsandItem.getMagicsandItemByItem(e.getItemInHand());
        if(magicsandItem == null) {
            return;
        }
        Magicsand magicsand = new Magicsand(magicsandItem.getType(), e.getBlock().getLocation());
        magicsand.run();
        e.getPlayer().sendMessage("Magic sand placed!");
    }

    @EventHandler
    public void onMagicsandBreak(BlockBreakEvent e){
        Location location = e.getBlock().getLocation();
        Magicsand magicsand = getMagicsandAtLocation(location);
        if(magicsand == null) return;
        magicsand.deactivate();
    }


    /**
     * Adds an active magicsand to a plot
     * @param plot the plot to magicsand was placed in
     * @param location the location of the magicsand
     * @param magicsand the magicsand placed
     */
    public static Magicsand activateBlockAsMagicsand(Plot plot, Location location){
        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null){
            plotMagicSand = new HashMap<>();
        }

        // Failsafe for too many magicsand
        if(plotMagicSand.size() > FeaturesConfig.getMagicsandMaxPerPlot()){
            plot.getPlayersInPlot().forEach(player -> player.sendMessage(StaticCaption.of("This plot has reached the maximum amount of magicsand")));
            return null;
        }

        MagicsandType type = MagicsandType.getByInactiveBlock(location.getBlock().getType());
        if(type == null && location.getBlock().getType().equals(MagicsandType.getActiveBlock())){
            type = MagicsandType.SAND;
        } else if (type == null){
            return null;
        }
        Magicsand magicsand = new Magicsand(type, location);
        plotMagicSand.put(location, magicsand);

        activePlotMagicSands.put(plot, plotMagicSand);
        return magicsand;
    }

    /**
     * Removes a magicsand from a plot
     * @param location the location of the block that was broken
     * @return true if the magicsand was removed, false if it was not found
     */
    public static void deactivateMagicsand(Location location){
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Plot plot = plotLocation.getPlot();
        if(plot == null) return; // Not in a plot

        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null) return;
        Magicsand magicsand = plotMagicSand.get(location);
        if(magicsand == null) return; // Not a magicsand block as was not in map

        plotMagicSand.remove(location); // remove from list of magicsand -> garbage collection
        activePlotMagicSands.put(plot, plotMagicSand);
        location.getBlock().setType(magicsand.getType().getInactiveBlock());
        clearSandBelow(location.getBlock());
    }

    /**
     * Checks if nearby block type is an inactive magicsand block and if so, spawns a new magicsand
     * @param player player to check the radius of
     */
    public static void refillMagicsand(CannonPlayer player){
        List<Block> blocks = player.getNearbyBlocks(FeaturesConfig.getMaxRadius());
        if(blocks.isEmpty()){
            return;
        }
        for (Block block : blocks) {
            // If block is inactive type or active type
            if(MagicsandManager.magicsandInactiveBlocks.contains(block.getType()) || block.getType().equals(MagicsandType.getActiveBlock()) ){
                activateBlockAsMagicsand(player.getCurrentPlot(), block.getLocation());
            }
        }
    }

    /**
     * Checks if nearby block type is an inactive magicsand block and if so, spawns a new magicsand
     * @param player player to check the radius of
     */
    public static void refillMagicsand(Player player){
        refillMagicsand(CannonPlayer.getCannonPlayer(player));
    }

    /**
     * Clears all magicsand in a players radius
     * @param player player to check the radius of
     */
    public static void clearMagicsand(CannonPlayer player){
        List<Block> blocks = player.getNearbyBlocks(FeaturesConfig.getMaxRadius());
        if(blocks.isEmpty()){
            return;
        }
        Plot plot = player.getCurrentPlot();
        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null) return;
        for (Block block : blocks) {
            Location location = block.getLocation();
            if(plotMagicSand.get(location) == null) continue;
            deactivateMagicsand(location);
        }
    }

    /**
     * Clears all magicsand in a players radius
     * @param player player to check the radius of
     */
    public static void clearMagicsand(Player player){
        clearMagicsand(CannonPlayer.getCannonPlayer(player));
    }

    public static Magicsand getMagicsandAtLocation(Location location){
        Plot plot = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()).getPlot();
        if(plot == null) return null;
        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null) return null;
        return plotMagicSand.get(location);
    }

    private static void clearSandBelow(Block block){
        for (int y = block.getY() - 64; y < 320; y++) {
            Location newLoc = new Location(block.getWorld(), block.getX(), y, block.getZ());
            Block newBlock = newLoc.getBlock();
            Material blockType = newBlock.getType();
            if (magicsandSpawnBlocks.contains(blockType)) {
                newBlock.setType(Material.AIR);
            }
        }
    }
}
