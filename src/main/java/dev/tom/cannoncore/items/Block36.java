package dev.tom.cannoncore.items;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import dev.tom.cannoncore.CannonCore;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@Getter
public class Block36  {

//    public static List<Block36> block36Locations = new ArrayList<>();
//
//    public Block36(){
//        super("block36",
//                Utils.createItemStack(
//                Material.PLAYER_HEAD,
//                1,
//                "&fBlock36",
//                Arrays.asList("Place this block to simulate a pulsating floor")
//        ));
//        // Applies our custom nbt for this object as it requires skull nbt
//        applyNBT();
//        applyNBT(this.getCannonItemStack());
//    }
//
//
//    // Override to apply skull nbt specific for this item
//    @Override
//    public void applyNBT() {
//        String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.
//
//        NBTItem nbti = new NBTItem(this.getCannonItemStack()); // Creating the wrapper.
//
//        NBTCompound skull = nbti.addCompound("SkullOwner");
//        skull.setString("Name", "Dragonsbreath Opal");
//        skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
//        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
//        texture.setString("Value",  textureValue);
//
//        // Set the global cannonitemstack to this modified version
//        this.setCannonItemStack(nbti.getItem());
//    }
//
//
//
//    public ArmorStand createArmorStand(Location location){
//        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
//        armorStand.setGravity(false);
//        armorStand.setInvulnerable(true);
//        armorStand.setInvisible(true);
//        armorStand.getEquipment().setHelmet(getCannonItemStack());
//
//        return armorStand;
//    }
//
//
//    /**
//     * Reads the NBT identifier of the block and checks if its equal to the one set earlier using applyNBT()
//     * @param itemStack
//     * @return If the itemStack is of type block36
//     */
//    public static boolean isBlock36(ItemStack itemStack){
//        return NBT.get(itemStack, (Function<ReadableItemNBT, String>) nbt -> nbt.getString(CannonCore.NBT_IDENTIFIER)).equals("block36");
//    }


}
