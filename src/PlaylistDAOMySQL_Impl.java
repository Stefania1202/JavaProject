import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOMySQL_Impl implements PlaylistDAO {

	public static final String CONNECTION_URL = "jdbc:mysql://localhost/song_db";

	public Song searchSong(String title, String artist) {
		try {
			Song sg = null;
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("select * from songs where title = ? AND artist = ?");
			ps.setString(1, title);
			ps.setString(2, artist);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String title2 = rs.getString("title");
				String artist2 = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				sg = new Song(id, title2, artist2, duration, type, link);

			}

			closeConnection(conn);
			return sg;

		} catch (SQLException e) {
			return null;
		}
	}

	public Song[] searchSongs(String artist) {
		try {
			Connection conn = getConnection();

			PreparedStatement ps = conn.prepareStatement("select * from songs where artist = ?");
			ps.setString(1, artist);
			ResultSet rs = ps.executeQuery();
			List<Song> songList = new ArrayList<Song>();

			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String artist2 = rs.getString("artist");
				String duration = rs.getString("duration");
				String type = rs.getString("type");
				String link = rs.getString("link");
				songList.add(new Song(id, title, artist2, duration, type, link));

			}

			closeConnection(conn);
			return songList.toArray(new Song[songList.size()]);

		} catch (SQLException e) {
			return null;
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, "root", "root");
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
}
