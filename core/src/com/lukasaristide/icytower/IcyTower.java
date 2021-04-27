package com.lukasaristide.icytower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Collections;

public class IcyTower extends ApplicationAdapter {
	Model model;
	View view;
	Controller controller;

	@Override
	public void create () {
		model = new Model();
		controller = new Controller(model);
		view = new View(model, controller);
	}

	@Override
	public void render () {
		model.act();
		view.draw();
		controller.control();
	}
	
	@Override
	public void dispose () {
		model.dispose();
		view.dispose();
	}

}
