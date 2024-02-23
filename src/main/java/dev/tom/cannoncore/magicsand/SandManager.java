package dev.tom.cannoncore.magicsand;

import com.plotsquared.core.plot.Plot;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import dev.tom.cannoncore.CannonCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;
import java.util.function.Function;

public class SandManager implements Listener {

    public static List<Material> magicsandSpawnBlocks = new ArrayList<>();
    public static Map<String, Magicsand> magicsands = new HashMap<>();
    public static Map<Plot, HashMap<Location, ActiveMagicsand>> plotMagicSand = new HashMap<>();

    public SandManager(CannonCore plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        registerMagicSand();
        magicsands.forEach((id, magicsand) -> {
            magicsandSpawnBlocks.add(magicsand.getSpawnBlock());
        });
    }

    public void registerMagicSand(){
        // Loop concretes
        for (Material material: Material.values()){
            String name = material.name();
            if(name.contains("CONCRETE_POWDER")){
                String id = material.name().toLowerCase();
                Material baseBlock = Material.valueOf(name.replace("CONCRETE_POWDER", "CONCRETE"));
                magicsands.put(id, new Magicsand(id, baseBlock, material));
            }
        }
        magicsands.put("sand", new Magicsand("sand", Material.SANDSTONE, Material.SAND));
        magicsands.put("red_sand", new Magicsand("red_sand", Material.RED_SANDSTONE, Material.RED_SAND));
        magicsands.put("gravel", new Magicsand("gravel", Material.ANDESITE, Material.GRAVEL));
        magicsands.put("dragon_egg", new Magicsand("dragon_egg", Material.DEEPSLATE_BRICKS, Material.DRAGON_EGG));
        magicsands.put("anvil", new Magicsand("anvil", Material.IRON_BLOCK, Material.ANVIL));
        magicsands.put("chipped_anvil", new Magicsand("chipped_anvil", Material.RAW_IRON_BLOCK, Material.CHIPPED_ANVIL));
    }

    @EventHandler
    public void onMagicsandPlace(BlockPlaceEvent e){
        String id = NBT.get(e.getItemInHand(), (Function<ReadableItemNBT, String>)
                nbt -> nbt.getString(CannonCore.NBT_IDENTIFIER + "_magicsand"));
        if(id == null || id.isEmpty()) return;
        Magicsand sand = magicsands.get(id);
        if(sand == null) return; // sand id not registered?
        Location location = e.getBlock().getLocation();
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(
                location.getWorld().getName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        Plot plot = plotLocation.getPlot();
        if(plot == null){
            e.getPlayer().sendMessage("You can't place magic sand outside of a plot!");
        }

        HashMap<Location, ActiveMagicsand> activeMagicsands = plotMagicSand.get(plot);
        if(activeMagicsands == null){
            activeMagicsands = new HashMap<>();
        }
        ActiveMagicsand activeSand = new ActiveMagicsand(sand, location);
        activeMagicsands.put(location, activeSand);
        plotMagicSand.put(plot, activeMagicsands);
        e.getPlayer().sendMessage("Magic sand placed!");
    }

    @EventHandler
    public void onMagicsandBreak(BlockBreakEvent e){
        Location location = e.getBlock().getLocation();
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(
                location.getWorld().getName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
        Plot plot = plotLocation.getPlot();
        if(plot == null) return;
        HashMap<Location, ActiveMagicsand> activeMagicsands = plotMagicSand.get(plot);
        if(activeMagicsands == null) return;
        ActiveMagicsand activeMagicsand = activeMagicsands.get(location);
        if(activeMagicsand == null) return;
        activeMagicsand.getSpawnTask().cancel(); // stop spawning
        activeMagicsands.remove(location);
        plotMagicSand.put(plot, activeMagicsands);

        clearSandBelow(e.getBlock(), e.getPlayer());
    }

    private void clearSandBelow(Block block, Player player){
        for (int y = block.getY() - 64; y < 320; y++) {
            Location newLoc = new Location(block.getWorld(), block.getX(), y, block.getZ());
            Block newBlock = newLoc.getBlock();
            Material blockType = newBlock.getType();
            if (magicsandSpawnBlocks.contains(blockType)) {
                BlockBreakEvent bbe = new BlockBreakEvent(newBlock, player);
                Bukkit.getServer().getPluginManager().callEvent(bbe);
                if (!bbe.isCancelled()) {
                    newBlock.setType(Material.AIR);
                }
            }
        }
    }
}
