package dev.tom.cannoncore.magicsand;

import dev.tom.cannoncore.items.MagicsandItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
public enum MagicsandType {

    SAND("sand", "Sand", Material.SANDSTONE, Material.SAND),
    GRAVEL("gravel", "Gravel", Material.ANDESITE, Material.GRAVEL),
    RED_SAND("red_sand", "Red Sand", Material.RED_SANDSTONE, Material.RED_SAND),
    DRAGON_EGG("dragon_egg", "Dragon Egg", Material.DEEPSLATE_BRICKS, Material.DRAGON_EGG),
    ANVIL("anvil", "Anvil", Material.IRON_BLOCK, Material.ANVIL),
    CHIPPED_ANVIL("chipped_anvil", "Chipped Anvil", Material.RAW_IRON_BLOCK, Material.CHIPPED_ANVIL),
    WHITE_CONCRETE_POWDER("white_concrete_powder", "White Concrete Powder", Material.WHITE_CONCRETE, Material.WHITE_CONCRETE_POWDER),
    ORANGE_CONCRETE_POWDER("orange_concrete_powder", "Orange Concrete Powder", Material.ORANGE_CONCRETE, Material.ORANGE_CONCRETE_POWDER),
    MAGENTA_CONCRETE_POWDER("magenta_concrete_powder", "Magenta Concrete Powder", Material.MAGENTA_CONCRETE, Material.MAGENTA_CONCRETE_POWDER),
    LIGHT_BLUE_CONCRETE_POWDER("light_blue_concrete_powder", "Light Blue Concrete Powder", Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_BLUE_CONCRETE_POWDER),
    YELLOW_CONCRETE_POWDER("yellow_concrete_powder", "Yellow Concrete Powder", Material.YELLOW_CONCRETE, Material.YELLOW_CONCRETE_POWDER),
    LIME_CONCRETE_POWDER("lime_concrete_powder", "Lime Concrete Powder", Material.LIME_CONCRETE, Material.LIME_CONCRETE_POWDER),
    PINK_CONCRETE_POWDER("pink_concrete_powder", "Pink Concrete Powder", Material.PINK_CONCRETE, Material.PINK_CONCRETE_POWDER),
    GRAY_CONCRETE_POWDER("gray_concrete_powder", "Gray Concrete Powder", Material.GRAY_CONCRETE, Material.GRAY_CONCRETE_POWDER),
    LIGHT_GRAY_CONCRETE_POWDER("light_gray_concrete_powder", "Light Gray Concrete Powder", Material.LIGHT_GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE_POWDER),
    CYAN_CONCRETE_POWDER("cyan_concrete_powder", "Cyan Concrete Powder", Material.CYAN_CONCRETE, Material.CYAN_CONCRETE_POWDER),
    PURPLE_CONCRETE_POWDER("purple_concrete_powder", "Purple Concrete Powder", Material.PURPLE_CONCRETE, Material.PURPLE_CONCRETE_POWDER),
    BLUE_CONCRETE_POWDER("blue_concrete_powder", "Blue Concrete Powder", Material.BLUE_CONCRETE, Material.BLUE_CONCRETE_POWDER),
    BROWN_CONCRETE_POWDER("brown_concrete_powder", "Brown Concrete Powder", Material.BROWN_CONCRETE, Material.BROWN_CONCRETE_POWDER),
    GREEN_CONCRETE_POWDER("green_concrete_powder", "Green Concrete Powder", Material.GREEN_CONCRETE, Material.GREEN_CONCRETE_POWDER),
    RED_CONCRETE_POWDER("red_concrete_powder", "Red Concrete Powder", Material.RED_CONCRETE, Material.RED_CONCRETE_POWDER),
    BLACK_CONCRETE_POWDER("black_concrete_powder", "Black Concrete Powder", Material.BLACK_CONCRETE, Material.BLACK_CONCRETE_POWDER);

    private final String id;
    private final String friendlyName;
    @Setter
    @Getter
    private MagicsandItem item;
    private final Material inactiveBlock;
    private final Material spawnBlock;
    @Getter
    public static final Material activeBlock = Material.CRYING_OBSIDIAN;

     MagicsandType(String id, String friendlyName, Material inactiveBlock, Material spawnBlock){
         this.id = id;
         this.friendlyName = friendlyName;
         this.inactiveBlock = inactiveBlock;
         this.spawnBlock = spawnBlock;
    }

    // Static method to initialize MagicsandItem etc. for each enum value
    public static void initializeMagicsandTypes() {
        for (MagicsandType type : values()) {
            type.item = new MagicsandItem(type.id);
            MagicsandManager.magicsandSpawnBlocks.add(type.getSpawnBlock());
            MagicsandManager.magicsandInactiveBlocks.add(type.getInactiveBlock());
        }
    }

    public static boolean isMagicsandType(String s){
        for(MagicsandType type : values()){
            if(type.getId().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    public static MagicsandType getByFriendlyName(String friendlyName) {
        for (MagicsandType type : values()) {
            if (type.getFriendlyName().equalsIgnoreCase(friendlyName)) {
                return type;
            }
        }
        return null;
    }

    public static MagicsandType getByInactiveBlock(Material material){
        for(MagicsandType type : values()){
            if(type.getInactiveBlock().equals(material)){
                return type;
            }
        }
        return null;
    }


    public static MagicsandType getById(String id){
        for(MagicsandType type : values()){
            if(type.getId().equals(id)){
                return type;
            }
        }
        return null;
    }

}
