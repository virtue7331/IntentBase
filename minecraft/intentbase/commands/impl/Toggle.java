package intentbase.commands.impl;

import intentbase.commands.Command;
import intentbase.modules.Module;
import intentbase.IntentBase;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles a module", "toggle <name>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        String moduleName = args[0];

        for (Module module : IntentBase.INSTANCE.modules) {
            if (module.name.equalsIgnoreCase(moduleName)) {
                module.toggle();
                break;
            }
        }
    }
}
