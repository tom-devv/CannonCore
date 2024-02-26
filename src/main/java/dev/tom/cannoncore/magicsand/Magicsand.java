package dev.tom.cannoncore.magicsand;


import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockVector;

import java.util.HashMap;

import static dev.tom.cannoncore.Util.replaceBlockType;

@Getter
public class Magicsand {

    private final MagicsandType type;
    private final Location location;
    private final BlockVector3 blockVector3;
    private final com.plotsquared.core.location.Location plotLocation;
    private final Plot plot;
    private BukkitTask spawnTask;
    private int counter = 0;
    private final Region region;

    /**
     * Creates a new instance of a magicsand
     * when initialized it will run the magicsand
     * @param type the type of magicsand
     * @param location the location of the magicsand
     */
    public Magicsand(MagicsandType type, org.bukkit.Location location){
        this.type = type;
        this.location = location;
        this.plotLocation = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        this.plot = plotLocation.getPlot();
        this.region = new CuboidRegion(BukkitAdapter.adapt(location.getWorld()), BukkitAdapter.asBlockVector(location), BukkitAdapter.asBlockVector(location.clone().add(0, -64 - location.getBlockY(), 0)));
        this.blockVector3 = BukkitAdapter.asBlockVector(location);
        run();
    }

    public void deactivate(){
        if(spawnTask != null){
            spawnTask.cancel();
        }
    }

    public void run() {
        if (plot == null) {
            return;
        }
        // Failsafe for too many magicsand - should never be called
        HashMap<Location, Magicsand> plotMagicsand = MagicsandManager.activePlotMagicSands.get(plot);
        if (plotMagicsand != null && plotMagicsand.size() > FeaturesConfig.magicsandMaxPerPlot) {
            return;
        }
        spawnTask = new BukkitRunnable(){
            @Override
            public void run() {
                Location under = location.clone().subtract(0,1,0);
                if(under.getBlock().getType().equals(getType().getSpawnBlock())) return;
                spawnColumn();
            }

            @Override
            public synchronized boolean isCancelled() throws IllegalStateException {
                return super.isCancelled();
            }
        }.runTaskTimer(CannonCore.getCore(), 0, 1);
    }

    private void spawnColumn(){
        counter = 1;
            new BukkitRunnable(){
                @Override
                public void run() {
                    Location underClone = getLocation().clone();
                    underClone.subtract(0, counter ,0);
                    if(underClone.getBlockY() <= -64 || !underClone.getBlock().getType().equals(Material.AIR) || spawnTask.isCancelled()) {
                        this.cancel();
                        return;
                    }
                    underClone.getBlock().setType(getType().getSpawnBlock(), false);
                    counter++;
                }
            }.runTaskTimer(CannonCore.getCore(), 1, 1);

        counter = 1;
    }


}
