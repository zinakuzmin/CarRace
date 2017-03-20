package zrace.client.app.world.cars.objs;

/**
 * The Enum Songs.
 */
public enum Songs {
	
	/** The Guns N roses. */
	Guns_N_Roses("Guns N Roses - Sweet.mp3", 0, 63),
	
	/** The I like to move it. */
	I_Like_To_Move_It("I Like To Move It.mp3", 1, 60),
	
	/** The Smooth criminal. */
	Smooth_Criminal("Michael Jackson - Smooth Criminal.mp3", 2, 61);
	
	/** The song name. */
	private String songName;
	
	/** The id. */
	private int id;
	
	/** The duraion in seconds. */
	private int duraionInSeconds;

	/**
	 * Instantiates a new songs.
	 *
	 * @param name the name
	 * @param id the id
	 * @param duraionInSeconds the duraion in seconds
	 */
	private Songs(String name, int id, int duraionInSeconds) {
		songName = name;
		this.id = id;
		this.duraionInSeconds = duraionInSeconds;
	}

	/**
	 * Gets the duraion in seconds.
	 *
	 * @return the duraion in seconds
	 */
	public int getDuraionInSeconds() {
		return duraionInSeconds;
	}
	
	/**
	 * Gets the song name.
	 *
	 * @return the song name
	 */
	public String getSongName() {
		return songName;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the song by uid.
	 *
	 * @param songUid the song uid
	 * @return the song by uid
	 */
	public static Songs getSongByUid(int songUid) {
		for (Songs song : Songs.values()) {
			if (song.getId()==songUid) {
				return song;
			}
		}
		
		throw new RuntimeException("Song ID not found in Songs enum:" + songUid);
	}
	
	
}
