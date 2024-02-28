package dev.tom.cannoncore.objects;

import lombok.Data;
import lombok.Getter;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class RedstoneNode {

    public static Map<UUID, RedstoneNodeList> selectedNodes = new HashMap<>();
    public static Map<Block, Long> nodeActivations = new HashMap<>();

    private final int index;
    private final Block block;
    private Long lastActivation = null;

    public RedstoneNode(int index, Block block) {
        this.index = index;
        this.block = block;
        this.lastActivation = getLastActivation();
    }

    /**
     * Get the last activation of the node from the global map
     * @return last activation of the node
     */
    public Long getLastActivation(){
        return nodeActivations.get(this.getBlock());
    }
}
