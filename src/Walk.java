import java.util.Random;

public class Walk {
	
	private final Random random = new Random();
	private final Robot robot;
	
	private final double alpha = 0.8;
	private final double gamma = 0.5;
	
	private double epsilon = 1.0;
	
	private final int reward = 100;
	private final int penality = -1000;
	
	private final int epochs = 15;
	
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
		int maxAction = Q[state][0];
		int maxActionIdx = 0;
		int[] actions = actionByState[state];	
		
		for(int i = 0; i < currentStateActions.length; i++){
			int current = currentStateActions[i];
			if(current > maxAction && actions[i] >= 0){
				maxAction = current;
				maxActionIdx = i;
			}
		}
		
		return actions[maxActionIdx];

	}
	
	private int getRandomAction(int[] actions){
		int i = (int)Math.round(random.nextFloat()*10.0);
		if(i < actions.length){
			if(actions[i] >= 0){
				return i;
			}
		}
		
		return getRandomAction(actions);
	}
	
	private int getAnyAction(int state){
		if(random.nextFloat() < epsilon){
			return this.getRandomAction(actionByState[state]);
		}
		
		return this.getHigherQActionByState(state);
	}
	
	private int getReward(){
		int distance = robot.getDistance();
		
		if(distance >= currentDistance){
			return penality;
		}else{
			return reward;
		}
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
		int count = 0;
		while(!stop){
			print("epoch: "+ count);
			for(int i = 0; i < epochs; i++){
				//escolher ação A para o estado S
				int a = getAnyAction(s);
				//executar ação
				executeAction(a);
				//novo estado _s
				int _s = getNextState(s, a);
				//recompensa
				int r = getReward();
				//max Q
				int maxQ = getHigherQActionByState(_s);
				//Q-learning
				Q[s][a] = (int)(Q[s][a] + alpha * (r + gamma * maxQ - Q[s][a]));
				
				//new state
				s = _s;
			}
			
			this.epsilon -= 0.1;
			
			if(this.robot.isTouchSensorPressed()){
				this.stop = true;
			}
				
		}
	}
	
	void printQ(){
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
	
}
