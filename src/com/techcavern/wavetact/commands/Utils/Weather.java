package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;

/**
 * Created by jztech101 on 6/24/14.
 */
public class Weather extends Command {
        public Weather() {
            super("weather", 0, "Weather [zipcode]");
        }

        @Override
        public void onCommand(MessageEvent<?> event, String... args) throws Exception {
            List<String> waresults = GeneralUtils.getWAResult("weather " + StringUtils.join(args, " "));
            event.getChannel().send().message(waresults.get(1));
        }

    }