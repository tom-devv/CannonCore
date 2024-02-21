package dev.tom.cannoncore.commands;

import dev.tom.cannoncore.CannonCore;
import org.bukkit.ChatColor;

import java.util.Set;

public class CannonCoreCommands {

    /*
    Change this, I just needed a way to register the plugins, can you try and load them automatically?
     */

    public CannonCoreCommands(){
        Set<Class<? extends CannonCoreCommand>> commandClasses = CannonCore.getReflection().getSubTypesOf(CannonCoreCommand.class);
        for (Class<?> commandClass : commandClasses) {
            try {
                CannonCoreCommand command = (CannonCoreCommand) commandClass.newInstance();
                command.registerCommand();
                System.out.println(ChatColor.RED + "[CannonCore] Registered command: " + command.getName());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
