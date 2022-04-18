package edu.fsoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fsoft.spring.model.News;

public interface NewsRepository extends JpaRepository<News, Integer> {

}