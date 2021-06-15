package com.lukasaristide.icytower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

class View implements Disposable {
    private Model model;
    private Controller controller;
    SpriteBatch batch;
    private BitmapFont font128, font64, font32;
    private Texture bg;
    Texture hero;
    Skin buttonSkin;
    TextButton rec, ranking_b, settings_b;
    Button back_menu_game, back_menu_ranking, back_menu_settings;
    Slider jump, tilt, speed;
    String title = "Slimy Well";
    float scaleX, scaleY;

    private void loadTextures(){
        bg = new Texture("background2.png");
        hero = new Texture("hero.png");
        buttonSkin = new Skin(
                Gdx.files.internal("button_skin.json"),
                new TextureAtlas(Gdx.files.internal("button_skin.atlas"))
        );
        buttonSkin.add("font", font64, font64.getClass());
    }

    private void setUpButtons(){
        Label.LabelStyle lstyle = new Label.LabelStyle(font64, Color.GOLD);

        rec = new TextButton("Play",buttonSkin);
        Label game_label = new Label("Play",lstyle);
        game_label.setAlignment(Align.center);
        rec.setLabel(game_label);

        ranking_b = new TextButton("Ranking", buttonSkin);
        Label ranking_label = new Label("Ranking",lstyle);
        ranking_label.setAlignment(Align.center);
        ranking_b.setLabel(ranking_label);

        settings_b = new TextButton("Settings", buttonSkin);
        Label settings_label = new Label("Settings",lstyle);
        settings_label.setAlignment(Align.center);
        settings_b.setLabel(settings_label);

        back_menu_game = new Button(buttonSkin,"back");
        back_menu_ranking = new Button(buttonSkin,"back");
        back_menu_settings = new Button(buttonSkin,"back");

        float menu_button_w = model.width*0.7f, menu_button_h = model.height * 0.1f;

        rec.setPosition(model.width * 0.15f,model.height * 0.49f);
        rec.setSize(menu_button_w,menu_button_h);
        rec.addListener(controller.game_button);

        ranking_b.setPosition(model.width * 0.15f,model.height * 0.36f);
        ranking_b.setSize(menu_button_w,menu_button_h);
        ranking_b.addListener(controller.ranking_button);

        settings_b.setPosition(model.width * 0.15f,model.height * 0.23f);
        settings_b.setSize(menu_button_w,menu_button_h);
        settings_b.addListener(controller.settings_button);

        back_menu_game.setPosition(model.width * 0.8f,model.height * 0.9f);
        back_menu_game.setSize(model.width * 0.1f, model.width * 0.1f);
        back_menu_game.addListener(controller.back_menu_button);

        back_menu_ranking.setPosition(model.width * 0.8f,model.height * 0.9f);
        back_menu_ranking.setSize(model.width * 0.1f, model.width * 0.1f);
        back_menu_ranking.addListener(controller.back_menu_button);

        back_menu_settings.setPosition(model.width * 0.8f,model.height * 0.9f);
        back_menu_settings.setSize(model.width * 0.1f, model.width * 0.1f);
        back_menu_settings.addListener(controller.back_menu_button);
    }

    private void createMenu(){
        model.menu.addActor(rec);
        model.menu.addActor(ranking_b);
        model.menu.addActor(settings_b);
    }

    void createReturnButtonGame(){
        model.game.addActor(back_menu_game);
    }

    private void createReturnButtonRanking(){
        model.ranking.addActor(back_menu_ranking);
    }

    private void createReturnButtonSettings(){
        model.settings.addActor(back_menu_settings);
    }

    void createRanking(){
        model.ranking.clear();
        createReturnButtonRanking();
        model.ranking.addListener(controller.back_key);
        if(model.scores == null)
            return;
        Label.LabelStyle lstyle = new Label.LabelStyle(font64, Color.GOLD);
        float rec_w = model.width*0.7f, rec_h = model.height * 0.07f;
        int which = 0;
        for(Integer i : model.scores){
            rec = new TextButton(i.toString(),buttonSkin);
            Label label = new Label(i.toString(),lstyle);
            label.setAlignment(Align.center);
            rec.setLabel(label);

            rec.setPosition(model.width * 0.15f,model.height * 0.8f - model.height * 0.1f * which++);
            rec.setSize(rec_w,rec_h);
            model.ranking.addActor(rec);
        }
    }

