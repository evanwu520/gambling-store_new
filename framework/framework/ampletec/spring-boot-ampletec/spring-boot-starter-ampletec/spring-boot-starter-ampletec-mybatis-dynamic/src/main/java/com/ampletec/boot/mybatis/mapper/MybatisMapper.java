package com.ampletec.boot.mybatis.mapper;

@Deprecated
public interface MybatisMapper<T> {

	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public T getById(int id);

	/**
	 * 添加对象
	 * 
	 * @param t
	 */
	public int add(T t);

	/**
	 * 修改对象
	 * 
	 * @param t
	 */
	public int update(T t);

	/**
	 * 删除对象
	 * 
	 * @param id
	 */
	public int delete(int id);
}
