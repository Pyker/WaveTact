package com.techcavern.wavetact.utils;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    @SuppressWarnings("unchecked")
    private static String getAccount(PircBotX bot, User u) {
        String user;
        bot.sendRaw().rawLineNow("WHOIS " + u.getNick());
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        WhoisEvent<PircBotX> test;
        try {
            test = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
            user = test.getRegisteredAs();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            user = null;
        }

        return user;
    }

    private static boolean isController(PircBotX bot, User u) {
        String v = getAccount(bot, u);
        if (v != null) {
            return GeneralRegistry.Controllers.contains(v.toLowerCase());
        } else {
            return GeneralRegistry.ControllerHostmasks.contains(u.getHostmask());
        }
    }

    public static int getPermLevel(PircBotX bot, User u, Channel z) {
        if (PermUtils.isController(bot, u)) {
            return 9001;
        } else if (z.isOwner(u)) {
            return 15;
        } else if (z.isOp(u) || z.isSuperOp(u)) {
            return 10;
        } else if (z.isHalfOp(u) || z.hasVoice(u)) {
            return 5;
        } else {
            return 0;
        }
    }
}