package dev.tom.cannoncore.items;

import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.magicsand.MagicsandType;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public class MagicsandItem extends AbstractCannonItem {

    private final MagicsandType type;

    public MagicsandItem(String id) {
        super(id);
        this.type = MagicsandType.getById(id);
        setItemStack(createItemStack());
        applyNBT("magicsand");
        AbstractCannonItem.getCannonItemMap().put(getId(), this);
    }

    @Override
    ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(getType().getInactiveBlock());
        ItemMeta meta = itemStack.getItemMeta();
        TextComponent displayName = Component.text("Magicsand")
        .color(TextColor.lerp(0.5f, TextColor.fromHexString("#FBB20E"), TextColor.fromHexString("#FF6C0F")))
        .append(Component.text(" - " + type.getFriendlyName(), NamedTextColor.GRAY));
        meta.displayName(displayName);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static MagicsandItem getMagicsandItemByItem(ItemStack itemStack){
        String id = AbstractCannonItem.getIdentifier(itemStack, "magicsand");
        AbstractCannonItem cannonItem = AbstractCannonItem.getCannonItemMap().get(id);
        if(cannonItem instanceof MagicsandItem){
            return (MagicsandItem) cannonItem;
        }
        return null;
    }


}
