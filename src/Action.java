
public class Action {

	static final int ACTION_SHOLDER_UP = 0; //negative
	static final int ACTION_SHOLDER_DOWN = 1; //positive
	static final int ACTION_ELBOW_UP = 2; //negative
	static final int ACTION_ELBOW_DOWN = 3; //positive
	
	private static final Action[] actions = new Action[] {
		new Action(Members.SHOULDER, -RobotSpecs.ANGLE_ROTATION),
		new Action(Members.SHOULDER, RobotSpecs.ANGLE_ROTATION),
		new Action(Members.ELBOW, -RobotSpecs.ANGLE_ROTATION),
		new Action(Members.ELBOW, RobotSpecs.ANGLE_ROTATION)
	};
	
	public String member;
	public int angle;
	
	private Action(String member, int angle) {
		this.member = member;
		this.angle = angle;
	}

	public static Action[] getActions() {
		return actions; 
	}

	public String toString() {
		return "Action [member=" + member + ", angle=" + angle + "]";
	}
	
}
