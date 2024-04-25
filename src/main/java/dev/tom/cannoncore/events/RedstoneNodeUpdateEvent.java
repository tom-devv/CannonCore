package dev.tom.cannoncore.events;

import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.listeners.NodeListeners;
import dev.tom.cannoncore.node.Node;
import dev.tom.cannoncore.node.NodeArray;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public class RedstoneNodeUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();


    public RedstoneNodeUpdateEvent(Block block){
        if(!NodeListeners.trackedNodes.contains(block.getLocation())) return;

        entryLoop:
        for (Map.Entry<Player, NodeArray> entry : NodeListeners.nodeArrays.entrySet()) {

            Player player = entry.getKey();
            NodeArray nodeArray = entry.getValue();
            Node head = entry.getValue().getHead();

            for (Node node : nodeArray.getNodes()) {

                // Node in array was activated
                if(node.getLocation().equals(block.getLocation())){

                    if(head == null){
                        head = node;
                        entry.getValue().setHead(node);
                    }

                    // Set node last activation to NOW
                    node.setActivated(CannonCore.getCurrentTick());

                    // Head node is already set so get diff
                    Util.sendMessage(player, "ID: " + nodeArray.getIndex(node) + " ACTIVATED = " + (node.getActivated() - head.getActivated()));
                    continue entryLoop;
                }

            }
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
