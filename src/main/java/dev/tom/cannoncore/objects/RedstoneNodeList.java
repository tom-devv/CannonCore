package dev.tom.cannoncore.objects;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Comparator;

public class RedstoneNodeList extends ArrayList<RedstoneNode> {

    @Override
    public boolean add(RedstoneNode redstoneNode) {
        boolean added = super.add(redstoneNode);
        sortByActivation();
        return added;
    }

    public RedstoneNode getByBlock(Block block){
        for(RedstoneNode node : this){
            if(node.getBlock().equals(block)) return node;
        }
        return null;
    }

    public RedstoneNode getFirstActivation(){
        sortByActivation();
        if(this.get(0) == null || this.get(0).getLastActivation() == -1) return null;
        return this.get(0);
    }

    private void sortByActivation(){
        this.sort(Comparator.comparing(RedstoneNode::getLastActivation));
    }
}
