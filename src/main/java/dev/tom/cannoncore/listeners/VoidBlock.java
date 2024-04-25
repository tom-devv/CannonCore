package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;



public class VoidBlock implements Listener {


    public VoidBlock(CannonCore plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("voidblock.use")) {
            List<String> debugMats = Collections.singletonList("CALCITE");
            Iterator var5 = debugMats.iterator();

            while(var5.hasNext()) {
                String s = (String)var5.next();
                if (e.getBlockPlaced().getType().equals(Material.valueOf(s))) {
                    this.setDebugBlock(e.getBlockPlaced(), p);
                    return;
                }
            }

        }
    }

    public void setDebugBlock(final Block block, final Player p) {
        Util.sendMessage(p, "debug block placed");
        final List<Entity> oldEntities = new ArrayList<>();
        final Location location = block.getLocation();
        final Material mat = block.getType();
        (new BukkitRunnable() {
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                } else if (!block.getType().equals(mat)) {
                    Util.sendMessage(p, "void block broken");
                    this.cancel();
                } else {
                    Collection<Entity> nearbyEntities = block.getWorld().getNearbyEntities(location, 2.0D, 1.0D, 2.0D);
                    Iterator var5 = nearbyEntities.iterator();

                    while(var5.hasNext()) {
                        Entity e = (Entity)var5.next();
                        if (!oldEntities.contains(e)) {
                            oldEntities.add(e);
                            if (e instanceof FallingBlock) {
                                e.remove();
                            }

                            if (e instanceof TNTPrimed) {
                                e.remove();
                            }
                        }
                    }
                }
            }
        }).runTaskTimer(CannonCore.getPlugin(CannonCore.class), 0L, 0L);
    }
}