    private void createSettings(){
        createReturnButtonSettings();
        jump = new Slider(0.8f, 1.2f, 0.01f, false, buttonSkin);
        speed = new Slider(0.5f, 3f, 0.01f, false, buttonSkin);
        tilt = new Slider(0.5f, 1.5f, 0.01f, false, buttonSkin);

        float sl_w = model.width*0.7f, sl_h = model.height * 0.2f;

        jump.setValue(model.mod_jump);
        speed.setValue(model.mod_speed);
        tilt.setValue(model.mod_tilt);

        jump.setSize(sl_w, sl_h);
        speed.setSize(sl_w, sl_h);
        tilt.setSize(sl_w, sl_h);

        jump.setPosition(model.width * 0.15f, model.height * 0.05f);
        speed.setPosition(model.width * 0.15f, model.height * 0.3f);
        tilt.setPosition(model.width * 0.15f, model.height * 0.55f);

        model.settings.addActor(jump);
        model.settings.addActor(speed);
        model.settings.addActor(tilt);

        Label.LabelStyle lstyle = new Label.LabelStyle(font64, Color.GOLD);
        Label jumpLabel = new Label("Jump scale", lstyle);
        Label speedLabel = new Label("Speed scale", lstyle);
        Label tiltLabel = new Label("Tilt scale", lstyle);

        jumpLabel.setAlignment(Align.center);
        speedLabel.setAlignment(Align.center);
        tiltLabel.setAlignment(Align.center);

        jumpLabel.setPosition(model.width * 0.15f, model.height * 0.15f);
        speedLabel.setPosition(model.width * 0.15f, model.height * 0.4f);
        tiltLabel.setPosition(model.width * 0.15f, model.height * 0.65f);

        jumpLabel.setSize(sl_w,sl_h);
        speedLabel.setSize(sl_w,sl_h);
        tiltLabel.setSize(sl_w,sl_h);

        model.settings.addActor(jumpLabel);
        model.settings.addActor(speedLabel);
        model.settings.addActor(tiltLabel);
    }

    private void genFonts(){
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("RoteFlora.ttf"));//"Manuskriptgotisch-X3yd.ttf"));//"Hamletornot-K7y7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(128 * scaleX);
        font128 = gen.generateFont(parameter);
        parameter.size = (int)(64 * scaleX);
        font64 = gen.generateFont(parameter);
        parameter.size = (int)(32 * scaleX);
        font32 = gen.generateFont(parameter);
    }

    private void drawMenu(){
        batch.begin();
        font128.draw(batch,title,0f, model.height * 0.75f, model.width, Align.center,true);
        batch.end();
        model.menu.draw();
    }

    private void drawScore(){
        batch.begin();
        font64.setColor(Color.RED);
        font64.draw(batch,String.valueOf(model.score),model.width / 10, model.height * 0.95f, model.width, Align.left, true);
        batch.end();
    }

    void draw(){
        ScreenUtils.clear(1,1,1,1);
        batch.setProjectionMatrix(model.camera.combined);
        batch.begin();
        batch.draw(bg,0,0,model.width,model.height);
        batch.end();
        switch (model.screen){
            case menu:
                drawMenu();
                break;
            case game:
                model.game.draw();
                drawScore();
                break;
            case ranking:
                model.ranking.draw();
                break;
            case settings:
                model.settings.draw();
                break;
        }
    }

    View(Model m, Controller c){
        model = m;
        controller = c;
        m.view = this;

        scaleX = m.width / 1080.0f;
        scaleY = m.height / 2080.0f;

        m.row /= scaleX;

        batch = m.batch;

        genFonts();
        loadTextures();
        setUpButtons();
        createMenu();
        createReturnButtonGame();
        createSettings();
    }

    @Override
    public void dispose() {
        font32.dispose();
        font64.dispose();
        font128.dispose();
        bg.dispose();
    }
}
