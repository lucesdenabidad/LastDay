package datta.core.paper.service.chest;

import datta.core.paper.Core;
import datta.core.paper.utilities.Color;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static datta.core.paper.utilities.Utils.center;

public class ChestEvent implements Listener {
    static List<ItemStack> items = new ArrayList<>((List<ItemStack>) Core.getInstance().getConfig().getList("chest.loot", new ArrayList<>()));

    static List<ItemStack> dropItems = new ArrayList<>((List<ItemStack>) Core.getInstance().getConfig().getList("chest.drop", new ArrayList<>()));
    public static List<Block> blocks = new ArrayList<>();
    public static boolean status = true;

    @EventHandler
    public void canceledChest(PlayerInteractEvent event) {
        if (status) {
            if (event.getPlayer().getGameMode() != GameMode.SPECTATOR) {

                if (event.hasBlock()) {
                    Block clickedBlock = event.getClickedBlock();
                    if (clickedBlock.getType().toString().contains("CHEST")) {
                        Location location = clickedBlock.getLocation();

                        if (!blocks.contains(clickedBlock)) {
                            Chest state = (Chest) clickedBlock.getState();
                            Inventory blockInventory = state.getBlockInventory();
                            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.TRAPPED_CHEST){
                            fillInventory(clickedBlock);
                            }

                            if (clickedBlock.getType() == Material.ENDER_CHEST){
                                fillDrop(clickedBlock);
                            }


                        } else {
                            Player player = event.getPlayer();
                            player.sendMessage(Color.co("&e&lEvento &8» &fEste cofre ya fue abierto."));

                        }

                        event.setCancelled(true);
                    }
                }
            }
        }
    }



    public static void spawnLight(Location location) {
        Location clone = location.getWorld().getHighestBlockAt(location).getLocation().clone();
        clone.subtract(0, 5, 0);
        Block block = clone.getBlock();
        block.setType(Material.AIR);
        block.setType(Material.END_GATEWAY);
    }

    public static void fillDrop(Block block) {
        ItemStack[] itemStacks = genContent(dropItems.size(), dropItems.size(), dropItems);
        World world = block.getWorld();
        Location location = block.getLocation().add(0, 0.5, 0);
        Location center = center(location).add(0, 1, 0);

        for (ItemStack itemStack : itemStacks) {
            Item item = world.dropItem(center, itemStack);
            item.setVelocity(new Vector(0, 0.30, 0));
        }

        world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1, 2);
        world.spawnParticle(Particle.CLOUD, center(location.add(0, 1, 0)), 5, 1, 1, 1, 0);

        blocks.add(block);
    }

    public static void fillInventory(Block block) {
        ItemStack[] itemStacks = genContent(5, 10, items);
        World world = block.getWorld();
        Location location = block.getLocation().add(0, 0.5, 0);
        Location center = center(location).add(0, 1, 0);

        for (ItemStack itemStack : itemStacks) {
            Item item = world.dropItem(center, itemStack);
            item.setVelocity(new Vector(0, 0.30, 0));
        }
        world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1, 2);
        world.spawnParticle(Particle.CLOUD, center(location.add(0,1,0)), 5, 1, 1, 1, 0);

        blocks.add(block);
    }


    public static ItemStack[] genContent(int minItems, int maxItems, List<ItemStack> list) {
        if (minItems <= 0 || maxItems <= 0 || minItems > maxItems) {
            throw new IllegalArgumentException("Argumentos inválidos para genContent.");
        }

        ItemStack[] content = new ItemStack[new Random().nextInt(maxItems - minItems + 1) + minItems];

        for (int i = 0; i < content.length; i++) {
            ItemStack randomItem = getRandomItem(list);
            if (randomItem != null) {
                content[i] = randomItem;
            }
        }

        return content;
    }

    private static ItemStack getRandomItem(List<ItemStack> items) {
        if (items.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(items.size());
        return items.get(randomIndex).clone();
    }
}
