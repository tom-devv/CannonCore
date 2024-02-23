package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.items.Utils;

public class HelpCommand extends CannonCoreCommand {

    public HelpCommand(){
        super("help");

    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions(new PlotEditCondition())
                        .executesPlayer((player, strings) -> {
                            Util.sendMessage(player, CannonCore.chatMessages.helpmessage);
                        });

    }


}
