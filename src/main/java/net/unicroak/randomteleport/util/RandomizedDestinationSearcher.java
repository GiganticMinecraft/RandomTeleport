package net.unicroak.randomteleport.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class RandomizedDestinationSearcher {

    private RandomizedDestinationSearcher() {
    }

    public static Optional<Location> search(Player player, int radiusToSearch) {
        if (!player.isOnline() || player.isDead()) return Optional.empty();

        Chunk randomChunk = null;
        for (int tryCountToSearchChunk = 0; tryCountToSearchChunk < 16; tryCountToSearchChunk++) {
            randomChunk = ChunkUtil.getRandomizedChunk(player.getWorld(), radiusToSearch);

            if (!ChunkUtil.containsOceanBiome(randomChunk)) {
                break;
            }
        }
        if (ChunkUtil.containsOceanBiome(randomChunk)) return Optional.empty();

        Optional<Location> optionalDestination = ChunkUtil.getRandomizedSafetyLocation(randomChunk);

        if (!optionalDestination.isPresent()) {
            return Optional.empty();
        } else {
            Location destination = optionalDestination.get();
            player.teleport(destination.add(0.5, 1.0, 0.5));
            return Optional.empty();
        }
    }

}
