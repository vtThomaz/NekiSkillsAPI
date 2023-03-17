package br.com.neki.neki_skills.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.neki.neki_skills.entity.User;




public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User> findByUserLogin(String userLogin);
}
