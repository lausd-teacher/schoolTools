package net.videmantay.server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.ImmutableList;

import net.videmantay.server.entity.StandardReview;
import net.videmantay.server.entity.StudentWork;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StudentWorkStatus;

public class TestingFields {

	public static void main(String[] args) {

		StudentWork sw = new StudentWork();
		sw.setDateTaken("2015-22-12");
		sw.setId(new Date().getTime());
		sw.setLetterGrade("4");
		sw.setPointsEarned(45.0);
		sw.setType(GradedWorkType.HOMEWORK);
		sw.setStudentWorkStatus(StudentWorkStatus.TURNED_IN);
		sw.setRosterStudentId(new Date().getTime());
		StandardReview r1 = new StandardReview();
		
		for(Field f: sw.getClass().getDeclaredFields()){
			try {
				f.setAccessible(true);
				System.out.println("Field Name: " + f.getName() +" , Value: " + f.get(sw));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
