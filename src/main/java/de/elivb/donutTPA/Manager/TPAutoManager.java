package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.TPA;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class TPAutoManager {
   private final TPA plugin;
   private final Set<UUID> autoAcceptPlayers = new HashSet();

   public TPAutoManager(TPA plugin) {
      this.plugin = plugin;
   }

   public void loadAutoAcceptStatus() {
      this.autoAcceptPlayers.clear();

      for(String uuidString : this.plugin.getDataManager().getAutoAcceptList()) {
         try {
            this.autoAcceptPlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var4) {
         }
      }

   }

   public boolean isAutoAcceptEnabled(Player player) {
      return this.autoAcceptPlayers.contains(player.getUniqueId());
   }

   public void toggleAutoAccept(Player player) {
      UUID playerId = player.getUniqueId();
      if (this.autoAcceptPlayers.contains(playerId)) {
         this.autoAcceptPlayers.remove(playerId);
         player.sendMessage(this.plugin.getLanguageManager().getMessage("tpa-auto-disabled"));
      } else {
         this.autoAcceptPlayers.add(playerId);
         player.sendMessage(this.plugin.getLanguageManager().getMessage("tpa-auto-enabled"));
      }

      this.saveAutoAcceptStatus(playerId);
   }

   private void saveAutoAcceptStatus(UUID playerId) {
      boolean auto = this.autoAcceptPlayers.contains(playerId);
      this.plugin.getDataManager().updateAutoAcceptStatus(playerId, auto);
   }

   public void removePlayer(UUID playerId) {
      this.autoAcceptPlayers.remove(playerId);
      this.plugin.getDataManager().updateAutoAcceptStatus(playerId, false);
   }

   public Set<UUID> getAutoAcceptPlayers() {
      return this.autoAcceptPlayers;
   }
}
