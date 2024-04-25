package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BudCheckCommand extends CannonCoreCommand{


    public static HashMap<Player, Location> budcheck = new HashMap<>();



    int radius = CannonCore.featuresConfig.bradius;

    public BudCheckCommand(){
        super("budcheck", "bc");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
            .executesPlayer((player, strings) -> {


                if(budcheck.containsKey(player)){
                    resetBlocks(budcheck.get(player), player, radius);
                    return;
                }


                budcheck.put(player, player.getLocation());
                World world = player.getWorld();
                Location l = player.getLocation();
                for (int x = l.getBlockX() - radius; x <= l.getBlockX() + radius; x++) {
                    for (int y = l.getBlockY() - radius; y <= l.getBlockY() + radius; y++) {
                        for (int z = l.getBlockZ() - radius; z <= l.getBlockZ() + radius; z++) {
                            Block block = world.getBlockAt(x, y, z);
                            if (block.getType().equals(Material.DISPENSER)) {
                                Location loc = block.getLocation();
                                int tnt = getNumberOfTnt(block);
                                int tnt2 = 0;
                                for (int x1 = -1; x1 < 2; x1++) {
                                    for (int y1 = -1; y1 < 2; y1++) {
                                        for (int z1 = -1; z1 < 2; z1++) {
                                            Block checkBlock = world.getBlockAt(loc.getBlockX() + x1, loc.getBlockY() + y1, loc.getBlockZ() + z1);
                                            if (checkBlock.getType().equals(Material.DISPENSER)) {
                                                tnt2 = getNumberOfTnt(checkBlock);
                                            }
                                        }
                                    }
                                }
                                if (tnt > tnt2) {
                                    Material budded = Material.getMaterial(String.valueOf(Material.RED_STAINED_GLASS));
                                    player.sendBlockChange(block.getLocation(), budded.createBlockData());
                                } else {
                                    Material notbudded = Material.getMaterial(String.valueOf(Material.GREEN_STAINED_GLASS));
                                    player.sendBlockChange(block.getLocation(), notbudded.createBlockData());
                                }

                            }
                        }
                    }
                }

                Util.sendMessage(player, "");

            });

    }

    private static int getNumberOfTnt(Block block) {
        Dispenser dispenser = (Dispenser) block.getState();
        Inventory dInv = dispenser.getInventory();
        int number = 0;
        for (int inv = 0; inv < 9; inv++) {
            if (dInv.getItem(inv) != null && dInv.getItem(inv).getType().equals(Material.TNT)) {
                ItemStack tnt = dInv.getItem(inv);
                number = tnt.getAmount() + number;
            }
        }
        return number;
    }
    private void resetBlocks(Location prevLocation, Player player, int r){
        budcheck.remove(player);
        World world = player.getWorld();
        for (int x = prevLocation.getBlockX() - r; x <= prevLocation.getBlockX() + r; x++) {
            for (int y = prevLocation.getBlockY() - r; y <= prevLocation.getBlockY() + r; y++) {
                for (int z = prevLocation.getBlockZ() - r; z <= prevLocation.getBlockZ() + r; z++) {
                    world.getBlockAt(x, y, z).getState().update(true);
                }
            }
        }
        Util.sendMessage(player, "reset");
    }

}
