package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.Util;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UnfillCommand extends CannonCoreCommand{


    public UnfillCommand(){
        super("unfill", "uf", "tntunfill", "tfunfill");
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions(new PlotEditCondition())
                        .executesPlayer((player, strings) -> {
                            CannonPlayer cannonPlayer = new CannonPlayer(player);
                            cannonPlayer.fillRadius(new ItemStack(Material.AIR, 1));
                            Util.sendMessage(player, CannonCore.chatMessages.unfill);
                        });

    }
}
