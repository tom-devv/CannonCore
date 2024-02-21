package dev.tom.cannoncore.magicsand;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SandManager {

    public static List<Magicsand> magicsands = new ArrayList<>();

    public static void registerMagicSand(){
        // Loop concretes
        for (Material material: Material.values()){
            String name = material.name();
            if(name.contains("CONCRETE")){
                Material spawnBlock = Material.valueOf(name.replace("CONCRETE", "CONCRETE_POWDER"));
                magicsands.add(new Magicsand(material, spawnBlock));
            }
        }
        magicsands.add(new Magicsand(Material.SANDSTONE, Material.SAND));
        magicsands.add(new Magicsand(Material.RED_SANDSTONE, Material.RED_SAND));
        magicsands.add(new Magicsand(Material.ANDESITE, Material.GRAVEL));
        magicsands.add(new Magicsand(Material.DEEPSLATE_BRICKS, Material.DRAGON_EGG));
        magicsands.add(new Magicsand(Material.IRON_BLOCK, Material.ANVIL));
        magicsands.add(new Magicsand(Material.RAW_IRON_BLOCK, Material.CHIPPED_ANVIL));
    }
}
