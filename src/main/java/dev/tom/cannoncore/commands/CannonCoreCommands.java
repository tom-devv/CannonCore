package dev.tom.cannoncore.commands;

import dev.tom.cannoncore.CannonCore;

public class CannonCoreCommands {

    /*
    Change this, I just needed a way to register the plugins, can you try and load them automatically?
     */

    public CannonCoreCommands(CannonCore plugin){
        new TNTFillCommand("tntfill", "tf").registerCommand();
    }

}
