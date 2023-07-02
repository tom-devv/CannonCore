package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.items.Block36;
import dev.tom.cannoncore.items.CannonItemManager;
import dev.tom.cannoncore.objects.CannonPlayer;

public class Block36Command extends CannonCoreCommand{


    private final String[] aliases;

    public Block36Command(String... aliases){
        super(aliases);
        this.aliases = aliases;
    }

    @Override
    public SYSCommand command() {
        return
        new SYSCommand(aliases)
                .executesPlayer((player, strings) -> {
                    Block36 block36Item = (Block36) CannonItemManager.getCannonItemMap().get("block36");
                    block36Item.giveItem(player);
                });
    }
}
