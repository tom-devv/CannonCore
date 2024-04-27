package dev.tom.cannoncore.commands;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.arguments.IntegerArgument;
import dev.tom.cannoncore.commands.arguments.MaterialArgument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class WallsCommand extends CannonCoreCommand {

    @Override
    public SYSCommand command() {
        return new SYSCommand("walls")
                .setArguments(new IntegerArgument(), new IntegerArgument(), new MaterialArgument())
                .executesPlayer((player, strings) -> {
                    int width = Integer.parseInt(strings[0]);
                    int height = Integer.parseInt(strings[1]);
                    Material material = Material.valueOf(strings[2].toUpperCase());

                    int y = Math.min(player.getLocation().getBlockY(), 320);


                });
    }
}
