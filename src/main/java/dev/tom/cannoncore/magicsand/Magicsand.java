package dev.tom.cannoncore.magicsand;


import com.plotsquared.core.plot.Plot;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

@Getter
public class Magicsand {

    private final MagicsandType type;
    private final Location location;
    private final com.plotsquared.core.location.Location plotLocation;
    private final Plot plot;
    private BukkitTask spawnTask;
    private int counter = 0;

    public Magicsand(MagicsandType type, org.bukkit.Location location){
        this.type = type;
        this.location = location;
        this.plotLocation = com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        this.plot = plotLocation.getPlot();
        run();
    }

    public void deactivate(){
        if(spawnTask != null){
            spawnTask.cancel();
        }
        MagicsandManager.deactivateMagicsand(getLocation());
    }

    public void run() {
        if (plot == null) {
            return;
        }
        // Failsafe for too many magicsand
        HashMap<Location, Magicsand> plotMagicsand = MagicsandManager.activePlotMagicSands.get(plot);
        if (plotMagicsand.size() > FeaturesConfig.magicsandMaxPerPlot) {
            return;
        }
        getLocation().getBlock().setType(MagicsandType.getActiveBlock());
        MagicsandManager.activateBlockAsMagicsand(plot, location); // It's running so add it to the map
        spawnTask = new BukkitRunnable(){
            @Override
            public void run() {
                Location under = location.clone().subtract(0,1,0);
                if(under.getBlock().getType().equals(getType().getSpawnBlock())) return;
                under.getBlock().setType(getType().getSpawnBlock(), false);
                spawnColumn();
            }

            @Override
            public synchronized boolean isCancelled() throws IllegalStateException {
                return super.isCancelled();
            }
        }.runTaskTimer(CannonCore.getCore(), 0, 1);
    }

    private void spawnColumn(){
        counter = 0;
        new BukkitRunnable(){
            @Override
            public void run() {
                Location underClone = getLocation().clone().subtract(0,2,0); // Block under the magicsand has already been set to spawnBlock
                underClone.subtract(0, counter ,0);
                if(underClone.getBlockY() <= -64 || !underClone.getBlock().getType().equals(Material.AIR) || spawnTask.isCancelled()) {
                    this.cancel();
                    return;
                }
                underClone.getBlock().setType(getType().getSpawnBlock(), false);
                counter++;
            }
        }.runTaskTimer(CannonCore.getCore(), 0, 1);
        counter = 0;
    }


}
