package intentbase.settings;

public class KeybindSetting extends Setting {

    public int code;

    public KeybindSetting(int keyCode) {
        this.name = "Keybind";
        this.code = keyCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
