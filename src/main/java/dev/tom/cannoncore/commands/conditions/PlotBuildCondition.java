package dev.tom.cannoncore.commands.conditions;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCondition;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.List;

public class PlotBuildCondition extends SYSCondition {
    @Override
    public boolean isValid(CommandSender commandSender) {
        if(!(commandSender instanceof Player)) return false;
        CannonPlayer cannonPlayer = new CannonPlayer((Player) commandSender);
        return true; //TODO FIX THIS
    }

    @Override
    public List<String> getConditionNotMetMessage(CommandSender commandSender) {
        return Arrays.asList("You are not trusted on this plot");
    }
}
