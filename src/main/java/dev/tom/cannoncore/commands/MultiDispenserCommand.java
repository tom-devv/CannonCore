package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class MultiDispenserCommand extends CannonCoreCommand{

    public MultiDispenserCommand() {
        super("multidispenser", "md", "multi");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .executes((commandSender, args) -> {

                    Player p = (Player) commandSender;
                    if (args.length < 1) {
                        p.sendMessage("&c&lERROR&8 >> &cInvalid Input! Use /Multi [Amount] [Fuse]");
                        return;
                    }

                    int amount = 1;
                    int fuse = 80;

                    try {
                        amount = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        p.sendMessage("&c&lERROR&8 >> &cInvalid Input! Use /Multi [Amount] [Fuse]");
                        return;
                    }

                    if (args.length > 1) {
                        try {
                            fuse = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            p.sendMessage("&c&lERROR&8 >> &cInvalid Input! Use /multi [Amount] [Fuse]");
                            return;
                        }
                    }

                    if (amount > 2000) {
                        p.sendMessage("&c&lERROR&8 >> Maximum Amount Is 2000");
                        amount = 2000;
                    }

                    String itemName = amount + " : " + fuse;
                    ItemStack item = new ItemStack(Material.DISPENSER, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));
                    meta.addEnchant(Enchantment.DURABILITY, 10, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(meta);
                    p.getInventory().setItemInMainHand(item);
                    p.sendMessage("&c&l[!] &f MultiTnt Given");

       });
    }
}


