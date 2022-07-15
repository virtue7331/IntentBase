package intentbase.modules.render;

import intentbase.events.Event;
import intentbase.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", Keyboard.KEY_O, Category.RENDER);
    }

    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
