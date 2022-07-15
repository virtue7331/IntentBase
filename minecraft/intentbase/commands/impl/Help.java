package intentbase.commands.impl;

import intentbase.commands.Command;
import intentbase.IntentBase;
import intentbase.util.Printer;

public class Help extends Command {

    public Help() {
        super("Help", "Displays commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Printer.print("List of commands:");
        for (Command c : IntentBase.INSTANCE.commandManager.commands) {
            Printer.print(String.format("- %s", c.name));
        }
    }
}
