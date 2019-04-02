package net.unicroak.randomteleport.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Optional;
import java.util.Random;

/**
 * @author unicroak
 */
public final class ChunkUtil {

    private static final int DESTINATION_Y_MAX = 150;
    private static final int DESTINATION_Y_MIN = 30;

    private static final Random randomGenerator = new Random();

    private ChunkUtil() {
    }

    public static Chunk getRandomizedChunk(World world, int blockRadius) {
        int chunkRadius = blockRadius / 16;
        int chunkX = randomGenerator.nextInt(2 * chunkRadius) - chunkRadius;
        int chunkZ = randomGenerator.nextInt(2 * chunkRadius) - chunkRadius;

        return world.getChunkAt(chunkX, chunkZ);
    }

    public static Optional<Location> getRandomizedSafetyLocation(Chunk chunk) {
        int x = randomGenerator.nextInt(16);
        int z = randomGenerator.nextInt(16);

        int y = -1;
        for (int unsafeY = DESTINATION_Y_MAX; unsafeY >= DESTINATION_Y_MIN; unsafeY--) {
            Block block = chunk.getBlock(x, unsafeY, z);

            if (BlockUtil.isSurface(block)) {
                y = unsafeY;
                break;
            }
        }
        if (y == -1) {
            return Optional.empty();
        }

        Block block = chunk.getBlock(x, y, z);

        if (block.getBiome().toString().contains("OCEAN")) {
            return Optional.empty();
        }

        return Optional.of(block.getLocation().add(0.5, 1.0, 0.5));
    }

}
