package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.events.RedstoneNodeUpdateEvent;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.node.Node;
import dev.tom.cannoncore.node.NodeArray;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class NodeListeners implements Listener {

    public static Map<Player, NodeArray> nodeArrays = new HashMap<>();
    public static Set<Location> trackedNodes = new HashSet<>();

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

    public NodeListeners(){
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }


    @EventHandler
    public void onDispense(BlockDispenseEvent e){
        RedstoneNodeUpdateEvent event = new RedstoneNodeUpdateEvent(e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    //Observers, redstone wire, redstone torch, redstone comparator, redstone repeater
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent e){
        if(!redstoneNodes.contains(e.getBlock().getType())) return; // Type that we aren't tracking

        // Torches work in reverse, when we "activate" a torch it turns off
        if(e.getBlock().getType().equals(Material.REDSTONE_TORCH) || e.getBlock().getType().equals(Material.REDSTONE_WALL_TORCH)){
            if(e.getNewCurrent() != 0) return;
        }

        // For everything else we want to track when it turns on
        else if(e.getOldCurrent() != 0 ) return;
        RedstoneNodeUpdateEvent event = new RedstoneNodeUpdateEvent(e.getBlock());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }


    @EventHandler
    public void onNodeSelect(PlayerInteractEvent e){
        ItemStack itemMainHand = e.getPlayer().getInventory().getItemInMainHand();
        if(itemMainHand.getType().equals(Material.AIR)) return;
        if(!AbstractCannonItem.getIdentifier(itemMainHand, "node").equalsIgnoreCase("nodestick")) return;

        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            nodeArrays.remove(e.getPlayer());
            Util.sendMessage(e.getPlayer(), "Cleared selected nodes");
            return;
        }

        if(e.getClickedBlock() == null) return;
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!redstoneNodes.contains(e.getClickedBlock().getType())) return;

        e.setCancelled(true);

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        trackedNodes.add(block.getLocation());

        NodeArray nodeArray = new NodeArray();
        if(nodeArrays.containsKey(player)){
            nodeArray = nodeArrays.get(player);
        }
        Node node = new Node(block.getLocation(), -1);
        nodeArray.addNode(node);

        nodeArrays.put(player, nodeArray);

        player.sendMessage("Added node: " + nodeArray.getIndex(node));
    }

}
