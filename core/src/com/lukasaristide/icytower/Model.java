package com.lukasaristide.icytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Model implements Disposable {
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage menu, ranking, settings, game;
    private World world;
    Screen screen = Screen.menu;
    float width, height;

    void act(){
        switch (screen){
            case menu:
                menu.act();
                break;
            case settings:
                settings.act();
                break;
            case ranking:
                ranking.act();
                break;
            case game:
                game.act();
                break;
            default:
                break;
        }
    }

    void setMenu(){
        screen = Screen.menu;
    }
    void setSettings(){
        screen = Screen.settings;
    }
    void setRanking(){
        screen = Screen.ranking;
    }
    void setGame(){
        screen = Screen.game;
    }

    Model(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        camera.update();

        batch = new SpriteBatch();

        ScreenViewport viewport = new ScreenViewport(camera);

        menu = new Stage(viewport, batch);
        ranking = new Stage(viewport, batch);
        settings = new Stage(viewport, batch);
        game = new Stage(viewport, batch);

        world = new World(new Vector2(0,-10),true);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        setMenu();
    }
    @Override
    public void dispose() {
        batch.dispose();
        menu.dispose();
        ranking.dispose();
        settings.dispose();
        game.dispose();
        world.dispose();
    }
}

enum Screen{
    menu, ranking, settings, game;
}
