package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.TPA;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;

public class GUIManager {
   private final TPA plugin;
   private final Map<String, YamlConfiguration> guiConfigs = new HashMap();

   public GUIManager(TPA plugin) {
      this.plugin = plugin;
   }

   public void loadGUIConfigs() {
      File guiFolder = new File(this.plugin.getDataFolder(), "gui");
      if (!guiFolder.exists()) {
         guiFolder.mkdirs();
      }

      this.createDefaultGUI("gui/tpa-accept.yml");
      this.createDefaultGUI("gui/tpa-here-accept.yml");
      this.createDefaultGUI("gui/tpa-send.yml");
      this.createDefaultGUI("gui/tpa-here-send.yml");
      File[] files = guiFolder.listFiles((dir, namex) -> namex.endsWith(".yml"));
      if (files != null) {
         for(File file : files) {
            String name = file.getName().replace(".yml", "");
            String internalName = this.convertToInternalName(name);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            this.guiConfigs.put(internalName, config);
         }
      }

   }

   private void createDefaultGUI(String resourcePath) {
      File targetFile = new File(this.plugin.getDataFolder(), resourcePath);
      if (!targetFile.exists()) {
         YamlConfiguration config = new YamlConfiguration();
         if (resourcePath.equals("gui/tpa-accept.yml")) {
            config.set("name", "&8xáᴄ ɴʜậɴ ᴛᴘᴀ");
            config.set("rows", 3);
            config.set("icons.cancel-icon.slot", 10);
            config.set("icons.cancel-icon.material", "RED_STAINED_GLASS_PANE");
            config.set("icons.cancel-icon.display-name", "&#FF0000ʜủʏ");
            config.set("icons.cancel-icon.lore", List.of("&fNhấn để từ chối yêu cầu TPA"));
            config.set("icons.location-icon.slot", 12);
            config.set("icons.location-icon.material", "GRASS_BLOCK");
            config.set("icons.location-icon.display-name", "&#00f986ᴠị ᴛʀí");
            config.set("icons.location-icon.lore", List.of("&7%world%"));
            config.set("icons.player-icon.slot", 13);
            config.set("icons.player-icon.material", "PLAYER_HEAD");
            config.set("icons.player-icon.display-name", "&#00f986ɴɢườɪ ᴄʜơɪ");
            config.set("icons.player-icon.lore", List.of("&7%player%"));
            config.set("icons.confirm-icon.slot", 16);
            config.set("icons.confirm-icon.material", "LIME_STAINED_GLASS_PANE");
            config.set("icons.confirm-icon.display-name", "&#00FF00xáᴄ ɴʜậɴ");
            config.set("icons.confirm-icon.lore", List.of("&fNhấn để chấp nhận yêu cầu dịch chuyển từ %player%"));
            config.set("icons.fly-icon.slot", 14);
            config.set("icons.fly-icon.material", "FEATHER");
            config.set("icons.fly-icon.display-name", "&#00f986đᴀɴɢ ʙᴀʏ");
            config.set("icons.fly-icon.lore", List.of("&7%is_flying%"));
         } else if (resourcePath.equals("gui/tpa-here-accept.yml")) {
            config.set("name", "&8xáᴄ ɴʜậɴ ᴛᴘᴀ ʜᴇʀᴇ");
            config.set("rows", 3);
            config.set("icons.cancel-icon.slot", 10);
            config.set("icons.cancel-icon.material", "RED_STAINED_GLASS_PANE");
            config.set("icons.cancel-icon.display-name", "&#FF0000ʜủʏ");
            config.set("icons.cancel-icon.lore", List.of("&fNhấn để huỷ"));
            config.set("icons.location-icon.slot", 12);
            config.set("icons.location-icon.material", "GRASS_BLOCK");
            config.set("icons.location-icon.display-name", "&#00f986ᴠị ᴛʀí");
            config.set("icons.location-icon.lore", List.of("&7%world%"));
            config.set("icons.player-icon.slot", 13);
            config.set("icons.player-icon.material", "PLAYER_HEAD");
            config.set("icons.player-icon.display-name", "&#00f986ɴɢườɪ ᴄʜơɪ");
            config.set("icons.player-icon.lore", List.of("&7%player%"));
            config.set("icons.confirm-icon.slot", 16);
            config.set("icons.confirm-icon.material", "LIME_STAINED_GLASS_PANE");
            config.set("icons.confirm-icon.display-name", "&#00FF00xáᴄ ɴʜậɴ");
            config.set("icons.confirm-icon.lore", List.of("&fNhấn để chấp nhận yêu cầu TPA Here từ %player%"));
            config.set("icons.fly-icon.slot", 14);
            config.set("icons.fly-icon.material", "FEATHER");
            config.set("icons.fly-icon.display-name", "&#00f986đᴀɴɢ ʙᴀʏ");
            config.set("icons.fly-icon.lore", List.of("&7%is_flying%"));
         } else if (resourcePath.equals("gui/tpa-send.yml")) {
            config.set("name", "&8ɢửɪ ʏêᴜ ᴄầᴜ ᴛᴘᴀ");
            config.set("rows", 3);
            config.set("icons.cancel-icon.slot", 10);
            config.set("icons.cancel-icon.material", "RED_STAINED_GLASS_PANE");
            config.set("icons.cancel-icon.display-name", "&#FF0000ʜủʏ");
            config.set("icons.cancel-icon.lore", List.of("&fNhấn để từ chối"));
            config.set("icons.location-icon.slot", 12);
            config.set("icons.location-icon.material", "GRASS_BLOCK");
            config.set("icons.location-icon.display-name", "&#00f986ᴠị ᴛʀí");
            config.set("icons.location-icon.lore", List.of("&7%world%"));
            config.set("icons.player-icon.slot", 13);
            config.set("icons.player-icon.material", "PLAYER_HEAD");
            config.set("icons.player-icon.display-name", "&#00f986ɴɢườɪ ᴄʜơɪ");
            config.set("icons.player-icon.lore", List.of("&7%player%"));
            config.set("icons.confirm-icon.slot", 16);
            config.set("icons.confirm-icon.material", "LIME_STAINED_GLASS_PANE");
            config.set("icons.confirm-icon.display-name", "&#00FF00xáᴄ ɴʜậɴ");
            config.set("icons.confirm-icon.lore", List.of("&fNhấn để gửi yêu cầu dịch chuyển đến %player%"));
            config.set("icons.fly-icon.slot", 14);
            config.set("icons.fly-icon.material", "FEATHER");
            config.set("icons.fly-icon.display-name", "&#00f986đᴀɴɢ ʙᴀʏ");
            config.set("icons.fly-icon.lore", List.of("&7%is_flying%"));
         } else if (resourcePath.equals("gui/tpa-here-send.yml")) {
            config.set("name", "&8ɢửɪ ʏêᴜ ᴄầᴜ ᴛᴘᴀ ʜᴇʀᴇ");
            config.set("rows", 3);
            config.set("icons.cancel-icon.slot", 10);
            config.set("icons.cancel-icon.material", "RED_STAINED_GLASS_PANE");
            config.set("icons.cancel-icon.display-name", "&#FF0000ʜủʏ");
            config.set("icons.cancel-icon.lore", List.of("&fNhấn để huỷ dịch chuyển"));
            config.set("icons.location-icon.slot", 12);
            config.set("icons.location-icon.material", "GRASS_BLOCK");
            config.set("icons.location-icon.display-name", "&#00f986ᴠị ᴛʀí");
            config.set("icons.location-icon.lore", List.of("&7%world%"));
            config.set("icons.player-icon.slot", 13);
            config.set("icons.player-icon.material", "PLAYER_HEAD");
            config.set("icons.player-icon.display-name", "&#00f986ɴɢườɪ ᴄʜơɪ");
            config.set("icons.player-icon.lore", List.of("&7%player%"));
            config.set("icons.confirm-icon.slot", 16);
            config.set("icons.confirm-icon.material", "LIME_STAINED_GLASS_PANE");
            config.set("icons.confirm-icon.display-name", "&#00FF00xáᴄ ɴʜậɴ");
            config.set("icons.confirm-icon.lore", List.of("&fNhấn để xác nhận"));
            config.set("icons.fly-icon.slot", 14);
            config.set("icons.fly-icon.material", "FEATHER");
            config.set("icons.fly-icon.display-name", "&#00f986đᴀɴɢ ʙᴀʏ");
            config.set("icons.fly-icon.lore", List.of("&7%is_flying%"));
         }

         try {
            config.save(targetFile);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }

   private String convertToInternalName(String fileName) {
      switch (fileName) {
         case "tpa-accept" -> {
            return "tpa_accept_gui";
         }
         case "tpa-here-accept" -> {
            return "tpa_here_accept_gui";
         }
         case "tpa-send" -> {
            return "tpa_send_gui";
         }
         case "tpa-here-send" -> {
            return "tpa_here_send_gui";
         }
         default -> {
            return fileName;
         }
      }
   }

   public void reloadGUIConfigs() {
      this.guiConfigs.clear();
      this.loadGUIConfigs();
   }

   public YamlConfiguration getGUIConfig(String name) {
      return (YamlConfiguration)this.guiConfigs.get(name);
   }

   public String getGUIName(String guiName, String defaultName) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? defaultName : config.getString("name", defaultName);
   }

   public int getGUIRows(String guiName, int defaultRows) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? defaultRows : config.getInt("rows", defaultRows);
   }

   public int getIconSlot(String guiName, String iconName, int defaultSlot) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? defaultSlot : config.getInt("icons." + iconName + ".slot", defaultSlot);
   }

   public String getIconMaterial(String guiName, String iconName, String defaultMaterial) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? defaultMaterial : config.getString("icons." + iconName + ".material", defaultMaterial);
   }

   public String getIconDisplayName(String guiName, String iconName, String defaultName) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? defaultName : config.getString("icons." + iconName + ".display-name", defaultName);
   }

   public List<String> getIconLore(String guiName, String iconName) {
      YamlConfiguration config = (YamlConfiguration)this.guiConfigs.get(guiName);
      return config == null ? Collections.emptyList() : config.getStringList("icons." + iconName + ".lore");
   }
}
