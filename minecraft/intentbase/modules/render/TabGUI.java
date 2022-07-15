package intentbase.modules.render;

import intentbase.IntentBase;
import intentbase.events.Event;
import intentbase.events.listeners.*;
import intentbase.events.listeners.EventRenderGUI;
import intentbase.modules.Module;
import intentbase.settings.*;
import intentbase.util.Printer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.apache.commons.lang3.event.EventUtils;
import org.lwjgl.input.Keyboard;
import java.awt.Color;
import java.util.List;

public class TabGUI extends Module {

    public int currentTab;
    public boolean expanded;
    public BooleanSetting keyPressDebug = new BooleanSetting("Debug", false);
    public ModeSetting rgbMode = new ModeSetting("RBG Mode", "Off", "SimpleRGB", "Rainbow", "Off");

    public TabGUI() {
        super("TabGUI", Keyboard.KEY_RBRACKET, Category.RENDER);
        toggled = true;
        this.addSettings(keyPressDebug, rgbMode);
    }

    public void onDisable() {
        Printer.print("There is no ClickGUI at the moment so press " + Keyboard.getKeyName(getKey()));
    }

    public void onEvent(Event e) {

        if (e instanceof EventUpdate) {
            Category category = Module.Category.values()[currentTab];
            List<Module> modules = IntentBase.INSTANCE.getModulesByCategory(category);

            if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                Module module = modules.get(category.moduleIndex);

                if (!module.settings.isEmpty() && module.settings.get(module.index).focused && module.settings.get(module.index) instanceof KeybindSetting) {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.motionZ = 0;
                }
            }
        }
        if (e instanceof EventRenderGUI) {
            FontRenderer fr = mc.fontRendererObj;

            int primaryColor = rgbMode.is("SimpleRGB") ? getRainbow(4, 0.8f, 3) : 0xff00ffff, secondaryColor = rgbMode.is("SimpleRGB") ? getRainbow(4, 0.8f, 0.8f) : 0xff00ffaa;

            Gui.drawRect(5, 30.5, 70, 30 + Category.values().length * 16 + 1.5, 0x90000000);
            Gui.drawRect(6.5, 33 + currentTab * 16, 7 + 61, 33 + currentTab * 16 + 12, primaryColor);

            int count = 0;
            for (Category c : Module.Category.values()) {
                fr.drawStringWithShadow(c.name, 11, 35 + count * 16, -1);

                count++;
            }

            if (expanded) {
                Category category = Module.Category.values()[currentTab];
                List<Module> modules = IntentBase.INSTANCE.getModulesByCategory(category);

                if (modules.size() == 0) {
                    return;
                }

                Gui.drawRect(70, 30.5, 70 + 68, 30 + modules.size() * 16 + 1.5, 0x90000000);
                Gui.drawRect(70, 33 + category.moduleIndex * 16, 7 + 61 + 68, 33 + category.moduleIndex * 16 + 12, primaryColor);

                count = 0;
                for (Module m : modules) {
                    fr.drawStringWithShadow(m.name, 73, 35 + count * 16, -1);

                    if (count == category.moduleIndex && m.expanded) {
                        int index = 0, maxLength = 0;
                        for (Setting setting : m.settings) {
                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                if (maxLength < fr.getStringWidth(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"))) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"));
                                }
                            }

                            if (setting instanceof NumberSetting) {
                                NumberSetting number = (NumberSetting) setting;
                                if (maxLength < fr.getStringWidth(setting.name + ": " + number.getValue())) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + number.getValue());
                                }
                            }

                            if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                if (maxLength < fr.getStringWidth(setting.name + ": " + mode.getMode())) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + mode.getMode());
                                }
                            }
                            if (setting instanceof KeybindSetting) {
                                KeybindSetting keybindSetting = (KeybindSetting) setting;
                                if (maxLength < fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keybindSetting.code))) {
                                    maxLength = fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keybindSetting.code));
                                }
                            }
                            index++;
                        }


                        if (!m.settings.isEmpty()) {
                            Gui.drawRect(70 + 68, 30.5, 70 + 68 + maxLength + 6, 30 + m.settings.size() * 16 + 1.5, 0x90000000);
                            Gui.drawRect(70 + 68, 33 + m.index * 16, 7 + 61 + 68 + maxLength + 6, 33 + m.index * 16 + 12, m.settings.get(m.index).focused ? secondaryColor : primaryColor);


                            index = 0;
                            for (Setting setting : m.settings) {
                                if (setting instanceof BooleanSetting) {
                                    BooleanSetting bool = (BooleanSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + (bool.isEnabled() ? "Enabled" : "Disabled"), 73 + 68, 35 + index * 16, -1);
                                }

                                if (setting instanceof NumberSetting) {
                                    NumberSetting number = (NumberSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + number.getValue(), 73 + 68, 35 + index * 16, -1);
                                }

                                if (setting instanceof ModeSetting) {
                                    ModeSetting mode = (ModeSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), 73 + 68, 35 + index * 16, -1);
                                }
                                if (setting instanceof KeybindSetting) {
                                    KeybindSetting keybindSetting = (KeybindSetting) setting;
                                    fr.drawStringWithShadow(setting.name + ": " + Keyboard.getKeyName(keybindSetting.code), 73 + 68, 35 + index * 16, -1);
                                }
                                index++;
                            }
                        }
                    }
                        count++;

                }

            }
        }

            if (e instanceof EventKey) {

                int code = ((EventKey) e).code;

                Category category = Module.Category.values()[currentTab];
                List<Module> modules = IntentBase.INSTANCE.getModulesByCategory(category);
                if (keyPressDebug.isEnabled()) {
                    Printer.print("called " + Keyboard.getKeyName(code) + " " + currentTab + " " + category.moduleIndex + " " + (expanded ? modules.get(category.moduleIndex).name + " " : "") + (modules.get(category.moduleIndex).expanded ? modules.get(category.moduleIndex).settings.get(modules.get(category.moduleIndex).index).name : "") + ((modules.get(category.moduleIndex).settings.get(modules.get(category.moduleIndex).index).focused) ? " locked" : ""));
                }

                if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                    Module module = modules.get(category.moduleIndex);

                    if (!module.settings.isEmpty() && module.settings.get(module.index).focused && module.settings.get(module.index) instanceof KeybindSetting) {
                        if (code != Keyboard.KEY_RETURN && code != Keyboard.KEY_UP && code != Keyboard.KEY_LEFT && code != Keyboard.KEY_RIGHT && code != Keyboard.KEY_DOWN) {
                            if (code == Keyboard.KEY_ESCAPE) {
                                KeybindSetting keyBind = (KeybindSetting) module.settings.get(module.index);

                                keyBind.code = Keyboard.KEY_NONE;
                                keyBind.focused = false;
                                return;
                            }
                            KeybindSetting keyBind = (KeybindSetting) module.settings.get(module.index);

                            keyBind.code = code;
                            keyBind.focused = false;

                            return;
                        }
                    }
                }

                if (code == Keyboard.KEY_UP) {
                    if (expanded) {
                        if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                            Module module = modules.get(category.moduleIndex);
                            if (module.settings.get(module.index).focused){
                                Setting setting = modules.get(category.moduleIndex).settings.get(module.index);
                                if (setting instanceof NumberSetting) {
                                    ((NumberSetting)setting).increment(true);
                                }
                            } else {
                                if (module.index <= 0) {
                                    module.index = module.settings.size() - 1;
                                } else {
                                    module.index--;
                                }
                            }
                        } else {
                            if (category.moduleIndex <= 0) {
                                category.moduleIndex = modules.size() - 1;
                            } else {
                                category.moduleIndex--;
                            }
                        }
                    } else {
                        if (currentTab <= 0) {
                            currentTab = Module.Category.values().length - 1;
                        } else {
                            currentTab--;
                        }
                    }
                }
