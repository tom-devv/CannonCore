package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.SYSCommandBranch;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.commands.conditions.PlotCondition;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TNTFillCommand extends CannonCoreCommand {

    private final String[] aliases;

    public TNTFillCommand(String... aliases){
        super(aliases);
        this.aliases = aliases;
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(aliases)
                .setConditions(new PlotCondition())
                .executesPlayer((player, strings) -> {
                fillRadius(player);
        });

    }

    private void fillRadius(Player player){
        for (int i = 0; i < getFeaturesConfig().maxRadius; i++) {

        }
    }
}
