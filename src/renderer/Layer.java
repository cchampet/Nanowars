package renderer;

public enum Layer {
	BACKGROUND(0),
	SUB_EFFECT(10),
	TOWER(20),
	BASE(30),
	UNIT(40),
	EFFECT(50),
	UI(60);
	
	
	private Integer layerIndex;
	
	Layer(int layerId){
		this.layerIndex = new Integer(layerId);
	}
	
	public Integer id(){
		return this.layerIndex;
	}
}
