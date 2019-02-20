package pl.jacci.flappybird.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;                       //Brent używał tu vector3 (x,y,z) zamiast Vector2 (x,y). Zmieniłem to.

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 150;                //szybkość przesuwania się rur (prędkość ptaka w poziomie)
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flapSound;

    private Texture bird;

    public Bird(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
            //bird = new Texture("bird.png");       //używane przed wprowadzeniem animacji
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
            //bounds = new Rectangle(x,y, bird.getWidth(), bird.getHeight());
        bounds = new Rectangle(x,y, texture.getWidth()/3, texture.getHeight());
        flapSound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

    }

    public void update(float dt){
        birdAnimation.update(dt);
        if (position.y > 0){
            velocity.add(0, GRAVITY);
        }
        velocity.scl(dt);                               //mnoży x i y wektora2 (velocity) przez dt
        position.add(MOVEMENT * dt, velocity.y);
        if (position.y < 0){
            position.y = 0;
        }

        velocity.scl(1/dt);                             //cofa wcześniejsze mnożenie aby można było z powrotem je wykonać w kolejnej klatce
        bounds.setPosition(position.x, position.y);
    }

    public void jump(){
        velocity.y = 250;
        flapSound.play(0.2f);
    }

    public void dispose(){
        texture.dispose();
        flapSound.dispose();
    }


    //GETTERY

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
