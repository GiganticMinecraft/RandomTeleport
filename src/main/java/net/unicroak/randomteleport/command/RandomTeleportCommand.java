package net.unicroak.randomteleport.command;

import net.unicroak.randomteleport.RandomTeleportConfig;
import net.unicroak.randomteleport.RandomTeleporter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RandomTeleportCommand implements CommandExecutor {

    private RandomTeleportConfig randomTeleportConfig;

    public RandomTeleportCommand(RandomTeleportConfig randomTeleportConfig) {
        this.randomTeleportConfig = randomTeleportConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行されなければなりません");
            return true;
        }

        Player player = (Player) sender;
        RandomTeleporter teleporter = new RandomTeleporter(
                player,
                randomTeleportConfig.getRadiusIn(player.getWorld()),
                randomTeleportConfig.getEnabledWorldList()
        );

        if (teleporter.execute()) {
            // success
        } else {
            // failure
            sender.sendMessage(ChatColor.RED + "候補となるテレポート先が見つかりませんでした 再度お試しください");
        }

        return true;
    }

}
