package net.videmantay.server.user;

import static com.googlecode.objectify.ObjectifyService.*;

import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Work;


public class DB<T> {
	
	
	
	static {
		
		//Users
		factory().register(AppUser.class);
		
		//Roster and Deps
		factory().register(Roster.class);
		factory().register(RosterDetail.class);
		factory().register(RosterStudent.class);
		factory().register(RosterSetting.class);
		/*factory().register(GradedWork.class);
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
		factory().register(EducationalLink.class);*/
		
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
  
  
  public  Key<T> save(final T clazz){
	
		return ofy().transactNew(new Work<Key<T>>(){

			@Override
			public Key<T> run() {
				// TODO Auto-generated method stub
				return db().save().entity(clazz).now();
			}});
	}
	
	public  void delete(T entity){
		DB.db().delete().entity(entity).now();
	}
	
	public  List<T> list(){
		return DB.db().transactNew(new Work<List<T>>(){

			@Override
			public List<T> run() {
				return DB.db().load().type(clazz).list();
			}});
		
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
		


