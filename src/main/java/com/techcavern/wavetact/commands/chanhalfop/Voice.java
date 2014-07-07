/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Voice extends GenericCommand {
    @CMD
    public Voice() {
        super(GeneralUtils.toArray("voice vo vop"), 6, "Voice (-)(User)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                event.getChannel().send().deVoice(event.getUser());
            } else if (args[0].startsWith("-")) {
                event.getChannel().send().deVoice(GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")));
            } else {
                event.getChannel().send().voice(GetUtils.getUserByNick(event.getChannel(), args[0]));

            }
        } else {
            event.getChannel().send().voice(event.getUser());
        }
    }
}
