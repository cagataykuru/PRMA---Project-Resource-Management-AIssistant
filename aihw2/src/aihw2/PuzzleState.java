package aihw2;

import java.util.ArrayList;
import java.util.Collections;

public class PuzzleState {
	public ArrayList<ArrayList<Integer>> stateInfo;//İkinci dimension yatay olarak düşünüldü
	public ArrayList<PuzzleState> children;
	public int depth;
	public int heuristicValue;
	public PuzzleState(int first, int second, int third, int fourth, int fifth, int sixth, int seventh, int eighth, int ninth, int depthParam){
		stateInfo = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> firstRow = new ArrayList<Integer>();
		firstRow.add(first);
		firstRow.add(second);
		firstRow.add(third);
		ArrayList<Integer> secondRow = new ArrayList<Integer>();
		secondRow.add(fourth);
		secondRow.add(fifth);
		secondRow.add(sixth);
		
		ArrayList<Integer> thirdRow = new ArrayList<Integer>();
		thirdRow.add(seventh);
		thirdRow.add(eighth);
		thirdRow.add(ninth);
		
		stateInfo.add(firstRow);
		stateInfo.add(secondRow);
		stateInfo.add(thirdRow);
		
		depth = depthParam;
		children = new ArrayList<PuzzleState>();
		heuristicValue = this.getStaticValue();
	}
	
	public ArrayList<PuzzleState> getPossibleNextStates(){
		ArrayList<PuzzleState> nextStates = new ArrayList<PuzzleState>();
		if(stateInfo.get(0).get(0)==-1){//If left upper
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(1), stateInfo.get(0).get(0), stateInfo.get(0).get(2), 
												   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
												   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(0).get(1)==-1){//If upper middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(1), stateInfo.get(0).get(0), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(2), stateInfo.get(0).get(1), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(0).get(2)==-1){//If right upper
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(2), stateInfo.get(0).get(1), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(1).get(0)==-1){//If left middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(1), stateInfo.get(1).get(0), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(1).get(1)==-1){//If center
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(1), stateInfo.get(1).get(0), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(2), stateInfo.get(1).get(1), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState4 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
			nextStates.add(newState4);
		}else if(stateInfo.get(1).get(2)==-1){//If right middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(2), stateInfo.get(1).get(1), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(2).get(0)==-1){//If left lower
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(1), stateInfo.get(2).get(0), stateInfo.get(2).get(2), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
		}else if(stateInfo.get(2).get(1)==-1){//If lower middle
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					   stateInfo.get(2).get(1), stateInfo.get(2).get(0), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), depth + 1);
			PuzzleState newState3 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(2), stateInfo.get(2).get(1), depth + 1);
			nextStates.add(newState1);
			nextStates.add(newState2);
			nextStates.add(newState3);
		}else if(stateInfo.get(2).get(2)==-1){//If lower right
			PuzzleState newState1 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					   stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(2).get(2), 
					   stateInfo.get(2).get(0), stateInfo.get(2).get(1), stateInfo.get(1).get(2), depth + 1);
			PuzzleState newState2 = new PuzzleState(stateInfo.get(0).get(0), stateInfo.get(0).get(1), stateInfo.get(0).get(2), 
					stateInfo.get(1).get(0), stateInfo.get(1).get(1), stateInfo.get(1).get(2), 
					stateInfo.get(2).get(0), stateInfo.get(2).get(2), stateInfo.get(2).get(1), depth + 1);
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
			ArrayList<PuzzleState> possibleNextStates = currentState.getPossibleNextStates();
			Collections.shuffle(possibleNextStates);
			return shufflerRecursive(possibleNextStates.get(0), iterationLeft-1);
		}else{
			return currentState;
		}
	}
	public int getStaticValue(){

		int staticValue;
		int numMisplacedTiles = 0;

		if (stateInfo.get(0).get(0) != 1)
		numMisplacedTiles ++;
		if (stateInfo.get(0).get(1) != 2)
		numMisplacedTiles ++;
		if (stateInfo.get(0).get(2) != 3)
		numMisplacedTiles ++;
		if (stateInfo.get(1).get(0) != 4)
		numMisplacedTiles ++;
		if (stateInfo.get(1).get(1) != 5)
		numMisplacedTiles ++;
		if (stateInfo.get(1).get(2) != 6)
		numMisplacedTiles ++;
		if (stateInfo.get(2).get(0) != 7)
		numMisplacedTiles ++;
		if (stateInfo.get(2).get(1) != 8)
		numMisplacedTiles ++;
		if (stateInfo.get(2).get(2) != -1)
		numMisplacedTiles ++;

		staticValue = 9 - numMisplacedTiles; 
		//staticValue = numMisplacedTiles;
		return staticValue;
	}
	public String toString(){
		String str;
		str = stateInfo.get(0).get(0) + "\t" + stateInfo.get(0).get(1) + "\t" + stateInfo.get(0).get(2) + "\n"
			+ stateInfo.get(1).get(0) + "\t" + stateInfo.get(1).get(1) + "\t" + stateInfo.get(1).get(2) + "\n"
			+ stateInfo.get(2).get(0) + "\t" + stateInfo.get(2).get(1) + "\t" + stateInfo.get(2).get(2) + "\n";
		return str;
	}
	public boolean equals(PuzzleState p){
		if(stateInfo.get(0).get(0) == p.stateInfo.get(0).get(0) &&
		   stateInfo.get(0).get(1) == p.stateInfo.get(0).get(1) &&
		   stateInfo.get(0).get(2) == p.stateInfo.get(0).get(2) &&
		   stateInfo.get(1).get(0) == p.stateInfo.get(1).get(0) &&
		   stateInfo.get(1).get(1) == p.stateInfo.get(1).get(1) &&
		   stateInfo.get(1).get(2) == p.stateInfo.get(1).get(2) &&
		   stateInfo.get(2).get(0) == p.stateInfo.get(2).get(0) &&
		   stateInfo.get(2).get(1) == p.stateInfo.get(2).get(1) &&
		   stateInfo.get(2).get(2) == p.stateInfo.get(2).get(2))
			return true;
		
		return false;
	}
}
