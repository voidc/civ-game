package de.gymwkb.civ.input;

import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.gymwkb.civ.CivGame;

public class HUD extends Stage {
    private HorizontalGroup hgroup;
    private Skin skin;
    
    public HUD() {
        super(new ScreenViewport());
        skin = CivGame.instance.assets.get("skin/uiskin.json");
        hgroup = new HorizontalGroup();
        hgroup.align(Align.topRight);
        hgroup.pad(8);
        Label label = new Label("Label", skin, "default");
        hgroup.addActor(label);
        Button b = new TextButton("Button", skin, "default");
        b.addListener(l(event -> label.setText("Click")));
        hgroup.addActor(b);
        hgroup.setBounds(0, 0, getWidth(), getHeight());
        addActor(hgroup);
    }
    
    @Override
    public void draw() {
        super.draw();
    }
    
    private ChangeListener l(Consumer<ChangeEvent> listener) {
        return new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.accept(event);
            }
            
        };
    }
}