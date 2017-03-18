package zrace.client.app.world.cars.objs;

public enum Songs {
	Guns_N_Roses("Guns N Roses - Sweet.mp3", 0, 63),
	I_Like_To_Move_It("I Like To Move It.mp3", 1, 60),
	Smooth_Criminal("Michael Jackson - Smooth Criminal.mp3", 2, 61);
	
	private String songName;
	private int id;
	private int duraionInSeconds;

	private Songs(String name, int id, int duraionInSeconds) {
		songName = name;
		this.id = id;
		this.duraionInSeconds = duraionInSeconds;
	}

	public int getDuraionInSeconds() {
		return duraionInSeconds;
	}
	
	public String getSongName() {
		return songName;
	}

	public int getId() {
		return id;
	}

	public static Songs getSongByUid(int songUid) {
		for (Songs song : Songs.values()) {
			if (song.getId()==songUid) {
				return song;
			}
		}
		
		throw new RuntimeException("Song ID not found in Songs enum:" + songUid);
	}
	
	
}
