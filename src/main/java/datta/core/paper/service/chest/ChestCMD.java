package datta.core.paper.service.chest;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import datta.core.paper.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.service.chest.ChestEvent.spawnLight;
import static datta.core.paper.utilities.Color.locationToString;
import static datta.core.paper.utilities.player.PlayerUtil.*;


@CommandPermission("core.admin")
@CommandAlias("chest|chestloot|lootchest")
public class ChestCMD extends BaseCommand {

    @Subcommand("loot set normal")
    public void lootset(Player player) {
        Block targetBlock = player.getTargetBlock(null, 10);
        if (targetBlock != null && targetBlock.getState() instanceof Chest) {
            Chest chest = (Chest) targetBlock.getState();
            Inventory chestInventory = chest.getInventory();
            ItemStack[] chestContents = chestInventory.getContents().clone();

            List<ItemStack> content = new ArrayList<>();
            for (ItemStack itemStack : chestContents) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    content.add(itemStack.clone());
                }
            }

            Core.getInstance().getConfig().set("chest.loot", content);
            Core.getInstance().getConfig().safeSave();

            ChestEvent.items = content;

            send(player, "&e&lEvento &8» &fEl loot se ha actualizado.");
        } else {
            send(player, "&e&lEvento &8» &fNo estas mirando un cofre.");
        }
    }

    @Subcommand("loot set airdrop")
    public void loot(Player player) {
        Block targetBlock = player.getTargetBlock(null, 10);
        if (targetBlock != null && targetBlock.getState() instanceof Chest) {
            Chest chest = (Chest) targetBlock.getState();
            Inventory chestInventory = chest.getInventory();
            ItemStack[] chestContents = chestInventory.getContents().clone();

            List<ItemStack> content = new ArrayList<>();
            for (ItemStack itemStack : chestContents) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    content.add(itemStack.clone());
                }
            }

            Core.getInstance().getConfig().set("chest.drop", content);
            Core.getInstance().getConfig().safeSave();

            ChestEvent.dropItems = content;

            send(player, "&e&lEvento &8» &fEl loot se ha actualizado.");
        } else {
            send(player, "&e&lEvento &8» &fNo estas mirando un cofre.");
        }
    }


    @Subcommand("loot generate")
    public void generate(Player player) {
        Location location = player.getLocation();
        Block block = location.getBlock();
        block.setType(Material.ENDER_CHEST);

        ChestEvent.fillDrop(block);
        String s = locationToString(block.getLocation());

        for (Player t : Bukkit.getOnlinePlayers()) {
            sendTitle(t, "&a&lDROP&a!", "&8> &f¡Drop en &e" + s + "&f! &8<");
            sendSound(t, Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 2);
            send(t,
                    "",
                    "&b&lNotificacion de simulacion",
                    "&fSe genero un airdrop",
                    "&ffueron &eregenerados&f, por lo tanto puedes volver",
                    "&fa abrirlos.",
                    "");
        }

        spawnLight(location);

        int contador = 60;
        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                if (counter == contador) {
                    block.setType(Material.AIR);
                    cancel();
                }

                spawnLight(location);
                counter++;
            }
        }.runTaskTimer(Core.getInstance(), 0L, 20L);
    }


    @Subcommand("refill")
    public void refill(Player player) {

        ChestEvent.blocks.clear();

        for (Player t : Bukkit.getOnlinePlayers()) {
            sendTitle(t, "&a&lEVENTO&a!", "&8> &f¡Cofres rellenados! &8<");
            sendSound(t, Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 2);
            send(t,
                    "",
                    "&b&lNotificacion de simulacion",
                    "&fTodos los cofres del mapa que han sido abiertos",
                    "&ffueron &eregenerados&f, por lo tanto puedes volver",
                    "&fa abrirlos.",
                    "");
        }

        send(player, "&e&lEvento &8» &fRellanaste los cofres.");
    }


    @Subcommand("toggle")
    public void toggle(Player player){
        ChestEvent.status = !ChestEvent.status;

        send(player, "&e&lEvento &8» &fEstado de cofres modificado a &e"+ ChestEvent.status+"&f.");
    }

}
