package aihw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class main {
	public static void main(String [] args){
		
		ArrayList<ArrayList<PuzzleState>> agenda = new ArrayList<ArrayList<PuzzleState>>();
		ArrayList<PuzzleState> extendedList = new ArrayList<PuzzleState>();
		PuzzleState initialState = new PuzzleState(1, 2, 3, 4, 5, 6, 7, 8, -1, 0);
		PuzzleState shuffledState;
		PuzzleState iterState;
		ArrayList<PuzzleState> nextStates = new ArrayList<PuzzleState>();
		int w = 4;
		int iterCount = 0;
		int currentDepth = 0;
		boolean inExtList = false;
		
		shuffledState = initialState.shuffle(1000);
		//shuffledState = initialState;
		
		//System.out.println(initialState.toString());
		System.out.println(shuffledState.toString());
		
		ArrayList<PuzzleState> initial = new ArrayList<PuzzleState>();
		
		initial.add(shuffledState);
		agenda.add(initial);
		
		do{
			//System.out.println("here");
			//try {
				//Thread.sleep(1);
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			int loopCount = agenda.size();
			for(int i = 0;i<loopCount; i++){
				System.out.println("Extended list:"+ extendedList.size());
				for(int j = 0; j < extendedList.size(); j++){
					//System.out.println("Extended list no: "+j+"\n"+extendedList.get(j).toString());
					
					for(int o = 0; o < agenda.size(); o++){
						if(agenda.get(o).get(agenda.get(o).size() - 1).equals(extendedList.get(j))){
							//inExtList = true;
							agenda.remove(o);
							o--;
						}
					}
				}
				//System.out.println("Extended list end********");
				if(!inExtList)
				{
					System.out.println("ifin içi");
					ArrayList<PuzzleState> currentPath = agenda.get(0);
					System.out.println("Su an baktýgý: \n"+currentPath.get(currentPath.size()-1).toString());
					if(currentPath.get(currentPath.size()-1).equals(initialState))
						break;
					agenda.remove(0);
					
					ArrayList<PuzzleState> possibleNextStates = currentPath.get(currentPath.size()-1).getPossibleNextStates();
					int possibleSizeNextStatesSize = possibleNextStates.size();
					for(int k = 0; k<possibleSizeNextStatesSize;k++){
						//System.out.println(possibleNextStates.size());
						ArrayList<PuzzleState> newPath = new ArrayList<PuzzleState>(currentPath.size());
						//Collections.copy(newPath, currentPath);
						for(int p = 0; p<currentPath.size(); p++){
							newPath.add(currentPath.get(p));
						}
				
						newPath.add(possibleNextStates.get(0));
						
						System.out.println(possibleNextStates.get(0).toString());
						
						possibleNextStates.remove(0);
						boolean addTrue = true;
						for(int o = 0; o < extendedList.size(); o++){
							if(newPath.get(newPath.size() - 1).equals(extendedList.get(o))){
								addTrue=false;
							}
						}
						if(addTrue)
							agenda.add(newPath);
					}
					if(!currentPath.get(currentPath.size()-1).equals(initialState)){
						extendedList.add(currentPath.get(currentPath.size()-1));
						System.out.println("Extended liste eklenen: \n"+currentPath.get(currentPath.size()-1)+"/n eklenen bitis" );
					}
				}else{
					inExtList = false;
					System.out.println("false yaptýk");
				}
			}
			
			Collections.sort(agenda, new Comparator<ArrayList<PuzzleState>>(){
				@Override
				public int compare(ArrayList<PuzzleState> first, ArrayList<PuzzleState> second){
					return Integer.compare(second.get(second.size()-1).heuristicValue, first.get(first.size()-1).heuristicValue);
				}
			}); 
			
			for(int i = 0; i < agenda.size(); i ++)
				//System.out.println( i + ") First Heuristics: " + agenda.get(i).get(agenda.get(i).size() - 1 ).getStaticValue() + "\n\n");
			while(agenda.size()>w){
				agenda.remove(w);
				
				//System.out.println("here2");
			}
			for(int i = 0; i < agenda.size(); i ++)
				System.out.println( i + ") Second Heuristics: " + agenda.get(i).get(agenda.get(i).size() - 1).getStaticValue() + "\n\n");
			System.out.println(agenda.size());
			
		} while(!agenda.get(0).get(agenda.get(0).size()-1).equals(initialState));
		//for(int i = 0; i<agenda.get(0).size(); i++)
			//System.out.println(agenda.get(0).get(i).toString()+"*****\n");
		System.out.println("Path size: "+agenda.get(0).size());
	}
}
