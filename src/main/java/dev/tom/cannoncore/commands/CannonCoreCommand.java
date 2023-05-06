package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class CannonCoreCommand {

    private final String name;
    private final String permission;
    private final SYSCommand command;


    public CannonCoreCommand(SYSCommand command, String... names) {
        this.name = names[0];
        this.command = command;
        this.permission = command.getPermission();
    }

    public abstract SYSCommand command();
}
