package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.AccountUtils;
import com.techcavern.wavetact.utils.objects.Account;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ConCMD
public class FDrop extends IRCCommand {

    public FDrop() {
        super(GeneralUtils.toArray("fdrop"), 9001, "fdrop [user]", "Forcefully drops a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!PermUtils.checkIfAccountEnabled(network)) {
            ErrorUtils.sendError(user, "This network is set to " + GetUtils.getAuthType(network) + " authentication");
            return;
        }
        AuthedUser authedUser = PermUtils.getAuthedUser(network, args[0]);
        if (authedUser != null) {
            Registry.AuthedUsers.remove(authedUser);
        }
        Account account = AccountUtils.getAccount(args[0]);
        if (account != null) {
            Registry.Accounts.remove(account);
            AccountUtils.saveAccounts();
            IRCUtils.sendMessage(user, network, channel, "Account dropped", prefix);
        } else {
            ErrorUtils.sendError(user, "Account does not exist");
        }
    }
}
