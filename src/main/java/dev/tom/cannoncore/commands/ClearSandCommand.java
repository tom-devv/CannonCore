package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;

public class ClearSandCommand extends CannonCoreCommand {

    public ClearSandCommand() {
        super("clearsand", "cs");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
            .executesPlayer((player, strings) -> {
                player.performCommand("//replacenear 50 sand,red_sand,gravel,white_concrete_powder,orange_concrete_powder,magenta_concrete_powder,light_blue_concrete_powder,yellow_concrete_powder,lime_concrete_powder,pink_concrete_powder,gray_concrete_powder,light_gray_concrete_powder,cyan_concrete_powder,purple_concrete_powder,blue_concrete_powder,brown_concrete_powder,green_concrete_powder,red_concrete_powder,black_concrete_powder air");
            });
    }
}
