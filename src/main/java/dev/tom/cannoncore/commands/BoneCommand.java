package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BoneCommand extends CannonCoreCommand {

    public BoneCommand() {
        super("bone", "b");
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .executes((commandSender, strings) -> {
                            Player player = (Player) commandSender;
                            ItemStack bone = new ItemStack(Material.BONE, 1);
                            player.getInventory().addItem(bone);
                            Util.sendMessage(player, CannonCore.chatMessages.bonereceive);
                        });
    }
}