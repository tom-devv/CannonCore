package dev.tom.cannoncore.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MagicSand extends AbstractCannonItem{

    private Map<MagicSandTypes, ItemStack> magicSandTypes = new HashMap<>();
    private String NBT_TYPE_MODIFIER = "sand_type";

    public MagicSand() {
        // Create the default sand item
        super("magicsand",
                Utils.createItemStack(
                        Material.SAND,
                        1,
                        "&eSand",
                        Arrays.asList("Place this spawn falling blocks")
                ));
        applyNBT();
        magicSandTypes.put(MagicSandTypes.DEFAULT, getCannonItemStack());
    }

    // Register and create then other sand types
    private void createTypes(){

    }

    @Override
    public void giveItem(Player player, String type) {
        super.giveItem(player);
    }
}


