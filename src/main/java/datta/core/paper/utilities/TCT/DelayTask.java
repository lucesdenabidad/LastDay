package datta.core.paper.utilities.TCT;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Utility Runnable that eases the creation of delayed tasks.
 * 
 * @author jcedeno
 */
public class DelayTask {
    private static Plugin plugin = null;
    private int id = -1;

    public DelayTask(Plugin instance){
        this.plugin = instance;
    }
    public DelayTask(Runnable runnable){
        this(0, runnable);
    }

    public DelayTask(long delay, Runnable runnable){
       if (plugin.isEnabled()){
           id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
       }else{
           runnable.run();
       }
    }
}
