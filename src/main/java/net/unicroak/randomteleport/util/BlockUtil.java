package net.unicroak.randomteleport.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;
import java.util.List;

/**
 * @author unicroak
 */
public final class BlockUtil {

    private static final List<Material> GAS_MATERIAL_LIST = Arrays.asList(
            Material.AIR,
            Material.GRASS_PATH,
            Material.LONG_GRASS,
            Material.DEAD_BUSH
    );

    public static boolean isSurface(final Block block) {
        if (block.isLiquid() || GAS_MATERIAL_LIST.contains(block.getType())) {
            return false;
        }

        Block relative;
        for (int y = 0; y < 3; y++) {
            relative = block.getRelative(BlockFace.UP);

            if (!GAS_MATERIAL_LIST.contains(relative.getType())) {
                return false;
            }
        }

        return true;
    }

}
