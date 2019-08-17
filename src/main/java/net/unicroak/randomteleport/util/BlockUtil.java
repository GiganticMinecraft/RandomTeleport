package net.unicroak.randomteleport.util;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.List;

/**
 * @author unicroak
 */
public final class BlockUtil {

    public static final List<Biome> OCEAN_BIOME_LIST = Arrays.asList(
            Biome.OCEAN,
            Biome.DEEP_OCEAN,
            Biome.FROZEN_OCEAN
    );

    private static final List<Material> PENETRATE_MATERIAL_LIST = Arrays.asList(
            Material.AIR,
            Material.GRASS_PATH,
            Material.LONG_GRASS,
            Material.DEAD_BUSH,
            Material.SNOW
    );

    private static final List<Material> GROUND_MATERIAL_LIST = Arrays.asList(
            Material.STONE,
            Material.MOSSY_COBBLESTONE,
            Material.SANDSTONE,
            Material.RED_SANDSTONE,
            Material.OBSIDIAN,
            Material.DIRT,
            Material.GRASS,
            Material.GRASS_PATH,
            Material.EMERALD_ORE,
            Material.LAPIS_ORE,
            Material.GOLD_ORE,
            Material.IRON_ORE,
            Material.DIAMOND_ORE,
            Material.COAL_ORE,
            Material.REDSTONE_ORE,
            Material.SAND,
            Material.GRAVEL,
            Material.ICE,
            Material.FROSTED_ICE,
            Material.PURPUR_BLOCK,
            Material.BONE_BLOCK,
            Material.CLAY,
            Material.SPONGE,
            Material.PRISMARINE,
            Material.PACKED_ICE,
            Material.COBBLESTONE,
            Material.PUMPKIN,
            Material.MELON,
            Material.SNOW_BLOCK,
            Material.NETHERRACK,
            Material.GLOWSTONE,
            Material.SEA_LANTERN,
            Material.ENDER_STONE
    );

    public static boolean isSafeForPlayer(Block block) {
        if (!GROUND_MATERIAL_LIST.contains(block.getType())
                || OCEAN_BIOME_LIST.contains(block.getBiome())) {
            return false;
        }

        Block destination;
        for (int y = 0; y < 3; y++) {
            destination = block.getRelative(BlockFace.UP);

            if (!PENETRATE_MATERIAL_LIST.contains(destination.getType())) {
                return false;
            }
        }

        return true;
    }

}
