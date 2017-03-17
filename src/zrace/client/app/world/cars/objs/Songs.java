package zrace.client.app.world.cars.objs;

public enum Songs {
	Guns_N_Roses("Guns N Roses - Sweet.mp3", 0),
	I_Like_To_Move_It("I Like To Move It.mp3", 1),
	Smooth_Criminal("Michael Jackson - Smooth Criminal.mp3", 2);
	
	private String songName;
	private int id;

	private Songs(String name, int id) {
		songName = name;
		this.id = id;
	}

	public String getSongName() {
		return songName;
	}

	public int getId() {
		return id;
	}

	public static Songs getSongByUid(int songUid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
