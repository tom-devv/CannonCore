package dev.tom.cannoncore.magicsand;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.BlockTypeMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
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
import java.util.stream.Collectors;

import static dev.tom.cannoncore.Util.replaceBlockType;

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
        Magicsand magicsand = activateBlockAsMagicsand(CannonPlayer.getCannonPlayer(e.getPlayer()).getCurrentPlot(), magicsandItem.getType(), e.getBlock().getLocation());
        // Wasn't activated, so don't place it
        if(magicsand == null) {
            e.setCancelled(true);
        }
        e.getBlock().setType(MagicsandType.getActiveBlock(), false);
        e.getPlayer().sendMessage("Magic sand placed!");
    }

    @EventHandler
    public void onMagicsandBreak(BlockBreakEvent e){
        Location location = e.getBlock().getLocation();
        Magicsand magicsand = getMagicsandAtLocation(location);
        if(magicsand == null) return;
        deactivateMagicsand(location);
    }

    public static Magicsand activateBlockAsMagicsand(Plot plot, Location location){
        MagicsandType type = MagicsandType.getByInactiveBlock(location.getBlock().getType());
        if(type == null && location.getBlock().getType().equals(MagicsandType.getActiveBlock())){
            type = MagicsandType.SAND;
        } else if (type == null){
            return null;
        }
        return activateBlockAsMagicsand(plot, type, location);
    }

    /**
     * Adds an active magicsand to a plot
     * @param plot the plot to magicsand was placed in
     * @param location the location of the magicsand
     */
    public static Magicsand activateBlockAsMagicsand(Plot plot, MagicsandType type, Location location){
        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null){
            plotMagicSand = new HashMap<>();
        }

        // Failsafe for too many magicsand
        if(plotMagicSand.size() - 1 > FeaturesConfig.getMagicsandMaxPerPlot()){
            for (PlotPlayer<?> plotPlayer : plot.getPlayersInPlot()) {
                try {
                    Bukkit.getPlayer(plotPlayer.getUUID()).sendMessage("This plot has reached the maximum amount of magicsand");
                } catch (Exception ignored){
                }
            }
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
    public static Magicsand deactivateMagicsand(Location location){
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Plot plot = plotLocation.getPlot();
        if(plot == null) return null; // Not in a plot

        HashMap<Location, Magicsand> plotMagicSand = activePlotMagicSands.get(plot);
        if(plotMagicSand == null) return null;
        Magicsand magicsand = plotMagicSand.get(location);
        if(magicsand == null) return null; // Not a magicsand block as was not in map
        magicsand.deactivate();
        plotMagicSand.remove(location); // remove from list of magicsand -> garbage collection
        activePlotMagicSands.put(plot, plotMagicSand);
        return magicsand;
    }

    /**
     * Checks if nearby block type is an inactive magicsand block and if so, spawns a new magicsand
     * @param player player to check the radius of
     */
    public static void refillMagicsand(CannonPlayer player){
        EllipsoidRegion region = player.getSphereRegion(FeaturesConfig.getMaxRadius());
        World world = BukkitAdapter.adapt(player.getPlayer().getWorld());
        Set<BlockVector3> activatedSandBlocks = new HashSet<>();
        for (BlockVector3 blockVector3 : region) {
            Material blockMaterial = BukkitAdapter.adapt(blockVector3.getBlock(world).getBlockType());
            if(!magicsandInactiveBlocks.contains(blockMaterial)) continue;
            Location location = new Location(player.getPlayer().getWorld(), blockVector3.getX(), blockVector3.getY(), blockVector3.getZ());
            Magicsand magicsand = activateBlockAsMagicsand(player.getCurrentPlot(), location);
            if(magicsand == null) { // Failed to create, full? Stop
                break;
            }
            activatedSandBlocks.add(blockVector3);
        }

        BlockType type = BlockTypes.get(MagicsandType.getActiveBlock().name().toLowerCase());
        try (EditSession session = WorldEdit.getInstance().newEditSession(world)) {
            session.setBlocks(activatedSandBlocks, type);
        };
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
        EllipsoidRegion region = player.getSphereRegion(FeaturesConfig.getMaxRadius());
        World world = BukkitAdapter.adapt(player.getPlayer().getWorld());
        HashMap<Location, Magicsand> plotMagicsand = activePlotMagicSands.get(player.getCurrentPlot());
        Map<MagicsandType, Set<Magicsand>> magicsandBlocks = new HashMap<>();
        if(plotMagicsand == null) return;
        for (BlockVector3 blockVector3 : region) {
            Location location = new Location(player.getPlayer().getWorld(), blockVector3.getX(), blockVector3.getY(), blockVector3.getZ());
            Magicsand magicsand = deactivateMagicsand(location);
            if(magicsand == null) continue;
            Set<Magicsand> sands = magicsandBlocks.get(magicsand.getType()) == null ? new HashSet<>() : magicsandBlocks.get(magicsand.getType());
            sands.add(magicsand);
            magicsandBlocks.put(magicsand.getType(), sands);
        }

        try (EditSession session = WorldEdit.getInstance().newEditSession(world)) {
            magicsandBlocks.forEach((magicsandType, magicsand) -> {
                session.setBlocks(magicsand.stream().map(Magicsand::getBlockVector3).collect(Collectors.toSet()), BlockTypes.get(magicsandType.getInactiveBlock().name().toLowerCase()));
                Mask mask = new BlockTypeMask(session, Util.magicsandBlockTypes);
                Set<Region> regions = magicsand.stream().map(Magicsand::getRegion).collect(Collectors.toSet());
                for (Region r : regions) {
                    session.replaceBlocks(r, mask , BlockTypes.get(Material.AIR.name().toLowerCase()));
                }
            });
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

}
