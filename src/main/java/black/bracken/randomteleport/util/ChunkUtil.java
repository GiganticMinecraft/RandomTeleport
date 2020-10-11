package black.bracken.randomteleport.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;
import java.util.stream.IntStream;

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
        List<Location> safetyLocationList = collectSafetyLocations(chunk);

        if (safetyLocationList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(safetyLocationList.get(randomGenerator.nextInt(safetyLocationList.size())));
        }
    }

    public static boolean containsOceanBiome(Chunk chunk) {
        List<Block> cornerBlockList = Arrays.asList(
                chunk.getBlock(0, 1, 0),
                chunk.getBlock(15, 1, 0),
                chunk.getBlock(0, 1, 15),
                chunk.getBlock(15, 1, 15)
        );

        return cornerBlockList.parallelStream()
                .anyMatch(block -> BlockUtil.OCEAN_BIOME_LIST.contains(block.getBiome()));
    }

    private static List<Location> collectSafetyLocations(Chunk chunk) {
        ArrayList<Location> safetyLocationList = new ArrayList<>(256);
        IntStream.range(0, 16).forEach(x -> IntStream.range(0,16).forEach(z ->
                findHighestSafetyBlockLocation(chunk, x, z).ifPresent(safetyLocationList::add)
        ));

        safetyLocationList.trimToSize();
        return safetyLocationList;
    }

    private static Optional<Location> findHighestSafetyBlockLocation(Chunk chunk, int x, int z) {
        return IntStream.range(DESTINATION_Y_MIN, DESTINATION_Y_MAX)
                .boxed()
                .collect(CollectorUtil.toReversed())
                .map(unsafeY -> chunk.getBlock(x, unsafeY, z))
                .filter(BlockUtil::isSafeForPlayer)
                .findFirst()
                .map(Block::getLocation);
    }

}
