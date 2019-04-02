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

    private static final int MAX_TRY_COUNT = 256;

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

        Chunk randomChunk = ChunkUtil.getRandomizedChunk(player.getWorld(), blockRadius);

        Location location = null;
        for (int tryCount = 0; tryCount < MAX_TRY_COUNT; tryCount++) {
            Optional<Location> optionalLocation = ChunkUtil.getRandomizedSafetyLocation(randomChunk);
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
