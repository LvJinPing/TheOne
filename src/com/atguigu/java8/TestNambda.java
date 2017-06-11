package com.atguigu.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.Test;
//左边：表达式的参数列表
//右边：nambda 表达式的功能实现
public class TestNambda {
	
	List<Employees> list = Arrays.asList(
			new Employees("张三", 18 , 9999),
			new Employees("李四", 68 , 6699),
			new Employees("王舞", 48 , 9955),
			new Employees("赵六", 88 , 1199),
			new Employees("田七", 78 , 4499)
			);
	/**
	 * ①声明一个带两个泛型的函数式接口，泛型类型为<T, R>  T 为参数，R 为返回值
	 *②接口中声明对应抽象方法
	 *③在 TestLambda 类中声明方法，使用接口作为参数，计算两个 long 型参数的和。
	 *④再计算两个 long 型参数的乘积。
	 */
	@Test
	public void test6(){
		long l = testCount(111, 222 , (x,y) ->x+y );
		System.out.println(l);
	}
	public Long testCount( long l1 , long l2 , BiFunction<Long , Long , Long> fun){
		return fun.apply(l1, l2);
	}
/*	@Test
	public void test5(){
		MyPridicate1<Long,Long> mp1 = (x,y) ->x+y;  //可变参数怎么写？
	}*/
	/**
	 *①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
	 *②声明类 TestLambda ，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
	 *③再将一个字符串的第2个和第4个索引位置进行截取子串。
	 */
	@Test
	public void test4(){
		MyPridicate mp1 = (str) -> str.toUpperCase();
		String str = mp1.getValue("asdfhalkd");
		MyPridicate mp2 = (str1) -> str1.substring(1, 4);
		str = mp2.getValue(str);
	}
	//排序
	@Test
	public void test3(){
		Comparator<Employees> com = (x , y) -> {
			if(!x.getAge().equals(y.getAge())){
				return x.getAge().compareTo(y.getAge());
			}
			return x.getName().compareTo(y.getName());
		};
		Collections.sort(list, com);
		for (Employees employees : list) {
			System.out.println(employees);
		}
	}
	@Test
	public void test1(){
		Comparator<Integer> com = (x , y) -> Integer.compare(x , y);
		System.out.println(com.compare(10, 15));
	}
	@Test
	public void test2(){
		Runnable r = () -> {
			System.out.println("表达式 nambda");
		};
		r.run();
	}
}
