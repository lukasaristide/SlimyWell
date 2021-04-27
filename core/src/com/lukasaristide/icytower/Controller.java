package com.lukasaristide.icytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.time.Clock;
import java.time.LocalDateTime;

public class Controller {
    Model model;
    InputListener
            game_button = new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                    model.setGame();
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }
            },
            settings_button = new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                    model.setSettings();
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }
            },
            ranking_button = new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                    model.setRanking();
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }
            },
            back_menu_button = new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                    model.setMenu();
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }
            },
            back_key = new InputListener(){
                @Override
                public boolean keyDown (InputEvent event, int keycode){
                    if(keycode == Input.Keys.BACK)
                            model.setMenu();
                    return true;
                }
            },
            back_key_menu = new InputListener(){
                Clock clock = Clock.systemDefaultZone();
                long lastClicked = 0;
                @Override
                public boolean keyDown (InputEvent event, int keycode){
                    if(keycode == Input.Keys.BACK) {
                        long time_now = clock.instant().getEpochSecond();
                        if(time_now - lastClicked < 2) {
                            Gdx.app.exit();
                            System.exit(0);
                        }
                        else
                            lastClicked = time_now;
                    }
                    return true;
                }
            };

    void control(){
        switch (model.screen){
            case menu:
                Gdx.input.setInputProcessor(model.menu);
                break;
            case game:
                Gdx.input.setInputProcessor(model.game);
                break;
            case ranking:
                Gdx.input.setInputProcessor(model.ranking);
                break;
            case settings:
                Gdx.input.setInputProcessor(model.settings);
        }
    }

    Controller(Model m){
        model = m;
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        model.menu.addListener(back_key_menu);
        model.game.addListener(back_key);
        model.ranking.addListener(back_key);
        model.settings.addListener(back_key);
    }
}
