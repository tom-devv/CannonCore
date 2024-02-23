package dev.tom.cannoncore.commands.arguments;

import dev.splityosis.commandsystem.SYSArgument;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.magicsand.SandManager;
import me.samsuik.sakura.physics.PhysicsVersion;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MagicsandArgument extends SYSArgument {

    @Override
    public boolean isValid(String s) {
        return SandManager.magicsands.containsKey(s.toLowerCase()) || s.equalsIgnoreCase("clear") || s.equalsIgnoreCase("refill") || s.isEmpty();
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
        return List.of("Invalid magicsand type: " + s.toLowerCase());
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> matchingMagicsand = new ArrayList<>();
        for (String id : SandManager.magicsands.keySet()) {
            if(id.toUpperCase().contains(input.toUpperCase())){
                matchingMagicsand.add(id);
            }
        }
        if(input.contains("clear") || input.contains("refill")){
            matchingMagicsand.add(input);
        }
        return matchingMagicsand;
    }
}
