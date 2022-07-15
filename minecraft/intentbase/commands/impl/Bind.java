package intentbase.commands.impl;

import intentbase.IntentBase;
import intentbase.commands.Command;
import intentbase.modules.Module;
import intentbase.util.Printer;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

    public Bind() {
        super("Bind", "Binds a module", "bind <name> <key> | clear", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];

            for (Module module : IntentBase.INSTANCE.modules) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    module.keyBindSetting.setCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
                    Printer.print(String.format("%s is now binded to %s", module.name, Keyboard.getKeyName(module.getKey())));
                    break;
                }
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (Module module : IntentBase.INSTANCE.modules) {
                    module.keyBindSetting.setCode(Keyboard.KEY_NONE);
                }
            }
            Printer.print("Cleared all keybinds");
        }
    }
}
