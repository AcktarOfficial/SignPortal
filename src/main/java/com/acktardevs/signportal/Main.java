package com.acktardevs.signportal;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;

public class Main extends PluginBase implements Listener {

@Override
public void onEnable() {    
      this.getServer().getPluginManager().registerEvents(this, this);
}

@EventHandler
public void playerBlockTouch(PlayerInteractEvent e){
       Player p = e.getPlayer();
       Block b = e.getBlock();
       Action action = e.getAction();
       
       if (b.getId() == 68 || b.getId() == 63) {
          if (!(p.getLevel().getBlockEntity(b.getLocation()) instanceof BlockEntitySign)) {
              return;
           }

       BlockEntitySign bes = (BlockEntitySign)p.getLevel().getBlockEntity(b.getLocation());
          if (action == Action.RIGHT_CLICK_BLOCK) {
            String[] signtext = bes.getText();
               String line1 = "";
               String line2 = "";
               if (signtext.length > 1) {
                  line1 = signtext[0];
                  line2 = signtext[1];
                  if (line1.equals("[WORLD]") && p.isOp()) {
                     bes.setText(new String[]{"§a§c§e§c§b§r[§a§c§e§c§bWORLD§r]", line2});
                  }

                  if (line1.equals("§a§c§e§c§b§r[§a§c§e§c§bWORLD§r]")) {
                     Level targetLevel = this.getServer().getLevelByName(line2);
                        if(targetLevel != null && this.getServer().isLevelLoaded(line2)) {
                          p.teleport(targetLevel.getSpawnLocation());
                  } else {
                    p.sendMessage("§l§7(§6SignPortal§7)§r§c The world you are trying to go to is currently unavailable");
                }
            }
                  }

            }
        }
    }

@EventHandler
public void onEvent(BlockBreakEvent e) {
       Player p = e.getPlayer();
       Block b = e.getBlock();
         if (b.getId() == 68 || b.getId() == 63) {
            if (!(p.getLevel().getBlockEntity(b.getLocation()) instanceof BlockEntitySign)) {
               return;
            }

            BlockEntitySign bes = (BlockEntitySign)p.getLevel().getBlockEntity(b.getLocation());
            String[] signtext = bes.getText();
            String line1 = "";
            if (signtext.length > 0) {
               line1 = signtext[0];
               if (line1.equals("§a§c§e§c§b§r[§a§c§e§c§bWORLD§r]") && !p.isOp()) {
                  e.setCancelled(true);
               }
            }
         }
     }
} 
