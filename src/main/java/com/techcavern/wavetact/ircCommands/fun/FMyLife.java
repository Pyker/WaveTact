package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class FMyLife extends IRCCommand {

    public FMyLife() {
        super(GeneralUtils.toArray("fmylife fml"), 0, "fmylife (def #)", "Sends random fml or specified fml", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://m.fmylife.com/random").userAgent(Registry.userAgent).get();
        Elements FML = doc.select(".text");
        String fmylife = FML.get(0).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "");
        IRCUtils.sendMessage(user, network, channel, fmylife, prefix);
    }
}
