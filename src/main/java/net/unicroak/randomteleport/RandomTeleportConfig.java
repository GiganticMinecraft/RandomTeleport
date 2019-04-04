package net.unicroak.randomteleport;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author unicroak
 */
public final class RandomTeleportConfig {

    private static final String KEY_RADIUS = "Radius";
    private static final String KEY_RADIUS_PER_WORLD = "RadiusPerWorld";
    private static final String KEY_ENABLE_WORLDS = "EnableWorlds";

    private final int radius;
    private final HashMap<World,Integer> radiusPerWorld;
    private final List<World> enableWorldList;

    private RandomTeleportConfig(int radius, HashMap<World,Integer> radiusPerWorld, List<World> enableWorldList) {
        this.radius = radius;
        this.radiusPerWorld = radiusPerWorld;
        this.enableWorldList = enableWorldList;
    }

    public static RandomTeleportConfig from(FileConfiguration configuration) {
        int radius = configuration.getInt(KEY_RADIUS);
        HashMap<World,Integer> radiusPerWorld = new HashMap<>();
        Bukkit.getWorlds().forEach(world -> radiusPerWorld
        		.put(world, configuration.getInt(KEY_RADIUS_PER_WORLD + "." + world.getName(),radius)));
        	
        
        List<World> enableWorldList = configuration.getStringList(KEY_ENABLE_WORLDS)
                .stream()
                .map(Bukkit::getWorld)
                .collect(Collectors.toList());

        return new RandomTeleportConfig(radius, radiusPerWorld, enableWorldList);
    }

    public int getRadius() {
        return this.radius;
    }
    
    public int getRadiusPerWorld(World world) {
        return this.radiusPerWorld.get(world);
    }

    public List<World> getEnableWorldList() {
        return this.enableWorldList;
    }

}
