package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.arguments.MagicsandArgument;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.items.CannonItemManager;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.objects.CannonPlayer;

public class MagicsandCommand extends CannonCoreCommand {


    public MagicsandCommand() {
        super("magicsand", "ms");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setArguments(new MagicsandArgument())
                .executesPlayer((player, strings) -> {
                    AbstractCannonItem cannonItem = CannonItemManager.getCannonItemMap().get(strings[0]);
                    if(!(cannonItem instanceof Magicsand) && (strings[0].equalsIgnoreCase("refill") || strings[0].equalsIgnoreCase("clear"))){
                        CannonPlayer cannonPlayer = CannonPlayer.getCannonPlayer(player);
                        if(!cannonPlayer.isTrusted()) {
                            cannonPlayer.sendMessage("&cYou are not trusted on this plot.");
                        }
                        switch (strings[0].toLowerCase()){
                            case "refill":
                                // Refill all magicsands
                                return;
                            case "clear":
                                // Clear all magicsands
                                return;
                        }
                    } else {
                        return;
                    }
                    player.getInventory().addItem(cannonItem.getCannonItemStack());
                });
    }
}
