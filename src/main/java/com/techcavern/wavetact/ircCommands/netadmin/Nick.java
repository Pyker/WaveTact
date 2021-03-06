/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.SERVERS;


/**
 * @author jztech101
 */
@IRCCMD
public class Nick extends IRCCommand {

    public Nick() {
        super(GeneralUtils.toArray("nick"), 20, "nick (+)[channel]", "changes nick on server", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean permanent = false;
        if (args[0].startsWith("+")) {
            args[0] = args[0].replace("+", "");
            permanent = true;
        }
        if (permanent) {
            Record server = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network));
            server.setValue(SERVERS.NICK, args[0]);
            DatabaseUtils.updateServer(server);
        }
        Registry.MessageQueue.add(new NetProperty("NICK :" + args[0], network));
    }
}
