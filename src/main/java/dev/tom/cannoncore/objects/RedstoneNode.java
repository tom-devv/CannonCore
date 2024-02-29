package dev.tom.cannoncore.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class RedstoneNode {

    public static Map<Block, Set<RedstoneNode>> trackedNodes = new HashMap<>();

    private final int selectedNodeIndex;
    private final UUID uuid;
    private final PlayerNodeSession playerNodeSession;

    public RedstoneNode(int selectedNodeIndex, UUID uuid, PlayerNodeSession playerNodeSession){
        this.selectedNodeIndex = selectedNodeIndex;
        this.uuid = uuid;
        this.playerNodeSession = playerNodeSession;
    }


    public void onTick(){
        playerNodeSession.onTick(this);
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }



}
