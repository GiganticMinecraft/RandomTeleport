package net.unicroak.randomteleport.command;

import net.unicroak.randomteleport.RandomTeleportConfig;
import net.unicroak.randomteleport.util.RandomizedDestinationSearcher;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

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

        Optional<Location> optionalDestination = randomTeleportConfig
                .getRadiusIn(player.getWorld())
                .flatMap(radius -> RandomizedDestinationSearcher.search(player, radius));

        if (optionalDestination.isPresent()) {
            player.teleport(optionalDestination.get());
        } else {
            sender.sendMessage(ChatColor.RED + "候補となるテレポート先が見つかりませんでした 再度お試しください");
        }

        return true;
    }

}
