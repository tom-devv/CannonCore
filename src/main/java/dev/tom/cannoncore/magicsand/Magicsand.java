package dev.tom.cannoncore.magicsand;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Magicsand {

    public static Material activeBlock = Material.CRYING_OBSIDIAN;
    private final Material baseBlock;
    private final Material spawnBlock;

    public Magicsand(Material baseBlock, Material spawnBlock){
        this.baseBlock = baseBlock;
        this.spawnBlock = spawnBlock;
    }




}
