package dev.tom.cannoncore.objects;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import lombok.Data;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CannonPlayer {

    private final Player player;
    private final PlotPlayer<Player> plotPlayer;

    public CannonPlayer(Player player){
        this.player = player;
        this.plotPlayer = PlotPlayer.from(player);
    }


    /**
     *
     * @param radius distance from the player to search
     * @return List of blocks in a given radius
     */

    public List<Block> getNearbyBlocks(int radius) {
        World world = player.getLocation().getWorld();
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

    public boolean canBuild(){
        Plot plot = getPlotPlayer().getCurrentPlot();
        return plot.getMembers().contains(getPlotPlayer().getUUID());

    }

    /**
     * Checks if the player is trusted or a plot owner
     * @return If the player has elevated permissions on the plot
     */

    public boolean canEdit(){
        Plot plot = getPlotPlayer().getCurrentPlot();
        System.out.println(plot.getOwner());
        System.out.println(getPlotPlayer().getUUID());
        if(plot.getOwner() == getPlotPlayer().getUUID()){
            return true;
        }
        return plot.getTrusted().contains(getPlotPlayer().getUUID());
    }
}
