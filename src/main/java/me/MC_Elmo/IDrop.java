package me.MC_Elmo;

import me.MC_Elmo.commands.CommandIDrop;
import me.MC_Elmo.listeners.ItemDrop;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class IDrop extends JavaPlugin
{
    public static IDrop instance;
    private FileConfiguration config;
    private PluginManager pm;

    public final String prefix = ChatColor.RED + "[IDrop] " + ChatColor.RESET;

    /**
     * Created by MCElmo, modified by MaxPlays
     */


    @Override
    public void onEnable()
    {
        instance = this;
        pm = getServer().getPluginManager();
        config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("idrop").setExecutor(new CommandIDrop());
        registerListeners();
    }

    private void registerListeners()
    {
        pm.registerEvents(new ItemDrop(), this);
    }

    public void reload() {
        reloadConfig();
        config = getConfig();
    }
}
