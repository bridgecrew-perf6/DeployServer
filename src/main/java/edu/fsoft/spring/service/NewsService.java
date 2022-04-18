package edu.fsoft.spring.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.formobj.ProductFormObj;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.News;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.repository.NewsRepository;

@Service
public class NewsService  {

	@Autowired
	private NewsRepository repo;
	
	public List<News> listAll() {		
		return repo.findAll();
	}
	public void save(News news) {
		News news2 = null;
		LocalDate ld = LocalDate.now();
		if (news.getId() > 0) {
			news2 = repo.findById(news.getId()).get();
			news2.setContent(news.getContent());
			news2.setCreatedBy(news.getCreatedBy());
			news2.setCreatedDate(ld);
			news2.setImage(news.getImage());
			news2.setShortContent(news.getShortContent());
			news2.setTitle(news.getTitle());
		}else {
			news2 = new News();
			news2.setContent(news.getContent());
			news2.setCreatedBy(news.getCreatedBy());
			news2.setCreatedDate(ld);
			news2.setImage(news.getImage());
			news2.setShortContent(news.getShortContent());
			news2.setTitle(news.getTitle());
		}
		repo.save(news2);
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
	public News get(int id) {
		return repo.findById(id).get();
	}
	
	
}