package de.gymwkb.civ.registry;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Registry implements Disposable {
    private static Registry instance;
    private Array<UnitType> unitTypes;

    private Registry() {

    }

    public static Registry getInstance() {
        if (instance == null)
            instance = new Registry();
        return instance;
    }

    public static void disposeInstance() {
        if (instance != null)
            instance.dispose();
    }

    @Override
    public void dispose() {

    }
}
