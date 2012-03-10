package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        String unmute = args[0];
        if (handler.unMute(unmute))
        {
            sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unmuted!");
            plugin.getLogger().info(sender.getName() + " unmuted player " + args[0]);
            
            // if player is online, tell him he was muted
            Player p = plugin.getServer().getPlayer(unmute);
            if (p != null)
                p.sendMessage(ChatColor.RED + "You have been unmuted.");
        }
        else
            sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is muted.");
        
        return true;
    }
}
