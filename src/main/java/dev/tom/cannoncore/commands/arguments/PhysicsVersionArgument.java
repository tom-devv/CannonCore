package dev.tom.cannoncore.commands.arguments;

import dev.splityosis.commandsystem.SYSArgument;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.Util;
import me.samsuik.sakura.physics.PhysicsVersion;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhysicsVersionArgument extends SYSArgument {
    @Override
    public boolean isValid(String s) {
        return PhysicsVersion.of(s) != null;
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
        return Collections.singletonList(Util.colorize("Invalid physics version: " + s));
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> matchingVersions = new ArrayList<>();
        for(PhysicsVersion version : PhysicsVersion.values()){
            if(version.getFriendlyName().toLowerCase().startsWith(input.toLowerCase())){
                matchingVersions.add(version.getFriendlyName());
            }
        }
        return matchingVersions;
    }
}
