package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
@CMD
@GenCMD
public class Commands extends GenericCommand {

    public Commands() {
        super(GeneralUtils.toArray("commands list cmds"), 0, "commands [permlevel/all]", "Returns list of Commands specific to that permlevel", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int permlevel = 0;
        if (args.length > 0) {
            try {
                permlevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.AllListCommands, ", "), prefix);
                return;
            }
        }
        if (permlevel >= 9001) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ControllerListCommands, ", "), prefix);
        } else if (permlevel >= 20) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.NetAdminListCommands, ", "), prefix);
        } else if (permlevel >= 18) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ChanAdminListCommands, ", "), prefix);
        } else if (permlevel >= 15) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ChanOwnOpListCommands, ", "), prefix);
        } else if (permlevel >= 13) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ChanOpListCommands, ", "), prefix);
        } else if (permlevel >= 10) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ChanOpListCommands, ", "), prefix);
        } else if (permlevel >= 7) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.ChanHalfOpListCommands, ", "), prefix);
        } else if (permlevel >= 5) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.TrustedListCommands, ", "), prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(Constants.GenericListCommands, ", "), prefix);
        }

    }
}

