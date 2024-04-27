package dev.tom.cannoncore.objects;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.C;

public class Wall {

    private final BlockFace direction;
    private final Material material;
    private final int width;
    private final int amount;
    private final int length;
    private final Player player;
    private final EditSession editSession;
    private final LocalSession localSession;
    private final int y;
    private final Vector horizontalStep;
    private final BlockVector3 forwardStep;
    private final BlockVector3 topLeft;
    private final BlockVector3 bottomRight;


    public Wall(Player player, int width, int amount, Material material, BlockFace direction) {
        this.player = player;
        this.width = width;
        // Each wall is material + water & add one to the length
        this.length = (amount * 2) + 1;
        this.amount = amount;
        this.material = material;
        this.direction = direction;
        this.localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        this.editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(player.getWorld()));
        this.y = Math.min(player.getLocation().getBlockY(), 318);
        this.horizontalStep = stepHorizontalVector(direction);
        this.forwardStep = stepBlockVector3(direction);

        Location midLocation = player.getLocation();
        Location topMid = midLocation.clone().add(direction.getModX(), this.y, direction.getModZ());
        Location bottomMid = midLocation.clone().add(direction.getModX(), 0, direction.getModZ());
        topLeft = BukkitAdapter.asBlockVector(
                topMid.add(
                        horizontalStep.clone().multiply(width / 2)
                ).add(
                        horizontalStep.clone().multiply(-1 * (amount / 2))
                )
        );
        bottomRight = BukkitAdapter.asBlockVector(
                bottomMid.add(
                        horizontalStep.clone().multiply(-1 * (width / 2))
                ).add(
                        horizontalStep.clone().multiply(amount / 2)
                )
        );
    }

    public void water(){
        BlockVector3 backRight = bottomRight.add(getForwardStep().multiply(length));
        CuboidRegion blocks =  new CuboidRegion(topLeft, bottomRight.add());
        editSession.setBlocks((Region) blocks, BlockTypes.get(material.name()));
    }


    public void sandwich(int offset) {
        CuboidRegion blocks =  new CuboidRegion(topLeft, bottomRight);
        // Step one closer
        CuboidRegion frontWater = new CuboidRegion(
                topLeft.add(getForwardStep()),
                bottomRight.add(getForwardStep())
        );
        // Step one away
        CuboidRegion backWater = new CuboidRegion(
                topLeft.add(getForwardStep().multiply(-1)),
                bottomRight.add(getForwardStep().multiply(-1))
        );
        editSession.setBlocks((Region) blocks, BlockTypes.get(material.name()));
    }


    private BlockVector3 stepBlockVector3(BlockFace dir){
        switch (dir){
            case NORTH:
                return BlockVector3.at(0, 0, -1);
            case EAST:
                return BlockVector3.at(1, 0, 0);
            case SOUTH:
                return BlockVector3.at(0, 0, 1);
            case WEST:
                return BlockVector3.at(-1, 0, 0);
            default:
                return BlockVector3.at(0, 0, 0);
        }
    }

    private Vector stepHorizontalVector(BlockFace dir) {
        switch (dir) {
            case NORTH:
            case SOUTH:
                return new Vector(1, 0, 0);
            case EAST:
            case WEST:
                return new Vector(0, 0, 1);
            default:
                return new Vector(0, 0, 0);
        }
    }

    public int getAmount() {
        return amount;
    }

    public BlockFace getDirection() {
        return direction;
    }

    public Material getMaterial() {
        return material;
    }

    public int getWidth() {
        return width;
    }

    public Player getPlayer() {
        return player;
    }

    public EditSession getEditSession() {
        return editSession;
    }

    public LocalSession getLocalSession() {
        return localSession;
    }

    public int getY() {
        return y;
    }

    public Vector getHorizontalStep() {
        return horizontalStep;
    }

    public BlockVector3 getTopLeft() {
        return topLeft;
    }

    public BlockVector3 getBottomRight() {
        return bottomRight;
    }

    public BlockVector3 getForwardStep() {
        return forwardStep;
    }
}
