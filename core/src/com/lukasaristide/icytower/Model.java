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

import java.util.Collections;
import java.util.List;

public class Model implements Disposable {
    SavedSettings savedSettings;
    Database database;
    List<Integer> scores;
    View view;
    Controller controller;
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage menu, ranking, settings, game;
    World world;
    Screen screen = Screen.menu;
    Hero hero;
    float width, height, speed = 0, row, scale, hero_height = 1.5f, speed_default = 0, mod_jump = 1, mod_tilt = 1, mod_speed = 1;
    int score;

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
                world.step(1/60f, 6, 2);
                game.act();
                break;
            default:
                break;
        }
    }

    void setFloors(){
        Floor ground = new Floor(this);
        game.addActor(ground);
        for(int j = 3; j <= height; j += 3)
            game.addActor(new Floor(this, j, j % 2 == 0));
    }

    void startGame(){
        speed_default = score = 0;

        world = new World(new Vector2(0,-5),true);
        game.clear();
        view.createReturnButtonGame();
        controller.addListenerGame();
        setFloors();
        hero = new Hero(this);
        game.addActor(hero);
        game.addListener(controller.hero_listener);

        game.addActor(new Wall(this, true));
        game.addActor(new Wall(this, false));
    }

    void saveScore(){
        if(score == 0)
            return;
        if(scores == null)
            scores = database.get();
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder());
        while(scores.size() > 8)
            scores.remove(scores.size()-1);
        database.clear();
        database.insert(scores);
    }

    void setMenu(){
        if(screen == Screen.game)
            saveScore();
        else if(screen == Screen.settings){
            savedSettings.mod_jump = mod_jump = view.jump.getValue();
            savedSettings.mod_speed = mod_speed = view.speed.getValue();
            savedSettings.mod_tilt = mod_tilt = view.tilt.getValue();
        }
        screen = Screen.menu;
    }
    void setSettings(){
        screen = Screen.settings;
    }
    void setRanking(){
        scores = database.get();
        view.createRanking();
        screen = Screen.ranking;
    }
    void setGame(){
        startGame();
        screen = Screen.game;
    }

    Model(Database d, SavedSettings s){
        database = d;
        savedSettings = s;
        mod_tilt = s.mod_tilt;
        mod_speed = s.mod_speed;
        mod_jump = s.mod_jump;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        camera.update();

        batch = new SpriteBatch();

        ScreenViewport viewport = new ScreenViewport(camera);

        menu = new Stage(viewport, batch);
        ranking = new Stage(viewport, batch);
        settings = new Stage(viewport, batch);
        game = new Stage(viewport, batch);


        scale = width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        row = height / 25;
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

