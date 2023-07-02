package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.items.CannonItemManager;
import dev.tom.cannoncore.items.MagicSand;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MagicsandCommand extends CannonCoreCommand {


    public MagicsandCommand(String... names) {
        super(names);
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                        .setConditions()
                        .executesPlayer((player, strings) -> {
                            MagicSand magicSand = (MagicSand) CannonItemManager.getCannonItemMap().get("magicsand");
                            magicSand.giveItem(player);
                        });

    }
}
