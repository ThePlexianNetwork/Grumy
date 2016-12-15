package org.plexian.grumy.world;

import java.io.File;
import java.net.URISyntaxException;

import org.plexian.grumy.Game;

public enum Sound {
   // PLAYER_HURT("/sounds/entity/player/hurt.ogg", 1.0f),
    TILE_BREAK("sounds/tile/break.wav", 1.0f),
    TILE_PLACE("sounds/tile/place.wav", 1.0f);
    
    private float volume;
    private String locationString;
    
    static{
        for(Sound s : values()){
           // Game.world.getSoundManager().addSound(s.name().toLowerCase(), s.getLocation());
        }
    };
    
    Sound(String file, float volume){
        try{
            this.locationString = file;
            this.volume = volume;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public float getDefaultVolume(){
        return this.volume;
    }
    
    public String getLocation(){
        return this.locationString;
    }
    
    public static void playSound(Sound sound, Location loc){
        loc.getWorld().getSoundManager().playSound(sound);
    }
}
