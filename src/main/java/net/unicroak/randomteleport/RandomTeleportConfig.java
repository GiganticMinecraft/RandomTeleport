package net.unicroak.randomteleport;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author unicroak
 */
public final class RandomTeleportConfig {

    private static final String KEY_RADIUS = "Radius";
    private static final String KEY_DESTINATION_WORLDS = "DestinationWorlds";

    private final int radius;
    private final List<World> destinationWorldList;

    private RandomTeleportConfig(int radius, List<World> destinationWorldList) {
        this.radius = radius;
        this.destinationWorldList = destinationWorldList;
    }

    public static RandomTeleportConfig from(FileConfiguration configuration) {
        int radius = configuration.getInt(KEY_RADIUS);
        List<World> destinationWorldList = configuration.getStringList(KEY_DESTINATION_WORLDS)
                .stream()
                .map(Bukkit::getWorld)
                .collect(Collectors.toList());

        return new RandomTeleportConfig(radius, destinationWorldList);
    }

    public int getRadius() {
        return this.radius;
    }

    public List<World> getDestinationWorldList() {
        return this.destinationWorldList;
    }

}
