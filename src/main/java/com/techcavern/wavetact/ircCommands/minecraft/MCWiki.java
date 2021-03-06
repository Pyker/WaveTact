package com.techcavern.wavetact.ircCommands.minecraft;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class MCWiki extends IRCCommand {

    public MCWiki() {
        super(GeneralUtils.toArray("mcwiki mcw mwiki"), 0, "mcwiki [query minecraft wikis]", "Searches official minecraft wiki & official & unofficial ftb wikis", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = null;
        Elements content = null;
        for (int i = 0; i < args.length; i++) {
            args[i] = StringUtils.capitalize(args[i]);
        }
        String url = "http://minecraft.gamepedia.com/" + StringUtils.join(args, "%20");
        try {
            doc = Jsoup.connect(url).userAgent(Registry.userAgent).get();
            if (doc.location().toLowerCase().contains("login")) {
                throw new Exception();
            }
        } catch (Exception eee) {
            try {
                url = "http://ftb.gamepedia.com/" + StringUtils.join(args, "%20");
                doc = Jsoup.connect(url).userAgent(Registry.userAgent).get();
                if (doc.location().toLowerCase().contains("login")) {
                    throw new Exception();
                }

            } catch (Exception e) {
                try {
                    url = "http://ftbwiki.org/" + StringUtils.join(args, "%20");
                    doc = Jsoup.connect(url).userAgent(Registry.userAgent).get();
                } catch (Exception ee) {
                    ee.printStackTrace();
                    ErrorUtils.sendError(user, "Query returned no results or wikis are Down");
                    return;
                }
            }
        }

        content = doc.select("#mw-content-text");
        content.select(".atemplate").remove();
        content.select(".notaninfobox").remove();
        content.select(".infobox").remove();
        content = content.select("p");
        String text = "";
        int i = 0;
        while (text.replaceAll(" ", "").isEmpty() && i <= content.size()) {
            text = content.get(i).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "").replaceAll("\\[.*\\]", "");
            i++;
        }
        if (text.length() > 700) {
            text = StringUtils.substring(text, 0, 697) + "...";
        }
        IRCUtils.sendMessage(user, network, channel, text, prefix);
        IRCUtils.sendMessage(user, network, channel, url, prefix);
    }
}
