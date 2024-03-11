package dev.tom.cannoncore.objects;

import dev.tom.cannoncore.CannonCore;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerNodeSession {

    public static Map<UUID, PlayerNodeSession> nodeSessions = new HashMap<>();

    public static final int DECAY_TICKS = 80;
    private List<RedstoneNode> nodes = new ArrayList<>();
    private long initialTick;
    private long lastNodeActivationTick;

    public PlayerNodeSession(){
        initialTick = CannonCore.getCurrentTick();
        lastNodeActivationTick = initialTick;
    }

    public void onTick(RedstoneNode node){

        long diff = 0;
        if(CannonCore.getCurrentTick() - lastNodeActivationTick <= DECAY_TICKS){
            lastNodeActivationTick = CannonCore.getCurrentTick();
            diff = lastNodeActivationTick - initialTick;
        } else {
            initialTick = CannonCore.getCurrentTick();
            lastNodeActivationTick = initialTick;
            diff = 0;
        }

        node.getPlayer().sendMessage("Node [" + node.getSelectedNodeIndex() + "] | " + diff +  " GT");
    }

    public static PlayerNodeSession getPlayerSession(Player player){
        if(nodeSessions.containsKey(player.getUniqueId())){
            return nodeSessions.get(player.getUniqueId());
        } else {
            PlayerNodeSession session = new PlayerNodeSession();
            nodeSessions.put(player.getUniqueId(), session);
            return session;
        }
    }

}
