package classes;

public class Ability {
	public enum Type {
	    A, B, C, D,
	    E, F, G, H
	}
	public Type name;
	public double level;
	
	public Ability(String name, double level){
		if(name.equals("A")){
			this.name = Type.A;
			this.level = level;
		}
		else if(name.equals("B")){
			this.name = Type.B;
			this.level = level;
		}
		else if(name.equals("C")){
			this.name = Type.C;
			this.level = level;
		}
		else if(name.equals("D")){
			this.name = Type.D;
			this.level = level;
		}
		else if(name.equals("E")){
			this.name = Type.E;
			this.level = level;
		}
		else if(name.equals("F")){
			this.name = Type.F;
			this.level = level;
		}
		else if(name.equals("G")){
			this.name = Type.G;
			this.level = level;
		}
		else if(name.equals("H")){
			this.name = Type.H;
			this.level = level;
		}
	}
}

//enumerated abilityler olabilir.