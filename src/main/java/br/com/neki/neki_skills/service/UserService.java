package br.com.neki.neki_skills.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neki.neki_skills.entity.User;
import br.com.neki.neki_skills.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public User getById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByLogin(String userLogin){
        return userRepository.findByUserLogin(userLogin).get();
    }
	
	public User saveUser(User user) {
		System.out.println(user.getId());
		return userRepository.save(user);
	}
	
	public User updateUser(Integer id, User user) {
		User userAtualizado = userRepository.findById(id).orElse(null);
		if(userAtualizado != null) {
			userAtualizado.setUserLogin(user.getUserLogin());
			return userRepository.save(userAtualizado);
		}else {
			return null;
		}
	}

	public Boolean deleteUser(Integer id) {
		User user = userRepository.findById(id).orElse(null);
		if(user != null) {
			userRepository.delete(user);
			return true;
		}else {
			return false;
		}
	}
}