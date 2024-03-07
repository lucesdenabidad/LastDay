package datta.core.paper.games.lastday;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import datta.core.paper.games.lastday.enums.LastDayEnum;
import datta.core.paper.utilities.services.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;

import static datta.core.paper.utilities.Utils.center;
import static datta.core.paper.utilities.player.PlayerUtil.sendSound;
import static datta.core.paper.utilities.player.PlayerUtil.sendTitle;


@CommandPermission("core.admin")
@CommandAlias("lastday")
public class LastDayCMD extends BaseCommand {

    @Subcommand("start")
    public void start(LastDayEnum type) {
        type.getRunnable().run();
    }

    @Subcommand("pvp")
    public void spvp(boolean boo){
        LastDayGames.setPvp(boo);
    }
    
    @Subcommand("end")
    public void end(OnlinePlayer onlinePlayer){
        Player player = onlinePlayer.getPlayer();
        World world = player.getWorld();
        Location spawnLocation = world.getSpawnLocation();

        for (Player t : Bukkit.getOnlinePlayers()) {
            t.teleport(center(spawnLocation));
            sendTitle(t, "&a&lGANADOR&a!", "&8> &f¡&e"+player.getName()+"&f gano la simulación! &8<");
            sendSound(t, Sound.UI_TOAST_CHALLENGE_COMPLETE,1,1);
            t.setGameMode(GameMode.SURVIVAL);
            t.setHealth(20);
            t.setFoodLevel(20);
            t.getInventory().clear();
        }

        world.getWorldBorder().setSize(280);
        Timer.removeBar();
    }
}

