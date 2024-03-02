package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.events.RedstoneNodeUpdateEvent;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.objects.PlayerNodeSession;
import dev.tom.cannoncore.objects.RedstoneNode;
import org.bukkit.Bukkit;
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
        if(!RedstoneNode.trackedNodes.containsKey(e.getBlock())) return;
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
            PlayerNodeSession.nodeSessions.remove(e.getPlayer().getUniqueId());
            Util.sendMessage(e.getPlayer(), "Cleared selected nodes");
            return;
        }

        if(e.getClickedBlock() == null) return;
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!redstoneNodes.contains(e.getClickedBlock().getType())) return;

        // Clicked correct block with node stick
        e.setCancelled(true);
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        PlayerNodeSession session = PlayerNodeSession.nodeSessions.get(player.getUniqueId());
        if(session == null){
            session = new PlayerNodeSession();
        }
        Set<RedstoneNode> trackedNodes = RedstoneNode.trackedNodes.get(block);
        if(trackedNodes == null) return;
        boolean contained = false;
        for (RedstoneNode trackedNode : trackedNodes) {
            // Remove tracked node
            if(trackedNode.getPlayerNodeSession().equals(session)){
                contained = true;
                trackedNodes.remove(trackedNode);
                RedstoneNode.trackedNodes.put(block, trackedNodes);
                player.sendMessage("Removed node: " + trackedNode.getSelectedNodeIndex());
            }
        }
        if(!contained) {
            trackedNodes.add(new RedstoneNode(trackedNodes.size(), player.getUniqueId(), session));
            RedstoneNode.trackedNodes.put(block, trackedNodes);
            player.sendMessage("Added node: " + (trackedNodes.size() - 1));
        }



//        RedstoneNodeList playersNodes = RedstoneNode.playerSelectedNodes.get(player.getUniqueId()) == null ? new RedstoneNodeList() : RedstoneNode.playerSelectedNodes.get(player.getUniqueId());
//
//        // Block is node in the player's selected nodes
//        if(playersNodes.getByBlock(block) == null){
//            playersNodes.add(new RedstoneNode(block)); // Add to player's tracked nodes
//            RedstoneNode.playerSelectedNodes.put(player.getUniqueId(), playersNodes);
//
//            // Add to globally tracked nodes if not already
//            if(!RedstoneNode.trackedNodeActivations.containsKey(block)){
//                RedstoneNode.trackedNodeActivations.put(block, -1L);
//            }
//
//            Util.sendMessage(player, "Block selected ID:" + (playersNodes.size() -1 ) + "at" + "X:" + e.getClickedBlock().getX() + " Y:" + e.getClickedBlock().getY() + " Z:" + e.getClickedBlock().getZ());
//        }
//        // Remove block from player's nodes
//        else {
//            playersNodes.remove(playersNodes.getByBlock(block));
//            // Handle it not tracked by anyone -> remove from tracked blocks forever
//        }
    }

}
