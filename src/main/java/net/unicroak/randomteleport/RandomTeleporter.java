package net.unicroak.randomteleport;

import net.unicroak.randomteleport.util.ChunkUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * @author unicroak
 */
public final class RandomTeleporter {

    private final Player player;
    private final int blockRadius;
    private final List<World> enableWorldList;

    public RandomTeleporter(Player player, int blockRadius, List<World> enableWorldList) {
        this.player = player;
        this.blockRadius = blockRadius;
        this.enableWorldList = enableWorldList;
    }

    public boolean execute() {
        if (!player.isOnline() || player.isDead()) {
            return false;
        }

        if (!enableWorldList.contains(player.getWorld())) {
            return false;
        }

        Chunk randomChunk = null;
        for (int tryCountToSearchChunk = 0; tryCountToSearchChunk < 16; tryCountToSearchChunk++) {
            randomChunk = ChunkUtil.getRandomizedChunk(player.getWorld(), blockRadius);

            if (!ChunkUtil.containsOceanBiome(randomChunk)) {
                break;
            }
        }
        if (ChunkUtil.containsOceanBiome(randomChunk)) return false;

        Optional<Location> optionalDestination = ChunkUtil.getRandomizedSafetyLocation(randomChunk);

        if (!optionalDestination.isPresent()) {
            return false;
        } else {
            Location destination = optionalDestination.get();
            player.teleport(destination.add(0.5, 1.0, 0.5));
            return true;
        }
    }

}
