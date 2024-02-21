package dev.tom.cannoncore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Arrays;
import java.util.List;

public class WaterproofRedstone implements Listener {
    private final List<Material> waterproofBlocks = Arrays.asList(
            Material.REDSTONE_WIRE,
            Material.REDSTONE_TORCH,
            Material.REDSTONE_WALL_TORCH,
            Material.LEVER,
            Material.REPEATER,
            Material.COMPARATOR
    );

    private final List<Material> buttons1 = Arrays.asList(
            Material.OAK_BUTTON

    );

    private final List<Material> buttons2 = Arrays.asList(
            Material.STONE_BUTTON
    );

    public WaterproofRedstone() {}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFromTo(BlockFromToEvent event) {
        Material material = event.getToBlock().getType();
        if (waterproofBlocks.contains(material) || buttons1.contains(material) || buttons2.contains(material)) {
            event.setCancelled(true);
        }
    }
}
