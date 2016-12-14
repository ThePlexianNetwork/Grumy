package org.plexian.grumy.world;

import java.io.File;
import java.net.URISyntaxException;

import org.plexian.grumy.Game;

public enum Sound {
    PLAYER_HURT("sounds/entity/player/hurt.ogg", 1.0f),
    TILE_BREAK("sounds/tile/break.ogg", 1.0f),
    TILE_PLACE("sounds/tile/place.ogg", 1.0f);
    
    private File file;
    private float volume;
    
    Sound(String file, float volume){
        try{
            this.file = new File(Game.class.getClassLoader().getResource(file).toURI());
            this.volume = volume;
        }catch(Exception e){
            Game.LOG.severe(e.getLocalizedMessage());
        }
    }
    
    public File getFile(){
        return file;
    }
    
    public float getDefaultVolume(){
        return this.volume;
    }
    
    public static void playSound(Sound sound, Location loc, float volume, float pitch){
        loc.getWorld().getSoundManager().play(sound.file, loc.getX(), loc.getY(), volume, pitch);
    }
    
    public static void playSound(Sound sound, Location loc, float pitch){
        Sound.playSound(sound, loc, sound.getDefaultVolume(), pitch);
    }
    
    public static void playound(Sound sound, Location loc){
        Sound.playSound(sound, loc, sound.getDefaultVolume(), 1.0f);
    }
    
}
