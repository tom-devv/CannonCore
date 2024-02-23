package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;

public class TNTFillFireCommand extends CannonCoreCommand {

    public TNTFillFireCommand(){
        super("tntfillfire", "tfire", "tff");

    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions(new PlotEditCondition())
                        .executesPlayer((player, strings) -> {
                            player.performCommand("tntfill");
                            player.performCommand("fire");
                        });

    }


}
