package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;

public class SMKick extends SMCommand
{
    protected static final String[] permissions = {"simplemod.kick"};
    
    /**
     * Initialize kick command
     */
    public SMKick(SimpleMod plugin)
    {
        super(plugin, null, 1, permissions);
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
            // if we have more args, treat as kick reason
            if (args.length > 1)
                kicking.kickPlayer("Kicked for: " + Arrays.toString(Arrays.copyOfRange(args, 1, args.length)).replaceAll("[,\\[\\]]", ""));
            else
                kicking.kickPlayer("You have been kicked!");
            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' kicked!");
            plugin.getLogger().info(sender.getName() + " kicked player " + playerName);
        }
        else
            sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot kick!");
        
        return true;
    }
}
