package dev.tom.cannoncore.commands.arguments;

import dev.splityosis.commandsystem.SYSArgument;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.magicsand.MagicsandType;
import org.bukkit.Material;

import java.util.List;

public class MaterialArgument extends SYSArgument {

    @Override
    public boolean isValid(String s) {
        try {
            Material.valueOf(s.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
        return List.of("Invalid material: " + s.toUpperCase());
    }

}
