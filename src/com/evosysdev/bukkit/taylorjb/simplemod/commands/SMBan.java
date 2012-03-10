package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMBan extends SMCommand
{
    protected static final String[] permissions = {"simplemod.ban"};
    
    /**
     * Initialize ban command
     */
    protected SMBan(SimpleMod plugin, SimpleModHandler handler, int reqArgs, String... permissions)
    {
        super(plugin, handler, reqArgs, permissions);
    }
    
    /**
     * Initialize mute command with default mute behavior
     */
    public SMBan(SimpleMod plugin, SimpleModHandler handler)
    {
        this(plugin, handler, 1, permissions);
    }
    
    @Override
    /**
     * Execute ban command
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        ban(args[0], 0, sender, stringArrayToString(args, 1));
        return true;
    }
    
    /**
     * Ban player
     * 
     * @param banning
     *            name of player we are banning
     * @param time
     *            time period in hours of ban
     * @param sender
     *            sender of the ban command
     * @param reason
     *            reason for the ban
     */
    protected void ban(String banning, int time, CommandSender sender, String reason)
    {
        handler.ban(banning, time); // ban
        
        StringBuilder message = new StringBuilder(" banned");
        
        // append time info if there is a time limit on it
        if (time > 0)
            message.append(" for ").append(time).append(" hours");
        
        // append reason if it exists
        if (reason.length() > 0)
            message.append(" for: ").append(reason);
        
        message.append('!');
        
        // sender feedback
        sender.sendMessage(ChatColor.GREEN + "Player '" + banning + "'" + message.toString());
        
        // broadcast message if we need to
        if (handler.broadcastBan())
            plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player '" + banning + "'" + message.toString());
        
        // if player is online, kick him
        Player p = plugin.getServer().getPlayer(banning);
        if (p != null)
            p.kickPlayer(ChatColor.RED + "You have been" + message.toString());
        
        // log mute
        plugin.getLogger().info(sender.getName() + " banned player " + banning + ", reason: " + reason);
    }
}
