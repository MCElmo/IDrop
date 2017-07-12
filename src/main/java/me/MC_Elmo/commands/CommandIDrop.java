package me.MC_Elmo.commands;

import me.MC_Elmo.IDrop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Max_Plays on 11.07.2017.
 */
public class CommandIDrop implements CommandExecutor {

    	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    			if (cmd.getName().equalsIgnoreCase("idrop")) {
                    if(!sender.hasPermission("idrop.bypass"))
                    {
                        sender.sendMessage(IDrop.instance.prefix + " You do not have permission to execute this command!");
                        return true;
                    }
                    if(args.length == 1)
                    {
                        if(args[0].equalsIgnoreCase("reload"))
                        {
                            IDrop.instance.reload();
                            sender.sendMessage(IDrop.instance.prefix + " Successfully reloaded plugins/IDrop/config.yml");
                        }else
                        {
                            sender.sendMessage(IDrop.instance.prefix + "Invalid command syntax");
                        }
                    }else
                    {
                        sender.sendMessage(IDrop.instance.prefix + " Invalid command syntax");
                    }
    			}
    			return true;
    		}

}
