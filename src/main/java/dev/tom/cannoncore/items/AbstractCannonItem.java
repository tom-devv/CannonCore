package dev.tom.cannoncore.items;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;

import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import dev.tom.cannoncore.CannonCore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
public abstract class AbstractCannonItem {

    @Getter
    private static Map<String, AbstractCannonItem> cannonItemMap = new HashMap<>();

    @Setter
    private ItemStack itemStack;
    private final String id;


    public AbstractCannonItem(String id){
        this.id = id;
    }

    /**
     * Applies identifier NBT data so it can be identified later
     * @param itemStack NBT modified ItemStack
     */
    public void applyNBT(String prefix){
        NBTItem nbtItem = new NBTItem(getItemStack());
        nbtItem.setString(CannonCore.NBT_IDENTIFIER + ":" + prefix, id);
        this.itemStack = nbtItem.getItem();
    }

    public static String getIdentifier(ItemStack itemStack, String prefix){
        return NBT.get(itemStack, (Function<ReadableItemNBT, Object>) nbt -> nbt.getString(CannonCore.NBT_IDENTIFIER + ":" + prefix)).toString();
    }

    public void giveItem(Player player){
        player.getInventory().setItemInMainHand(getItemStack());
    }
    abstract ItemStack createItemStack();



}
