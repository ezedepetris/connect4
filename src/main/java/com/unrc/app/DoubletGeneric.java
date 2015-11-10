/* this classs whe uses for storage the state with her value*/
package com.unrc.app;
public class DoubletGeneric<T,E>{
	private T fst;
	private E snd;

	public DoubletGeneric(T first,E second){
		fst = first;
		snd = second;
	}

	public T getFst(){
		return fst ;
	}

	public E getSnd(){
		return snd;
	}

	public void setFst(T first){
		fst = first;
	}

	public void setSnd(E second){
		snd = second;
	}
}