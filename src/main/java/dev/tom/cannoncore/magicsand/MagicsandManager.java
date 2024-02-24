package dev.tom.cannoncore.magicsand;

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
    public static Map<Plot, HashMap<Location, Magicsand>> plotsMagicSand = new HashMap<>();

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
        removeMagicsand(location); // Tries every block and determines if its magicsand in func
    }


    /**
     * Adds an active magicsand to a plot
     * @param plot the plot to magicsand was placed in
     * @param location the location of the magicsand
     * @param magicsand the magicsand placed
     */
    public static void addMagicsand(Plot plot, Location location, Magicsand magicsand){
        HashMap<Location, Magicsand> plotMagicSand = plotsMagicSand.get(plot);
        if(plotMagicSand == null){
            plotMagicSand = new HashMap<>();
        }
        plotMagicSand.put(location, magicsand);
        plotsMagicSand.put(plot, plotMagicSand);
    }

    /**
     * Removes a magicsand from a plot
     * @param location the location of the block that was broken
     * @return true if the magicsand was removed, false if it was not found
     */
    public static void removeMagicsand(Location location){
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Plot plot = plotLocation.getPlot();
        if(plot == null) return; // Not in a plot

        HashMap<Location, Magicsand> plotMagicSand = plotsMagicSand.get(plot);
        if(plotMagicSand == null) return;
        Magicsand magicsand = plotMagicSand.get(location);
        if(magicsand == null) return; // Not a magicsand block as was not in map

        magicsand.stop(); // stop magicsand checking, spawning columns
        plotMagicSand.remove(location); // remove from list of magicsand -> garbage collection
        plotsMagicSand.put(plot, plotMagicSand);
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
            if(MagicsandManager.magicsandInactiveBlocks.contains(block.getType())){
                MagicsandType magicsandType = MagicsandType.getByInactiveBlock(block.getType());
                if(magicsandType == null) continue;
                new Magicsand(magicsandType, block.getLocation()).run();
            }
            // Block was active but is now just activeBlock not spawning, weird edge case
            else if(block.getType().equals(MagicsandType.getActiveBlock())){
                // We can't know the type because all activeBlocks are the same
                // lets hope it was sand!
                new Magicsand(MagicsandType.SAND, block.getLocation()).run();
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
        HashMap<Location, Magicsand> plotMagicSand = plotsMagicSand.get(plot);
        if(plotMagicSand == null) return;
        for (Block block : blocks) {
            Location location = block.getLocation();
            if(plotMagicSand.get(location) == null) continue;
            removeMagicsand(location);
        }
    }

    /**
     * Clears all magicsand in a players radius
     * @param player player to check the radius of
     */
    public static void clearMagicsand(Player player){
        clearMagicsand(CannonPlayer.getCannonPlayer(player));
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
