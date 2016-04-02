package aihw2;

import java.util.ArrayList;
import java.util.Collections;

public class PuzzleState {
	public ArrayList<ArrayList<Integer>> stateInfo;//İkinci dimension yatay olarak düşünüldü
	
	public PuzzleState(int first, int second, int third, int fourth, int fifth, int sixth, int seventh, int eighth, int ninth){
		stateInfo = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> firstRow = new ArrayList<Integer>();
		firstRow.add(0, first);
		firstRow.add(1, second);
		firstRow.add(2, third);
		ArrayList<Integer> secondRow = new ArrayList<Integer>();
		secondRow.add(3, fourth);
		secondRow.add(4, fifth);
		secondRow.add(5, sixth);
		
		ArrayList<Integer> thirdRow = new ArrayList<Integer>();
		thirdRow.add(6, seventh);
		thirdRow.add(7, eighth);
		thirdRow.add(8, ninth);
		
		stateInfo.add(0, firstRow);
		stateInfo.add(1, secondRow);
		stateInfo.add(2, thirdRow);
	}
	
	public ArrayList<PuzzleState> getPossibleNextStates(){
		ArrayList<PuzzleState> nextStates = new ArrayList<PuzzleState>();
		if(stateInfo.get(0).get(0)==-1){//If left upper
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(1), stateInfo.get(0).get(0), stateInfo.get(0).get(2), 
												   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
												   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(0).get(1)==-1){//If upper middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(1), stateInfo.get(0).get(0), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(2), stateInfo.get(0).get(1), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(0).get(2)==-1){//If right upper
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(2), stateInfo.get(0).get(1), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(1).get(0)==-1){//If left middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(1), stateInfo.get(1).get(0), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(1).get(1)==-1){//If center
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(1), stateInfo.get(1).get(0), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(2), stateInfo.get(1).get(1), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState4 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
			nextStates.add(newState4);
		}else if(stateInfo.get(1).get(2)==-1){//If right middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(2), stateInfo.get(1).get(1), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(2).get(0)==-1){//If left lower
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(1), stateInfo.get(2).get(0), stateInfo.get(2).get(2));
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(2).get(1)==-1){//If lower middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(1), stateInfo.get(2).get(0), stateInfo.get(2).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2));
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(2), stateInfo.get(2).get(1));
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(2).get(2)==-1){//If lower right
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2));
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(2), stateInfo.get(2).get(1));
			nextStates.add(newState1);
			nextStates.add(newState2);
		}
		return nextStates;
	}
	public PuzzleState shuffle(int iterationNumber){
		return shufflerRecursive(this, iterationNumber);
	}
	
	private PuzzleState shufflerRecursive(PuzzleState currentState, int iterationLeft){
		if(iterationLeft>=0){
			ArrayList<PuzzleState> possibleNextStates = new ArrayList<PuzzleState>();
			Collections.shuffle(possibleNextStates);
			shufflerRecursive(possibleNextStates.get(0), iterationLeft-1);
		}else{
			return currentState;
		}
		return null;
	}
	public int getStaticValue(PuzzleState p){

		int staticValue;
		int numMisplacedTiles = 0;

		if (p.stateInfo.get(0).get(0) != 1)
		numMisplacedTiles ++;
		if (p.stateInfo.get(0).get(1) != 2)
		numMisplacedTiles ++;
		if (p.stateInfo.get(0).get(2) != 3)
		numMisplacedTiles ++;
		if (p.stateInfo.get(1).get(0) != 4)
		numMisplacedTiles ++;
		if (p.stateInfo.get(1).get(1) != 5)
		numMisplacedTiles ++;
		if (p.stateInfo.get(1).get(2) != 6)
		numMisplacedTiles ++;
		if (p.stateInfo.get(2).get(0) != 7)
		numMisplacedTiles ++;
		if (p.stateInfo.get(2).get(1) != 8)
		numMisplacedTiles ++;
		if (p.stateInfo.get(2).get(2) != -1)
		numMisplacedTiles ++;

		staticValue = 9 - numMisplacedTiles; 

		return staticValue;
	}
}
