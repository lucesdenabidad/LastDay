package datta.core.paper.games.lastday.teams;

import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.games.lastday.teams.Equipos.*;
import static datta.core.paper.utilities.Color.co;
import static datta.core.paper.utilities.MenuBuilder.slot;
import static datta.core.paper.utilities.Utils.getFoodBar;
import static datta.core.paper.utilities.Utils.getHealthBar;

public class EquipoEvents implements Listener {

    @EventHandler
    public void interact(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (rightClicked instanceof Player target) {
            if (player.isSneaking()){
            open(player, target);
            }
        }
    }

    public void open(Player player, Player target){
        menuBuilder.createMenu(player, "Opciones de "+target.getName()+".", 9 * 3, false);
        menuBuilder.setContents(player, ()-> {
                    Equipo equipo = obtenerEquipo(target);
                    String s = (equipo != null) ? equipo.getColor() + "#" + equipo.getId() + " " : "Sin equipo";
                    menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(Material.PLAYER_HEAD, "&e" + target.getName(),
                            "",
                            "&fVida: &#fc0303" + getHealthBar(target),
                            "&fComida: &#d49f6e" + getFoodBar(target),
                            "&fEquipo: &c" + s,
                            "").buildPlayerHead(target.getName()), () -> {

                    });
            menuBuilder.setItem(player, slot(4,2), new ItemBuilder(Material.PLAYER_HEAD,"&aInvitar a grupo",
                    "",
                    "&fInvita a este jugador al grupo si estas en uno.",
                    "", "&eClic para unirte.").buildTexture("ef17c6c12fd53b3a7ef8b9ba889403687441ba1d6a7bd8817d9a637640330df"),()->{
                invitar(player, target);
            });

            menuBuilder.setItem(player, slot(5,2), new ItemBuilder(Material.PLAYER_HEAD,"&aUnirse a su a grupo",
                    "",
                    "&fEntra al grupo del jugador si este te invito.",
                    "", "&eClic para unirte.").buildTexture("c73e8bd3c43c4514c76481ca1daf55149dfc93bd1bcfa8ab9437b9f7eb3392d9"),()->{

                if (equipo == null){
                    player.sendMessage(co("&#ff0004(!) No fuiste invitado al grupo o el jugador no tiene grupo."));
                    return;
                }

                int id = equipo.getId();
                unirse(player, id);
            });


            menuBuilder.setItem(player, slot(6,2), new ItemBuilder(Material.PLAYER_HEAD,"&aCambiar color de grupo",
                    "",
                    "&fCambia el color del grupo al que prefieras.",
                    "", "&eClic para cambiar color.").buildTexture("c7ff1377754563ab41b8a0305dac03de63e02e5a39a6956afd6ccabf295a96d8"),()->{
                cambiarColor(player);
            });
            menuBuilder.setItem(player, slot(7,2), new ItemBuilder(Material.PLAYER_HEAD,"&cAbandonar grupo",
                    "",
                    "&fAbandona el grupo en el que estas actualmente",
                    "&fsi estas en uno claramente.",
                    "", "&eClic para abandonar grupo.").buildTexture("4051b59085d2c4249577823f63e1e2eb9f7cf64b7c78785a21805fad3ef14"),()->{

                abandonarGrupo(player);
            });

        });
    }
}
