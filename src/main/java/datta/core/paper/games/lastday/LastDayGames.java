package datta.core.paper.games.lastday;

import datta.core.paper.Core;
import datta.core.paper.games.lastday.enums.LastDayColor;
import datta.core.paper.games.lastday.teams.Equipo;
import datta.core.paper.games.lastday.teams.Equipos;
import datta.core.paper.utilities.worldedit.BukkitIteratorPlayers;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static datta.core.paper.utilities.Color.co;
import static datta.core.paper.utilities.Color.stringToLocation;
import static datta.core.paper.utilities.Utils.countdown;
import static datta.core.paper.utilities.player.PlayerUtil.sendSound;
import static datta.core.paper.utilities.player.PlayerUtil.sendTitle;
import static datta.core.paper.utilities.services.Timer.timer;

public class LastDayGames {
    public static World world = Bukkit.getWorlds().get(0);

    @Getter
    @Setter
    protected static boolean pvpStatus = false;


    public static List<LastDayColor> lastDayColorList = List.of(
            new LastDayColor("pvp", "&#ff0000", "&#ff6666"),
            new LastDayColor("storm", "&#ab43c8", "&#eda2f4")
    );



    public static void start(int countdown, int time, int pvpStartAt, int gameDuration, int endSize) {
        int gameDurationToSeconds = 60 * 20;

        World world = Bukkit.getWorld("world");

        LastDayColor pvpColor = LastDayColor.fetchColor("pvp", lastDayColorList);
        String pvpfirstColor = pvpColor.getFirstColor();
        String pvpsecondColor = pvpColor.getFirstColor();

        LastDayColor stormColor = LastDayColor.fetchColor("storm", lastDayColorList);
        String stormfirstColor = stormColor.getFirstColor();
        String stormsecondColor = stormColor.getFirstColor();

        world.setTime(time);
        countdown(countdown, () -> {
            BukkitIteratorPlayers.clearInventory();
            BukkitIteratorPlayers.heal();
            BukkitIteratorPlayers.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f);
            BukkitIteratorPlayers.giveEffect(PotionEffectType.SLOW_FALLING, 20 * 10, 5);
            BukkitIteratorPlayers.teleportAll(stringToLocation("-31 192 46"));

            for (Player target : Bukkit.getOnlinePlayers()) {
                Location genlocation = genlocation();
                target.teleport(genlocation);
            }

            for (Player t : Bukkit.getOnlinePlayers()) {
                sendTitle(t, "&a&lEVENTO&a!", "&8> &f¡Fuiste desplegado! &8<");
            }

            for (Equipo equipo : Equipos.equipoList) {
                if (equipo.getMembers().size() > 1) {
                    Player player = equipo.getMembers().get(0);
                    player.sendMessage(co("&e&lEvento &8» &fTeletransportando a todo tu equipo hacia ti."));


                    for (Player p : equipo.getMembers()) {
                        if (p != player) {
                            p.teleport(player);
                            p.sendMessage(co("&e&lEvento &8» &fFuiste teletransportado a tu equipo."));
                        }
                    }
                }
            }

            timer(pvpsecondColor + "El PVP se activará en " + pvpfirstColor + "{time}", BarColor.RED, BarStyle.SOLID, 60 * pvpStartAt, () -> {
                Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
                    LastDayGames.setPvp(true);

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + endSize + " " + gameDurationToSeconds);

                    timer(stormsecondColor + "La tormenta se cerrará en " + stormfirstColor + "{time}", BarColor.PURPLE, BarStyle.SOLID, gameDurationToSeconds, () -> {

                        Bukkit.broadcastMessage(""+gameDurationToSeconds);

                        BukkitIteratorPlayers.sendTitle(stormfirstColor + "&lTORMENTA", stormsecondColor + "La tormenta se ha cerrado");
                    });
                },20L);
            });
        });
    }
    public static void setPvp(boolean pvpStatus) {
        LastDayGames.pvpStatus = pvpStatus;
        LastDayColor pvpColor = LastDayColor.fetchColor("pvp", lastDayColorList);

        String firstColor = pvpColor.getFirstColor();
        String secondColor = pvpColor.getSecondColor();

        if (pvpStatus) {
            bc("&a¡El combate entre supervivientes ha sido activado!",
                    "&a¡Prepárate para luchar contra otros supervivientes!",
                    "&c¡Ten cuidado! Los zombies no son tu única amenaza.",
                    "&7¡Demuestra tu valentía en esta lucha por sobrevivir!");

            for (Player t : Bukkit.getOnlinePlayers()) {
                sendTitle(t, firstColor + "&l¡PVP!", secondColor + "¡Prepárate para luchar!");
                sendSound(t, Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 2);

            }

        } else {
            bc("&c¡El combate entre supervivientes ha sido desactivado!",
                    "&c¡Ahora puedes estar tranquilo, al menos de otros jugadores!",
                    "&7Recuerda, trabajar en equipo es clave para sobrevivir.",
                    "&7¡Mantente alerta y protege a tus compañeros!");

            for (Player t : Bukkit.getOnlinePlayers()) {
                sendTitle(t, firstColor+"&l¡PVP!", secondColor + "¡Ahora puedes estar tranquilo!");
            }
        }
    }


    public static void bc(String... s) {
        for (String t1 : s) {
            Bukkit.broadcastMessage(co(t1));
        }

    }

    public static Location genlocation() {
        World world = stringToLocation("0 100 0 ").getWorld();

        int minX = -136;
        int maxX = 100;
        int minZ = -53;
        int maxZ = 155;

        int randomX = minX + (int) (Math.random() * ((maxX - minX) + 1));
        int randomZ = minZ + (int) (Math.random() * ((maxZ - minZ) + 1));

        int fixedY = 140;

        Location randomLocation = new Location(world, randomX, fixedY, randomZ);

        return randomLocation;
    }
}
