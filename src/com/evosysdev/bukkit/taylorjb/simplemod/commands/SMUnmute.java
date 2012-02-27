package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMUnmute extends SMCommand
{
    protected static String[] permissions = {"simplemod.unmute"};
    
    /**
     * Initialize unmute command
     */
    public SMUnmute(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, "simplemod.unmute");
    }
    
    @Override
    /**
     * Mute player with name at arg[0]
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        if (handler.unMute(args[0]))
        {
            sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unmuted!");
            plugin.getLogger().info(sender.getName() + " unmuted player " + args[0]);
        }
        else
            sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is muted.");
        
        return true;
    }
}
