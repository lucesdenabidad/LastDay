package datta.core.paper.utilities;

import datta.core.paper.Core;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.utilities.Color.co;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class Utils {

    public static Location center(Location location) {

        World world = location.getWorld();
        double x = location.getBlockX() + 0.5;
        double y = location.getBlockY();
        double z = location.getBlockZ() + 0.5;

        Location centerLocation = new Location(world, x, y, z);
        return centerLocation;
    }

    public static Location[] sLocation(String input) {
        String[] parts = input.split("\\s+");

        if (parts.length != 6) {
            return null; // La entrada no tiene el formato correcto
        }

        try {
            int x1 = Integer.parseInt(parts[0]);
            int y1 = Integer.parseInt(parts[1]);
            int z1 = Integer.parseInt(parts[2]);

            int x2 = Integer.parseInt(parts[3]);
            int y2 = Integer.parseInt(parts[4]);
            int z2 = Integer.parseInt(parts[5]);

            World world = Bukkit.getWorlds().get(0); // Obtener el primer mundo, ajusta esto según tu configuración
            Location pos1 = new Location(world, x1, y1, z1);
            Location pos2 = new Location(world, x2, y2, z2);

            return new Location[]{pos1, pos2};
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null; // Error al convertir la entrada a números
        }
    }

    /* Posibles input */
/*    private final List<Material> materials = List.of(
            Material.WHITE_WOOL,
            Material.ORANGE_WOOL,
            Material.MAGENTA_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.YELLOW_WOOL,
            Material.LIME_WOOL,
            Material.PINK_WOOL,
            Material.GRAY_WOOL,
            Material.LIGHT_GRAY_WOOL,
            Material.CYAN_WOOL,
            Material.PURPLE_WOOL,
            Material.BLUE_WOOL,
            Material.BROWN_WOOL,
            Material.GREEN_WOOL,
            Material.RED_WOOL,
            Material.BLACK_WOOL
    );*/

    public static String translateMaterialToSpanish(Material material) {
        String string = material.toString();
        string = string.replace("_WOOL", "");
        switch (string.toUpperCase()) {
            case "WHITE":
                return "Blanco";
            case "ORANGE":
                return "Naranja";
            case "MAGENTA":
                return "Magenta";
            case "LIGHT_BLUE":
                return "Azul claro";
            case "YELLOW":
                return "Amarillo";
            case "LIME":
                return "Verde lima";
            case "PINK":
                return "Rosa";
            case "GRAY":
                return "Gris";
            case "LIGHT_GRAY":
                return "Gris claro";
            case "CYAN":
                return "Cian";
            case "PURPLE":
                return "Púrpura";
            case "BLUE":
                return "Azul";
            case "BROWN":
                return "Marrón";
            case "GREEN":
                return "Verde";
            case "RED":
                return "Rojo";
            case "BLACK":
                return "Negro";
            default:
                return "desconocido"; // Si el color no está en la lista
        }
    }

    public static ItemStack buildItemSkull(String name, String owner, int amount) {
        ItemStack Item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta ItemM = (SkullMeta) Item.getItemMeta();
        ItemM.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        ItemM.setOwner(owner);
        Item.setItemMeta(ItemM);
        return Item;
    }

    public static List<Location> createCylinder2(Location base, double radius, int quantity) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            double angle, x, z;
            angle = 2 * Math.PI * i / quantity;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            locations.add(base.clone().add(x, 0, z));
        }
        return locations;
    }

    public static Location genLocationInPos(Location pos1, Location pos2, boolean mantenerY) {
        World world = pos1.getWorld();

        double x = randomDouble(pos1.getX(), pos2.getX());
        double y = mantenerY ? pos1.getY() : randomDouble(pos1.getY(), pos2.getY());
        double z = randomDouble(pos1.getZ(), pos2.getZ());

        return center(new Location(world, x, y, z));
    }

    private static double randomDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }


    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    static int countdownTask;


    public static void countdown(int time) {
        countdown(time, () -> {
        });
    }

    public static boolean isInRegion(Location loc, Location pos1, Location pos2) {
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());

        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return loc.getX() >= minX && loc.getX() <= maxX
                && loc.getY() >= minY && loc.getY() <= maxY
                && loc.getZ() >= minZ && loc.getZ() <= maxZ;
    }

    public static void countdown(int time, Runnable runnable) {
        int[] countdownTime = new int[]{time};
        countdownTask = Bukkit.getScheduler().runTaskTimer(Core.getInstance(), () -> {
            if (countdownTime[0] > 0) {
                Bukkit.getOnlinePlayers().forEach(t -> t.sendTitle(co("&a&l" + countdownTime[0]), ""));
                Bukkit.broadcastMessage(co("&e&lEvento &8> &fEl nivel inicia en &a" + countdownTime[0] + "&f."));
                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2));
                countdownTime[0]--;
            } else {
                runnable.run();
                Bukkit.broadcastMessage(co("&e&lEvento &8> &f¡Buena suerte!"));
                Bukkit.getOnlinePlayers().forEach(t -> t.sendTitle(co("&a&l¡YA!"), co("&8> &f¡Buena suerte! &8<")));
                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 2));
                Bukkit.getScheduler().cancelTask(countdownTask);
            }
        }, 0L, 20L).getTaskId();
    }

    public static void eliminateDeath(Player player) {
        Bukkit.broadcastMessage(co("&e&lEvento &8> &f¡Se ha eliminado a &a" + player.getName() + "&f!"));
        player.getLocation().getWorld().spigot().strikeLightningEffect(player.getLocation(), false);
        player.setHealth(0);
        player.removeMetadata("palitoxdddd", Core.getInstance());
    }

    public static void eliminate(Player player) {
        Bukkit.broadcastMessage(co("&e&lEvento &8> &f¡Se ha eliminado a &a" + player.getName() + "&f!"));
        player.getLocation().getWorld().spigot().strikeLightningEffect(player.getLocation(), false);
        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&c¡Gracias por participar!"));
        player.removeMetadata("palitoxdddd", Core.getInstance());
    }

    public static DyeColor woolToDyeColor(Material material) {
        switch (material) {
            case WHITE_WOOL:
                return DyeColor.WHITE;
            case ORANGE_WOOL:
                return DyeColor.ORANGE;
            case MAGENTA_WOOL:
                return DyeColor.MAGENTA;
            case LIGHT_BLUE_WOOL:
                return DyeColor.LIGHT_BLUE;
            case YELLOW_WOOL:
                return DyeColor.YELLOW;
            case LIME_WOOL:
                return DyeColor.LIME;
            case PINK_WOOL:
                return DyeColor.PINK;
            case GRAY_WOOL:
                return DyeColor.GRAY;
            case LIGHT_GRAY_WOOL:
                return DyeColor.LIGHT_GRAY;
            case CYAN_WOOL:
                return DyeColor.CYAN;
            case PURPLE_WOOL:
                return DyeColor.PURPLE;
            case BLUE_WOOL:
                return DyeColor.BLUE;
            case BROWN_WOOL:
                return DyeColor.BROWN;
            case GREEN_WOOL:
                return DyeColor.GREEN;
            case RED_WOOL:
                return DyeColor.RED;
            case BLACK_WOOL:
                return DyeColor.BLACK;
            default:
                return null;
        }
    }

    public static ChatColor dyeToColor(DyeColor dyeColor) {
        switch (dyeColor) {
            case WHITE:
                return ChatColor.WHITE;
            case ORANGE:
                return ChatColor.GOLD;
            case MAGENTA:
                return ChatColor.LIGHT_PURPLE;
            case LIGHT_BLUE:
                return ChatColor.BLUE;
            case YELLOW:
                return ChatColor.YELLOW;
            case LIME:
                return ChatColor.GREEN;
            case PINK:
                return ChatColor.LIGHT_PURPLE;
            case GRAY:
                return ChatColor.GRAY;
            case LIGHT_GRAY:
                return ChatColor.DARK_GRAY;
            case CYAN:
                return ChatColor.AQUA;
            case PURPLE:
                return ChatColor.DARK_PURPLE;
            case BLUE:
                return ChatColor.DARK_BLUE;
            case BROWN:
                return ChatColor.GOLD;
            case GREEN:
                return ChatColor.DARK_GREEN;
            case RED:
                return ChatColor.RED;
            case BLACK:
                return ChatColor.BLACK;
            default:
                return ChatColor.RESET;
        }
    }

    public static String getHealthBar(Player player) {
        int maxHealth = 20;
        int health = (int) player.getHealth();
        int missingHearts = maxHealth / 2 - health / 2;

        StringBuilder healthBar = new StringBuilder();
        for (int i = 0; i < health / 2; i++) {
            healthBar.append("❤");
        }
        for (int i = 0; i < missingHearts; i++) {
            healthBar.append("&7❤");
        }

        return healthBar.toString();
    }

    public static String getFoodBar(Player player) {
        int foodLevel = player.getFoodLevel();
        int maxFoodLevel = 20;
        int heartsPerFood = maxFoodLevel / 2;

        StringBuilder foodBar = new StringBuilder();
        for (int i = 0; i < foodLevel / 2; i++) {
            foodBar.append("🍖");
        }
        for (int i = 0; i < (maxFoodLevel - foodLevel) / 2; i++) {
            foodBar.append("🍖");
        }

        return foodBar.toString();
    }
}