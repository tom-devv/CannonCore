package dev.tom.cannoncore.commands;

import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class WildCommand extends CannonCoreCommand {

    public WildCommand() {
        super("wild");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setPermission("wild.use")
                .executesPlayer((player, strings) -> {
                    PlotPlayer plotPlayer = PlotPlayer.from(player);
                    Plot plot = Plot.getPlot(plotPlayer.getLocation());
                    if (plot.isOwner(plotPlayer.getUUID())) {
                        Util.sendMessage(player, CannonCore.chatMessages.notinplot);
                        return;
                    }
                    Random random = new Random();
                    int x = random.nextInt(10000);
                    int z = random.nextInt(10000);
                    World world = player.getWorld();
                    Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
                    player.teleport(location);
                    Util.sendMessage(player, "You have been teleported to " + Util.locationToString(location));
                });
    }
}
