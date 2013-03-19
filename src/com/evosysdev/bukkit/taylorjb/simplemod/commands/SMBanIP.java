package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMBanIP extends SMCommand
{
    protected static final String[] permissions = {"simplemod.ipban"};
    
    /**
     * Initialize ban IP command
     */
    public SMBanIP(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, permissions);
    }

    @Override
    /**
     * Execute IP ban command
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        String playerName = args[0];
        Player banning = plugin.getServer().getPlayer(playerName);
        
        if (banning != null)
        {
            String reason = "";
            
            if (args.length > 1)
                reason = " for" + this.stringArrayToString(args, 1);

            // broadcast message if we need to
            if (handler.broadcastMute())
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player '" + banning.getDisplayName() + "' IP banned" + reason);
            
            handler.banIP(banning.getAddress().getAddress().getHostAddress());
            banning.kickPlayer("You have been banned" + reason + "!");
            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' IP banned" + reason + "!");
            plugin.getLogger().info(sender.getName() + " IP banned player " + playerName + "(" + banning.getAddress().getHostString() + ")" + reason);
        }
        else
            sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot ban their IP!");
        
        return true;
    }
}
