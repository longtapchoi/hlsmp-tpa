package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.TPA;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
   private final TPA plugin;
   private File dataFile;
   private YamlConfiguration data;

   public DataManager(TPA plugin) {
      this.plugin = plugin;
      this.dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
      this.loadData();
   }

   private void loadData() {
      if (!this.dataFile.exists()) {
         this.data = new YamlConfiguration();
         this.saveData();
      } else {
         this.data = YamlConfiguration.loadConfiguration(this.dataFile);
      }

   }

   private void saveData() {
      try {
         this.data.save(this.dataFile);
      } catch (Exception var2) {
      }

   }

   public void loadToggleStatus(Set<UUID> disabledPlayers, Set<UUID> disabledTpaherePlayers) {
      disabledPlayers.clear();
      disabledTpaherePlayers.clear();

      for(String uuidString : this.data.getStringList("tpa-disabled")) {
         try {
            disabledPlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var7) {
         }
      }

      for(String uuidString : this.data.getStringList("tpahere-disabled")) {
         try {
            disabledTpaherePlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var6) {
         }
      }

   }

   public void saveToggleStatus(UUID playerId, boolean tpaDisabled, boolean tpahereDisabled) {
      this.updateTPAStatus(playerId, tpaDisabled);
      this.updateTPAHereStatus(playerId, tpahereDisabled);
   }

   public void updateTPAStatus(UUID playerId, boolean disabled) {
      List<String> tpaDisabled = this.data.getStringList("tpa-disabled");
      if (disabled) {
         if (!tpaDisabled.contains(playerId.toString())) {
            tpaDisabled.add(playerId.toString());
         }
      } else {
         tpaDisabled.remove(playerId.toString());
      }

      this.data.set("tpa-disabled", tpaDisabled);
      this.saveData();
   }

   public void updateTPAHereStatus(UUID playerId, boolean disabled) {
      List<String> tpahereDisabled = this.data.getStringList("tpahere-disabled");
      if (disabled) {
         if (!tpahereDisabled.contains(playerId.toString())) {
            tpahereDisabled.add(playerId.toString());
         }
      } else {
         tpahereDisabled.remove(playerId.toString());
      }

      this.data.set("tpahere-disabled", tpahereDisabled);
      this.saveData();
   }

   public void loadGuiModeStatus(Set<UUID> guiModePlayers, Set<UUID> guiModeTpaherePlayers) {
      guiModePlayers.clear();
      guiModeTpaherePlayers.clear();

      for(String uuidString : this.data.getStringList("tpa-gui-mode")) {
         try {
            guiModePlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var7) {
         }
      }

      for(String uuidString : this.data.getStringList("tpahere-gui-mode")) {
         try {
            guiModeTpaherePlayers.add(UUID.fromString(uuidString));
         } catch (IllegalArgumentException var6) {
         }
      }

   }

   public void updateGuiModeStatus(UUID playerId, boolean tpaGui, boolean tpahereGui) {
      List<String> tpaGuiList = this.data.getStringList("tpa-gui-mode");
      if (tpaGui) {
         if (!tpaGuiList.contains(playerId.toString())) {
            tpaGuiList.add(playerId.toString());
         }
      } else {
         tpaGuiList.remove(playerId.toString());
      }

      this.data.set("tpa-gui-mode", tpaGuiList);
      List<String> tpahereGuiList = this.data.getStringList("tpahere-gui-mode");
      if (tpahereGui) {
         if (!tpahereGuiList.contains(playerId.toString())) {
            tpahereGuiList.add(playerId.toString());
         }
      } else {
         tpahereGuiList.remove(playerId.toString());
      }

      this.data.set("tpahere-gui-mode", tpahereGuiList);
      this.saveData();
   }

   public List<String> getGuiModeList(boolean isTpaHere) {
      return isTpaHere ? this.data.getStringList("tpahere-gui-mode") : this.data.getStringList("tpa-gui-mode");
   }

   public boolean isGuiModeEnabled(UUID playerId, boolean isTpaHere) {
      return isTpaHere ? this.data.getStringList("tpahere-gui-mode").contains(playerId.toString()) : this.data.getStringList("tpa-gui-mode").contains(playerId.toString());
   }

   public List<String> getAutoAcceptList() {
      return this.data.getStringList("tpa-auto");
   }

   public void updateAutoAcceptStatus(UUID playerId, boolean auto) {
      List<String> autoList = this.data.getStringList("tpa-auto");
      if (auto) {
         if (!autoList.contains(playerId.toString())) {
            autoList.add(playerId.toString());
         }
      } else {
         autoList.remove(playerId.toString());
      }

      this.data.set("tpa-auto", autoList);
      this.saveData();
   }

   public boolean isAutoAcceptEnabled(UUID playerId) {
      return this.data.getStringList("tpa-auto").contains(playerId.toString());
   }

   public void removePlayer(UUID playerId) {
      this.updateTPAStatus(playerId, false);
      this.updateTPAHereStatus(playerId, false);
      this.updateGuiModeStatus(playerId, false, false);
      this.updateAutoAcceptStatus(playerId, false);
   }

   public boolean isTPADisabled(UUID playerId) {
      return this.data.getStringList("tpa-disabled").contains(playerId.toString());
   }

   public boolean isTPAHereDisabled(UUID playerId) {
      return this.data.getStringList("tpahere-disabled").contains(playerId.toString());
   }

   public void closeConnection() {
   }
}
