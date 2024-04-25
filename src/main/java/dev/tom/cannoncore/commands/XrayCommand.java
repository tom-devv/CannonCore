package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class XrayCommand extends CannonCoreCommand{

    public static HashMap<Player, Location> xray = new HashMap<>();

    int r = CannonCore.featuresConfig.xradius;


    public XrayCommand(){
        super("xray");
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .executesPlayer((player, strings) -> {

                    if (xray.containsKey(player)) {
                        resetBlocks(xray.get(player), player, r);
                        return;
                    }

                    xray.put(player, player.getLocation());

                    World world = player.getWorld();
                    Location l = player.getLocation();
                    Material mat = Material.getMaterial(String.valueOf(Material.BARRIER));

                    for (int x = l.getBlockX() - r; x <= l.getBlockX() + r; x++) {
                        for (int y = l.getBlockY() - r; y <= l.getBlockY() + r; y++) {
                            for (int z = l.getBlockZ() - r; z <= l.getBlockZ() + r; z++) {
                                Block block = world.getBlockAt(x, y, z);
                                if(!(block.getType().equals(Material.AIR)) && !(block.getType().equals(Material.BEDROCK))){
                                    if(block.getType().equals(Material.DISPENSER) || block.getType().equals(Material.OBSIDIAN) || block.getType().equals(Material.STONE)){
                                        player.sendBlockChange(block.getLocation(), mat.createBlockData());
                                    }
                                }
                            }
                        }
                    }
                    Util.sendMessage(player, "xray enabled");

                });
    }

    private void resetBlocks(Location prevLocation, Player player, int r){
        xray.remove(player);
        World world = player.getWorld();
        for (int x = prevLocation.getBlockX() - r; x <= prevLocation.getBlockX() + r; x++) {
            for (int y = prevLocation.getBlockY() - r; y <= prevLocation.getBlockY() + r; y++) {
                for (int z = prevLocation.getBlockZ() - r; z <= prevLocation.getBlockZ() + r; z++) {
                    world.getBlockAt(x, y, z).getState().update(true);
                }
            }
        }
        Util.sendMessage(player, "xray disabled");
    }
}
