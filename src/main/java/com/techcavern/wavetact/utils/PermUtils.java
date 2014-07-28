package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.events.WhoisEvent;

public class PermUtils {

    @SuppressWarnings("unchecked")
    public static String getAccount(PircBotZ bot, String userObject) {
        String userString;
        WhoisEvent whois = IRCUtils.WhoisEvent(bot, userObject);
        if (whois != null) {
            userString = whois.getRegisteredAs();
            if (userString != null && userString.isEmpty()) {
                    userString = userObject;

            }
        } else {
            userString = null;
        }
        return userString;
    }

    private static int getAutomaticPermLevel(User userObject, Channel channelObject) {
        if (userObject.isIrcop()) {
            return 20;
        } else if (channelObject.isOwner(userObject)) {
            return 15;
        } else if (channelObject.isSuperOp(userObject)) {
            return 13;
        } else if (channelObject.isOp(userObject)) {
            return 10;
        } else if (channelObject.isHalfOp(userObject)) {
            return 7;
        } else if (channelObject.hasVoice(userObject)) {
            return 5;
        } else {
            return 0;
        }
    }

    private static int getManualPermLevel(PircBotZ bot, String userObject, Channel channelObject) {
        String account = getAccount(bot, userObject);
        if (account != null) {
            if (GetUtils.getControllerByNick(account) != null) {
                return 9001;
            }
            if (GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null) {
                return 20;
            }
            if (PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()) != null) {
                return PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()).getPermLevel();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int getPermLevel(PircBotZ bot, String userObject, Channel channelObject) {
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(bot, userObject, channelObject);
            int apermlevel = getAutomaticPermLevel(GetUtils.getUserByNick(bot, userObject), channelObject);
            if (mpermlevel < 0) {
                return mpermlevel;
            } else if (apermlevel < mpermlevel) {
                return mpermlevel;
            } else {
                return apermlevel;
            }
        } else {
            String account = getAccount(bot, userObject);
            if (account != null) {
                if (GetUtils.getControllerByNick(account) != null) {
                    return 9001;
                } else if (GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null) {
                    return 20;
                } else {
                    return 2;
                }
            } else {
                return 2;
            }
        }
    }
}
