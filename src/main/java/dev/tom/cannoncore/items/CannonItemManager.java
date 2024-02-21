package dev.tom.cannoncore.items;

import dev.tom.cannoncore.CannonCore;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CannonItemManager {

    private final CannonCore core;
    private static Map<String, AbstractCannonItem> cannonItemMap = new HashMap<>();

    public CannonItemManager(CannonCore core){
        this.core = core;
    }

    public void registerCannonItems(){
        Block36 block36 = new Block36();
        cannonItemMap.put(block36.getId(), block36);

//        MagicSand magicSand = new MagicSand();
//        cannonItemMap.put(magicSand.getId(), magicSand);
    }

    public static Map<String, AbstractCannonItem> getCannonItemMap() {
        return cannonItemMap;
    }
}
