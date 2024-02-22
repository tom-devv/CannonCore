package dev.tom.cannoncore.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class MultiDispenser implements Listener {

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.getItem().getType() != Material.TNT && e.getItem().getType() != Material.SAND &&
                e.getItem().getType() != Material.GRAVEL)
            return;

        BlockState state = e.getBlock().getState();
        if (!(state instanceof Dispenser))
            return;

        Dispenser dispenser = (Dispenser) state;
        String title = dispenser.getCustomName();

        if (title == null || title.equals("container.dispenser"))
            return;

        String[] args = title.split(":");

        if (args.length < 2) {
            return;
        }

        int amount;
        int fuse;

        try {
            amount = Integer.parseInt(args[0].trim());
            fuse = Integer.parseInt(args[1].trim());
        } catch (NumberFormatException ex) {
            return;
        }

        if (amount <= 0 || fuse <= 0) {
            return;
        }

        e.setCancelled(true);

        Location loc = e.getBlock().getLocation().clone();
        BlockData blockData = e.getBlock().getBlockData();
        Directional directional = (Directional) blockData;
        BlockFace face = directional.getFacing();
        loc.add(face.getModX() + 0.5, face.getModY(), face.getModZ() + 0.5);

        if (e.getItem().getType() == Material.TNT) {
            for (int i = 0; i < amount; ++i) {
                TNTPrimed tnt = e.getBlock().getWorld().spawn(loc, TNTPrimed.class);
                tnt.setFuseTicks(fuse);
            }
            return;
        }
        if (e.getItem().getType() == Material.SAND || e.getItem().getType() == Material.GRAVEL || e.getItem().getType()== Material.RED_SAND || e.getItem().getType() == Material.WHITE_CONCRETE_POWDER) {
            for (int j = 0; j < amount; ++j) {
                e.getBlock().getWorld().spawnFallingBlock(loc, e.getItem().getType(), (byte) 0);
            }
        }
    }
}
