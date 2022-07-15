package intentbase.modules;

import intentbase.events.Event;
import intentbase.settings.KeybindSetting;
import intentbase.settings.Setting;
import intentbase.util.Printer;
import net.minecraft.client.Minecraft;
import java.util.*;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public boolean toggled;
    public KeybindSetting keyBindSetting = new KeybindSetting(0);
    public Category category;

    public boolean expanded;
    public int index;
    public List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, int keyBind, Category category) {
        this.name = name;
        this.keyBindSetting.code = keyBind;
        this.category = category;
        this.addSettings(keyBindSetting);
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyBindSetting ? 1 : 0));
    }

    public void onEvent(Event event) {

    }

    public boolean isEnabled() {
        return toggled;
    }

    public int getKey() {
        return keyBindSetting.code;
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            Printer.print("Enabled " + this.name);
            onEnable();
        } else {
            Printer.print("Disabled " + this.name);
            onDisable();
        }
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        RENDER("Render");

        public String name;
        public int moduleIndex;

        Category(String name) {
            this.name = name;
        }
    }

}
