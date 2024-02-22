package dev.tom.cannoncore.commands;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashSet;
import java.util.Set;

public class ParityCommand extends CannonCoreCommand implements Listener {

    public static Set<Plot> parityPlots = new HashSet<>();

    public ParityCommand() {
        super("parity");
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setConditions(new PlotEditCondition())
                .executesPlayer((sender, strings) -> {
                    CannonPlayer player = CannonPlayer.getCannonPlayer(sender);
                    Plot plot = player.getCurrentPlot();
                    if(parityPlots.contains(player.getCurrentPlot())){
                        parityPlots.remove(plot);
                        player.sendMessage("&6Parity has been disabled for this plot");
                    } else {
                        parityPlots.add(plot);
                        player.sendMessage("&6Parity has been enabled for this plot");
                    }
                });
    }

    @EventHandler
    public void onFallingBlockSpawn(EntitySpawnEvent e){
        if(!(e.getEntity() instanceof FallingBlock)) return;
        FallingBlock fallingBlock = (FallingBlock) e.getEntity();
        Location location = Location.at(fallingBlock.getLocation().getWorld().getName(), fallingBlock.getLocation().getBlockX(), fallingBlock.getLocation().getBlockY(), fallingBlock.getLocation().getBlockZ());
        Plot plot = location.getPlot();
        if(plot == null) return;
        fallingBlock.setHeightParity(parityPlots.contains(plot));
    }


}
