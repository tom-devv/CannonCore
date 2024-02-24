package dev.tom.cannoncore.objects;


import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
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

    private Player player = null;
    private PlotPlayer<Player> plotPlayer;
    private UUID uuid = null;
    public static Map<UUID, CannonPlayer> cannonPlayerMap = new HashMap<>();

    public CannonPlayer(Player player){
        this.player = player;
        this.uuid = player.getUniqueId();
        this.plotPlayer = PlotPlayer.from(player);
        cannonPlayerMap.put(this.uuid, this);
    }

    public CannonPlayer(UUID uuid){
        new CannonPlayer(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
    }

    public void sendMessage(String... message){
        this.player.sendMessage(Util.colorize(message));
    }


    /**
     *
     * @param radius distance from the player to search
     * @return List of blocks in a given radius
     */

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

    public static CannonPlayer getCannonPlayer(UUID uuid){
        if(cannonPlayerMap.containsKey(uuid)) return cannonPlayerMap.get(uuid);
        return new CannonPlayer(uuid);
    }

    public static CannonPlayer getCannonPlayer(Player player){
        return getCannonPlayer(player.getUniqueId());
    }


    public PlotPlayer<Player> getPlotPlayer() {
        return plotPlayer;
    }
}
