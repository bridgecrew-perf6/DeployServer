package edu.fsoft.spring.interfaceService;

import java.util.List;

import edu.fsoft.spring.model.Feedback;

public interface IFeedbackService {
	public List<Feedback> listAll();
	public void save(Feedback feedback);
	public void deleteFeedback(Feedback feedback);
}
