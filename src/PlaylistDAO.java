
public interface PlaylistDAO{
	
	public Song searchSong(String title, String artist);
	public Song[] searchSongs(String artist);
	
}