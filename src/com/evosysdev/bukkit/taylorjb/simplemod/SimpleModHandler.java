package com.evosysdev.bukkit.taylorjb.simplemod;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Handles all of the lists required for SimpleMod's moderator actions
 * 
 * @author TJ
 * 
 */
public class SimpleModHandler
{
    private Map<String, Long> bans, // currently banned players
            mutes; // currently muted players
    
    private List<String> ipBans; // currently banned IP addresses

    /**
     * Initialize the handler
     */
    public SimpleModHandler()
    {
        bans = new HashMap<String, Long>();
        ipBans = new LinkedList<String>();
        mutes = new HashMap<String, Long>();
    }

    /**
     * Perm ban player
     * 
     * @param player
     *            player to be banned
     */
    public void ban(String player)
    {
        bans.put(player.toLowerCase(), new Long(-1));
    }

    /**
     * Ban player for hours
     * 
     * @param player
     *            player to be banned
     * @param hours
     *            hours to ban player for
     */
    public void ban(String player, int hours)
    {
        bans.put(player.toLowerCase(), System.currentTimeMillis() + (hours * 1000 * 60 * 60));
    }

    /**
     * Unban player
     * 
     * @param player
     *            player to unban
     * @return whether player was unbanned or not
     */
    public boolean unBan(String player)
    {
        player = player.toLowerCase();
        
        // no ban to remove
        if (!isBanned(player)) return false;

        // remove and success
        bans.remove(player);
        return true;
    }
    
    /**
     * Ban host
     * 
     * @param host
     *            host to be banned
     */
    public void banIP(String host)
    {
        ipBans.add(host);
    }

    /**
     * Check if a player is banned
     * 
     * @param player
     *            player to check ban status of
     * @return if player is banned or not
     */
    private boolean isBanned(String player)
    {
        player = player.toLowerCase();
        
        // make sure there is a ban
        if (bans.containsKey(player))
        {
            // ban is expired, remove it
            if (bans.get(player) > System.currentTimeMillis())
            {
                bans.remove(player);
            }
            else
                return true;
        }

        // not banned
        return false;
    }
    
    /**
     * Check if a player is banned
     * 
     * @param player
     *            player to check ban status of
     * @param inetSocketAddress.getHostString()
     *            host to check
     * @return if player is banned or not
     */
    public boolean isBanned(String player, InetSocketAddress inetSocketAddress)
    {
        player = player.toLowerCase();
        
        // make sure there is a ban
        if (bans.containsKey(player) || (inetSocketAddress!= null && ipBans.contains(inetSocketAddress.getHostString())))
        {
            // ban is expired, remove it
            if (bans.containsKey(player) && bans.get(player) > System.currentTimeMillis())
            {
                bans.remove(player);
            }
            else
                return true;
        }

        // not banned
        return false;
    }

    /**
     * Perm mute player
     * 
     * @param player
     *            to be muted
     */
    public void mute(String player)
    {
        mutes.put(player.toLowerCase(), new Long(-1));
    }

    /**
     * Mute player for time
     * 
     * @param player
     *            player to be muted
     * @param hours
     *            hours to mute player for
     */
    public void mute(String player, int hours)
    {
        mutes.put(player.toLowerCase(), System.currentTimeMillis() + (hours * 1000 * 60 * 60));
    }

    /**
     * Unmute player
     * 
     * @param player
     *            player to unmute
     * @return whether player was unmuted or not
     */
    public boolean unMute(String player)
    {
        player = player.toLowerCase();
        
        // no mute to remove
        if (!isMuted(player)) return false;

        // remove and success
        mutes.remove(player);
        return true;
    }

    /**
     * Check if a player is muted
     * 
     * @param player
     *            player to check mute status of
     * @return if player is muted or not
     */
    public boolean isMuted(String player)
    {
        player = player.toLowerCase();
        
        // make sure there is a mute
        if (mutes.containsKey(player))
        {
            // mute is expired, remove it
            if (mutes.get(player) > System.currentTimeMillis())
            {
                mutes.remove(player);
            }
            else
                return true;
        }

        // not muted
        return false;
    }
}
