package com.evosysdev.bukkit.taylorjb.simplemod.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.evosysdev.bukkit.taylorjb.simplemod.SimpleMod;
import com.evosysdev.bukkit.taylorjb.simplemod.SimpleModHandler;

public class SMTempBan extends SMBan
{
    protected static final String[] permissions = {"simplemod.ban.temp"};
    
    /**
     * Initialize temp ban command
     */
    public SMTempBan(SimpleMod plugin, SimpleModHandler handler)
    {
        super(plugin, handler, 2, permissions);
    }
    
    @Override
    /**
     * Execute temp ban command
     * 
     * @throws NumberFormatException when hours cannot be parsed to an int
     */
    protected boolean execute(CommandSender sender, Command command, String label, String[] args) throws NumberFormatException
    {
        int hours = Integer.parseInt(args[1]);
        
        if (hours < 1)
        {
            sender.sendMessage(ChatColor.RED + "Cannot temp ban indefinitely, use ban!");
            return true;
        }
        
        ban(args[0], hours, sender, stringArrayToString(args, 2));
        return true;
    }
}
