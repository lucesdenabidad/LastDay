package datta.core.paper.games.lastday;

import datta.core.paper.events.MessagesEvent;
import datta.core.paper.games.lastday.teams.Equipo;
import datta.core.paper.games.lastday.teams.Equipos;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import static datta.core.paper.games.lastday.teams.Equipos.getEquipoCoords;
import static datta.core.paper.games.lastday.teams.Equipos.obtenerEquipo;

public class LasyDayECloud extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "cc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "datta";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.1";
    }

    @Override
    public @NotNull String onRequest(OfflinePlayer offlinePlayer, String param) {

        Equipo equipo = obtenerEquipo(offlinePlayer.getPlayer());


        if (param.equalsIgnoreCase("survival")) {
            return "" + Bukkit.getOnlinePlayers().stream()
                    .filter(player -> player.getGameMode() == GameMode.SURVIVAL).count();
        }

        if (param.equalsIgnoreCase("team")) {
            return (equipo != null) ? equipo.getColor()+"[#"+equipo.getId()+"] " : "";
        }

        if (param.equalsIgnoreCase("team_show")) {
            return (equipo != null) ? "&f Equipo: "+equipo.getColor()+"[#"+equipo.getId()+"] " : "";
        }


        if (param.equalsIgnoreCase("team_coords_0")) {
            return getEquipoCoords(offlinePlayer.getPlayer(), 0);
        }

        if (param.equalsIgnoreCase("team_coords_1")) {
            return Equipos.getEquipoCoords(offlinePlayer.getPlayer(), 1);
        }

        if (param.equalsIgnoreCase("team_coords_2")) {
            return Equipos.getEquipoCoords(offlinePlayer.getPlayer(), 2);
        }

        if (param.equalsIgnoreCase("kills")) {
            return ""+ MessagesEvent.MAPSAVED.getOrDefault(offlinePlayer.getPlayer(),0);
        }

        if (param.equalsIgnoreCase("maxplayers")) {
            return ""+Bukkit.getServer().getMaxPlayers();
        }
        return "e_" + param;
    }
}
