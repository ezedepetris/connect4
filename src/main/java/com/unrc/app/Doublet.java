package com.unrc.app;
import org.javalite.activejdbc.Model;

/*this class model a column with her result after insert a token */
public class Doublet{
	private Integer first  ;
	private Integer second  ;

	public Doublet(Integer aFirst, Integer aSecond){
		first = aFirst;
		second = aSecond;
	}

	public Integer getFirst(){
		return first;
	}
	
	public Integer getSecond(){
		return second;
	}

	public void setFirst(Integer aFirst){
		first = first;
	}
	
	public void setSecond(Integer aSecond){
		second = aSecond;
	}
}