package de.gymwkb.civ.view;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.gymwkb.civ.CivGame;
import de.gymwkb.civ.GameScreen;
import de.gymwkb.civ.game.HumanPlayerController;
import de.gymwkb.civ.game.HumanPlayerController.PlayerListener;
import de.gymwkb.civ.game.Unit;
import de.gymwkb.civ.registry.Resource;

import java.util.function.Consumer;

public class HUD extends Stage implements PlayerListener {
    private Skin skin;
    private HumanPlayerController humanPlayer;
    
    private Table hudContainer;
    private Label[] resourceLabels;
    private Label infoLabel;
    
    private Window unitWindow;
    private Label[] unitPropertyLabels;
    /*
     * 0: Health
     * 1: Level
     * 2: Strength
     * 3: Defence
     * 4: ViewRange
     * 5: MoveRange
     * 6: AttackRange
     */

    private Button nextTurnButton;
    
    public HUD(HumanPlayerController controller) {
        super(new ExtendViewport(GameScreen.GAME_WIDTH, GameScreen.GAME_HEIGHT));
        this.humanPlayer = controller;
        humanPlayer.registerListener(this);
        skin = CivGame.instance.assets.get("skin/uiskin.json");
    }
    
    public void show() {
        hudContainer = new Table();
        //hudContainer.setDebug(true);
        hudContainer.setFillParent(true);
        addActor(hudContainer);
        
        hudContainer.left().top();
        
        resourceLabels = new Label[Resource.COUNT];
        for(int i = 0; i < Resource.COUNT; i++) {
            Resource r = Resource.values()[i];
            int amount = humanPlayer.getPlayer().getResource(r);
            resourceLabels[i] = new Label(r.name + ": " + amount, skin, "value");
            hudContainer.add(resourceLabels[i]).pad(10);
        }
        
        infoLabel = new Label("", skin, "value");
        hudContainer.add(infoLabel).pad(10);

        hudContainer.row();
        nextTurnButton = new TextButton("Next Turn", skin, "default");
        nextTurnButton.addListener(l(event -> humanPlayer.nextTurn()));
        hudContainer.add(nextTurnButton);
        
        unitWindow = new Window("Einheit", skin);
        unitWindow.setSize(350f, 250f);
        unitWindow.setPosition(0, 0);
        unitWindow.setVisible(false);
        addActor(unitWindow);
        
        unitPropertyLabels = new Label[7];
        unitWindow.defaults().pad(8f);
        for(int i = 0; i < 7; i++) {
           unitPropertyLabels[i] = new Label("", skin, "value");
        }
        unitWindow.add(unitPropertyLabels[0]).colspan(6);
        unitWindow.row();
        unitWindow.add(unitPropertyLabels[1]).colspan(6);
        unitWindow.row();
        unitWindow.add(unitPropertyLabels[2]).colspan(3);
        unitWindow.add(unitPropertyLabels[3]).colspan(3);
        unitWindow.row();
        unitWindow.add(unitPropertyLabels[4]).colspan(2);
        unitWindow.add(unitPropertyLabels[5]).colspan(2);
        unitWindow.add(unitPropertyLabels[6]).colspan(2);
    }
    
    private void updateResources() {
        for(int i = 0; i < Resource.COUNT; i++) {
            Resource r = Resource.values()[i];
            int amount = humanPlayer.getPlayer().getResource(r);
            resourceLabels[i].setText(r.name + ": " + amount);
        }
    }
    
    private void showUnitWindow(Unit unit) {
        unitWindow.getTitleLabel().setText(unit.type.name);
        unitPropertyLabels[0].setText(MathUtils.floor(unit.getHealth()) + "/" + 
                MathUtils.floor(unit.type.maxHealth) + " (" + (unit.getHealthPercentage() * 100f) + "%)");
        unitPropertyLabels[1].setText("Level " + unit.getLevel() + " (" + (unit.getLevelProgress() * 100) + "%)");
        unitPropertyLabels[2].setText("STR " + unit.type.strength);
        unitPropertyLabels[3].setText("DEF " + unit.type.defence);
        unitPropertyLabels[4].setText("VR " + unit.type.viewRange);
        unitPropertyLabels[5].setText("MR " + unit.getRemainingMoves());
        unitPropertyLabels[6].setText("AR " + unit.type.attackRange);
        unitWindow.setVisible(true);
    }
    
    private void hideUnitWindow() {
        unitWindow.setVisible(false);
    }
    
    @Override
    public void draw() {
        super.draw();
    }
    
    @Override
    public void onUnitSelected(Unit unit) {
        if(unit != null)
            showUnitWindow(unit);
        else
            hideUnitWindow();
    }
    
    @Override
    public void showInfo(String info) {
        infoLabel.setText(info);
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
