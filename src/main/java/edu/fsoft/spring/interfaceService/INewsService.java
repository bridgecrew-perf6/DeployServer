package edu.fsoft.spring.interfaceService;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fsoft.spring.model.News;

public interface INewsService{
	public List<News> listAll();
	public void save(News news);
	public void delete(int id);
	public News get(int id);
}