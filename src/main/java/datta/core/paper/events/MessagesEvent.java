package datta.core.paper.events;

import datta.core.paper.Core;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static datta.core.paper.utilities.Color.format;

public class MessagesEvent implements Listener {

    public static String JOIN_MSG = "&#92ff5c{0} se unio al servidor";
    public static String QUIT_MSG = "&#ff5c6f{0} abandono al servidor";


    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        event.setJoinMessage(format("&#92ff5c{0} se unio al servidor", player.getName()));
        player.teleport(Core.spawn);
    }


    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        event.setQuitMessage(format("&#ff5c6f{0} abandono al servidor", player.getName()));
    }

}