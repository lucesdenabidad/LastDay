package datta.core.paper.games.lastday.teams;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

import static datta.core.paper.utilities.Color.co;

@CommandAlias("equipos")
public class Equipos extends BaseCommand implements Listener {

    public static List<Equipo> equipoList = new ArrayList<>();
    static Map<Player, Equipo> invitaciones = new HashMap<>();

    @Subcommand("deleteall")
    public void eliminarEquipos() {
        for (Equipo equipo : equipoList) {
            List<Player> members = equipo.getMembers();
            for (Player player : members) {
                equipo.removePlayer(player);
            }
        }

        equipoList.clear();
    }

    @Subcommand("create")
    public static void crearGrupo(Player player) {

        Equipo equipo1 = obtenerEquipo(player);
        if (equipo1 != null) {
            player.sendMessage(co("&#ff0004(!) Ya tienes un equipo con la ID " + equipo1.getId()));
            return;
        }

        Equipo equipo = new Equipo(equipoList.size() + 1, player);
        equipoList.add(equipo);
        player.sendMessage(co("&#7bd10a(!) Creaste un equipo con la ID " + equipo.getId()));
    }

    @Subcommand("deletegroup")
    public static void eliminargrupo(Player player) {
        Equipo equipo = obtenerEquipo(player);
        if (equipo != null) {
            player.sendMessage(co("&#ff0004(!) Se elimino el equipo " + equipo.getId()));
            equipoList.remove(equipo);
        } else {
            player.sendMessage(co("&#ff0004(!) No tienes un equipo para eliminar."));
        }
    }

    @Subcommand("invite")
    public static void invitar(Player player, Player target) {
        Equipo equipoPlayer = obtenerEquipo(player);
        Equipo equipoTarget = obtenerEquipo(target);

        if (equipoTarget != null) {
            player.sendMessage(co("&#ff0004(!) El jugador " + target.getName() + " ya está en un equipo."));
            return;
        }

        if (equipoPlayer != null) {
            invitaciones.put(target, equipoPlayer);
            target.sendMessage(co("&#7bd10a(!) Has sido invitado al equipo de " + player.getName()));
            player.sendMessage(co("&#7bd10a(!) Invitaste a " + target.getName() + " en tu equipo."));
        } else {
            crearGrupo(player);
            equipoPlayer = obtenerEquipo(player);
            invitaciones.put(target, equipoPlayer);
            player.sendMessage(co("&#7bd10a(!) Creaste un grupo y invitaste a " + target.getName()));
            target.sendMessage(co("&#7bd10a(!) Has sido invitado al equipo de " + player.getName()));
        }
    }


    @Subcommand("leave")
    public static void abandonarGrupo(Player player) {
        Equipo equipo = obtenerEquipo(player);
        if (equipo != null) {
            player.sendMessage(co("&#ff0004(!) Has abandonado tu equipo."));
            notifyMembers(equipo, "&#ff0004(!) " + player.getName() + " abandono el grupo al grupo. " + (equipo.getMembers().size() - 1) + "/" + equipo.maxplayers);
            equipo.removePlayer(player);
        } else {
            player.sendMessage(co("&#ff0004(!) No estás en ningún equipo."));
        }
    }

    @Subcommand("join")
    public static void unirse(Player player, int id) {


        if (!invitaciones.containsKey(player)) {
            player.sendMessage(co("&#ff0004(!) No tienes invitaciones."));
            return;
        }

        Equipo equipo1 = invitaciones.get(player);

        if (equipo1 == null) {
            player.sendMessage(co("&#ff0004(!) No tienes invitaciones."));
            return;
        }

        if (equipo1.getId() != id) {
            player.sendMessage(co("&#ff0004(!) No fuiste invitado a este grupo."));
            return;
        }

        equipo1.addPlayer(player);
        invitaciones.remove(player);
        notifyMembers(equipo1, "&#7bd10a(!) " + player.getName() + " se ha unido al grupo. " + equipo1.getMembers().size() + "/" + equipo1.maxplayers);
    }


    public static void notifyMembers(Equipo equipo, String... msg) {
        for (String s : msg) {
            List<Player> members = equipo.getMembers();
            for (Player p : members) {
                p.sendMessage(co(s));
            }
        }
    }

    public static Equipo obtenerEquipo(Player player) {
        for (Equipo equipo : equipoList) {
            if (equipo.getMembers().contains(player)) {
                return equipo;
            }
        }
        return null;
    }


    public static String generarPaleta() {
        List<String> colores = new ArrayList<>();
        colores.add("#f8fb5d");
        colores.add("#2196f3");
        colores.add("#55dfd8");
        colores.add("#9befd7");
        colores.add("#ff1f8e");
        colores.add("#ff9a3e");
        colores.add("#20f26f");
        colores.add("#97f425");
        colores.add("#e8c39e");
        colores.add("#3ab8b0");
        colores.add("#35be7a");
        colores.add("#cee020");
        colores.add("#ab3ed8");
        colores.add("#7f00b2");
        colores.add("#dc143c");
        colores.add("#fdcae1");
        colores.add("#952f57");
        colores.add("#64c27b");
        colores.add("#e6aa77");
        colores.add("#00ffff");

        Random random = new Random();
        return colores.get(random.nextInt(colores.size()));
    }

    public static void cambiarColor(Player player) {
        String s = generarPaleta();
        Equipo equipo1 = obtenerEquipo(player);
        if (equipo1 == null) {
            player.sendMessage(co("&#ff0004(!) No tienes un equipo."));
            return;
        }

        equipo1.color = s;
    }

    public static String getEquipoCoords(Player player, int index) {
        Equipo equipo = obtenerEquipo(player);
        if (equipo == null || index < 0 || index >= equipo.getMembers().size()) {
            return "";
        }

        Player targetPlayer = equipo.getMembers().get(index);
        if (targetPlayer == player || targetPlayer == null) {
            return "";
        }

        Location location = targetPlayer.getLocation();
        return co("&8> " + equipo.getColor() + targetPlayer.getName() + "&7: &f" + location(location));
    }

    public static String location(Location location) {
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();
        return co("{0}, {2}", blockX, blockY, blockZ);
    }

}