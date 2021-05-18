package com.lukasaristide.icytower;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class Wall extends Actor {
    Model model;
    Body body;
    Drawable texture;
    float width;

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
    }

    Wall(Model m, boolean right){
        super();
        model = m;
        width = m.row;
        if(right)
            setPosition(m.width, 0);
        else
            setPosition(-m.row, 0);
        setSize(width,m.height*2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(Align.center) / model.scale, getY(Align.center) / model.scale);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape box = new PolygonShape();
        box.setAsBox((width / 2) / model.scale, (m.height) / model.scale);

        body = m.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(box,0f);
        fixture.setFriction(0);

        texture = model.view.buttonSkin.getDrawable("button");

        box.dispose();
    }
}
