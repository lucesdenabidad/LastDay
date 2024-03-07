package datta.core.paper.utilities.services;

import datta.core.paper.Core;
import datta.core.paper.utilities.Color;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static datta.core.paper.utilities.Color.formatTime;

public class Timer {
    public static BossBar bossBar;
    public static BukkitTask task;

    public static void timer(String title, BarColor barColor, BarStyle barStyle, int seconds, Runnable runnable) {
        JavaPlugin plugin = Core.getInstance();

        bossBar = Bukkit.createBossBar(Color.co(title), barColor, barStyle);
        final int[] totalTimeSeconds = {seconds};
        bossBar.setTitle(Color.co(title));
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(onlinePlayer);
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                String finalTitle = title.replace("{time}", formatTime(totalTimeSeconds[0]));
                double progress = (double) totalTimeSeconds[0] / (double) seconds;

                bossBar.setProgress(Math.max(0, Math.min(progress, 1)));
                bossBar.setTitle(Color.co(finalTitle));
                totalTimeSeconds[0]--;

                if (totalTimeSeconds[0] == 1) {
                    bossBar.setVisible(false);
                    bossBar.removeAll();
                    bossBar.setProgress(1.0);
                    bossBar.removeAll();
                    runnable.run();
                    bossBar = null;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20L);

    }

    public static void removeBar() {
        if (bossBar == null) {
            return;
        }

        bossBar.setVisible(false);
        bossBar.removeAll();
        bossBar = null;

        if (task != null){
            task.cancel();
        }
    }
}
