/******************************************************************************************
 * Copyright (c) 2014 Xiaoshi Zhong
 * All rights reserved. This program and the accompanying materials are made available
 * under the terms of the GNU lesser Public License v3 which accompanies this distribution,
 * and is available at http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors : Xiaoshi Zhong
 * ****************************************************************************************/

package ntu.scse.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicProcess {
	/**
	 * Valid digits
	 * */
	public static double validDigit(double realNumber, int digitCount){
		if(Double.isInfinite(realNumber) || Double.isNaN(realNumber) || realNumber == 0.0)
			return 0.0;
		return (new BigDecimal(realNumber, new MathContext(digitCount))).doubleValue();
	}
	public static double validDigit(double realNumber){
		return validDigit(realNumber, Setting.VALID_DIGITLEN);
	}
	
	/**
	 * Valid decimals
	 * */
	public static double validDecimal(double realNumber, int decimalCount){
		if(Double.isInfinite(realNumber) || Double.isNaN(realNumber) || realNumber == 0.0)
			return 0.0;
		return (new BigDecimal(realNumber)).setScale(decimalCount, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static double validDecimal(double realNumber){
		return validDecimal(realNumber, Setting.VALID_DECIMALLEN);
	}
	
	/**
	 * Sort HashMap
	 * */
	public static <K extends Comparable<? super K>, V> List<Map.Entry<K, V>> sortKeys(Map<K, V> map, boolean isAscendingOrder){
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>(){
			public int compare(Map.Entry<K, V> one, Map.Entry<K, V> two) {
				if(isAscendingOrder)
					return one.getKey().compareTo(two.getKey());
				else
					return two.getKey().compareTo(one.getKey());
			}
		});
		return list;
	}
	public static <K extends Comparable<? super K>, V> List<Map.Entry<K, V>> sortKeys(Map<K, V> map){
		return sortKeys(map, true);
	}
	
	public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortValues(Map<K, V> map, boolean isAscendingOrder){
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>(){
			public int compare(Map.Entry<K, V> one, Map.Entry<K, V> two) {
				if(isAscendingOrder)
					return one.getValue().compareTo(two.getValue());
				else
					return two.getValue().compareTo(one.getValue());
			}
		});
		return list;
	}
	public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortValues(Map<K, V> map){
		return sortValues(map, true);
	}
	
	/*************************************************************************************************************
	 * List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
	 *     Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	 *     public int compare(Map.Entry<String, Integer> one, Map.Entry<String, Integer> two) {
	 *         return two.getValue().compareTo(one.getValue());
	 *     }
	 * });
	 * ***********************************************************************************************************/
	
	/**
	 * Sort Set
	 * */
	public static <K extends Comparable<? super K>> List<K> sortSet(Set<K> set, boolean isAscendingOrder){
		List<K> list = new ArrayList<K>(set);
		Collections.sort(list);
		return list;
	}
	
	public static <K extends Comparable<? super K>> List<K> sortSet(Set<K> set){
		return sortSet(set, true);
	}
	
	public static boolean isAllCapital(String token) {
		for(int i = 0; i < token.length(); i++) {
			if(! Character.isUpperCase(token.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static String getFirstCapitlaToken(String token) {
		return token.substring(0, 1).toUpperCase() + token.substring(1).toLowerCase();
	}
	
	public static boolean isFirstCapital(String token) {
		if(Character.isUpperCase(token.charAt(0)))
			return true;
		return false;
	}
	
}
