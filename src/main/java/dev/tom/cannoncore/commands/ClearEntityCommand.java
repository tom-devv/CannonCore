package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import org.bukkit.entity.*;

public class ClearEntityCommand extends CannonCoreCommand {

    public ClearEntityCommand() {
        super("clearentity", "ce");
    }

    @Override
    public SYSCommand command() {
        return

                new SYSCommand(getAliases())
                        .executes((commandSender, strings) -> {
                            Player player = (Player) commandSender;
                            int r = 50;
                            int tnt = 0;
                            int falling = 0;
                            int item = 0;
                            for (Entity entity : player.getNearbyEntities(r, r, r)) {
                                if (entity instanceof TNTPrimed) {
                                    entity.remove();
                                    tnt++;
                                } else if (entity instanceof FallingBlock) {
                                    entity.remove();
                                    falling++;
                                } else if (entity instanceof Item) {
                                    entity.remove();
                                    item++;
                                }
                            }
                            if (tnt + falling + item < 1) {
                                player.sendMessage("§c§lCannoning§f§lMC §8» §fNo Entities Found");
                            } else {
                                player.sendMessage("§c§lCannoning§f§lMC §8» §fEntities Cleared");
                                if (tnt > 0) player.sendMessage("§cTNT:§f§l " + tnt);
                                if (falling > 0) player.sendMessage("§cSand:§f§l " + falling);
                                if (item > 0) player.sendMessage("§cItems:§f§l " + item);
                            };
                        });
    }

}


