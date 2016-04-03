package aihw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class main {
	public static void main(String [] args){
		
		//ArrayList<Boolean> successList = new ArrayList<Boolean>();
		
		int successNumber = 0;
		int failureNumber = 0;
		for(int y = 0; y<1000; y++){
			System.out.println(y);
			
		
			
			PuzzleState initialState = new PuzzleState(1, 2, 3, 4, 5, 6, 7, 8, -1, 0);
			PuzzleState shuffledState;
			
			shuffledState = initialState.shuffle(20);
			

			
			
			
			PuzzleState iterState;
			ArrayList<PuzzleState> nextStates = new ArrayList<PuzzleState>();
			
			for(int w=1; w<5; w++){
			
				//int w = 4;
				ArrayList<ArrayList<PuzzleState>> agenda = new ArrayList<ArrayList<PuzzleState>>();
				ArrayList<PuzzleState> extendedList = new ArrayList<PuzzleState>();
				ArrayList<PuzzleState> initial = new ArrayList<PuzzleState>();
				initial.add(shuffledState);
				agenda.add(initial);
				int iterCount = 0;
				int currentDepth = 0;
				boolean inExtList = false;
				


				boolean failureModeOn = false;
					
				
				do{
					int loopCount = agenda.size();
					for(int i = 0;i<loopCount; i++){
						//System.out.println("Extended list:"+ extendedList.size());
						for(int j = 0; j < extendedList.size(); j++){
							
							for(int o = 0; o < agenda.size(); o++){
								if(agenda.get(o).get(agenda.get(o).size() - 1).equals(extendedList.get(j))){
									agenda.remove(o);
									o--;
								}
							}
						}
						if(agenda.isEmpty()){
							failureNumber++;
							failureModeOn = true;
							break;
						}
						if(!inExtList)
						{
							//System.out.println("ifin içi");
							ArrayList<PuzzleState> currentPath = agenda.get(0);
							//System.out.println("Su an baktýgý: \n"+currentPath.get(currentPath.size()-1).toString());
							if(currentPath.get(currentPath.size()-1).equals(initialState))
								break;
							agenda.remove(0);
							
							ArrayList<PuzzleState> possibleNextStates = currentPath.get(currentPath.size()-1).getPossibleNextStates();
							int possibleSizeNextStatesSize = possibleNextStates.size();
							for(int k = 0; k<possibleSizeNextStatesSize;k++){

								ArrayList<PuzzleState> newPath = new ArrayList<PuzzleState>(currentPath.size());

								for(int p = 0; p<currentPath.size(); p++){
									newPath.add(currentPath.get(p));
								}
						
								newPath.add(possibleNextStates.get(0));
								
								//System.out.println(possibleNextStates.get(0).toString());
								
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
								//System.out.println("Extended liste eklenen: \n"+currentPath.get(currentPath.size()-1)+"/n eklenen bitis" );
							}
						}else{
							inExtList = false;
							//System.out.println("false yaptýk");
						}
					}
					
					Collections.sort(agenda, new Comparator<ArrayList<PuzzleState>>(){
						@Override
						public int compare(ArrayList<PuzzleState> first, ArrayList<PuzzleState> second){
							return Integer.compare(second.get(second.size()-1).heuristicValue, first.get(first.size()-1).heuristicValue);
						}
					}); 
					
					for(int i = 0; i < agenda.size(); i ++)

					while(agenda.size()>w){
						agenda.remove(w);
						
					}
					//for(int i = 0; i < agenda.size(); i ++)
						//System.out.println( i + ") Second Heuristics: " + agenda.get(i).get(agenda.get(i).size() - 1).getStaticValue() + "\n\n");
					//System.out.println(agenda.size());
						
				} while(!failureModeOn&&!agenda.get(0).get(agenda.get(0).size()-1).equals(initialState));
					if(!agenda.isEmpty())
						successNumber++;
			}
			//for(int i = 0; i<agenda.get(0).size(); i++)
				//System.out.println(agenda.get(0).get(i).toString()+"*****\n");
			//System.out.println("Path size: "+agenda.get(0).size());
			System.out.println("Successes: " + successNumber);
			System.out.println("Failures: " + failureNumber);
		}
	}
}
