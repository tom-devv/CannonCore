package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.config.FeaturesConfig;
import dev.tom.cannoncore.objects.CannonPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;


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
                .setConditions(new PlotEditCondition())
                .executesPlayer((player, strings) -> {
                fillRadius(player, new ItemStack(Material.TNT, 64));
        });

    }

    /**
     *
     * @param player
     * @param itemStack
     *
     * Fills dispensers with an itemstack in a radius
     */

    public static void fillRadius(Player player, ItemStack itemStack){
        CannonPlayer cannonPlayer = new CannonPlayer(player);
        List<Block> dispensers = cannonPlayer.getNearbyBlocks(FeaturesConfig.getMaxRadius())
                .stream()
                .filter(b -> b.getType().equals(Material.DISPENSER))
                .collect(Collectors.toList());

        for (Block block : dispensers) {
            Dispenser dispenser = (Dispenser) block.getState();
            Inventory inventory = dispenser.getInventory();
            for (int j = 0; j < 9; j++) {
                inventory.setItem(j, itemStack);
            }
        }
    }
}
