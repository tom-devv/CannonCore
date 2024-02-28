package dev.tom.cannoncore.events;

import dev.tom.cannoncore.listeners.NodeListeners;
import dev.tom.cannoncore.objects.RedstoneNode;
import dev.tom.cannoncore.objects.RedstoneNodeList;
import lombok.Getter;
import org.antlr.v4.runtime.misc.Triple;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class RedstoneNodeUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();



    private final Block block;
    private final Long tick;

    public RedstoneNodeUpdateEvent(Block block, Long tick){
        this.block = block;
        this.tick = tick;
        RedstoneNode.nodeActivations.put(block, tick);

        RedstoneNode.selectedNodes.forEach((uuid, nodeList) -> {
            sendUpdates(uuid);
        });
    }


    private void sendUpdates(Player player){
        UUID uuid = player.getUniqueId();
        if(!RedstoneNode.selectedNodes.containsKey(uuid)) return; // Should always be true
        RedstoneNodeList nodeList = RedstoneNode.selectedNodes.get(uuid);

        RedstoneNode currentActivation = nodeList.getByBlock(getBlock());
        // The activated block is NOT being tracked by this UUID
        if(currentActivation == null) return;

        RedstoneNode first = nodeList.getFirstActivation();
        int difference = 0;
        int index = currentActivation.getIndex();
        if(first != null) {
            difference = (int) (getTick() - first.getLastActivation());
        }
        player.sendMessage("Node: " + index + "|" + difference * 2  + " RST |" + difference + "GT |" + difference / 20 + " SEC");
    }

    private void sendUpdates(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;
        sendUpdates(player);
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
