package com.solardev.SolarHub.utils;

import com.solardev.SolarHub.SolarHub;
import org.bukkit.entity.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

public final class BungeeUtils {
    private BungeeUtils() {
    }

    public static void send(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException var5) {
        }

        player.sendPluginMessage(SolarHub.getINSTANCE(), "BungeeCord", b.toByteArray());
    }

    public static void sendAll(String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException var5) {
        }

        Iterator var3 = SolarHub.getINSTANCE().getServer().getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player player = (Player)var3.next();
            player.sendPluginMessage(SolarHub.getINSTANCE(), "BungeeCord", b.toByteArray());
        }

    }

}


