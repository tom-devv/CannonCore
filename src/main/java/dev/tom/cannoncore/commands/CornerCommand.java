package dev.tom.cannoncore.commands;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.arguments.IntegerArgument;
import org.bukkit.Bukkit;

public class CornerCommand extends CannonCoreCommand {

    public CornerCommand() {
        super("corner", "corners");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setArguments(new IntegerArgument())
                .executesPlayer((player, args) -> {
                    PlotPlayer plotPlayer = PlotPlayer.from(player);
                    Plot plot = Plot.getPlot(plotPlayer.getLocation());
                    Location[] corners = plot.getCorners();
                    if (plot.isOwner(plotPlayer.getUUID())) {
                        int _index = Integer.parseInt(args[0]) - 1;
                        Location plotLocation = corners[_index];
                        org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(plotLocation.getWorldName()), plotLocation.getX(), plotLocation.getY(), plotLocation.getZ());
                        player.teleport(location);
                    }
                });
    }
}
