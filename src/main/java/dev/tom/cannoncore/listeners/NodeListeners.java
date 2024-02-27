package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.events.RedstoneNodeUpdateEvent;
import dev.tom.cannoncore.items.AbstractCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class NodeListeners implements Listener {

    Set<Material> redstoneNodes = new HashSet<>(Arrays.asList(
            Material.OBSERVER,
            Material.REDSTONE,
            Material.REPEATER,
            Material.COMPARATOR,
            Material.REDSTONE_TORCH,
            Material.REDSTONE_WALL_TORCH,
            Material.REDSTONE_WIRE,
            Material.DISPENSER,
            Material.PISTON,
            Material.STICKY_PISTON
    )
    );

    public static Map<UUID, List<Block>> selectedNodes = new HashMap<>();
    public static Map<Block, Long> nodeActivations = new HashMap<>();

    public NodeListeners(){
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }


    @EventHandler
    public void onDispense(BlockDispenseEvent e){
        if(!nodeActivations.containsKey(e.getBlock())) return; // Dispenser is not being tracked
        RedstoneNodeUpdateEvent event = new RedstoneNodeUpdateEvent(e.getBlock(), CannonCore.getCurrentTick());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    // Observers, redstone wire, redstone torch, redstone comparator, redstone repeater
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent e){
        if(!redstoneNodes.contains(e.getBlock().getType())) return; // Type that we aren't tracking
        if(!nodeActivations.containsKey(e.getBlock())) return; // Block is not being tracked
        // Torches work in reverse, when we "activate" a torch it turns off
        if(e.getBlock().getType().equals(Material.REDSTONE_TORCH) || e.getBlock().getType().equals(Material.REDSTONE_WALL_TORCH)){
            if(e.getNewCurrent() != 0) return;
        }
        // For everything else we want to track when it turns on
        else if(e.getOldCurrent() != 0 || e.getNewCurrent() <= 0) return;
        RedstoneNodeUpdateEvent event = new RedstoneNodeUpdateEvent(e.getBlock(), CannonCore.getCurrentTick());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }


    @EventHandler
    public void onNodeSelect(PlayerInteractEvent e){
        if(!e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getClickedBlock() != null) return;
        if(!redstoneNodes.contains(e.getClickedBlock().getType())) return;
        ItemStack itemMainHand = e.getPlayer().getInventory().getItemInMainHand();
        if(AbstractCannonItem.getIdentifier(itemMainHand, "node") == null) return;

        // Clicked correct block with node stick
        e.setCancelled(true);
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        List<Block> playersNodes = selectedNodes.get(player.getUniqueId()) == null ? new ArrayList<>() : selectedNodes.get(player.getUniqueId());

        if(playersNodes.contains(block)){
            // To maintain index positions set the element to null
            playersNodes.set(playersNodes.indexOf(block), null);
        } else {
            playersNodes.add(block);

            if(!nodeActivations.containsKey(block)){
                nodeActivations.put(block, 0L);
            }

            // [node number] Added @ [x, y, z]
            Util.sendMessage(player, "Block selected ID:" + playersNodes + "at" + "X:" + e.getClickedBlock().getX() + " Y:" + e.getClickedBlock().getY() + " Z:" + e.getClickedBlock().getZ());
        }



    }

}
