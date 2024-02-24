package dev.tom.cannoncore.commands;

import com.sk89q.worldedit.blocks.Blocks;
import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.arguments.MagicsandArgument;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.items.MagicsandItem;
import dev.tom.cannoncore.magicsand.Magicsand;
import dev.tom.cannoncore.magicsand.MagicsandManager;
import dev.tom.cannoncore.magicsand.MagicsandType;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.block.Block;

import java.util.List;

public class MagicsandCommand extends CannonCoreCommand {


    public MagicsandCommand() {
        super("magicsand", "ms");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setArguments(new MagicsandArgument())
                .executesPlayer((p, strings) -> {

                    CannonPlayer player = CannonPlayer.getCannonPlayer(p);
                    if(strings[0].equalsIgnoreCase("refill")){
                        MagicsandManager.refillMagicsand(player);
                        return;
                    }

                    if(strings[0].equalsIgnoreCase("clear") ){
                        MagicsandManager.clearMagicsand(player);
                        return;
                    }
                    MagicsandType magicsandType = MagicsandType.getById(strings[0]);
                    if(magicsandType == null) {
                        player.sendMessage("magicsand type not found ?");
                        return;
                    }
                    MagicsandItem magicsandItem = magicsandType.getItem();
                    player.getPlayer().getInventory().addItem(magicsandItem.getItemStack());
                });
    }
}
