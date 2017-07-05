package me.MC_Elmo.listeners;

import me.MC_Elmo.IDrop;
import me.MC_Elmo.util.ItemNames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elom on 7/5/17.
 */
public class ItemDrop implements org.bukkit.event.Listener
{
    private IDrop instance;
    private FileConfiguration config;
    public ItemDrop(IDrop instance)
    {
        this.instance = instance;
        this.config = instance.getConfig();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if(!(event.getPlayer().hasPermission("idrop.bypass")))
        {
            config = instance.getConfig();
            Bukkit.getLogger().info("acsdac");
            ItemStack is = event.getItemDrop().getItemStack();
            for (String s : config.getStringList("IDrop.Item List"))
            {
                Bukkit.getLogger().info(s + "      " + ItemNames.lookup(is));
                try
                {
                    int i = Integer.valueOf(s);
                    if (is.getTypeId() == i)
                    {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                        return;
                    }
                } catch (NumberFormatException e)
                {
                    if (s.equalsIgnoreCase(ItemNames.lookup(is)))
                    {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                        return;
                    } else if (s.contains(":"))
                    {
                        short sh = Short.parseShort(s.split(":")[1]);
                        int type = Integer.valueOf(s.split(":")[0]);
                        if (is.getTypeId() == type && is.getDurability() == sh)
                        {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                            return;
                        }
                    } else if (ItemNames.lookup(is).toLowerCase().contains(s.toLowerCase()))
                    {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                        return;
                    }
                }
            }
        }
    }
    @EventHandler
    public void onMove(InventoryClickEvent event)
    {
        Player p = (Player) event.getWhoClicked();
        if (!(p.hasPermission("idrop.bypass")))
        {
            config = instance.getConfig();
            if (event.getInventory().getType() != InventoryType.PLAYER)
            {
                ItemStack is = event.getCurrentItem();
                if(is != null)
                {
                    for (String s : config.getStringList("IDrop.Item List"))
                    {
                        Bukkit.getLogger().info(s + "      " + ItemNames.lookup(is));
                        try
                        {
                            int i = Integer.valueOf(s);
                            if (is.getTypeId() == i)
                            {
                                event.setCancelled(true);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                                return;
                            }
                        } catch (NumberFormatException e)
                        {
                            if (s.equalsIgnoreCase(ItemNames.lookup(is)))
                            {
                                event.setCancelled(true);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                                return;
                            } else if (s.contains(":"))
                            {
                                short sh = Short.parseShort(s.split(":")[1]);
                                int type = Integer.valueOf(s.split(":")[0]);
                                if (is.getTypeId() == type && is.getDurability() == sh)
                                {
                                    event.setCancelled(true);
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                                    return;
                                }
                            } else if (ItemNames.lookup(is).toLowerCase().contains(s.toLowerCase()))
                            {
                                event.setCancelled(true);
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("IDrop.No Drop Message")));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        if(!(event.getEntity().hasPermission("idrop.bypass")))
        {
            config = instance.getConfig();
            outer:
            {
                for (ItemStack is : event.getDrops())
                {
                    for (String s : config.getStringList("IDrop.Item List"))
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
}
