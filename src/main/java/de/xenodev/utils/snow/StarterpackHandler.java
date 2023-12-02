package de.xenodev.utils.snow;

public class StarterpackHandler {
    private Boolean isStartedSnow = false;

    public StarterpackHandler() {
        if(isStartedSnow == false) {
            isStartedSnow = true;
            new FlakesHandler(new ConfigHandler());
        }
    }
}
