package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;

public class DiscordCommand extends CannonCoreCommand {

    public DiscordCommand(){
        super("discord");

    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions(new PlotEditCondition())
                        .executesPlayer((player, strings) -> {
                            Util.sendMessage(player, CannonCore.chatMessages.discordmessage);
                        });

    }


}
