package me.dags.noclip.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author dags <dags@dags.me>
 */
public class Binds {

    private KeyBind noClipBind = new KeyBind(Keyboard.KEY_N);
    private KeyBind fullBrightBind = new KeyBind(Keyboard.KEY_MINUS);

    public void tick() {
        if (noClipBind.pressed()) {
            NoClipClient.getNoClipData().setNoClipping(!NoClipClient.getNoClipData().noClip());
            NoClipClient.sendNoClipData();
        }
        if (fullBrightBind.pressed()) {
            NoClipClient.getNoClipData().setFullbright(!NoClipClient.getNoClipData().fullBright());
        }
    }

    private void save(File config) {
        config.getParentFile().mkdirs();
        System.out.println(config.getParentFile());
        try (FileWriter writer = new FileWriter(config)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Binds load() {
        File configDir = new File(NoClipClient.getGameDir(), "config");
        File config = new File(configDir, "noclip.json");
        if (config.exists()) {
            try (FileReader reader = new FileReader(config)) {
                Binds binds = new Gson().fromJson(reader, Binds.class);
                binds.fullBrightBind.init();
                binds.noClipBind.init();
                reader.close();
                return binds;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Binds binds = new Binds();
        binds.save(config);
        return binds;
    }
}
