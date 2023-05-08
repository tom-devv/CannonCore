package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProtectionBlockEvent implements Listener {

    public ProtectionBlockEvent(CannonCore plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void TNTExplodeEvent(EntityExplodeEvent e){

        List<Integer> pairs = new ArrayList<>();

        World world = e.getEntity().getWorld();
        for (int i = 0; i < e.blockList().size(); i++) {
            Block block = e.blockList().get(i);
            int hashcode = Objects.hash(block.getX(), block.getZ());
            for (int j = -63; j < 319; j++) {
                if (world.getBlockAt(block.getX(), j, block.getZ()).getType().equals(Material.BEDROCK)) {
                    pairs.add(hashcode);
                    break;
                }
            }
        }

        e.blockList().removeIf(b -> pairs.contains(Objects.hash(b.getX(), b.getZ())));

        for (int i = 0; i < e.blockList().size(); i++) {
            System.out.println("Not Protected: " + e.blockList().get(i));
        }
    }

}
