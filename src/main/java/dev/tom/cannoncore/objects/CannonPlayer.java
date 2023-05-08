package dev.tom.cannoncore.objects;


import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CannonPlayer {

    private final Player player;
//    private final PlotPlayer<Player> plotPlayer;

    public CannonPlayer(Player player){
        this.player = player;
//        this.plotPlayer = PlotPlayer.from(player);
    }


    /**
     *
     * @param radius distance from the player to search
     * @return List of blocks in a given radius
     */

    public List<Block> getNearbyBlocks(int radius) {
        World world = getPlayer().getLocation().getWorld();
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

//    /**
//     * Checks if the player is a member of the plot
//     * @return If the player can build
//     */
//
//    public boolean canBuild(){
//        Plot plot = getPlotPlayer().getCurrentPlot();
//        return plot.getMembers().contains(getPlotPlayer().getUUID());
//
//    }
//
//    /**
//     * Checks if the player is trusted or a plot owner
//     * @return If the player has elevated permissions on the plot
//     */
//
//    public boolean canEdit(){
//        Plot plot = getPlotPlayer().getCurrentPlot();
//        System.out.println(plot.getOwner());
//        System.out.println(getPlotPlayer().getUUID());
//        if(plot.getOwner() == getPlotPlayer().getUUID()){
//            return true;
//        }
//        return plot.getTrusted().contains(getPlotPlayer().getUUID());
//    }


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
}
