package dev.tom.cannoncore.commands.conditions;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCondition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlotCondition extends SYSCondition {
    @Override
    public boolean isValid(CommandSender commandSender) {
        if(!(commandSender instanceof Player)) return false;
        PlotPlayer<Player> player = PlotPlayer.from((Player) commandSender);
        Plot plot = player.getCurrentPlot();
        return plot.getTrusted().contains(player.getUUID());
    }

    @Override
    public List<String> getConditionNotMetMessage(CommandSender commandSender) {
        return Arrays.asList("You are not trusted on this plot");
    }
}
