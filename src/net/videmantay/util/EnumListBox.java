package net.videmantay.util;

import java.util.Arrays;

import com.google.gwt.user.client.ui.ValueListBox;

public class EnumListBox<E extends Enum<E>> extends ValueListBox<E> {
	public EnumListBox() {
		  super(new EnumRenderer<E>(), new EnumKeyProvider<E>());
		 }
		 
		 public EnumListBox(Class<E> clazz) {
		  this();
		  setAcceptableValues(Arrays.asList(clazz.getEnumConstants()));
		  this.setValue(clazz.getEnumConstants()[0]);
		 }

}
