package net.videmantay.util;

import com.google.gwt.view.client.ProvidesKey;

public class EnumKeyProvider<E extends Enum<E>> implements ProvidesKey<E> {
	 
	 @Override
	 public Object getKey(E item) {
	  if (item == null)
	   return null;
	  return item.name();
	 }
	 
	}
