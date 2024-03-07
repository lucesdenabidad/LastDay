package datta.core.paper.games.lastday;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class LastDayEvents implements Listener {
    @EventHandler
    public void disableMobs(CreatureSpawnEvent event) {
        List<EntityType> allowd = List.of(EntityType.ZOMBIE, EntityType.PHANTOM, EntityType.ARMOR_STAND, EntityType.CHICKEN);
        if (!allowd.contains(event.getEntityType())) {

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void damageWithPvp(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player entity) {
            if (!LastDayGames.isPvpStatus()) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!LastDayGames.isPvpStatus()) {
                event.setCancelled(true);
            }
        }
    }
}