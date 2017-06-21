package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Set;

public class QuizResult implements Serializable {
	public Long id;
	public Long studentId;
	public Long quizId;
	public Set<QuizSection> analysis;
	public int correctAnswers;
	public int incorrectAnswers;
	public int attempt;
	public String date;

}
