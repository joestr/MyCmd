package xyz.joestr.mycmd.util;
/*
 * The MIT License
 * Copyright (c) 2014-2015 Techcable
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

public class Reflection {
	
	/**
	 * Get version
	 * @return Version
	 */
	public static String getVersion() {
		
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		return packageName.substring(packageName.lastIndexOf('.') + 1);
	}
	
	/**
	 * Get a class
	 * @param name Name
	 * @return Class
	 */
	public static Class<?> getClass(String name) {

		try { return Class.forName(name); } catch(ClassNotFoundException exception) { return null; }
	}
	
	/**
	 * Get a class which extends of a superclass
	 * @param className Name
	 * @param superClass Superclass
	 * @return Class which extends superclass
	 */
	public static <T> Class<? extends T> getClass(String className, Class<T> superClass) {

		try { return Class.forName(className).asSubclass(superClass); } catch (ClassCastException | ClassNotFoundException exception) { return null; }
	}
	
	/**
	 * Get net.minecraft.server.(version).(name) class
	 * @param className Class name
	 * @return net.minecraft.server.(version).(name) class
	 */
	public static Class<?> getNMSClass(String className) {
		
		return getClass("net.minecraft.server." + getVersion() + "." + className);
	}
	
	/**
	 * Get org.bukkit.craftbukkit.(version).(name) class
	 * @param className Class name
	 * @return org.bukkit.craftbukkit.(version).(name) class
	 */
	public static Class<?> getCBClass(String className) {
		
		return getClass("org.bukkit.craftbukkit." + getVersion() + "." + className);
	}
	
	/**
	 * Get a player's connection
	 * @param player Player
	 * @return A player's connection
	 * @throws SecurityException Security
	 * @throws NoSuchMethodException Method not found
	 * @throws NoSuchFieldException Field not found
	 * @throws IllegalArgumentException Illegal argument
	 * @throws IllegalAccessException Illegal access
	 * @throws InvocationTargetException Invocation target
	 */
	public static Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
	    Method method = player.getClass().getMethod("getHandle");
	    Object objectplayer = method.invoke(player);
	    Field field = objectplayer.getClass().getField("playerConnection");
	    Object object = field.get(objectplayer);
	    return object;
	}
	
	/**
	 * Get net.minecraft.util.(name) class
	 * @param name Class name
	 * @return net.minecraft.util.(name) class
	 */
	public static Class<?> getUtilClass(String name) {
		
		try {
			
			return Class.forName(name); //Try before 1.8 first
		} catch(ClassNotFoundException ex1) {
			
			try {
				return Class.forName("net.minecraft.util." + name); //Not 1.8
			} catch (ClassNotFoundException ex2) { return null; }
		}
	}
	
	/**
	 * Get handle
	 * @param wrapper Objekt
	 * @return Objekt
	 */
	public static Object getHandle(Object wrapper) {
		
		Method getHandle = makeMethod(wrapper.getClass(), "getHandle");
		return callMethod(getHandle, wrapper);
	}
	
	//Utils
	/**
	 * Make a method
	 * @param clazz Class
	 * @param methodName Name of the method
	 * @param paramaters Parameters of the Method
	 * @return Method
	 */
	public static Method makeMethod(Class<?> clazz, String methodName, Class<?>... paramaters) {
		
		try {
			
			return clazz.getDeclaredMethod(methodName, paramaters);
		} catch (NoSuchMethodException ex) {
			
			return null;
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Call a method
	 * @param method Method
	 * @param instance Instance to call with
	 * @param paramaters Parameters for the method
	 * @return Type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T callMethod(Method method, Object instance, Object... paramaters) {
		
		if (method == null) throw new RuntimeException("No such method");
		method.setAccessible(true);
		
		try {

			return (T) method.invoke(instance, paramaters);
		} catch (InvocationTargetException ex) {

			throw new RuntimeException(ex.getCause());
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Make a constructor
	 * @param clazz Class
	 * @param paramaterTypes Types of the parameters
	 * @return Constructor
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> makeConstructor(Class<?> clazz, Class<?>... paramaterTypes) {

		try {

			return (Constructor<T>) clazz.getConstructor(paramaterTypes);
		} catch (NoSuchMethodException ex) {

			return null;
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Call a Constrcutor
	 * @param constructor Constructor
	 * @param paramaters Parameters
	 * @return Type
	 */
	public static <T> T callConstructor(Constructor<T> constructor, Object... paramaters) {

		if (constructor == null) throw new RuntimeException("No such constructor");
		constructor.setAccessible(true);

		try {

			return (T) constructor.newInstance(paramaters);
		} catch (InvocationTargetException ex) {

			throw new RuntimeException(ex.getCause());
		} catch(Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Make a field
	 * @param clazz Class
	 * @param name Name
	 * @return Field
	 */
	public static Field makeField(Class<?> clazz, String name) {

		try {

			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException ex) {

			return null;
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Get a Field
	 * @param field Field
	 * @param instance Instance
	 * @return Type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Field field, Object instance) {

		if (field == null) throw new RuntimeException("No such field");
		field.setAccessible(true);

		try {

			return (T) field.get(instance);
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	/**
	 * Set a Field
	 * @param field Field
	 * @param instance Instance
	 * @param value Value
	 */
	public static void setField(Field field, Object instance, Object value) {

		if (field == null) throw new RuntimeException("No such field");
		field.setAccessible(true);

		try {

			field.set(instance, value);
		} catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	// Fuzzy reflection
	/**
	 * Get only field
	 * @param toGetFrom Get from
	 * @param type Type
	 * @return Field
	 */
	@SuppressWarnings("null")
	public static Field getOnlyField(Class<?> toGetFrom, Class<?> type) {

		Field only = null;

		for (Field field : toGetFrom.getDeclaredFields()) {

			if (!type.isAssignableFrom(field.getClass())) continue;

			Preconditions.checkArgument(only == null, "More than one field of type %s on %s: %s and %s", type.getSimpleName(), toGetFrom.getSimpleName(), field.getName(), only.getName());

			only = field;
		}
		
		return only;
	}
	
	/**
	 * Get only Method
	 * @param toGetFrom Get from
	 * @param returnType Return type
	 * @param paramSpec Parameter specification
	 * @return Method
	 */
	@SuppressWarnings("null")
	public static Method getOnlyMethod(Class<?> toGetFrom, Class<?> returnType, Class<?>... paramSpec) {

		Method only = null;

		for (Method method : toGetFrom.getDeclaredMethods()) {

			if (!returnType.isAssignableFrom(method.getReturnType())) continue;

			if (!isParamsMatchSpec(method.getParameterTypes(), paramSpec)) continue;

			Preconditions.checkArgument(only == null, "More than one method matching spec on %s" + ((only.getName().equals(method.getName())) ? "" : ": " + only.getName() + " " + method.getName()), toGetFrom.getSimpleName());

			only = method;
		}

		return only;
	}
	
	/**
	 * Check is parameters match the specification
	 * @param parameters Parameters
	 * @param paramSpec Parameter specification
	 * @return
	 */
	public static boolean isParamsMatchSpec(Class<?>[] parameters, Class<?>... paramSpec) {

		if (parameters.length > paramSpec.length) return false;

		for (int i = 0; i < paramSpec.length; i++) {

			Class<?> spec = paramSpec[i];

			if (spec == null) continue;

			Class<?> parameter = parameters[i];
			
			if (!spec.isAssignableFrom(parameter)) return false;
		}

		return true;
	}
}