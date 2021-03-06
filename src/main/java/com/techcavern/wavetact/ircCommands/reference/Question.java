package com.techcavern.wavetact.ircCommands.reference;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.wolfram.alpha.*;
import com.wolfram.alpha.visitor.Visitable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Question extends IRCCommand {

    public Question() {
        super(GeneralUtils.toArray("question q wa wolframalpha"), 0, "question (answer #) [question]", "Ask wolfram alpha a question!", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String wolframalphaapikey;
        if (DatabaseUtils.getConfig("wolframalphaapikey") != null)
            wolframalphaapikey = DatabaseUtils.getConfig("wolframalphaapikey").getValue(CONFIG.VALUE);
        else {
            ErrorUtils.sendError(user, "Wolfram Alpha api key is null - contact bot controller to fix");
            return;
        }
        int ArrayIndex = 1;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args, 0);
        }
        WAEngine engine = new WAEngine();
        engine.setAppID(wolframalphaapikey);
        engine.addFormat("plaintext");
        WAQuery query = engine.createQuery();
        query.setInput(StringUtils.join(args, " "));
        WAQueryResult queryResult = engine.performQuery(query);
        WAPod[] result = queryResult.getPods();
        if (result.length > 0) {
            if (result.length - 1 >= ArrayIndex) {
                for (WASubpod sub : result[ArrayIndex].getSubpods()) {
                    for (Visitable visitable : sub.getContents()) {
                        if (sub.getTitle().isEmpty())
                            IRCUtils.sendMessage(user, network, channel, "[" + result[ArrayIndex].getTitle() + "] " + ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": "), prefix);
                        else
                            IRCUtils.sendMessage(user, network, channel, "[" + result[ArrayIndex].getTitle() + " - " + sub.getTitle() + "] " + ((WAPlainText) sub.getContents()[0]).getText().replaceAll("\\n", " - ").replaceAll(" \\| ", ": "), prefix);

                    }
                }
            } else {
                ErrorUtils.sendError(user, "Answer #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Question returned no answers");
        }
    }

}
