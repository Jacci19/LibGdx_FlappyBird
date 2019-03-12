package pl.jacci.flappybird.sprites;

/**omówienie w lekcji 13:   https://www.youtube.com/watch?v=G44YMwBoJXM&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy&index=13    */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;        // trzyma wszystkie ramki animacji
    private float maxFrameTime;                 // czas trwania pojedynczej ramki
    private float currentFrameTime;
    private int frameCount;                     // ilość ramek w animacji (tu: 3)
    private int frame;                          // aktualna ramka (tu: jedna z trzech)

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){                //pętla rozdziela klatki animacji sklejone w jednym pliku png do osobnych ramek
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        //System.out.println("Frame: " + frame);
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frame = 0;
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}