package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.SYSCommandBranch;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.conditions.PlotCondition;

public class TNTFillCommand extends CannonCoreCommand {
    

    public TNTFillCommand(String... names){

        super(
                new SYSCommand(names).setConditions(new PlotCondition())
        );
    }

    @Override
    public SYSCommand command() {
        SYSCommand command = new SYSCommand(getNames).setConditions(new PlotCondition())

    }
}
