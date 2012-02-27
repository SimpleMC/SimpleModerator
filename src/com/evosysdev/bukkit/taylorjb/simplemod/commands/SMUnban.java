package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMUnban extends SMCommand
{
    protected static final String[] permissions = {"simplemod.unban"};
    
    /**
     * Initialize unban command
     */
    public SMUnban(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, permissions);
    }

    @Override
    /**
     * Execute unban
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        if (handler.unBan(args[0]))
        {
            sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unbanned!");
            plugin.getLogger().info(sender.getName() + " unbanned player " + args[0]);
        }
        else
            sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is banned.");
        
        return true;
    }
}
