package net.unicroak.randomteleport;

import net.unicroak.randomteleport.util.ChunkUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author unicroak
 */
public final class RandomTeleporter {

    private final Player player;
    private final int blockRadius;
    private final List<World> destinationWorldList;

    public RandomTeleporter(Player player, int blockRadius, List<World> destinationWorldList) {
        this.player = player;
        this.blockRadius = blockRadius;
        this.destinationWorldList = destinationWorldList;
    }

    public boolean execute() {
        if (!player.isOnline() || player.isDead()) {
            return false;
        }

        if (destinationWorldList.isEmpty()) {
            return false;
        }

        Collections.shuffle(destinationWorldList);
        World randomWorld = destinationWorldList.get(0);
        Chunk randomChunk = ChunkUtil.getRandomizedChunk(randomWorld, blockRadius);

        Location location = null;
        for (int tryCount = 0; tryCount < 40; tryCount++) {
            Optional<Location> optionalLocation = ChunkUtil.getRandomizedSpawnableLocation(randomChunk);
            if (optionalLocation.isPresent()) {
                location = optionalLocation.get();
                break;
            }
        }
        if (location == null) {
            return false;
        }

        player.teleport(location);

        return true;
    }

}
