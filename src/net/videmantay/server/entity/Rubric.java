package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rubric implements Serializable {

	public Long Id;
	public String title;
	public String description;
	public String subject;
	public List<Criterion> criteria = new ArrayList<Criterion>();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}

	public static class Criterion implements Serializable {
		public String value;
		public String definition;

		public String getValue() {
			return value;
		}

		public String getDefinition() {
			return definition;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public void setDefinition(String definition) {
			this.definition = definition;
		}
	}

}
