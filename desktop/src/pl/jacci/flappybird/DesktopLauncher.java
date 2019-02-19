package pl.jacci.flappybird;

/**  na podstawie kursu youTube Brent Aureli: https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy&index=1
 *   https://github.com/BrentAureli/FlappyDemo
 *  Zastosowano LibGdx
*/

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = FlappyDemo.WIDTH;
        config.height = FlappyDemo.HEIGHT;
        config.title = FlappyDemo.TITLE;
        new LwjglApplication(new FlappyDemo(), config);
    }
}
