package net.unicroak.randomteleport;

import net.unicroak.randomteleport.command.RandomTeleportCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author unicroak
 */
public final class RandomTeleport extends JavaPlugin {

    private static RandomTeleport instance;

    private RandomTeleportConfig randomTeleportConfig;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getConfig();

        this.randomTeleportConfig = RandomTeleportConfig.from(getConfig());

        this.getCommand("randomteleport").setExecutor(new RandomTeleportCommand(randomTeleportConfig));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public RandomTeleportConfig getRandomTeleportConfig() {
        return randomTeleportConfig;
    }

    public static RandomTeleport getInstance() {
        return instance;
    }

}
