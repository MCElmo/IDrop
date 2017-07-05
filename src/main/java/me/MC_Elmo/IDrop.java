package me.MC_Elmo;

import me.MC_Elmo.listeners.ItemDrop;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class IDrop extends JavaPlugin implements CommandExecutor
{
    private IDrop instance;
    private FileConfiguration config;
    private PluginManager pm;

    @Override
    public void onEnable()
    {
        instance = this;
        pm = getServer().getPluginManager();
        this.config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("idrop").setExecutor(this);
        registerListeners();
        // Plugin startup logic

    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!sender.hasPermission("idrop.bypass"))
        {
            sender.sendMessage(ChatColor.RED + "[IDrop]" + ChatColor.RESET + " You do not have permission to execute this command!");
            return true;
        }
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("reload"))
            {
                this.reloadConfig();
                sender.sendMessage(ChatColor.RED + "[IDrop]" + ChatColor.RESET + " Successfully reloaded plugins/IDrop/config.yml");
                return true;
            }else
            {
                return false;
            }
        }else
        {
            sender.sendMessage(ChatColor.RED + "[IDrop]" + ChatColor.RESET + " Invalid Arguments!");
        }
        return false;
    }

    public void registerListeners()
    {
        pm.registerEvents(new ItemDrop(instance),this);
    }
}
