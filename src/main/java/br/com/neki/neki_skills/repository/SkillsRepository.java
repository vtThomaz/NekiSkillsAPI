package br.com.neki.neki_skills.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.neki.neki_skills.entity.Skills;

public interface SkillsRepository extends JpaRepository <Skills, Integer> {

}
