package dev.tom.cannoncore.objects;


import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.selector.SphereRegionSelector;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CannonPlayer {

    public static Map<UUID, CannonPlayer> cannonPlayerMap = new HashMap<>();

    private final Player player;
    private final PlotPlayer<Player> plotPlayer;
    private final UUID uuid;

    public CannonPlayer(Player player){
        this.player = player;
        this.uuid = player.getUniqueId();
        this.plotPlayer = PlotPlayer.from(player);
        cannonPlayerMap.put(this.uuid, this);
    }


    public void sendMessage(String... message){
        this.player.sendMessage(Util.colorize(message));
    }


    public EllipsoidRegion getSphereRegion(int radius) {
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(player.getWorld());
        BlockVector3 center = BlockVector3.at(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());

        // Create the sphere region
        SphereRegionSelector selection = new SphereRegionSelector(world, center, radius);
        return selection.getRegion();
    }

    public List<Block> getNearbyBlocks(int radius) {
        World world = this.getPlayer().getLocation().getWorld();
        List<Block> blocks = new ArrayList<>();
        if(world == null) return blocks;

        int px = player.getLocation().getBlockX();
        int py = player.getLocation().getBlockY();
        int pz = player.getLocation().getBlockZ();

        for (int x = px - radius; x < radius + px; x++) {
            for (int y = py - radius; y < radius + py; y++) {
                for (int z = pz - radius; z < radius + pz; z++) {
                    blocks.add(world.getBlockAt(x,y,z));
                }
            }
        }
        return blocks;
    }

    /**
     * Checks if the player is a member of the plot
     * @return If the player can build
     */

    public boolean isMember(){
        Plot plot = getPlotPlayer().getCurrentPlot();
        return plot.getMembers().contains(getPlotPlayer().getUUID());
    }

    public Plot getCurrentPlot(){
        return getPlotPlayer().getCurrentPlot();
    }


    public boolean isTrusted(){
        Plot plot = getPlotPlayer().getCurrentPlot();
        if(plot.getOwner() == getPlotPlayer().getUUID()){
            return true;
        }
        return plot.getTrusted().contains(getPlotPlayer().getUUID());
    }


    /**
     *
     * @param itemStack
     * Fills dispensers with an itemstack in a radius
     */

    public void fillRadius(ItemStack itemStack){
        List<Block> dispensers = getNearbyBlocks(FeaturesConfig.getMaxRadius())
                .stream()
                .filter(b -> b.getType().equals(Material.DISPENSER))
                .collect(Collectors.toList());

        for (int i = 0; i < dispensers.size(); i++) {
            Dispenser dispenser = (Dispenser) dispensers.get(i).getState();
            Inventory inventory = dispenser.getInventory();
            for (int j = 0; j < 9; j++) {
                inventory.setItem(j, itemStack);
            }
        }
    }


    public static CannonPlayer getCannonPlayer(Player player){
        return new CannonPlayer(player);
    }


    public PlotPlayer<Player> getPlotPlayer() {
        return plotPlayer;
    }
}
