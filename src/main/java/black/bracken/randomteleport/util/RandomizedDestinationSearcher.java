package black.bracken.randomteleport.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.stream.IntStream;

public final class RandomizedDestinationSearcher {

    private RandomizedDestinationSearcher() {
    }

    public static Optional<Location> search(Player player, int radiusToSearch) {
        if (!player.isOnline() || player.isDead()) return Optional.empty();

        World world = player.getWorld();
        Chunk randomChunk = IntStream.rangeClosed(0, 15)
                .parallel()
                .mapToObj(tryCountToSearchChunk -> ChunkUtil.getRandomizedChunk(world, radiusToSearch))
                .filter(chunk -> !ChunkUtil.containsOceanBiome(chunk))
                .findFirst()
                .orElse(null);

        if (randomChunk != null && ChunkUtil.containsOceanBiome(randomChunk)) return Optional.empty();

        return ChunkUtil.getRandomizedSafetyLocation(randomChunk)
                .map(location -> location.clone().add(0.5, 1.0, 0.5));
    }

}
