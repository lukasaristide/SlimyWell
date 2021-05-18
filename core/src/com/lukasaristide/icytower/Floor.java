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

public class Floor extends Actor {
    boolean right;
    Model model;
    Body body;
    Drawable texture;
    float width;

    @Override
    public void act(float delta) {
        body.setLinearVelocity(0, -model.speed);
        Vector2 v = body.getPosition();
        setPosition(v.x * model.scale, v.y * model.scale, Align.center);
        if(v.y < -0.5){
            remove();
            model.world.destroyBody(body);
            model.game.addActor(new Floor(model, model.height, right));
            model.score += 1;
        }
        if(v.y + (model.row/model.scale)/2 <= model.hero_height)
            body.getFixtureList().get(0).setSensor(false);
        else
            body.getFixtureList().get(0).setSensor(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(model.batch, getX(), getY(), width, model.row);
    }

    Floor(Model m, float h, boolean right){
        super();
        this.right = right;
        model = m;
        width = (MathUtils.random((m.width)/2 - m.row) + m.row);
        float pos = MathUtils.random((m.width - width)/2);
        if(right) {
            pos += m.width / 2;
            if(pos + width > model.width)
                pos = m.width / 2;
        }
        setPosition(pos, h * m.row);
        setSize(width,m.row);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(Align.center) / model.scale, getY(Align.center) / model.scale);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape box = new PolygonShape();
        box.setAsBox((width / 2) / model.scale, (0.5f * m.row) / model.scale);

        body = m.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(box,1f);
        fixture.setFriction(0.1f);

        texture = model.view.buttonSkin.getDrawable("button");

        box.dispose();
    }

    Floor(Model m){
        super();
        this.right = true;
        model = m;
        width = m.width;
        setPosition(0, 0);
        setSize(width,m.row);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(Align.center) / model.scale, getY(Align.center) / model.scale);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape box = new PolygonShape();
        box.setAsBox((width / 2) / model.scale, (m.row / 2) / model.scale);

        body = m.world.createBody(bodyDef);
        Fixture fixture = body.createFixture(box,1f);
        fixture.setFriction(0.1f);

        texture = model.view.buttonSkin.getDrawable("button");

        box.dispose();
    }
}