// if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                if (code == Keyboard.KEY_DOWN) {
                    if (expanded) {
                        if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                            Module module = modules.get(category.moduleIndex);

                            if (module.settings.get(module.index).focused) {
                                Setting setting = modules.get(category.moduleIndex).settings.get(module.index);
                                if (setting instanceof NumberSetting) {
                                    ((NumberSetting)setting).increment(false);
                                }
                            } else {
                                if (module.index >= module.settings.size() - 1) {
                                    module.index = 0;
                                } else {
                                    module.index++;
                                }
                            }
                        } else {
                            if (category.moduleIndex >= modules.size() - 1) {
                                category.moduleIndex = 0;
                            } else {
                                category.moduleIndex++;
                            }
                        }
                    } else {
                        if (currentTab >= Module.Category.values().length - 1) {
                            currentTab = 0;
                        } else {
                            currentTab++;
                        }
                    }
                }

                if (code == Keyboard.KEY_RETURN) {
                    if (expanded && modules.size() != 0) {
                        Module module = modules.get(category.moduleIndex);
                        if (module.settings.isEmpty()) {
                            module.toggle();
                        }
                        if (!module.expanded) {
                            module.expanded = true;
                        } else if (module.expanded) {
                            module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
                        }
                    }
                }

                if (code == Keyboard.KEY_RIGHT) {
                    if (expanded && modules.size() != 0) {
                        Module module = modules.get(category.moduleIndex);

                        if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                            if (module.settings.get(module.index).focused) {
                                Setting setting = modules.get(category.moduleIndex).settings.get(module.index);

                                if (setting instanceof BooleanSetting) {
                                    ((BooleanSetting) setting).toggle();
                                }
                                if (setting instanceof ModeSetting) {
                                    ((ModeSetting) setting).cycle();
                                }
                            }

                        } else {
                            modules.get(category.moduleIndex).toggle();
                        }
                    }
                    expanded = true;
                }

                if (code == Keyboard.KEY_LEFT) {
                    if (expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
                        Module module = modules.get(category.moduleIndex);

                        if (module.settings.get(module.index).focused) {

                        } else {
                            modules.get(category.moduleIndex).expanded = false;
                        }
                    } else {
                        expanded = false;
                    }
                }
            }
    }

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 4000) / (float) (seconds * 4000f));
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }
}
