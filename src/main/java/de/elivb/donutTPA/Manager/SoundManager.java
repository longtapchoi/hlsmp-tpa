package de.elivb.donutTPA.Manager;

import de.elivb.donutTPA.TPA;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
   private final TPA plugin;
   private Sound teleportCooldownSound;
   private Sound teleportingSound;
   private Sound buttonClickSound;
   private Sound teleportSuccessSound;
   private Sound teleportFailSound;
   private Sound reloadSound;

   public SoundManager(TPA plugin) {
      this.plugin = plugin;
   }

   public void loadSounds() {
      String teleportCooldown = this.plugin.getConfig().getString("sound.teleport-cooldown", "block.note_block.pling");
      String teleporting = this.plugin.getConfig().getString("sound.teleporting", "entity.enderman.teleport");
      String buttonClick = this.plugin.getConfig().getString("sound.button-click", "ui.button.click");
      String teleportSuccess = this.plugin.getConfig().getString("sound.teleport-success", "entity.player.levelup");
      String teleportFail = this.plugin.getConfig().getString("sound.teleport-fail", "entity.villager.no");
      String reload = this.plugin.getConfig().getString("sound.reload", "entity.experience_orb.pickup");
      this.teleportCooldownSound = this.getSound(teleportCooldown);
      this.teleportingSound = this.getSound(teleporting);
      this.buttonClickSound = this.getSound(buttonClick);
      this.teleportSuccessSound = this.getSound(teleportSuccess);
      this.teleportFailSound = this.getSound(teleportFail);
      this.reloadSound = this.getSound(reload);
   }

   private Sound getSound(String soundName) {
      if (soundName != null && !soundName.isEmpty()) {
         try {
            NamespacedKey key = NamespacedKey.minecraft(soundName);
            Sound sound = (Sound)Registry.SOUNDS.get(key);
            if (sound != null) {
               return sound;
            }
         } catch (Exception var4) {
         }

         return null;
      } else {
         return null;
      }
   }

   public void playTeleportCooldownSound(Player player) {
      this.playSound(player, this.teleportCooldownSound);
   }

   public void playTeleportingSound(Player player) {
      this.playSound(player, this.teleportingSound);
   }

   public void playButtonClickSound(Player player) {
      this.playSound(player, this.buttonClickSound);
   }

   public void playTeleportSuccessSound(Player player) {
      this.playSound(player, this.teleportSuccessSound);
   }

   public void playTeleportFailSound(Player player) {
      this.playSound(player, this.teleportFailSound);
   }

   public void playReloadSound(Player player) {
      this.playSound(player, this.reloadSound);
   }

   private void playSound(Player player, Sound sound) {
      if (player != null && sound != null && player.isOnline()) {
         player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
      }

   }
}
