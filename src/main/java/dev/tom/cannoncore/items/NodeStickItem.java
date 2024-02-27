package dev.tom.cannoncore.items;

import dev.tom.cannoncore.magicsand.MagicsandType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NodeStickItem extends AbstractCannonItem {

    public NodeStickItem() {
        super("nodestick");
        setItemStack(createItemStack());
        applyNBT("node");
        AbstractCannonItem.getCannonItemMap().put(getId(), this);}

    @Override
    ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta meta = itemStack.getItemMeta();
        TextComponent displayName = Component.text("Node Stick")
                .color(TextColor.lerp(0.5f, TextColor.fromHexString("#FBB20E"), TextColor.fromHexString("#FF6C0F")));
        meta.displayName(displayName);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;


    }
}
