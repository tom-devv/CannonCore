package dev.tom.cannoncore.items;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCannonItem {

    private ItemStack cannonItemStack;
    private final String id;

    /**
     * Links a cannon item to a player for better tracking and readability
     * @param id
     * @param itemStack
     */
    public AbstractCannonItem(String id, ItemStack itemStack){
        this.cannonItemStack = itemStack;
        this.id = id;
    }

    /**
     * Applies identifier NBT data so it can be identified later
     * @param itemStack NBT modified ItemStack
     */
    public void applyNBT(ItemStack itemStack){
        System.out.println("Applying NBT");
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(CannonCore.NBT_IDENTIFIER, id);
        this.cannonItemStack = nbtItem.getItem();
    }
    /**
     * Applies identifier NBT data to this.itemStack so it can be identified later
     */
    public void applyNBT(){
        applyNBT(this.cannonItemStack);
    }

    public void giveItem(Player player){
        player.getInventory().setItemInMainHand(getCannonItemStack());
    }

    /**
     * Checks if the item in hand has NBT matching @param type
     * @param itemStack ItemStack to check
     * @param type The type to check it against
     * @return The type of cannon item that it is
     */
    private boolean resolveItem(ItemStack itemStack, String type){
        String itemType = NBT.get(itemStack, nbt -> nbt.getString(CannonCore.NBT_IDENTIFIER));
        return itemType.equalsIgnoreCase(type);
    }


    public ItemStack getCannonItemStack() {
        return cannonItemStack;
    }

    public String getId() {
        return id;
    }

    public void setCannonItemStack(ItemStack cannonItemStack) {
        this.cannonItemStack = cannonItemStack;
    }
}
