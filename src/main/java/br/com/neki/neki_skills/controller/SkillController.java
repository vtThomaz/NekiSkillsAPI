package br.com.neki.neki_skills.controller;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.neki.neki_skills.entity.Skills;
import br.com.neki.neki_skills.service.SkillService;


@RestController
@RequestMapping("/skills")
public class SkillController {
	@Autowired
	SkillService skillService;
	
	@GetMapping
	public ResponseEntity<List<Skills>> getAll(){
		List<Skills> skills = skillService.getAll();
		if(!skills.isEmpty())
			return new ResponseEntity<>(skills, HttpStatus.OK);
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Skills> getById(@PathVariable Integer id) {
		Skills skill = skillService.getById(id);
		if(skill != null)
			return new ResponseEntity<>(skill, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);		
	}
	
	 @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,
		        MediaType.MULTIPART_FORM_DATA_VALUE })
		    public ResponseEntity<Skills> saveSkill(@RequestPart ("filename") String skill,
		    @RequestPart ("source") MultipartFile file) throws IOException{
		        Skills novoSkill = skillService.saveSkill(skill, file);
		        if(null == novoSkill)
		            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		        else
		            return new ResponseEntity<>(novoSkill, HttpStatus.CREATED);
		    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Skills> updateUser(@PathVariable Integer id, @RequestBody Skills skills) {
		Skills skillAtualizada = skillService.updateSkill(id, skills);
		if(skillAtualizada != null)
			return new ResponseEntity<>(skillAtualizada, HttpStatus.OK); 
		else 
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteSkill(@PathVariable Integer id) {
		if(skillService.deleteSkill(id))
			return new ResponseEntity<>(true, HttpStatus.OK);
		else 
			return new ResponseEntity<>(false, HttpStatus.OK);
	}
}