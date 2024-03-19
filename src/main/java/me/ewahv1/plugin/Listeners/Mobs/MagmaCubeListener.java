package me.ewahv1.plugin.Listeners.Mobs;

import me.ewahv1.plugin.Database.Connection;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MagmaCubeListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof MagmaCube && event.getEntity() instanceof Player) {
            try {
                PreparedStatement ps = Connection.getConnection().prepareStatement("SELECT Punch FROM magmacube_settings WHERE ID = ?");
                ps.setInt(1, ((MagmaCube) event.getDamager()).getEntityId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int punch = rs.getInt("Punch");
                    if (punch > 0) {
                        Vector velocity = event.getEntity().getVelocity();
                        Vector punchDirection = event.getDamager().getLocation().getDirection().multiply(punch);
                        velocity.add(punchDirection);
                        event.getEntity().setVelocity(velocity);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
