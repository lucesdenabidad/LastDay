package datta.core.paper;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import datta.core.paper.commands.GlobalCMD;
import datta.core.paper.events.MessagesEvent;
import datta.core.paper.service.PlayerReset;
import datta.core.paper.service.Slots;
import datta.core.paper.task.BroadcastSenderTask;
import datta.core.paper.utilities.MenuBuilder;
import datta.core.paper.utilities.configuration.Configuration;
import datta.core.paper.utilities.configuration.ConfigurationManager;
import datta.core.paper.utilities.score.ScoreHolder;
import datta.core.paper.utilities.services.Timer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {

    private static @Getter Core instance;
    private static @Getter PaperCommandManager commandManager;

    public static MenuBuilder menuBuilder;

    public ConfigurationManager configurationManager;
    @Getter
    public Configuration config;

    public static Location spawn;

    @Override
    public void onEnable() {
        instance = this;
        menuBuilder = new MenuBuilder(this);
        menuBuilder = new MenuBuilder(this);
        commandManager = new PaperCommandManager(this);
        configurationManager = new ConfigurationManager(this);
        config = configurationManager.getConfig("configuration.yml");
        spawn = Bukkit.getWorlds().get(0).getSpawnLocation().toCenterLocation();

        // HOLY
        BroadcastSenderTask.start();

        //SCORE
        ScoreHolder scoreHolder = new ScoreHolder(this,
                "&a&lCORE",
                "",
                "&fPlugin template",
                "&fby &7datta",
                "",
                "&ewww.holy.gg");
        scoreHolder.start(0, 20L);

        //LISTENERS
        register(new MessagesEvent());

        // SERVICES
        registerBoth(new Slots());
        PlayerReset playerReset = new PlayerReset();
        playerReset.hook();

        //COMMANDS
            commandManager.registerCommand(new GlobalCMD());
    }

    @Override
    public void onDisable() {
        Timer.removeBar();
    }

    public static void registerBoth(Object object) {
        register(object);
    }

    public static void register(Object object) {
        if (object instanceof BaseCommand) {
            commandManager.registerCommand((BaseCommand) object);
        } else if (object instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) object, Core.getInstance());
        } else if (object instanceof Listener && object instanceof BaseCommand) {
            Bukkit.getPluginManager().registerEvents((Listener) object, Core.getInstance());
            commandManager.registerCommand((BaseCommand) object);

        }
    }
}