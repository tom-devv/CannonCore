package dev.tom.cannoncore.listeners;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
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
            Block block = getTargetBlock(player, 500);
            if (block != null) {
                int removedCount = 0;

                for (int y = block.getY() - 64; y < 320; y++) {
                    Location newLoc = new Location(block.getWorld(), block.getX(), y, block.getZ());
                    Block newBlock = newLoc.getBlock();
                    Material blockType = newBlock.getType();
                    if (blockType == Material.SAND || blockType == Material.GRAVEL || blockType == Material.RED_SAND) {
                        BlockBreakEvent bbe = new BlockBreakEvent(newBlock, player);
                        Bukkit.getServer().getPluginManager().callEvent(bbe);
                        if (!bbe.isCancelled()) {
                            Util.setBlock(newBlock, Material.AIR);
                            removedCount++;
                        }
                    }
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
            Material blockType = block.getType();
            if (blockType == Material.SAND || blockType == Material.GRAVEL || blockType == Material.RED_SAND) {
                return block;
            }
            distance++;
        }
        return null;
    }
}

