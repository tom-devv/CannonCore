package dev.tom.cannoncore.node;

import org.bukkit.Location;

public class Node {

    private final Location location;
    private long activated;

    public Node(Location location, long activated) {
        this.location = location;
        this.activated = activated;
    }

    public long getActivated() {
        return activated;
    }

    public void setActivated(long activated) {
        this.activated = activated;
    }


    public Location getLocation() {
        return location;
    }

}
