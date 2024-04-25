package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;

public class DebugBlockCommand extends CannonCoreCommand{

    public DebugBlockCommand() {
        super("debug", "debugblock", "db");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .executesPlayer((player, strings) -> {
                    player.getInventory().addItem(CannonCore.featuresConfig.debugblock);
                });
    }
}
