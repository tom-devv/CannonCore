package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class ProtectionBlockEvent implements Listener {

    private static Set<Material> protBlocks = new HashSet<>();

    public ProtectionBlockEvent(CannonCore plugin){

        for (String material : FeaturesConfig.materials) {
            try {
                protBlocks.add(Material.valueOf(material.toUpperCase()));
            }catch (Exception e){
                //
            }
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        new BukkitRunnable(){
            @Override
            public void run() {
                protectedPairs.clear();
                protectedTriplets.clear();
            }
        }.runTaskTimer(plugin, 0, 0);
    }

    private Set<Integer> protectedPairs = new HashSet<>();
    private Map<Integer, List<Block>> protectedTriplets = new HashMap<>();

    @EventHandler
    public void TNTExplodeEvent(EntityExplodeEvent e){
        int tripletHash = Objects.hash(e.getLocation().getWorld(), e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ());
        List<Block> already = protectedTriplets.get(tripletHash);
        if (already != null){
            e.blockList().clear();
            e.blockList().addAll(already);
            return;
        }

        blocks:
        for (Block block : new ArrayList<>(e.blockList())) {
            int pairHash = Objects.hash(block.getWorld(), block.getX(), block.getZ());
            if (protectedPairs.contains(pairHash)){
                e.blockList().remove(block);
                continue;
            }
            for (int y = -64-block.getY(); y < 321-block.getY(); y++) {
                Block iBlock = block.getRelative(0, y, 0);
                if (FeaturesConfig.materials.contains(iBlock.getType().name())){
                    protectedPairs.add(pairHash);
                    e.blockList().remove(block);
                    continue blocks;
                }
            }
        }
        protectedTriplets.put(tripletHash, new ArrayList<>(e.blockList()));
    }



}