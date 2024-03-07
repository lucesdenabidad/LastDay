package datta.core.paper.games.lastday.teams;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.games.lastday.teams.Equipos.generarPaleta;

public class Equipo {
    @Getter
    public int id;

    @Getter
    public Player owner;

    @Getter
    public List<Player> members;

    @Getter
    public int maxplayers = 3;

    @Getter
    public String color;

    public Equipo(int id, Player owner) {
        this.id = id;
        this.owner = owner;
        this.members = new ArrayList<>();
        this.members.add(owner);
        this.color = generarPaleta();
    }

    public void addPlayer(Player player) {
        if (members.size() >= maxplayers) {
            player.sendMessage("El equipo está completo. (Máximo " + maxplayers + " jugadores por equipo)");
            return;
        }

        members.add(player);
    }

    public void removePlayer(Player player) {
        members.remove(player);
    }
}