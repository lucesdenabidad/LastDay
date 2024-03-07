package datta.core.paper.utilities.player;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static datta.core.paper.utilities.Color.co;

public class PlayerUtil {

    public static void sendBar(Player player, String msg) {
        player.sendActionBar(co(player,msg));
    }

    public static void sendTitle(Player player, String title, String subtitle, int fade, int stay) {
        player.sendTitle(co(title), co(subtitle), fade, stay, fade);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 20, 50);
    }

    public static void send(CommandSender sender, String... msg) {
        if (sender instanceof Player) {
            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }

        for (String s : msg) {
            sender.sendMessage(co(s));
        }
    }


    public static void sendSound(Player t, Sound sound, int volume, int pitch) {
        t.playSound(t.getLocation(),sound,volume,pitch);
    }
}