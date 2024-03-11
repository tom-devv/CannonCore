package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ProtectionBlockEvent implements Listener {

    public Set<Material> protectedMaterials = new HashSet<>(Set.of(
            Material.EMERALD_BLOCK
    ));

    public ProtectionBlockEvent(CannonCore plugin){


        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        new BukkitRunnable(){
            @Override
            public void run() {
                protectedCoords.clear();
                happenedExplosions.clear();
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private Set<Integer> protectedCoords = new HashSet<>();
    private Map<Integer, List<Block>> happenedExplosions = new HashMap<>();

    @EventHandler
    public void TNTExplodeEvent(EntityExplodeEvent e){
        int tripletHash = Objects.hash(e.getLocation().getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ());
        List<Block> already = happenedExplosions.get(tripletHash);

        if (already != null){
            e.blockList().clear();
            e.blockList().addAll(already);
            return;
        }

        for (Block block : new ArrayList<>(e.blockList())) {
            int pairHash = Objects.hash(block.getWorld(), block.getX(), block.getZ());
            if (protectedCoords.contains(pairHash)){
                e.blockList().remove(block);
                continue;
            }
            for (int y = -63-block.getY(); y < 321-block.getY(); y++) {
                Block iBlock = block.getRelative(0, y, 0);
                if (protectedMaterials.contains(iBlock.getType())){
                    protectedCoords.add(pairHash);
                    e.blockList().remove(block);
                }
            }
        }
        happenedExplosions.put(tripletHash, new ArrayList<>(e.blockList()));
    }



}