package me.dags.noclip.client;

import org.lwjgl.input.Keyboard;

/**
 * @author dags <dags@dags.me>
 */
public class KeyBind {

    private String keyName = "KEY_N";
    private transient int keyCode = -1;
    private transient boolean heldDown = false;

    public KeyBind() {
    }

    public KeyBind(int keyCode) {
        this.keyName = Keyboard.getKeyName(keyCode);
        this.keyCode = keyCode;
    }

    void init() {
        keyCode = Keyboard.getKeyIndex(keyName);
    }

    boolean pressed() {
        if (isKeyDown()) {
            return !heldDown && (heldDown = true);
        }
        return heldDown = false;
    }

    private boolean isKeyDown() {
        return Keyboard.isKeyDown(keyCode);
    }
}
