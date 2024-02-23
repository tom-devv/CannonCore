package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Lever;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeverCommand extends CannonCoreCommand implements Listener {

    public static Map<UUID, Block> levers = new HashMap<>();

    public LeverCommand(){
        super("lever", "l");
        CannonCore.getCore().getServer().getPluginManager().registerEvents(this, CannonCore.getCore());
    }

    @Override
    public SYSCommand command() {
        return new SYSCommand(getAliases()
        )
                .executesPlayer((player, strings) -> {
                            Block lever = levers.get(player.getUniqueId());
                            if (lever == null || !lever.getType().equals(Material.LEVER)) {
                                player.sendMessage("You have no lever selected!");
                                return;
                            }

                    Switch data = (Switch) lever.getBlockData();
                    data.setPowered(!data.isPowered());
                });
    }

    @EventHandler
    public void onButtonPress(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            Player player = e.getPlayer();
            UUID uuid = e.getPlayer().getUniqueId();
            if(block.getType() != Material.LEVER) return;
            levers.put(uuid, block);
            player.sendMessage("Lever selected @ + X:" + block.getX() + " Y:" + block.getY() + " Z:" + block.getZ());

        };
    }

}
