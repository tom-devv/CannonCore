package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.items.Block36Item;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class Block36Events implements Listener {

    public Block36Events(CannonCore plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     *
     * @param e event
     * Creates a block36 armor stand and sets a moving piston at its head
     */

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        if(!e.getBlock().getType().equals(Material.PLAYER_HEAD) && !e.getBlock().getType().equals(Material.PLAYER_WALL_HEAD)) return; // Prevents any code being ran most of time
        if(!Block36Item.isBlock36(e.getItemInHand())) return;
//        e.setCancelled(true);

        Block block = e.getBlock();
        block.setType(Material.MOVING_PISTON);

        Block36Item block36Item = new Block36Item(e.getPlayer());
        block36Item.createArmorStand(e.getBlock().getLocation().add(0.5, -1.2, 0.5)); // Move it down so the head is at 0.5 0.5
    }


    /**
     *
     * @param e event
     * Removes any block36 armor stands and block when clicked
     */

    @EventHandler
    public void onArmorClick (PlayerArmorStandManipulateEvent e){
        if(!Block36Item.isBlock36(e.getArmorStandItem())) return;
        e.setCancelled(true);
        e.getRightClicked().getLocation().add(0, 1.2 ,0) // Add 1.2 because we -1.2 when placing armor strand
                .getBlock().setType(Material.AIR);
        e.getRightClicked().remove();
    }

}
