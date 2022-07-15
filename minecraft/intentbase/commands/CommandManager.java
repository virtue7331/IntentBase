package intentbase.commands;

import intentbase.commands.impl.*;
import intentbase.events.listeners.EventChat;
import intentbase.util.*;

import java.util.*;

public class CommandManager {

    public CommandManager() {
        setup();
    }

    public List<Command> commands = new ArrayList<Command>();
    public String prefix = ".";

    public void setup() {
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new Info());
        commands.add(new Help());
    }

    public void handleChat(EventChat event) {
        String message = event.getMessage();

        if (!message.startsWith(prefix)) {
            return;
        }

        message = message.substring(prefix.length());

        boolean foundCommand = false;

        if (message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            for (Command c : commands) {
                if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    event.setCancelled(true);
                    foundCommand = true;
                    break;
                }
            }
        }

        if (!foundCommand) {
            Printer.print("Could not find command!");
        }

    }

}
