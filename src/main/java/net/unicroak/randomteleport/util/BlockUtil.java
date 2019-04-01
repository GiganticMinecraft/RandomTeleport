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

    private static final List<Material> SAFE_MATERIAL_LIST = Arrays.asList(
            Material.AIR,
            Material.GRASS,
            Material.GRASS_PATH,
            Material.LONG_GRASS,
            Material.DEAD_BUSH
    );

    public static boolean isSurface(final Block block) {
        Block relative = block;
        for (int y = 0; y < 3; y++) {
            if (!SAFE_MATERIAL_LIST.contains(relative.getType())) {
                return false;
            }

            relative = block.getRelative(BlockFace.UP);
        }

        return true;
    }

}
