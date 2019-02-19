package pl.jacci.flappybird.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;


public class GameStateManager {

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){                           //dt - delta time - różnica czasu pomiędzy ramkami
        states.peek().update(dt);                           //Looks at the object at the top of this stack without removing it from the stack
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}