package dev.tom.cannoncore.commands;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class TNTFlowCommand extends CannonCoreCommand implements Listener {

    public static Set<Plot> flowPlots = new HashSet<>();


    public TNTFlowCommand() {
        super("flow", "waterspread", "wspread");
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions(new PlotEditCondition())
                        .executesPlayer((sender, strings) -> {
                            CannonPlayer player = CannonPlayer.getCannonPlayer(sender);
                            Plot plot = player.getCurrentPlot();
                            if(flowPlots.contains(player.getCurrentPlot())){
                                flowPlots.remove(plot);
                                player.sendMessage("&6TNT flow has been disabled for this plot");
                            } else {
                                flowPlots.add(plot);
                                player.sendMessage("&6TNT flow has been enabled for this plot");
                            }
                        });
    }

    @EventHandler
    public void onTNTSpawn(EntitySpawnEvent e){
        if(!(e.getEntity() instanceof TNTPrimed)) return;
        TNTPrimed tnt = (TNTPrimed) e.getEntity();
        Location location = Location.at(tnt.getLocation().getWorld().getName(), tnt.getLocation().getBlockX(), tnt.getLocation().getBlockY(), tnt.getLocation().getBlockZ());
        if(flowPlots.contains(Plot.getPlot(location))){
            tnt.setPushedByFluid(true);
        } else {
            tnt.setPushedByFluid(false);
        }

    }



}

