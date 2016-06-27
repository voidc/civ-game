package de.gymwkb.civ.registry;

public enum Resource {
    STONE("Stein"),
    WOOD("Holz");
    
    public final String name;
    
    public static final int COUNT = Resource.values().length;
    
    private Resource(String name) {
        this.name = name;
    }
}
