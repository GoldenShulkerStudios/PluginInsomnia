package me.ewahv1.plugin.Listeners.Trinkets;

import me.ewahv1.plugin.Database.DatabaseConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class WarmogListener implements Listener {
    private Map<UUID, Long> lastDamaged = new HashMap<>();
    private me.ewahv1.plugin.Main plugin;

    public WarmogListener(me.ewahv1.plugin.Main plugin) {
        this.plugin = plugin;
        startCheckingTask();
    }

    private void startCheckingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ItemStack item = player.getInventory().getItemInOffHand();
                    applyRegenerationEffect(player, item);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Comienza inmediatamente y se repite cada 20 ticks (1 segundo)
    }

    @EventHandler
    public void onRavagerDeath(EntityDeathEvent event) throws SQLException {
        if (event.getEntity() instanceof Ravager) {
            Ravager ravager = (Ravager) event.getEntity();
            if (ravager.getKiller() instanceof Player) {
                Random rand = new Random();
                int chance = rand.nextInt(100);
                if (chance < 3) {// Chance de ser dropeado el trinket
                    ItemStack warmogArmor;
                    int goldenChance = rand.nextInt(100);
                    if (goldenChance < 1) { // Chance de ser dorado
                        warmogArmor = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK, 1);
                        ItemMeta meta = warmogArmor.getItemMeta();
                        meta.setDisplayName("§6§lArmadura de Warmog dorada");
                        meta.setLore(Arrays.asList("§aAumenta tu salud máxima en +4❤", "§6§lSlot: Mano secundaria"));
                        meta.setCustomModelData(8);
                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        meta.addEnchant(Enchantment.DURABILITY, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        warmogArmor.setItemMeta(meta);

                        // Actualizar el contador dorado en la base de datos
                        Connection connection = DatabaseConnection.getConnection();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("UPDATE tri_count_settings SET CountGold = CountGold + 1 WHERE ID = 3");
                    } else {
                        warmogArmor = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK, 1);
                        ItemMeta meta = warmogArmor.getItemMeta();
                        meta.setDisplayName("§a§lArmadura de Warmog");
                        meta.setLore(Arrays.asList("§aAumenta tu salud máxima en +2❤", "§6§lSlot: Mano secundaria"));
                        meta.setCustomModelData(7);
                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        warmogArmor.setItemMeta(meta);

                        // Actualizar el contador normal en la base de datos
                        Connection connection = DatabaseConnection.getConnection();
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("UPDATE tri_count_settings SET CountNormal = CountNormal + 1 WHERE ID = 3");
                    }
                    event.getDrops().add(warmogArmor);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            lastDamaged.put(player.getUniqueId(), System.currentTimeMillis());
            player.removePotionEffect(PotionEffectType.REGENERATION);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack item = player.getInventory().getItemInOffHand();
                    applyRegenerationEffect(player, item);
                }
            }.runTaskLater(plugin, 20L * 6); // Espera 6 segundos antes de volver a aplicar el efecto
        }
    }

    private void applyRegenerationEffect(Player player, ItemStack item) {
        if (item.getType() == Material.SHULKER_SHELL) {
            long currentTime = System.currentTimeMillis();
            long lastTimeDamaged = lastDamaged.getOrDefault(player.getUniqueId(), 0L);
            if (currentTime - lastTimeDamaged > 6000) { // 6000 milisegundos = 6 segundos
                if (item.getItemMeta().getDisplayName().equals("§a§lArmadura de Warmog")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 0, true, false, true)); // Regeneración I
                } else if (item.getItemMeta().getDisplayName().equals("§6§lArmadura de Warmog dorada")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1, true, false, true)); // Regeneración II
                }
            }
        }
    }
}
