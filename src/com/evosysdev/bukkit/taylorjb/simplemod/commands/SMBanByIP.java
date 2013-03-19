/**
 * 
 */
package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

/**
 * @author taylorjb
 * 
 */
public class SMBanByIP extends SMCommand
{
    protected static final String[] permissions = { "simplemod.ipban" };
    
    /**
     * Initialize unban IP command
     */
    public SMBanByIP(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 1, permissions);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean execute(CommandSender sender, Command command, String label, String[] args)
    {
        String ip = args[0]; // ip is the only arg
        
        // ban ip
        handler.banIP(ip);
        sender.sendMessage(ChatColor.GREEN + "Banned IP '" + ip + "'.");
        
        return true;
    }
    
}
