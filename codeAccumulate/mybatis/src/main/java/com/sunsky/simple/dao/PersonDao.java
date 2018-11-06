package com.sunsky.simple.dao;

import java.util.List;

import com.sunsky.simple.entity.Person;

public interface PersonDao {
	public Person queryById(int id);
	public List<Person> queryAll();
	public void insertPerson(Person person);
}
