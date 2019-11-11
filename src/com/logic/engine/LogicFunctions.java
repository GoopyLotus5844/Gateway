package com.logic.engine;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Defines all Functions that can be used by LComponents in their update() methods. 
 * @author toddstennes
 *
 */
public class LogicFunctions {
	
	/**
	 * The list of all functions that accept one boolean input. See setFunctions() for info.
	 */
	public static ArrayList<Function<Boolean, Boolean>> func1s = new ArrayList<Function<Boolean, Boolean>>();
	
	/**
	 * The list of all functions that accept two boolean inputs. See setFunctions() for info.
	 */
	public static ArrayList<BiFunction<Boolean, Boolean, Boolean>> func2s = new ArrayList<BiFunction<Boolean, Boolean, Boolean>>();
	
	/**
	 * The list of all functions that accept three boolean inputs. See setFunctions() for info.
	 */
	public static ArrayList<TriFunction<Boolean, Boolean, Boolean, Boolean>> func3s = new ArrayList<TriFunction<Boolean, Boolean, Boolean, Boolean>>();
	
	/**
	 * The list of all functions that accept four boolean inputs. See setFunctions() for info.
	 */
	public static ArrayList<QuadFunction<Boolean, Boolean, Boolean, Boolean, Boolean>> func4s = new ArrayList<QuadFunction<Boolean, Boolean, Boolean, Boolean, Boolean>>();
	
	
	/**
	 * Initializes all logic functions. Single input functions are loaded in the order NOT, BUFFER, and multi-input functions are loaded in 
	 * the order AND, NAND, OR, NOR, XOR, XNOR. This is the order that the functions appear in their respective arrays.
	 */
	public static void setFunctions() {
		func1s.add(a -> !a);
		func1s.add(a -> a);
		func2s.add((a, b) -> a & b);
		func2s.add((a, b) -> !(a & b));
		func2s.add((a, b) -> a | b);
		func2s.add((a, b) -> !(a | b));
		func2s.add((a, b) -> a ^ b);
		func2s.add((a, b) -> !(a ^ b));
		func3s.add((a, b, c) -> a & b & c);
		func3s.add((a, b, c) -> !(a & b & c));
		func3s.add((a, b, c) -> a | b | c);
		func3s.add((a, b, c) -> !(a | b | c));
		func3s.add((a, b, c) -> a ^ b ^ c);
		func3s.add((a, b, c) -> !(a ^ b ^ c));
		func4s.add((a, b, c, d) -> a & b & c & d);
		func4s.add((a, b, c, d) -> !(a & b & c & d));
		func4s.add((a, b, c, d) -> a | b | c | d);
		func4s.add((a, b, c, d) -> !(a | b | c | d));
		func4s.add((a, b, c, d) -> a ^ b ^ c ^ d);
		func4s.add((a, b, c, d) -> !(a ^ b ^ c ^ d));
	}
	
	/**
	 * A functional interface that accepts three inputs and provides one output
	 * @param <A> Input A
	 * @param <B> Input B
	 * @param <C> Input C
	 * @param <R> Output
	 */
	@FunctionalInterface
	public interface TriFunction<A, B, C, R> {
		public R apply(A a, B b, C c);
	}
	
	/**
	 * A functional interface that accepts four inputs and provides one output
	 * @param <A> Input A
	 * @param <B> Input B
	 * @param <C> Input C
	 * @param <D> Input D
	 * @param <R> Output
	 */
	@FunctionalInterface
	public interface QuadFunction<A, B, C, D, R> {
		public R apply(A a, B b, C c, D d);
	}
	
}
