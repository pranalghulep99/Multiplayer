//package com.jspider.project.Multiplayer;
package com.jspider.project.jdbc.Multiplayer;

public class Song {

	private int Id;
	private String SongName;
	private String SingerName;
	private String MovieName;
	private String Composer;
	private String Lyricist;
	private double Length;

	public Song(int id, String songName, String singerName, String movieName, String composer, String lyricist,
			double length) {
		super();
		Id = id;
		SongName = songName;
		SingerName = singerName;
		MovieName = movieName;
		Composer = composer;
		Lyricist = lyricist;
		Length = length;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getSongName() {
		return SongName;
	}

	public void setSongName(String songName) {
		SongName = songName;
	}

	public String getSingerName() {
		return SingerName;
	}

	public void setSingerName(String singerName) {
		SingerName = singerName;
	}

	public String getMovieName() {
		return MovieName;
	}

	public void setMovieName(String movieName) {
		MovieName = movieName;
	}

	public String getComposer() {
		return Composer;
	}

	public void setComposer(String composer) {
		Composer = composer;
	}

	public String getLyricist() {
		return Lyricist;
	}

	public void setLyricist(String lyricist) {
		Lyricist = lyricist;
	}

	public double getLength() {
		return Length;
	}

	public void setLength(double length) {
		Length = length;
	}

	@Override
	public String toString() {
		return (Id + ", " + SongName + ", " + SingerName + ", " + MovieName + ", " + Composer + ", " + Lyricist + ", "
				+ Length);
	}

}
