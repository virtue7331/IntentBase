package intentbase.commands.impl;

import intentbase.IntentBase;
import intentbase.commands.Command;
import intentbase.modules.Module;
import intentbase.util.Printer;

public class Info extends Command {

    public Info() {
        super("Info", "Displays information", "info", "i", "information");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Printer.print("\2479The client base made Nefarious Intent owner of \2479Intent Store made during his How to Code a Minecraft 1.8 \2479Hacked Client series");
        Printer.print("\2479do .help for the list of commands");
    }
}
