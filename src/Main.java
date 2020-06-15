import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Main {

	public static void main(String[] args) throws InterruptedException {
		Robot robot = new Robot(Motor.B, Motor.A, new UltrasonicSensor(SensorPort.S1));
		Walk walk = new Walk(robot, State.MAIS_MENOS);
		
		System.out.println("Inicializando...");
		Thread.sleep(2000);
			
		walk.execute();
		
		
		
//		for (int i = 0; i < 3; i++) {
//			robot.doAction(Action.ACTION_ELBOW_POSITIVE);
//			robot.doAction(Action.ACTION_SHOLDER_POSITIVE);
//			robot.doAction(Action.ACTION_ELBOW_NEGATIVE);
//			robot.doAction(Action.ACTION_SHOLDER_NEGATIVE);
//		}
		
		System.out.println("FIM");
	}
	
}
