package dev.tom.cannoncore.node;

import org.bukkit.Location;

import java.util.ArrayList;

public class NodeArray {

    private Node head;
    private ArrayList<Node> nodes = new ArrayList<>();
    private long lastTicked = -1;

    public NodeArray() {
        this.head = null;
    }

    public int getIndex(Node node) {
        return nodes.indexOf(node);
    }

    public Node getNodeByLocation(Location location){
        for (Node node : nodes) {
            if(node.getLocation().equals(location)){
                return node;
            }
        }
        return null;
    }

    public long getLastTicked() {
        return lastTicked;
    }

    public void setLastTicked(long lastTicked) {
        this.lastTicked = lastTicked;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

}
