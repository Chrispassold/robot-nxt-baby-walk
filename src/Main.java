import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public class Main {

	public static void print(String message){
		System.out.println(message);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Robot robot = new Robot(Motor.B, Motor.A, new UltrasonicSensor(SensorPort.S1));
		robot.setTouchSensor(new TouchSensor(SensorPort.S4));
		
		Walk walk = new Walk(robot, State.MENOS_MENOS);
		
		print("Inicializando...");		
		print("Calibrar");
		while(!robot.isTouchSensorPressed()){
			print("Distancia: "+ robot.getDistance());
		}
		
		System.out.flush();
		
		print("Começando...");
		Thread.sleep(3000);
		try{
			walk.execute();
		}catch(ArrayIndexOutOfBoundsException e){
			print("DEU ERRO");
		}
		
		print("Pressione o botao para sair");
		Thread.sleep(3000);
		walk.printQ();
		robot.pause();
		
//		for (int i = 0; i < 1; i++) {
//			robot.doAction(Action.ACTION_SHOLDER_UP);
//			robot.doAction(Action.ACTION_ELBOW_DOWN);
//			robot.doAction(Action.ACTION_SHOLDER_DOWN);
//			robot.doAction(Action.ACTION_ELBOW_UP);
//		}
		
		
		
	}
	
}
