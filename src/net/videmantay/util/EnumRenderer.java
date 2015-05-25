package net.videmantay.util;

import java.io.IOException;

import com.google.gwt.text.shared.Renderer;

public class EnumRenderer<E extends Enum<E>> implements Renderer<E> {
	 
	 @Override
	 public String render(E object) {
	  if (object == null)
	   return "";
	  return object.toString();
	 }
	 
	 @Override
	 public void render(E object, Appendable appendable) throws IOException {
	  appendable.append(render(object));
	 }
	 
	}
