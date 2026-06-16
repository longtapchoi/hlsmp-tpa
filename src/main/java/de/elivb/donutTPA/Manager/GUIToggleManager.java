package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.TPA;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class GUIToggleManager {
   private final TPA plugin;
   private final Set<UUID> guiModeDisabledPlayers = new HashSet();
   private final Set<UUID> guiModeTpaHereDisabledPlayers = new HashSet();

   public GUIToggleManager(TPA plugin) {
      this.plugin = plugin;
   }

   public void loadGuiModes() {
      this.guiModeDisabledPlayers.clear();
      this.guiModeTpaHereDisabledPlayers.clear();

      for(String uuidString : this.plugin.getDataManager().getGuiModeList(false)) {
         try {
            this.guiModeDisabledPlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var5) {
         }
      }

      for(String uuidString : this.plugin.getDataManager().getGuiModeList(true)) {
         try {
            this.guiModeTpaHereDisabledPlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var4) {
         }
      }

   }

   public boolean isGuiModeEnabled(Player player, boolean isTpaHere) {
      UUID playerId = player.getUniqueId();
      if (isTpaHere) {
         return !this.guiModeTpaHereDisabledPlayers.contains(playerId);
      } else {
         return !this.guiModeDisabledPlayers.contains(playerId);
      }
   }

   public void toggleGuiMode(Player player, boolean isTpaHere) {
      UUID playerId = player.getUniqueId();
      if (isTpaHere) {
         if (this.guiModeTpaHereDisabledPlayers.contains(playerId)) {
            this.guiModeTpaHereDisabledPlayers.remove(playerId);
            player.sendMessage(this.plugin.getLanguageManager().getMessage("tpahere-gui-enabled"));
         } else {
            this.guiModeTpaHereDisabledPlayers.add(playerId);
            player.sendMessage(this.plugin.getLanguageManager().getMessage("tpahere-gui-disabled"));
         }
      } else if (this.guiModeDisabledPlayers.contains(playerId)) {
         this.guiModeDisabledPlayers.remove(playerId);
         player.sendMessage(this.plugin.getLanguageManager().getMessage("tpa-gui-enabled"));
      } else {
         this.guiModeDisabledPlayers.add(playerId);
         player.sendMessage(this.plugin.getLanguageManager().getMessage("tpa-gui-disabled"));
      }

      this.saveGuiModes(playerId);
   }

   private void saveGuiModes(UUID playerId) {
      boolean tpaGuiDisabled = this.guiModeDisabledPlayers.contains(playerId);
      boolean tpahereGuiDisabled = this.guiModeTpaHereDisabledPlayers.contains(playerId);
      this.plugin.getDataManager().updateGuiModeStatus(playerId, tpaGuiDisabled, tpahereGuiDisabled);
   }

   public void removePlayer(UUID playerId) {
      this.guiModeDisabledPlayers.remove(playerId);
      this.guiModeTpaHereDisabledPlayers.remove(playerId);
      this.plugin.getDataManager().updateGuiModeStatus(playerId, false, false);
   }

   public Set<UUID> getGuiModePlayers() {
      return this.guiModeDisabledPlayers;
   }

   public Set<UUID> getGuiModeTpaHerePlayers() {
      return this.guiModeTpaHereDisabledPlayers;
   }
}
