package dev.tom.cannoncore.magicsand;

import dev.tom.cannoncore.CannonCore;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class ActiveMagicsand {

    private BukkitTask spawnTask;
    private final Location location;
    private final Location under;
    private final Magicsand magicsand;
    private int counter = 0;

    public ActiveMagicsand(Magicsand magicsand, Location location) {
        this.location = location;
        this.magicsand = magicsand;
        this.under = location.clone().subtract(0,1,0);
        location.getBlock().setType(magicsand.getActiveBlock(), false);
        spawnTask();
    }

    private void spawnColumn(){
        new BukkitRunnable(){
            @Override
            public void run() {
                Location underClone = under.clone().subtract(0,1,0);
                underClone.subtract(0, counter ,0);
                if(underClone.getBlockY() <= -64 || !underClone.getBlock().getType().equals(Material.AIR) || spawnTask.isCancelled()) {
                    this.cancel();
                    return;
                }
                underClone.getBlock().setType(magicsand.getSpawnBlock());
                counter++;
            }
        }.runTaskTimer(CannonCore.getCore(), 0, 1);
        counter = 0;
    }

    public void spawnTask() {
        spawnTask = new BukkitRunnable(){
            @Override
            public void run() {
                if(under.getBlock().getType().equals(magicsand.getSpawnBlock())) return;
                under.getBlock().setType(magicsand.getSpawnBlock());
                spawnColumn();
            }
        }.runTaskTimer(CannonCore.getCore(), 0, 5); // Config this (1 second atm)
    }

    public void clear() {

    }

    public void refill() {

    }

}
