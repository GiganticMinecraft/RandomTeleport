package net.unicroak.randomteleport;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author unicroak
 */
public final class RandomTeleportConfig {

    private static final String KEY_DEFAULT_RADIUS = "Radius";
    private static final String KEY_RADIUS_PER_WORLD = "RadiusPerWorld";
    private static final String KEY_ENABLE_WORLDS = "EnableWorlds";

    private final Map<World, Integer> worldRadiusMap;
    private final List<World> enableWorldList;

    private RandomTeleportConfig(Map<World, Integer> worldRadiusMap, List<World> enableWorldList) {
        this.worldRadiusMap = worldRadiusMap;
        this.enableWorldList = enableWorldList;
    }

    public static RandomTeleportConfig from(FileConfiguration configuration) {
        int defaultRadius = configuration.getInt(KEY_DEFAULT_RADIUS);

        Map<World, Integer> worldRadiusMap = Bukkit.getWorlds()
                .stream()
                .collect(
                        Collectors.toMap(
                                world -> world,
                                world -> configuration.getInt(KEY_RADIUS_PER_WORLD + "." + world.getName(), defaultRadius)
                        )
                );

        List<World> enableWorldList = configuration.getStringList(KEY_ENABLE_WORLDS)
                .stream()
                .map(Bukkit::getWorld)
                .collect(Collectors.toList());

        return new RandomTeleportConfig(worldRadiusMap, enableWorldList);
    }

    public int getRadiusIn(World world) {
        return this.worldRadiusMap.get(world);
    }

    public List<World> getEnableWorldList() {
        return this.enableWorldList;
    }

}
