
public class State {
	
	static final int MENOS_MENOS = 0;
	static final int MENOS_MAIS = 1;
	static final int MAIS_MENOS = 2;
	static final int MAIS_MAIS = 3;
	
	private static final State[] states = new State[] {
		new State(-RobotSpecs.ANGLE_STATE, -RobotSpecs.ANGLE_STATE), //-45-45
		new State(-RobotSpecs.ANGLE_STATE, RobotSpecs.ANGLE_STATE), //-45+45
		new State(RobotSpecs.ANGLE_STATE, -RobotSpecs.ANGLE_STATE),//+45-45
		new State(RobotSpecs.ANGLE_STATE, RobotSpecs.ANGLE_STATE),//+45+45
	};
	
	public static State[] getStates() {
		return states;
	}
	
	public static int getStateId(State state) {
		return getStateId(state.getShoulderDegree(), state.getElbowDegree());
	}
	
	public static int getStateId(int shoulderDegree, int elbowDegree) {
		for (int i = 0; i < states.length; i++) {
			State state = states[i];
			if (state.shoulderDegree == shoulderDegree && state.elbowDegree == elbowDegree) {
				return i;
			}
		}
		throw new RuntimeException("Estado inválido: (" + shoulderDegree + "," + elbowDegree + ")");
	}
	
	private int shoulderDegree;
	private int elbowDegree;
	
	private State(int shoulderDegree, int elbowDegree) {
		this.shoulderDegree = shoulderDegree;
		this.elbowDegree = elbowDegree;
	}
	
	public int getShoulderDegree() {
		return shoulderDegree;
	}
	
	public int getElbowDegree() {
		return elbowDegree;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + elbowDegree;
		result = prime * result + shoulderDegree;
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (elbowDegree != other.elbowDegree)
			return false;
		if (shoulderDegree != other.shoulderDegree)
			return false;
		return true;
	}

	public String toString() {
		return "State [shoulderDegree=" + shoulderDegree + ", elbowDegree="
				+ elbowDegree + "]";
	}
	
}
