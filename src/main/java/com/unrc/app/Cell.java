package com.unrc.app;
import org.javalite.activejdbc.Model;

//@BelongsTo(parent = Grid.class, foreignKeyName = "grid_id")
public class Cell extends Model{
	private Integer value;

	public Cell(){
		value = null;
	}

	public Cell(Integer player){
		value = player;
	}

	public Integer getCell(){
		return value;
	}
}