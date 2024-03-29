package me.ewahv1.plugin.Listeners.Difficulty.Mobs;
import me.ewahv1.plugin.Database.DatabaseConnection;
import me.ewahv1.plugin.Listeners.DayListener;
import org.bukkit.entity.Endermite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EndermiteListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Endermite) {
            Endermite endermite = (Endermite) event.getEntity();
            try {
                PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT Invulnerability, Strength FROM diff_endermite_settings WHERE ID = " + DayListener.getCurrentDay());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    boolean invulnerability = rs.getBoolean("Invulnerability");
                    int strength = rs.getInt("Strength");
                    if (invulnerability) {
                        endermite.setInvulnerable(true);
                    }
                    if (strength > 0) {
                        endermite.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, strength - 1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
