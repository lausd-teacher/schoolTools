package net.videmantay.shared;

import java.io.Serializable;

public enum GradeLevel implements Serializable{
KINDER("K"), FIRST("1"), SECOND("2"), THRID("3"), FOURTH("4"), FIFTH("5");

private String level;
GradeLevel(String level){
	this.level = level;
}

@Override
public String toString(){
	return level;
}
}
