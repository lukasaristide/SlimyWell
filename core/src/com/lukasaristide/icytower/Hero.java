package com.lukasaristide.icytower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class Hero extends Actor {
    Model model;
    Body body;
    Texture texture;

    @Override
    public void act(float delta) {
        super.act(delta);
        Vector2 v = body.getPosition();
        model.hero_height = v.y;
        setPosition(v.x * model.scale, v.y * model.scale, Align.center);
        if(v.y < 0)
            model.setMenu();
        if(v.y > (model.height * 0.7) / model.scale)
            model.speed += 0.01;
        else
            model.speed = model.speed_default;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        model.view.batch.draw(texture,getX(),getY(), model.row, model.row);
    }

    Hero(Model m){
        super();
        model = m;
        setPosition(m.width/2 - m.row/2, m.row + 10);
        setSize(m.row, m.row);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(Align.center) / model.scale, getY(Align.center) / model.scale);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape box = new PolygonShape();
        box.setAsBox((m.row / 2) / model.scale, (m.row / 2) / model.scale);

        body = m.world.createBody(bodyDef);
        body.setGravityScale(1);
        Fixture fixture = body.createFixture(box, 1);
        fixture.setFriction(0.15f);
        fixture.setRestitution(0);

        texture = model.view.hero;

        box.dispose();
    }
}
