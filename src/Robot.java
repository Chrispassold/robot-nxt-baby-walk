import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;

public class Robot {

	private final NXTRegulatedMotor shoulderMotor;
	private final NXTRegulatedMotor elbowMotor;
	private final UltrasonicSensor distanceSensor;

	
	public Robot(NXTRegulatedMotor shoulderMotor, NXTRegulatedMotor elbowMotor, UltrasonicSensor distanceSensor) {
		this.shoulderMotor = shoulderMotor;
		this.elbowMotor = elbowMotor;
		
		this.shoulderMotor.stop();
		this.elbowMotor.stop();
		
		this.shoulderMotor.setSpeed(RobotSpecs.MOTOR_SPEED);
		this.elbowMotor.setSpeed(RobotSpecs.MOTOR_SPEED);
		
		this.distanceSensor = distanceSensor;
	}
	
	public void doAction(int actionId) {
		Action action = Action.getActions()[actionId];
		this.rotate(action.member, action.angle);
	}
	
	private void rotate(String member, int angle) {
		if (Members.ELBOW.equals(member)) {
			this.elbowMotor.rotate(angle);
		} else if (Members.SHOULDER.equals(member)) {
			this.shoulderMotor.rotate(angle);
		}
	}
	
	public int getDistance() {
		return this.distanceSensor.getDistance();
	}
	
}
