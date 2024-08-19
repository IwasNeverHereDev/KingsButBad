package agmas.kingsbutbad.Loaders;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class LoadEvents {
   public static void init() {
      registerEvent(new BlockBreakListener());
      registerEvent(new EntityDamageByEntityListener());
      registerEvent(new EntityDamageListener());
      registerEvent(new EntityDismountListener());
      registerEvent(new EntityMountListener());
      registerEvent(new EntityTargetListener());
      registerEvent(new InventoryClickListener());
      registerEvent(new PlayerChatListener());
      registerEvent(new PlayerDeathListener());
      registerEvent(new PlayerFishListener());
      registerEvent(new PlayerInteractAtEntityListener());
      registerEvent(new PlayerInteractListener());
      registerEvent(new PlayerItemConsumeListener());
      registerEvent(new PlayerJoinListener());
      registerEvent(new PlayerRespawnListener());
      registerEvent(new PlayerToggleSneakListener());
      registerEvent(new ProjectileHitListener());
      registerEvent(new PlayerCommandPreprocessListener());
      registerEvent(new PlayerMoveListener());
      registerEvent(new PlayerDropListener());
      registerEvent(new PlayerQuitListener());
      registerEvent(new PlayerPickArrowListener());
      registerEvent(new PlayerPickItemListener());
      registerEvent(new CBPListener());
   }
   private static void registerEvent(Listener listener){
      Bukkit.getPluginManager().registerEvents(listener, KingsButBad.pl);
   }
}
