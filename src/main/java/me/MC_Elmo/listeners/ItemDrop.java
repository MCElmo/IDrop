package me.MC_Elmo.listeners;

import me.MC_Elmo.IDrop;
import me.MC_Elmo.util.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elom on 7/5/17.
 */
public class ItemDrop implements Listener
{

    private final IDrop instance;

    public ItemDrop(){
        instance = IDrop.instance;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if(!isAccepted(event.getItemDrop().getItemStack(), event.getPlayer())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("IDrop.No Drop Message")));
        }
    }
    @EventHandler
    public void onMove(InventoryClickEvent event)
    {
            Player p = (Player) event.getWhoClicked();
            if (event.getInventory().getType() != InventoryType.PLAYER)
            {
                ItemStack is = event.getCurrentItem();
                if(is != null)
                {
                    if(!isAccepted(is, p)){
                        event.setCancelled(true);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("IDrop.No Drop Message")));
                    }
                }
            }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        if(!(event.getEntity().hasPermission("idrop.bypass")))
        {
            outer:
            {
                for (ItemStack is : event.getDrops())
                {
                    for (String s : instance.getConfig().getStringList("IDrop.Item List"))
                    {
                        Bukkit.getLogger().info(s + "      " + ItemNames.lookup(is));
                        try
                        {
                            int i = Integer.valueOf(s);
                            if (is.getTypeId() == i)
                            {
                                event.getDrops().remove(is);
                                break outer;
                            }
                        } catch (NumberFormatException e)
                        {
                            if (s.equalsIgnoreCase(ItemNames.lookup(is)))
                            {
                                event.getDrops().remove(is);
                                break outer;
                            } else if (s.contains(":"))
                            {
                                short sh = Short.parseShort(s.split(":")[1]);
                                int type = Integer.valueOf(s.split(":")[0]);
                                if (is.getTypeId() == type && is.getDurability() == sh)
                                {
                                    event.getDrops().remove(is);
                                    break outer;
                                }
                            } else if (ItemNames.lookup(is).toLowerCase().contains(s.toLowerCase()))
                            {
                                event.getDrops().remove(is);
                                break outer;
                            }
                        }
                    }
                }
            }
        }
    }
    private boolean isAccepted(ItemStack is, Player p){
        if(!(p.hasPermission("idrop.bypass")))
        {
            for (String s : instance.getConfig().getStringList("IDrop.Item List"))
            {
                try
                {
                    int i = Integer.valueOf(s);
                    if (is.getTypeId() == i)
                        return false;
                } catch (NumberFormatException e)
                {
                    if (s.equalsIgnoreCase(ItemNames.lookup(is)))
                    {
                        return false;
                    } else if (s.contains(":"))
                    {

                        try {
                            short sh = Short.parseShort(s.split(":")[1]);
                            int type = Integer.valueOf(s.split(":")[0]);
                            if (is.getTypeId() == type && is.getDurability() == sh)
                                return false;
                        }catch(NumberFormatException ex){
                            //Provided ID and SUBID were malformed
                        }

                    } else if (ItemNames.lookup(is).toLowerCase().contains(s.toLowerCase()))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
