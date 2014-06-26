package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 6/26/14.
 */
public class Hostmask extends Command {
    public Hostmask() {
        super("Hostmask", 0, "Hostmask [nick]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message(IRCUtils.getUserByNick(event.getChannel(), args[0]).getHostmask());

    }
}
