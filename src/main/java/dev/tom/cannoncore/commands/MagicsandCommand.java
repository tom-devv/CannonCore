package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.arguments.MagicsandArgument;
import dev.tom.cannoncore.items.MagicsandItem;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import dev.tom.cannoncore.magicsand.MagicsandType;
import dev.tom.cannoncore.objects.CannonPlayer;

public class MagicsandCommand extends CannonCoreCommand {


    public MagicsandCommand() {
        super("magicsand", "ms");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setArguments(new MagicsandArgument())
                .executesPlayer((p, strings) -> {

                    CannonPlayer player = CannonPlayer.getCannonPlayer(p);
                    if(strings[0].equalsIgnoreCase("clearall")){
                        player.sendMessage(String.valueOf(MagicsandManager.activePlotMagicSands.get(player.getCurrentPlot()).size()));
                        return;
                    }
                    if(strings[0].equalsIgnoreCase("refill")){
                        MagicsandManager.refillMagicsand(player);
                        return;
                    }

                    if(strings[0].equalsIgnoreCase("clear") ){
                        MagicsandManager.clearMagicsand(player);
                        return;
                    }
                    MagicsandType magicsandType = MagicsandType.getById(strings[0]);
                    if(magicsandType == null) {
                        player.sendMessage("magicsand type not found ?");
                        return;
                    }
                    MagicsandItem magicsandItem = magicsandType.getItem();
                    player.getPlayer().getInventory().addItem(magicsandItem.getItemStack());
                });
    }
}
