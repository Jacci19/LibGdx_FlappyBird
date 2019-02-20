package pl.jacci.flappybird.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import pl.jacci.flappybird.FlappyDemo;
import pl.jacci.flappybird.sprites.Bird;
import pl.jacci.flappybird.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 140;                            //odstęp pomiędzy tubami
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -60;                         //przesunięcie poziomu gruntu

    private Bird bird;
    private Texture bg;
    private Tube tube;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        bird = new Bird(50, 300);
        bg = new Texture("bg.png");
        tube = new Tube(100);
        ground = new Texture("ground.png");
                                                            //grunty mają być jednocześnie wyświetlane dwa (obok siebie) bo jak jeden się przesunie to od razu musi być widoczny drugi.
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);                          //współrzędne pierwszego gruntu
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);    //współrzędne drugiego gruntu
        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){                               //tablica 4 tub, kolejne będą przenoszone ze schowanych po lewej stronie ekranu na prawą stronę
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){                                            //na kliknięcie gdziekolwiek na ekranie rób...
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);                                           //bierzemy tubę z tablicy

            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x  + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));     //jeśli się schowa po lewej to przenieś ją na prawą stronę
            }

            if(tube.collides(bird.getBounds())){
                gsm.set(new PlayState(gsm));                                    // po kolizji tuby z ptakiem ... restart game
            }
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){       // jeśli ptak dotknie gruntu...
            gsm.set(new PlayState(gsm));                                        //...restart game
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);                              // umieszcza tło
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);                     // umieszcza ptaka
        for (Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);             // umieszcza górne tuby
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);          // umieszcza dolne tuby
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);                                                // umieszcza grunt 1
        sb.draw(ground, groundPos2.x, groundPos2.y);                                                // umieszcza grunt 2 (obok grunt 1)
        sb.end();
    }

    @Override
    public void dispose() {                                         //usuwanie z pamięci
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes){
            tube.dispose();
        }
        System.out.println("Play State disposed");
    }

    private void updateGround(){                                                            //przelicza pozycje gruntu aby te już niewidoczne (schowane po lewej) pokazywały się po prawej stronie planszy
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}
