package com.atguigu.java8;
@FunctionalInterface
public interface MyPridicate1<T,R> {
	public abstract R getR(T ...ts ); 
}
