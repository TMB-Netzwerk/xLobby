package de.xenodev.utils;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.xenodev.xLobby;
import org.bukkit.entity.Player;

import java.io.File;

public class MusicBuilder {

    public static Song song1 = NBSDecoder.parse(new File("plugins//" + xLobby.getInstance().getName() + "//Music", "christmas.nbs"));
    public static Song song2 = NBSDecoder.parse(new File("plugins//" + xLobby.getInstance().getName() + "//Music", "halloween.nbs"));
    private RadioSongPlayer rsp;
    private Player player;

    public MusicBuilder(Player player){
        this.player = player;
    }

    public MusicBuilder getSongPlayer(Song song){
        rsp = new RadioSongPlayer(song);
        return this;
    }

    public MusicBuilder addPlayer(){
        rsp.addPlayer(player);
        return this;
    }

    public MusicBuilder removePlayer(){
        rsp.removePlayer(player);
        return this;
    }

    public MusicBuilder setPlaying(Boolean bool){
        rsp.setPlaying(bool);
        return this;
    }

    public MusicBuilder setLoop(){
        rsp.setRepeatMode(RepeatMode.ONE);
        return this;
    }

}
