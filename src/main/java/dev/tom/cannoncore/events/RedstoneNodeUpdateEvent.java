package dev.tom.cannoncore.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RedstoneNodeUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public RedstoneNodeUpdateEvent(Block block, Long tick){

    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}
