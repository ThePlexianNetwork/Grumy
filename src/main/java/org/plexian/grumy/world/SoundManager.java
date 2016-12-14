package org.plexian.grumy.world;

import org.plexian.grumy.Game;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOgg;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;    aulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
    private static SoundSystem mainSoundSystem;
    
    public SoundManager(){
        Class libraryType;
       
        
        SoundSystemConfig.setNumberStreamingChannels(4);;
        SoundSystemConfig.setNumberNormalChannels(28);
        
        try{
            if(SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class)){
                libraryType = LibraryLWJGLOpenAL.class;
            }else{
                libraryType = Library.class;
            }
            
            SoundSystemConfig.setCodec("ogg", CodecJOgg.class);
            
            mainSoundSystem = new SoundSystem(libraryType);
            mainSoundSystem.setMasterVolume(0.5f);
        }catch(Exception e){
            Game.LOG.severe(e.getLocalizedMessage());
        }
        
    }
    
    public void registerBackgroundMusic(String id, String filename, boolean loop){
        mainSoundSystem.backgroundMusic(id, filename, loop);
    }

    public void registerSound(Sound sound){
        mainSoundSystem.newStreamingSource(false, sound.name().toLowerCase(), sound.getFile().getAbsolutePath(), false, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
        mainSoundSystem.setVolume(sound.name().toLowerCase(), sound.getDefaultVolume());
    }
    
    public void playQuickSound(Sound sound, float x, float y){
        mainSoundSystem.quickPlay(false, sound.getFile().getName(), false, x, y, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
    }
    
    public void playSound(Sound sound, float x, float y){
        mainSoundSystem.play(sound.name().toLowerCase());
    }
}
