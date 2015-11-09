/*this class modela the behaviour of the opponent and help to choose the best choise */
package com.unrc.app;
import static java.lang.Math.*;
import java.util.*;

public class MinMaxEngine  {
	private Grid problem;
	private int maxDepth;
  
  public MinMaxEngine(){
  	maxDepth = 1;
  }

	public MinMaxEngine(Grid p){
  	problem = p;
  	maxDepth = 1;
  }

  public MinMaxEngine(Grid p,int depth){
  	problem = p;
  	maxDepth = depth;
  }

	public int minMaxAB (Grid s,int alfa, int beta, int maxLevel){ 
		if(problem.end(s)|| maxLevel==0)
			return problem.value(s);
		else{
			List<Grid> successors = problem.getSuccessors(s);
			while (!successors.isEmpty() && alfa<beta){ 
				if(s.isMax())
					alfa = max(alfa, minMaxAB(successors.get(0), alfa, beta,maxLevel-1));
				else
					beta = min(beta, minMaxAB(successors.get(0), alfa, beta,maxLevel-1));
				successors.remove(0);
			}
			if (s.isMax())
				return alfa;
			
			return beta;
		}
	}

/* this method uses min max for give a state value*/
	public int computeValue(Grid state){
		return minMaxAB(state,-1000,1000,maxDepth);
	}	

/* this method select the best state between the all successors state*/
	public Grid computeSuccessor(Grid state){
		List<Grid> successors = problem.getSuccessors(state);
		LinkedList<Doublet<Grid,Integer>> successorsValues = new LinkedList<Doublet<Grid,Integer>>();
		while (!successors.isEmpty()){
			successorsValues.add(new Doublet<Grid,Integer>(successors.get(0),computeValue(successors.get(0))));
			successors.remove(0);
		}
		int max = 0;
		int i =0;
		while (i<successorsValues.size()){
			if (successorsValues.get(max).getSnd() < successorsValues.get(i).getSnd()){
				max = i;
			}
			i++;
		}
		return successorsValues.get(max).getFst();
	}


	public void setProblem(Grid p) {
        problem = p;
    }

  public AdversarySearchEngine(Grid p, int depth) {
    problem = p;
    maxDepth = depth;
  }

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public P getProblem() {
		return problem;
	}


}
