package br.com.neki.neki_skills.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.neki.neki_skills.entity.UserSkill;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

}
