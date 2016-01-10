package net.videmantay.server.user;

import static com.googlecode.objectify.ObjectifyService.*;

import java.util.Collection;
import java.util.List;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import net.videmantay.server.entity.EducationalLink;
import net.videmantay.server.entity.GradedWork;
import net.videmantay.server.entity.Lesson;
import net.videmantay.server.entity.SeatingChart;
import net.videmantay.server.entity.SeatingChartDetail;
import net.videmantay.server.entity.Showcase;
import net.videmantay.server.entity.Standard;
import net.videmantay.server.entity.StandardReview;
import net.videmantay.server.entity.StudentIncident;
import net.videmantay.server.entity.StudentJob;
import net.videmantay.server.entity.StudentWork;
import net.videmantay.server.entity.Vocab;
import net.videmantay.server.entity.VocabList;


public class DB<T> {
	
	
	
	static {
		
		//Users
		factory().register(AppUser.class);
		
		//Roster and Deps
		factory().register(Roster.class);
		factory().register(RosterStudent.class);
		factory().register(GradedWork.class);
		factory().register(StudentWork.class);
		factory().register(StudentIncident.class);
		factory().register(StudentJob.class);
		factory().register(SeatingChart.class);
		factory().register(SeatingChartDetail.class);
		factory().register(Showcase.class);
		
		//Lesson and Deps
		factory().register(Lesson.class);
		factory().register(Vocab.class);
		factory().register(VocabList.class);
		factory().register(Standard.class);
		factory().register(StandardReview.class);
		factory().register(EducationalLink.class);
		
	}
	

	
	private DB(){
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void start(){
		new DB();
	}
	
	public static Objectify db(){
		return ofy();
	}
	//Revist using Generics at a later time for now just use objectify	
	private Class<T> clazz;
 
  
  public DB(Class<T> clazz){
		this.clazz = clazz;
	}
  
  
  public  Key<T> save(T clazz){
		
		return DB.db().save().entity(clazz).now();
	}
	
	public  void delete(T entity){
		DB.db().delete().entity(entity).now();
	}
	
	public  List<T> list(){
		return DB.db().load().type(clazz).list();
	}
	
	public  List<T> query(String condition, Object value){
		
	return	DB.db().load().type(clazz).filter(condition, value).list();
	}
	
	public  T getById(Long id){
		
	 return	DB.db().load().type(clazz).id(id).now();
	}
	
	public void deleteById(Long id){
		DB.db().delete().type(clazz).id(id);
		
	}
	
	public Collection<T> listByKeys(List<Key<T>> keys){
	
		return DB.db().load().keys(keys).values();
	}
	
	public List<T> search(String field, String query){
		return    DB.db().load().type(clazz).filter(field +" >=", query).filter(field + " <=", query + "\ufffd").list();
		}
	
}
		


