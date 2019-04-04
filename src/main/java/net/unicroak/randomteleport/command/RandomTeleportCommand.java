package net.unicroak.randomteleport.command;

import net.unicroak.randomteleport.RandomTeleportConfig;
import net.unicroak.randomteleport.RandomTeleporter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author unicroak
 */
public final class RandomTeleportCommand implements CommandExecutor {

    private RandomTeleportConfig randomTeleportConfig;

    public RandomTeleportCommand(RandomTeleportConfig randomTeleportConfig) {
        this.randomTeleportConfig = randomTeleportConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command must be executed in game.");
            return true;
        }

        RandomTeleporter teleporter = new RandomTeleporter(
                (Player) sender,
                randomTeleportConfig.getRadiusPerWorld(((Player)sender).getWorld()),
                randomTeleportConfig.getEnableWorldList()
        );

        if (teleporter.execute()) {
            // success
        } else {
            // failure
            sender.sendMessage(ChatColor.RED + "Failed to teleport and try again please.");
        }

        return true;
    }

}
