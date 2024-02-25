package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BoneEvent implements Listener {


    public BoneEvent(CannonCore plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Material item = event.getPlayer().getInventory().getItemInMainHand().getType();
        if (item.equals(Material.BONE)) {
            Player player = event.getPlayer();
            Block block = getTargetBlock(player, 200);
            if (block != null) {
                int removedCount = 0;
                Location loc = block.getLocation().clone();
                for (int i = 0; i < 384; i++) {
                    Block iterBlock = loc.add(0,1,0).getBlock();
                    if(MagicsandManager.magicsandSpawnBlocks.contains(iterBlock.getType())) continue;
                    Util.clearSandLike(iterBlock.getLocation());
                    break;
                }
                if (removedCount > 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "§c§lCannoning§f§lMC §8» §fRemoved " + removedCount + " Blocks"));
                }
            }
        }
    }

    private Block getTargetBlock(Player player, int range) {
        Location loc = player.getEyeLocation();
        int distance = 0;
        while (distance <= range) {
            loc = loc.add(loc.getDirection());
            Block block = loc.getBlock();
            if (MagicsandManager.magicsandSpawnBlocks.contains(block.getType())) {
                return block;
            }
            distance++;
        }
        return null;
    }
}

