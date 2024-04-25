package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;

public class VoidBlockCommand extends CannonCoreCommand{

    public VoidBlockCommand() {
        super("voidblock", "vb");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .executesPlayer((player, strings) -> {
                    player.getInventory().addItem(CannonCore.featuresConfig.voidblock);
                });
    }
}
