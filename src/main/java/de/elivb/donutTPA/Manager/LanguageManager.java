package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.Hex;
import de.elivb.donutTPA.TPA;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageManager {
   private final TPA plugin;
   private FileConfiguration messages;
   private File messagesFile;
   private boolean prefixEnabled;
   private String prefix;

   public LanguageManager(TPA plugin) {
      this.plugin = plugin;
      this.messagesFile = new File(plugin.getDataFolder(), "lang.yml");
   }

   public void loadMessages() {
      if (!this.messagesFile.exists()) {
         this.plugin.saveResource("lang.yml", false);
      }

      this.messages = YamlConfiguration.loadConfiguration(this.messagesFile);

      try {
         Reader defaultStream = new InputStreamReader(this.plugin.getResource("lang.yml"), StandardCharsets.UTF_8);
         YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultStream);
         this.messages.setDefaults(defaultConfig);
      } catch (Exception var3) {
      }

      this.prefixEnabled = this.messages.getBoolean("prefix-enable", true);
      this.prefix = Hex.translateAllColorCodes(this.messages.getString("prefix", "&#00f986ᴛᴘᴀ &7»"));
   }

   public String getMessage(String key) {
      String message = this.messages.getString("messages." + key);
      if (message == null) {
         this.plugin.getLogger().warning("Missing message: " + key);
         return null;
      } else {
         String formattedMessage = Hex.translateAllColorCodes(message);
         return this.prefixEnabled ? this.prefix + " " + formattedMessage : formattedMessage;
      }
   }

   public String getMessageWithoutPrefix(String key) {
      String message = this.messages.getString("messages." + key);
      if (message == null) {
         this.plugin.getLogger().warning("Missing message: " + key);
         return null;
      } else {
         return Hex.translateAllColorCodes(message);
      }
   }

   public String getActionBar(String key) {
      String actionBar = this.messages.getString("action-bars." + key);
      if (actionBar == null) {
         this.plugin.getLogger().warning("Missing actionbar: " + key);
         return null;
      } else {
         return Hex.translateAllColorCodes(actionBar);
      }
   }

   public String getCommandUsage() {
      String usage = this.messages.getString("command-usage");
      return usage == null ? "&#00f986&lTPA\n&#00f986➤ &f/tpa <người chơi>\n&#00f986➤ &f/tpahere <người chơi>\n&#00f986➤ &f/tpaccept\n&#00f986➤ &f/tpadeny\n&#00f986➤ &f/tpacancel\n&#00f986➤ &f/tpatoggle" : Hex.translateAllColorCodes(usage);
   }

   public String getAdminCommandUsage() {
      String usage = this.messages.getString("admin-command-usage");
      return usage == null ? "&#00f986&lTPA ADMIN\n&#00f986➤ &f/tpareload" : Hex.translateAllColorCodes(usage);
   }

   public FileConfiguration getMessages() {
      return this.messages;
   }

   public boolean isPrefixEnabled() {
      return this.prefixEnabled;
   }

   public String getPrefix() {
      return this.prefix;
   }
}
