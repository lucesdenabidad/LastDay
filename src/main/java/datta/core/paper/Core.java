package datta.core.paper;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import datta.core.paper.commands.GlobalCMD;
import datta.core.paper.events.MessagesEvent;
import datta.core.paper.games.lastday.LastDayCMD;
import datta.core.paper.games.lastday.LastDayEvents;
import datta.core.paper.games.lastday.LasyDayECloud;
import datta.core.paper.games.lastday.teams.EquipoEvents;
import datta.core.paper.games.lastday.teams.Equipos;
import datta.core.paper.service.PlayerReset;
import datta.core.paper.service.Slots;
import datta.core.paper.service.chest.ChestCMD;
import datta.core.paper.service.chest.ChestEvent;
import datta.core.paper.task.BroadcastSenderTask;
import datta.core.paper.utilities.MenuBuilder;
import datta.core.paper.utilities.configuration.Configuration;
import datta.core.paper.utilities.configuration.ConfigurationManager;
import datta.core.paper.utilities.services.Timer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static datta.core.paper.utilities.Utils.center;


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
        spawn = center(Bukkit.getWorlds().get(0).getSpawnLocation());

        // HOLY
        BroadcastSenderTask.start();

        //LISTENERS
        register(new MessagesEvent());

        // SERVICES
        registerBoth(new Slots());
        PlayerReset playerReset = new PlayerReset();
        playerReset.hook();

        // GAME
        register(new LastDayCMD());
        register(new LastDayEvents());

        register(new ChestCMD());
        register(new ChestEvent());


        registerBoth(new Equipos());
        register(new EquipoEvents());

        //COMMANDS
        commandManager.registerCommand(new GlobalCMD());

        new LasyDayECloud().register();
    }

    @Override
    public void onDisable() {
        Timer.removeBar();
        new LasyDayECloud().unregister();
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