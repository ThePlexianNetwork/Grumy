package org.plexian.grumy.world;

import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.plexian.grumy.Game;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundManager {
    private HashMap<String, Sound> sounds;
    
    public SoundManager(){
        sounds = new HashMap<String, Sound>();
        TinySound.init();
    }
    
    public void dispose(){
        TinySound.shutdown();
    }
    
    public void playSound(org.plexian.grumy.world.Sound sound) {
        try {
            final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));

            clip.addLineListener(new LineListener()
            {
                @Override
                public void update(LineEvent event)
                {
                    if (event.getType() == LineEvent.Type.STOP)
                        clip.close();
                }
            });

            clip.open(AudioSystem.getAudioInputStream(Game.class.getClassLoader().getResourceAsStream(sound.getLocation())));
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
