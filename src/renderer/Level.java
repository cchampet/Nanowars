package renderer;

public enum Level {
	LVL_1("./tex/datamap/datamap_1.png", 1),
	LVL_2("./tex/datamap/datamap_2.png", 2),
	LVL_3("./tex/datamap/datamap_3.png", 3);
	
	
	private String path;
	private int id;
	public static final int idOfLastLevel = 3;
	
	Level(String path, int id){
		this.path = path;
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public static Level getLevel(int id){
		for(Level l:Level.values()){
			if(l.id == id)
				return l;
		}
		return null;
	}
}
