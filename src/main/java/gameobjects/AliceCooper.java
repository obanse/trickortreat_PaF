package main.java.gameobjects;

import main.java.sounds.Sound;

public class AliceCooper extends Entity {


    public AliceCooper() {
        // this.sprite = new Image(AliceCooper.class.getResourceAsStream("alice_cooper.png"));
    }


    private boolean isPlaying;

    public void playSong(Player player) {

        if(!isPlaying){
            isPlaying = true;

            Sound.playCooper();
        }
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }




}
