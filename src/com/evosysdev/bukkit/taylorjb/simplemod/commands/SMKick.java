package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMKick extends SMCommand
{
    protected static final String[] permissions = {"simplemod.kick"};
    
    /**
     * Initialize kick command
     */
    public SMKick(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, permissions);
    }
    
    @Override
    /**
     * Kick player with name at arg[0]
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        String playerName = args[0];
        Player kicking = plugin.getServer().getPlayer(playerName);
        
        if (kicking != null)
        {
            String reason = stringArrayToString(args, 1),
                    kickMessage = "You have been kicked!";
            
            // if we have more args, treat as kick reason
            if (reason.length() > 0)
                kickMessage += " Reason: " + reason;
            
            kicking.kickPlayer(kickMessage);
            
            // broadcast kick if we need to
            if (handler.broadcastKick())
            {
                plugin.getServer().broadcastMessage(sender.getName() + " kicked player " + playerName + (reason.length() > 0 ? " for " + reason : ""));
            }
            
            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' kicked!");
            plugin.getLogger().info(sender.getName() + " kicked player " + playerName + ", reason: " + reason);
        }
        else
            sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot kick!");
        
        return true;
    }
}
