package dev.tom.cannoncore.events;

import dev.tom.cannoncore.objects.RedstoneNode;
import lombok.Getter;
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

//    private final RedstoneNode node;

    public RedstoneNodeUpdateEvent(Block block){
        Set<RedstoneNode> updatedNodes = RedstoneNode.trackedNodes.get(block);
        if(updatedNodes == null) return;
        for (RedstoneNode updatedNode : updatedNodes) {
            updatedNode.onTick();
        }
    }



    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
