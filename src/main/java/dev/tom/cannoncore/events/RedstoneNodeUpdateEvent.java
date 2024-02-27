package dev.tom.cannoncore.events;

import dev.tom.cannoncore.listeners.NodeListeners;
import lombok.Getter;
import org.antlr.v4.runtime.misc.Triple;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class RedstoneNodeUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static Map<UUID, List<Block>> selectedNodes = new HashMap<>();
    public static Map<UUID, Integer> firstNodeActivationIndex = new HashMap<>();
    public static Map<Block, Long> nodeActivations = new HashMap<>();

    private final Block block;
    private final Long tick;

    public RedstoneNodeUpdateEvent(Block block, Long tick){
        this.block = block;
        this.tick = tick;
        nodeActivations.put(block, tick);

        selectedNodes.forEach((uuid, blockMap) -> {
            Integer integer = blockMap.get(block);
            Long initialTick = nodeActivations.get(start);
        });
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
