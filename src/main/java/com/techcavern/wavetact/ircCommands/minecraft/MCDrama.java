package com.techcavern.wavetact.ircCommands.minecraft;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class MCDrama extends IRCCommand {

    public MCDrama() {
        super(GeneralUtils.toArray("mcdrama"), 0, null, "Displays minecraft drama", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://asie.pl/drama.php?2").get();
        String c = doc.select("h1").text();
        IRCUtils.sendMessage(user, network, channel, c, prefix);
    }
}