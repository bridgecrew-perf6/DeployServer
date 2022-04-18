package edu.fsoft.spring.service;

import java.util.List;

import edu.fsoft.spring.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.interfaceService.IFeedbackService;
import edu.fsoft.spring.model.Feedback;
import edu.fsoft.spring.repository.FeedbackRepository;

@Service
public class FeedbackService implements IFeedbackService {
	@Autowired
	FeedbackRepository repo;
	public void save(Feedback feedback){
		repo.save(feedback);	
	}
	public List<Feedback> listAll() {
		return repo.findAll();
	}
	public void deleteFeedback(Feedback feedback){
		repo.delete(feedback);
	}
	public Feedback get(int id) {
		return repo.findById(id).get();
	}
}
