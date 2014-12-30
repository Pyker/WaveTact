package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;

@CMDLine
public class AddBasicCommands extends ConsoleCommand {

    public AddBasicCommands() {
        super(GeneralUtils.toArray("addbasiccommands"),"addbasiccommands", "Pre-Populate the database with some pre-chosen commands (Use ONCE and only ONCE");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        SimpleAction potato = new SimpleAction("potato", 0, "is a potato", true);
        SimpleMessage ping = new SimpleMessage("ping", 0, "pong", true);
        SimpleMessage pong = new SimpleMessage("pong", 0, "ping", true);
        SimpleMessage releases = new SimpleMessage("releases", 0, "https://github.com/TechCavern/WaveTact/releases", true);
        SimpleMessage license = new SimpleMessage("license", 0, "MIT (Do What The Fuck You Want, We take ZERO liability)", true);
        SimpleMessage source = new SimpleMessage("source", 0,
                "http://github.com/TechCavern/WaveTact", true);
        SimpleMessage authors = new SimpleMessage("authors", 0, "Julian (JZTech101)", true);
        SimpleMessage contributors = new SimpleMessage("contributors", 0, "Kenneth (kaendfinger), Sam (samrg472), Logan, Laceh", true);
        SimpleAction nom = new SimpleAction("nom", 0, "noms on $*", true);
        Registry.SimpleActions.add(potato);
        Registry.SimpleMessages.add(ping);
        Registry.SimpleMessages.add(pong);
        Registry.SimpleMessages.add(source);
        Registry.SimpleMessages.add(license);
        Registry.SimpleMessages.add(releases);
        Registry.SimpleMessages.add(authors);
        Registry.SimpleMessages.add(contributors);
        Registry.SimpleActions.add(nom);
        SimpleActionUtils.saveSimpleActions();
        SimpleMessageUtils.saveSimpleMessages();
        commandIO.getPrintStream().println("Basic Commands Added");

    }
}
