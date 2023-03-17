package br.com.neki.neki_skills.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neki.neki_skills.entity.Skills;
import br.com.neki.neki_skills.entity.UserSkill;
import br.com.neki.neki_skills.repository.SkillsRepository;
import br.com.neki.neki_skills.repository.UserSkillRepository;

@Service
public class UserSkillService {
	@Autowired
	UserSkillRepository userSkillRepository;
	
	public List<UserSkill> getAll(){
		return userSkillRepository.findAll();
	}
	
	public UserSkill getById(Integer id) {
		return userSkillRepository.findById(id).orElse(null);
	}
	
	public UserSkill saveUserSkill(UserSkill userSkill) {
		return userSkillRepository.save(userSkill);
	}
	
	public UserSkill updateUserSkill(Integer id, UserSkill userSkill) {
		UserSkill skillAtualizado = userSkillRepository.findById(id).orElse(null);
		if(skillAtualizado != null) {
			skillAtualizado.setLevel(userSkill.getLevel());
			skillAtualizado.setUser(userSkill.getUser());
			skillAtualizado.setSkills(userSkill.getSkills());
			skillAtualizado.setUpdated(userSkill.getUpdated());
			return userSkillRepository.save(skillAtualizado);
		}else {
			return null;
		}
	}

	public Boolean deleteUserSkill(Integer id) {
		UserSkill userSkill = userSkillRepository.findById(id).orElse(null);
		if(userSkill != null) {
			userSkillRepository.delete(userSkill);
			return true;
		}else {
			return false;
		}
	}
}
