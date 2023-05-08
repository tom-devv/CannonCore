package dev.tom.cannoncore.items;

import de.tr7zw.nbtapi.*;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public class Block36Item {

    public static List<Block36Item> block36Locations = new ArrayList<>();
    private final Player player;
    private final ItemStack block36Item;

    public Block36Item(Player player){
        this.player = player;
        this.block36Item = createItem();
    }

    private ItemStack createItem(){
        ItemStack head = Manager.createItemStack(
                Material.PLAYER_HEAD,
                1,
                "&fBlock36",
                Arrays.asList("Place this block to simulate a pulsating floor")
        );

        String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.

        NBTItem nbti = new NBTItem(head); // Creating the wrapper.

        nbti.setString("block36", "true"); // Check later on block place
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", "Dragonsbreath Opal");
        skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value",  textureValue);

        head = nbti.getItem(); // Refresh the ItemStack
        return head;
    }

    public ArmorStand createArmorStand(Location location){
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.getEquipment().setHelmet(getBlock36Item());

        return armorStand;
    }


    public void giveItem(){
        getPlayer().getInventory().setItemInMainHand(getBlock36Item());
    }

    public static boolean isBlock36(ItemStack itemStack){
        return NBT.get(itemStack, readableNBT -> Boolean.parseBoolean(readableNBT.getString("block36"))); // Reads "block36" NBT tag
    }


}
