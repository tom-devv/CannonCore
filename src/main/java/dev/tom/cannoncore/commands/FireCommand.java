package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FireCommand extends CannonCoreCommand implements Listener {

    public static Map<UUID, Block> buttons = new HashMap<>();

    public FireCommand(){
        super("fire", "f");
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases())
                .setPermission("cannoncore.fire")
                .executesPlayer((player, strings) -> {
                    Block block = buttons.get(player.getUniqueId());
                    if(block == null) {
                        player.sendMessage("You have not selected a button yet!");
                        return;
                        
                    } 

                    {
                        Block button = block;
                        Switch data = (Switch) button.getBlockData();
                        data.setPowered(true);
                        button.setBlockData(data);
                    }

                    Bukkit.getScheduler().runTaskLater(CannonCore.getCore(), () -> {
                        Block button = block;
                        Switch data = (Switch) button.getBlockData();
                        data.setPowered(false);
                        button.setBlockData(data);
                    }, (long) (20 * 0.8) );
                });
    }

    @EventHandler
    public void onButtonPress(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            if(block == null) return;
            Player player = e.getPlayer();
            UUID uuid = e.getPlayer().getUniqueId();
            if(block.getType() != Material.STONE_BUTTON && block.getType() != Material.OAK_BUTTON) return;
            buttons.put(uuid, block);
            player.sendMessage("Button selected @ + X:" + block.getX() + " Y:" + block.getY() + " Z:" + block.getZ());


        };
    }

}
