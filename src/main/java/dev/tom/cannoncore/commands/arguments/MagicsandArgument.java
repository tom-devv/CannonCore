package dev.tom.cannoncore.commands.arguments;

import dev.splityosis.commandsystem.SYSArgument;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.magicsand.MagicsandType;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MagicsandArgument extends SYSArgument {

    @Override
    public boolean isValid(String s) {
        return MagicsandType.isMagicsandType(s) || s.equalsIgnoreCase("clear") || s.equalsIgnoreCase("refill") || s.equalsIgnoreCase("test");
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
        return List.of("Invalid magicsand type: " + s.toLowerCase());
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> matchingMagicsand = new ArrayList<>();
        for (MagicsandType value : MagicsandType.values()) {
            if(value.name().toLowerCase().contains(input.toLowerCase())){
                matchingMagicsand.add(value.name().toLowerCase());
            }
        }
        if(input.contains("clear") || input.contains("refill")){
            matchingMagicsand.add(input);
        }
        return matchingMagicsand;
    }
}
