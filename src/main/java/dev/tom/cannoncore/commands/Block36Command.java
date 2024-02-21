package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.items.Block36;
import dev.tom.cannoncore.items.CannonItemManager;
import dev.tom.cannoncore.objects.CannonPlayer;

public class Block36Command extends CannonCoreCommand{



    public Block36Command(){
        super("block36");
    }

    @Override
    public SYSCommand command() {
        return
        new SYSCommand(getAliases())
                .executesPlayer((player, strings) -> {
                    Block36 block36Item = (Block36) CannonItemManager.getCannonItemMap().get("block36");
                    block36Item.giveItem(player);
                });
    }
}
