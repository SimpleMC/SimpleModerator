package com.evosysdev.bukkit.taylorjb.simplemod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleMod extends JavaPlugin
{
    private SimpleModHandler smHandler; // our moderation action handler

    /**
     * Set up Permissions and set up listeners
     */
    public void onEnable()
    {
        smHandler = new SimpleModHandler(this);

        // create listener
        new SimpleModListener(this);
        
        System.out.println(getDescription().getName() + " version " + getDescription().getVersion() + " enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // help command
        if (command.getName().equalsIgnoreCase("simplemoderator"))
        {
            // show help for only commands user has access to
            // this seems like a silly way to do it.
            if (sender.hasPermission("simplemod"))
            {
                if (sender.hasPermission("simplemod.ban") || sender.hasPermission("simplemod.ban.temp"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("ban").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("ban").getDescription());
                }

                if (sender.hasPermission("simplemod.ipban"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("banip").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("banip").getDescription());
                }

                if (sender.hasPermission("simplemod.unban"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("unban").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("unban").getDescription());
                }

                if (sender.hasPermission("simplemod.mute") || sender.hasPermission("simplemod.mute.temp"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("mute").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("mute").getDescription());
                }

                if (sender.hasPermission("simplemod.unmute"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("unmute").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("unmute").getDescription());
                }

                if (sender.hasPermission("simplemod.kick"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("kick").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("kick").getDescription());
                }
            }
            else
                sender.sendMessage(ChatColor.RED + "You do not have permissions for that!");

            return true;
        }
        else if (command.getName().equalsIgnoreCase("ban"))
        {
            if (sender.hasPermission("simplemod.ban") || sender.hasPermission("simplemod.ban.temp"))
            {
                // if there are actual args
                if (args.length > 0)
                {
                    String playerName = args[0];

                    // if there is a time arg
                    if (args.length > 1)
                    {
                        try
                        {
                            int hours = Integer.parseInt(args[1]);
                            smHandler.ban(playerName, hours);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully banned for " + hours + " hours!");

                            // if player is online, kick them
                            Player banning = getServer().getPlayer(playerName);
                            if (banning != null) banning.kickPlayer("You have been banned!");
                            
                            getLogger().info(sender.getName() + " banned player " + playerName + " for " + hours + " hours.");
                        }
                        catch (NumberFormatException nfe)
                        {
                            sender.sendMessage(ChatColor.RED + "Error parsing hour/length!");
                            return false;
                        }
                    }
                    else
                    {
                        // check for perm ban permissions
                        if (sender.hasPermission("simplemod.ban"))
                        {
                            smHandler.ban(playerName);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully banned!");
                            
                            // if player is online, kick them
                            Player banning = getServer().getPlayer(playerName);
                            if (banning != null) banning.kickPlayer("You have been banned!");
                            
                            getLogger().info(sender.getName() + " banned player " + playerName);
                        }
                        else
                            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                    }

                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("banip"))
        {
            if (sender.hasPermission("simplemod.ipban"))
            {
                if (args.length > 0)
                {
                    String playerName = args[0];
                    Player banning = getServer().getPlayer(playerName);

                    if (banning != null)
                    {
                        smHandler.banIP(banning.getAddress().getHostString());
                        banning.kickPlayer("You have been banned!");
                        sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully IP banned!");
                        getLogger().info(sender.getName() + " IP banned player " + playerName + "(" + banning.getAddress().getHostString() + ")");
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot ban their IP!");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("unban"))
        {
            if (sender.hasPermission("simplemod.unban"))
            {
                if (args.length > 0)
                {
                    if (smHandler.unBan(args[0]))
                    {
                        sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unbanned!");
                        getLogger().info(sender.getName() + " unbanned player " + args[0]);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is banned.");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("mute"))
        {
            if (sender.hasPermission("simplemod.mute") || sender.hasPermission("simplemod.mute.temp"))
            {
                // if there are actual args
                if (args.length > 0)
                {
                    String playerName = args[0];

                    // if there is a time arg
                    if (args.length > 1)
                    {
                        try
                        {
                            int hours = Integer.parseInt(args[1]);
                            smHandler.mute(playerName, hours);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully muted for " + hours + " hours!");
                            getLogger().info(sender.getName() + " muted player " + playerName + " for " + hours + " hours.");
                        }
                        catch (NumberFormatException nfe)
                        {
                            sender.sendMessage(ChatColor.RED + "Error parsing hour/length!");
                            return false;
                        }
                    }
                    else
                    {
                        // check for perm mute permissions
                        if (sender.hasPermission("simplemod.mute"))
                        {
                            smHandler.mute(playerName);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully muted!");
                            getLogger().info(sender.getName() + " muted player " + playerName);
                        }
                        else
                            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                    }

                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("unmute"))
        {
            if (sender.hasPermission("simplemod.unmute"))
            {
                if (args.length > 0)
                {
                    if (smHandler.unMute(args[0]))
                    {
                        sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unmuted!");
                        getLogger().info(sender.getName() + " unmuted player " + args[0]);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is muted.");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("kick"))
        {
            if (sender.hasPermission("simplemod.kick"))
            {
                if (args.length > 0)
                {
                    String playerName = args[0];
                    Player kicking = getServer().getPlayer(playerName);

                    if (kicking != null)
                    {
                        kicking.kickPlayer("You have been kicked!");
                        sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' kicked!");
                        getLogger().info(sender.getName() + " kicked player " + playerName);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot kick!");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }

        return false;
    }

    /**
     * plugin disabled
     */
    public void onDisable()
    {
        getLogger().info("SimpleModerator disabled.");
    }

    /**
     * Get our moderation handler
     * 
     * @return our moderation handler
     */
    public SimpleModHandler getHandler()
    {
        return smHandler;
    }
}
