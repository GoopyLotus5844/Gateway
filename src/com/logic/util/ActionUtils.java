package com.logic.util;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractAction;

public class ActionUtils {

	public static AbstractAction makeAbstractAction(Object object, String methodName) {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Method method = null;
				try {
					method = object.getClass().getMethod(methodName);
				} catch (NoSuchMethodException | SecurityException e2) {
					e2.printStackTrace();
				}
				try {
					method.invoke(object);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) { 
					e1.printStackTrace();
				}
			}
		};
	}
}
