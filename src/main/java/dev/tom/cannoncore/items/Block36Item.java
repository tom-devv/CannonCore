package dev.tom.cannoncore.items;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;
import de.tr7zw.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block36Item {

    public static List<Block36Item> block36Locations = new ArrayList<>();

    public Block36Item(){

    }

    public void giverItem(Player player){
        ItemStack head = Manager.createItemStack(Material.PLAYER_HEAD, 1, "&fBlock36", Arrays.asList("my lore", "ez"));

        String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.

        NBTItem nbti = new NBTItem(head); // Creating the wrapper.

        NBTCompound disp = nbti.addCompound("display");
        disp.setString("Name", "Testitem"); // Setting the name of the Item

        NBTList l = disp.getStringList("Lore");
        l.add("Some lore"); // Adding a bit of lore.


        nbti.setString("block36", "true"); // Check later on block place

        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", "Dragonsbreath Opal");
        skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");

        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value",  textureValue);

        head = nbti.getItem(); // Refresh the ItemStack

        player.getInventory().addItem(head);

    }


}
