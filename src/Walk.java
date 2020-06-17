import java.util.Random;

public class Walk {
	
	private final Random random = new Random();
	private final Robot robot;
	
	private final double alpha = 0.8;
	private final double gamma = 0.5;
	
	private final int stateSize = 4;
	private final int actionSize = 4;

	private int[][] actionByState = new int[stateSize][actionSize];
	private int[][] Q = new int[stateSize][actionSize];
	
	private int currentDistance;
	private int state;
	
	private boolean stop = false;
	
	public Walk(Robot robot, int initialState){
		this.robot = robot;		
		this.state = initialState;
		this.initQ();
		this.initActionByState();
	}
	
	private void initQ(){
		for(int i = 0; i < Q.length; i++){
			for(int j = 0; j < Q[i].length; j++){
				Q[i][j] = 0;
			}
		}
	}
	
	private void initActionByState(){
		for(int i = 0; i < actionByState.length; i++){
			for(int j = 0; j < actionByState[i].length; j++){
				actionByState[i][j] = -1;
			}	
		}
		
		actionByState[State.MENOS_MENOS][Action.ACTION_SHOLDER_DOWN] = State.MAIS_MENOS;
		actionByState[State.MENOS_MENOS][Action.ACTION_ELBOW_DOWN] = State.MENOS_MAIS;

		actionByState[State.MENOS_MAIS][Action.ACTION_SHOLDER_DOWN] = State.MAIS_MAIS;
		actionByState[State.MENOS_MAIS][Action.ACTION_ELBOW_UP] = State.MENOS_MENOS;
	
		actionByState[State.MAIS_MENOS][Action.ACTION_SHOLDER_UP] = State.MENOS_MENOS;
		actionByState[State.MAIS_MENOS][Action.ACTION_ELBOW_DOWN] = State.MAIS_MAIS;
	
		actionByState[State.MAIS_MAIS][Action.ACTION_SHOLDER_UP] = State.MENOS_MAIS;
		actionByState[State.MAIS_MAIS][Action.ACTION_ELBOW_UP] = State.MAIS_MENOS;
	}
	
	private int getHigherQActionByState(int state){		
		int[] currentStateActions = Q[state];
		int maxActionIdx = 0;
		int maxAction = currentStateActions[maxActionIdx];	
		
		for(int i = 0; i < actionSize; i++){
			int current = currentStateActions[i];
			if(current > maxAction){
				maxAction = current;
				maxActionIdx = i;
			}
		}
		
		return maxActionIdx;
	}
	
	private int getRandomAction(int s){
		while(true){
			int a = random.nextInt(actionSize*100)/100;
			if(actionByState[s][a] >= 0)
				return a;
		}
	}
	
	private int getReward(){
		int distance = robot.getDistance();
		int reward = currentDistance - distance;
		currentDistance = distance;
		return reward;
	}
	
	private int getNextState(int state, int action){
		return actionByState[state][action];
	}
	
	private void executeAction(int action){
		robot.doAction(action);
	}
	
	public void execute(){
		this.currentDistance = robot.getDistance();
		int s = this.state;
		while(!stop){
			//escolher ação A para o estado S
			int a = getRandomAction(s);
			
			if(actionByState[s][a] != s){
				//executar ação
				executeAction(a);
				//novo estado _s
				int _s = getNextState(s, a);
				//recompensa
				int r = getReward();
				//max Q
				int _a = getHigherQActionByState(_s);
				//Q-learning
				Q[s][a] = (int)(Q[s][a] + alpha * (r + gamma * Q[_s][_a] - Q[s][a]));
				//new state
				s = _s;
				
				printQ();
				sleep(500);
			}
			
			if(this.robot.isTouchSensorPressed()){
				this.stop = true;
			}	
		}
		
		print("Repetindo treinamento");
		sleep(5000);
		
		while(!this.robot.isTouchSensorPressed()){
			int a = getHigherQActionByState(s);
			executeAction(a);
			s = getNextState(s, a);
		}
	}
	
	void printQ(){
		System.out.flush();
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < Q[i].length; j++) {
                System.out.print(Q[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	private void print(String message){
		System.out.println(message);
	}
	
	private void sleep(long milis){
		try{
			Thread.sleep(milis);
		}catch(Exception e){
		}
	}
	
}
