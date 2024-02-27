package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.objects.CannonPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class TNTFillCommand extends CannonCoreCommand {


    public TNTFillCommand(){
        super("tntfill", "tf");
    }

    @Override
    public SYSCommand command() {
        return
                new SYSCommand(getAliases())
                .setConditions(new PlotEditCondition())
                .executesPlayer((player, strings) -> {
                    CannonPlayer cannonPlayer = new CannonPlayer(player);
                    cannonPlayer.fillRadius(new ItemStack(Material.TNT, 64));
                    Util.sendMessage(player, CannonCore.chatMessages.tntfill);
        });

    }


}

