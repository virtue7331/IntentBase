package intentbase;

import intentbase.commands.CommandManager;
import intentbase.events.Event;
import intentbase.events.listeners.EventChat;
import intentbase.events.listeners.EventKey;
import intentbase.modules.Module;
import intentbase.modules.combat.Killaura;
import intentbase.modules.movement.Fly;
import intentbase.modules.movement.Sprint;
import intentbase.modules.player.Derp;
import intentbase.modules.player.NoFall;
import intentbase.modules.render.Fullbright;
import intentbase.modules.render.Hud;
import intentbase.modules.render.TabGUI;
import intentbase.ui.HUD;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum IntentBase {
    INSTANCE;

    public String chatName = "Intent";
    public String name = "Intent Base";
    public String version = "1";
    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public HUD hud = new HUD();
    public CommandManager commandManager = new CommandManager();

    public void startClient() {
        System.out.println("Starting " + name + " " + version);
        Display.setTitle(name + " " + version);

        modules.add(new Fly());
        modules.add(new Sprint());
        modules.add(new Fullbright());
        modules.add(new NoFall());
        modules.add(new Hud());
        modules.add(new TabGUI());
        modules.add(new Killaura());
        modules.add(new Derp());
    }

    public void onEvent(Event event) {
        if (event instanceof EventChat) {
            commandManager.handleChat((EventChat)event);
        }
        for (Module module : modules) {
            if (!module.toggled)
                continue;

            module.onEvent(event);

        }
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : this.modules) {
            if (m.category == category) {
                modules.add(m);
            }
        }

        return modules;
    }

    public void keyPress(int key) {
        onEvent(new EventKey(key));
        for (Module m :
                modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }

}
