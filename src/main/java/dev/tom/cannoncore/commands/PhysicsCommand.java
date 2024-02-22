package dev.tom.cannoncore.commands;

import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.arguments.PhysicsVersionArgument;
import dev.tom.cannoncore.objects.CannonPlayer;
import me.samsuik.sakura.physics.PhysicsVersion;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashMap;
import java.util.Map;

public class PhysicsCommand extends CannonCoreCommand implements Listener {
    
    public static Map<Plot, PhysicsVersion> plotPhysics = new HashMap<>();

    public PhysicsCommand() {
        super("physics", "phys");
        for (Plot aPlot : CannonCore.getPlotAPI().getAllPlots()) {
            plotPhysics.put(aPlot, PhysicsVersion.LATEST);
        }
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setArguments(new PhysicsVersionArgument())
                .executesPlayer((player, strings) -> {
                    CannonPlayer cannonPlayer = CannonPlayer.getCannonPlayer(player);
                    Plot plot = cannonPlayer.getCurrentPlot();
                    plotPhysics.put(plot, PhysicsVersion.of(strings[0]));
                });
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        Plot plot = Plot.getPlot(
                Location.at(
                        entity.getLocation().getWorld().getName(),
                        entity.getLocation().getBlockX(),
                        entity.getLocation().getBlockY(),
                        entity.getLocation().getBlockZ())
        );
        e.getEntity().setPhysicsVersion(plot == null ? PhysicsVersion.LATEST : plotPhysics.get(plot));
    }


}
