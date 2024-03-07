package datta.core.paper.events;

import datta.core.paper.Core;
import datta.core.paper.utilities.task.BukkitDelayedTask;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

import static datta.core.paper.utilities.Color.co;

public class MessagesEvent implements Listener {

    public static String JOIN_MSG = "&#92ff5c{0} se unio al servidor";
    public static String QUIT_MSG = "&#ff5c6f{0} abandono al servidor";

    public static Map<Player, Integer> MAPSAVED = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        event.setJoinMessage(co(JOIN_MSG, player.getName()));
        player.teleport(Core.spawn);
    }


    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        event.setQuitMessage(co(QUIT_MSG, player.getName()));
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String msg;

        if (player.getKiller() == null) {
            msg = "&c(!) %player_name% murio.";
        } else {
            Player killer = player.getKiller();
            Integer orDefault = MAPSAVED.getOrDefault(killer, 0);
            MAPSAVED.put(killer, orDefault + 1);

            msg = "&c(!) %player_name% murio a manos de " + player.getKiller().getName() + ".";
        }

        BukkitDelayedTask.runTask(() -> {
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
        }, 20L);

        event.setDeathMessage(co(player, msg));
    }

    @EventHandler
    public void deathEntity(EntityDeathEvent event) {
        if (event.getEntity() instanceof Zombie) {
            LivingEntity entity = event.getEntity();
            event.getDrops().clear();
        }
    }
}