package com.techcavern.wavetact.commands;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputChannel;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;

public class Quiet extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		String[] messageParts = event.getMessage().toString().split(" ");
			if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "quiet"))){
				if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){

				quietchary qc = new quietchary();
				qc.run(Integer.parseInt(messageParts[2]), IRCUtils.getUserByNick(event.getChannel(), messageParts[1]),event.getChannel(),event.getBot());
				
			}else {
            	event.getChannel().send().message("Permission Denied"); 

			}
			}
	}
		public class quietchary extends Thread{
			public void run(int s, User u, Channel c, PircBotX b) throws InterruptedException{
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("+q ", u.getHostmask());
				TimeUnit.SECONDS.sleep(s);
				o.setMode("-q ", u.getHostmask());
		}
	
}
}
