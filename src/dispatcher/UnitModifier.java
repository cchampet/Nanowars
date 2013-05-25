package dispatcher;

public enum UnitModifier {
	NONE("NONE"),
	SPEED("SPEED"),
	PROLIFERATION("PROLIFERATION"),
	DIVISION("DIVISION"),
	RESISTANT("RESISTANT");
	
	private String name;
	
	UnitModifier(String name){
		this.name= name;
	}
	
	public String getName(){
		return this.name;
	}
}
