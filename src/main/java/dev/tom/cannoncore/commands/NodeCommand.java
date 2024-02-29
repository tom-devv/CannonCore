package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.Util;
import dev.tom.cannoncore.commands.conditions.PlotEditCondition;
import dev.tom.cannoncore.items.AbstractCannonItem;
import dev.tom.cannoncore.items.NodeStickItem;
import org.w3c.dom.Node;

public class NodeCommand extends CannonCoreCommand {

    public NodeCommand() {
        super("node", "dnode");
    }

    @Override
    public SYSCommand command() {
        return
            new SYSCommand(getAliases())
                .executesPlayer((player, strings) -> {
                NodeStickItem nodeStickItem = (NodeStickItem) AbstractCannonItem.getCannonItemMap().get("nodestick");
                  nodeStickItem.giveItem(player);
                  Util.sendMessage(player, CannonCore.chatMessages.nodereceive);
            });

    }
}
