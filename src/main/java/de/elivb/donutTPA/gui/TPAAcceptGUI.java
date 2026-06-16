package de.elivb.donutTPA.gui;

import de.elivb.donutTPA.Hex;
import de.elivb.donutTPA.TPA;
import de.elivb.donutTPA.Manager.TPARequest;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;

public class TPAAcceptGUI implements Listener {
   private final TPA plugin;
   private final Player target;
   private final TPARequest request;
   private Inventory gui;
   private final String guiConfigName = "tpa_accept_gui";

   public TPAAcceptGUI(TPA plugin, Player target, TPARequest request) {
      this.plugin = plugin;
      this.target = target;
      this.request = request;
   }

   public void open() {
      String guiName = this.plugin.getGUIManager().getGUIName("tpa_accept_gui", "&8ᴛᴘᴀ ᴀᴄᴄᴇᴘᴛ");
      int rows = this.plugin.getGUIManager().getGUIRows("tpa_accept_gui", 3);
      this.gui = Bukkit.createInventory((InventoryHolder)null, rows * 9, Hex.translateAllColorCodes(guiName));
      ItemStack cancelItem = this.createIcon("cancel-icon", Material.RED_STAINED_GLASS_PANE);
      int cancelSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "cancel-icon", 10);
      this.gui.setItem(cancelSlot, cancelItem);
      ItemStack locationItem = this.createLocationIcon();
      int locationSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "location-icon", 12);
      this.gui.setItem(locationSlot, locationItem);
      ItemStack playerItem = this.createPlayerIcon();
      int playerSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "player-icon", 13);
      this.gui.setItem(playerSlot, playerItem);
      ItemStack confirmItem = this.createIcon("confirm-icon", Material.LIME_STAINED_GLASS_PANE);
      int confirmSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "confirm-icon", 16);
      this.gui.setItem(confirmSlot, confirmItem);
      ItemStack flyItem = this.createIcon("fly-icon", Material.FEATHER);
      int flySlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "fly-icon", 14);
      this.gui.setItem(flySlot, flyItem);
      PluginManager pm = this.plugin.getServer().getPluginManager();
      pm.registerEvents(this, this.plugin);
      this.target.openInventory(this.gui);
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getWhoClicked() instanceof Player) {
         if (event.getInventory().equals(this.gui)) {
            event.setCancelled(true);
            Player player = (Player)event.getWhoClicked();
            int slot = event.getSlot();
            int confirmSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "confirm-icon", 16);
            int cancelSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "cancel-icon", 10);
            int locationSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "location-icon", 12);
            int playerSlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "player-icon", 13);
            int flySlot = this.plugin.getGUIManager().getIconSlot("tpa_accept_gui", "fly-icon", 14);
            if (slot == confirmSlot || slot == cancelSlot || slot == locationSlot || slot == playerSlot || slot == flySlot) {
               this.plugin.getSoundManager().playButtonClickSound(player);
            }

            if (slot == confirmSlot) {
               player.closeInventory();
               this.plugin.handleTPAAcceptDirect(this.target, this.request);
            } else if (slot == cancelSlot) {
               player.closeInventory();
               this.plugin.handleTPADenyDirect(this.target, this.request);
            }

         }
      }
   }

   @EventHandler
   public void onInventoryClose(InventoryCloseEvent event) {
      if (event.getInventory().equals(this.gui)) {
         HandlerList.unregisterAll(this);
      }
   }

   private String getFlyStatus(boolean isFlying) {
      String path = "fly-status." + isFlying;
      String status = this.plugin.getConfigManager().getConfig().getString(path);
      if (status == null) {
         return isFlying ? "Có" : "Không";
      } else {
         return Hex.translateAllColorCodes(status);
      }
   }

   private ItemStack createIcon(String iconName, Material defaultMaterial) {
      String materialName = this.plugin.getGUIManager().getIconMaterial("tpa_accept_gui", iconName, defaultMaterial.name());
      Material material = Material.getMaterial(materialName);
      if (material == null) {
         material = defaultMaterial;
      }

      ItemStack item = new ItemStack(material);
      ItemMeta meta = item.getItemMeta();
      String displayName = this.plugin.getGUIManager().getIconDisplayName("tpa_accept_gui", iconName, (String)null);
      if (displayName != null) {
         String parsed = Hex.translateAllColorCodes(displayName);
         if (displayName.contains("%player%")) {
            Player sender = Bukkit.getPlayer(this.request.getSender());
            if (sender != null) {
               parsed = parsed.replace("%player%", sender.getName());
            }
         }

         meta.setDisplayName(parsed);
      }

      List<String> lore = this.plugin.getGUIManager().getIconLore("tpa_accept_gui", iconName);
      if (lore != null && !lore.isEmpty()) {
         List<String> translatedLore = new ArrayList();

         for(String line : lore) {
            String translated = Hex.translateAllColorCodes(line);
            if (line.contains("%player%")) {
               Player sender = Bukkit.getPlayer(this.request.getSender());
               if (sender != null) {
                  translated = translated.replace("%player%", sender.getName());
               }
            }

            if (line.contains("%world%")) {
               Player sender = Bukkit.getPlayer(this.request.getSender());
               if (sender != null) {
                  String worldName = sender.getWorld().getName();
                  String worldNickname = this.plugin.getConfigManager().getWorldNickname(worldName);
                  translated = translated.replace("%world%", Hex.translateAllColorCodes(worldNickname));
               }
            }

            if (line.contains("%is_flying%")) {
               Player sender = Bukkit.getPlayer(this.request.getSender());
               if (sender != null) {
                  String flyStatus = this.getFlyStatus(sender.isFlying());
                  translated = translated.replace("%is_flying%", flyStatus);
               }
            }

            translatedLore.add(translated);
         }

         meta.setLore(translatedLore);
      }

      item.setItemMeta(meta);
      return item;
   }

   private ItemStack createLocationIcon() {
      ItemStack item = new ItemStack(Material.GRASS_BLOCK);
      ItemMeta meta = item.getItemMeta();
      String displayName = this.plugin.getGUIManager().getIconDisplayName("tpa_accept_gui", "location-icon", "&#00f986ʟᴏᴄᴀᴛɪᴏɴ");
      meta.setDisplayName(Hex.translateAllColorCodes(displayName));
      Player sender = Bukkit.getPlayer(this.request.getSender());
      if (sender != null) {
         List<String> lore = this.plugin.getGUIManager().getIconLore("tpa_accept_gui", "location-icon");
         if (lore != null && !lore.isEmpty()) {
            List<String> translatedLore = new ArrayList();

            for(String line : lore) {
               String translated = Hex.translateAllColorCodes(line);
               if (line.contains("%world%")) {
                  String worldName = sender.getWorld().getName();
                  String worldNickname = this.plugin.getConfigManager().getWorldNickname(worldName);
                  translated = translated.replace("%world%", Hex.translateAllColorCodes(worldNickname));
               }

               translatedLore.add(translated);
            }

            meta.setLore(translatedLore);
         } else {
            String worldName = sender.getWorld().getName();
            String worldNickname = this.plugin.getConfigManager().getWorldNickname(worldName);
            List<String> loreList = new ArrayList();
            loreList.add(Hex.translateAllColorCodes("&7" + worldNickname));
            meta.setLore(loreList);
         }
      }

      item.setItemMeta(meta);
      return item;
   }

   private ItemStack createPlayerIcon() {
      Player sender = Bukkit.getPlayer(this.request.getSender());
      ItemStack item = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta meta = (SkullMeta)item.getItemMeta();
      if (sender != null) {
         meta.setOwningPlayer(sender);
         String displayName = this.plugin.getGUIManager().getIconDisplayName("tpa_accept_gui", "player-icon", "&#00f986ᴘʟᴀʏᴇʀ");
         meta.setDisplayName(Hex.translateAllColorCodes(displayName));
         List<String> lore = this.plugin.getGUIManager().getIconLore("tpa_accept_gui", "player-icon");
         if (lore != null && !lore.isEmpty()) {
            List<String> translatedLore = new ArrayList();

            for(String line : lore) {
               String translated = Hex.translateAllColorCodes(line);
               if (line.contains("%player%")) {
                  translated = translated.replace("%player%", sender.getName());
               }

               translatedLore.add(translated);
            }

            meta.setLore(translatedLore);
         } else {
            List<String> loreList = new ArrayList();
            loreList.add(Hex.translateAllColorCodes("&7" + sender.getName()));
            meta.setLore(loreList);
         }
      }

      item.setItemMeta(meta);
      return item;
   }
}
