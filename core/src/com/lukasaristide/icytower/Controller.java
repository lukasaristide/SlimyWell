package com.lukasaristide.icytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.time.Clock;

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
                            //System.exit(0);
                        }
                        else
                            lastClicked = time_now;
                    }
                    return true;
                }
            },
            hero_listener = new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Vector2 pos = model.hero.body.getPosition();
                    Vector2 vel = model.hero.body.getLinearVelocity();
                    if(vel.y <= -model.speed/2)
                        model.hero.body.applyLinearImpulse(0, model.mod_jump * 0.013f*(model.view.scaleY*model.view.scaleY) + Math.abs(vel.x/10000), pos.x, pos.y, true);
                    model.speed_default = model.mod_speed * 0.04f + model.score/10000;
                    return true;
                }
                @Override
                public boolean keyDown (InputEvent event, int keycode){
                    Vector2 pos = model.hero.body.getPosition();
                    switch (keycode){
                        case Input.Keys.UP:
                            Vector2 vel = model.hero.body.getLinearVelocity();
                            if(vel.y <= -model.speed/2)
                                model.hero.body.applyLinearImpulse(0, model.mod_jump * 0.013f*(model.view.scaleY/model.view.scaleX) + Math.abs(vel.x/10000), pos.x, pos.y, true);
                            model.speed_default = model.mod_speed * 0.04f + model.score/10000;
                            break;
                        case Input.Keys.RIGHT:
                            model.hero.body.applyLinearImpulse(model.mod_tilt * 0.002f/model.view.scaleX, 0, pos.x, pos.y, true);
                            break;
                        case Input.Keys.LEFT:
                            model.hero.body.applyLinearImpulse(model.mod_tilt * -0.002f/model.view.scaleX, 0, pos.x, pos.y, true);
                            break;
                        default:
                            break;
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

    void addListenerGame(){
        model.game.addListener(back_key);
    }

    Controller(Model m){
        model = m;
        m.controller = this;
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        model.menu.addListener(back_key_menu);
        addListenerGame();
        model.ranking.addListener(back_key);
        model.settings.addListener(back_key);
    }
}
