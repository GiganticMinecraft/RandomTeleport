package black.bracken.randomteleport;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RandomTeleportConfig {

    private static final String KEY_DEFAULT_RADIUS = "Radius";
    private static final String KEY_RADIUS_PER_WORLD = "RadiusPerWorld";
    private static final String KEY_ENABLE_WORLDS = "EnableWorlds";

    private final Map<String, Integer> worldRadiusMap;

    private RandomTeleportConfig(Map<String, Integer> worldRadiusMap) {
        this.worldRadiusMap = worldRadiusMap;
    }

    public static RandomTeleportConfig from(FileConfiguration configuration) {
        int defaultRadius = configuration.getInt(KEY_DEFAULT_RADIUS);

        List<String> enabledWorldNameList = configuration.getStringList(KEY_ENABLE_WORLDS);
        Map<String, Integer> worldRadiusMap = enabledWorldNameList
                .stream()
                .collect(
                        Collectors.toMap(
                                worldName -> worldName,
                                worldName -> configuration.getInt(KEY_RADIUS_PER_WORLD + "." + worldName, defaultRadius)
                        )
                );

        return new RandomTeleportConfig(worldRadiusMap);
    }

    public Optional<Integer> getRadiusIn(World world) {
        return Optional.ofNullable(this.worldRadiusMap.getOrDefault(world.getName(), null));
    }

}
