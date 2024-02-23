package dev.tom.cannoncore.magicsand;

import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.items.Utils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

@Getter
public class Magicsand extends AbstractCannonItem {

    @Getter
    public final Material activeBlock = Material.CRYING_OBSIDIAN;
    private final Material baseBlock;
    private final Material spawnBlock;

    public Magicsand(String id, Material baseBlock, Material spawnBlock){
        super(id, Utils.createItemStack(baseBlock, 1, "&6Magic Sand &7[" + id.replace('_', ' ')+ "]" , Arrays.asList("&7Place this block to spawn", "&7a new block every second")));
        this.baseBlock = baseBlock;
        this.spawnBlock = spawnBlock;
        applyNBT();
    }

    @Override
    public void applyNBT() {
        applyNBT(this.getCannonItemStack(), "magicsand");
    }
}
