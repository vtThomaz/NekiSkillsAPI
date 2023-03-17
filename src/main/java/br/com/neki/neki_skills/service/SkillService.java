package br.com.neki.neki_skills.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.neki.neki_skills.dto.imgbb.ImgBBDTO;
import br.com.neki.neki_skills.entity.Skills;
import br.com.neki.neki_skills.entity.User;
import br.com.neki.neki_skills.repository.SkillsRepository;
import br.com.neki.neki_skills.repository.UserRepository;

@Service
public class SkillService {
	
	@Value("${imgbb.host.url}")
	private String imgBBHostUrl;
	
	@Value("${imgbb.host.key}")
    private String imgBBHostKey;

	@Autowired
	SkillsRepository skillsRepository;
	
	public List<Skills> getAll(){
		return skillsRepository.findAll();
	}
	
	public Skills getById(Integer id) {
		return skillsRepository.findById(id).orElse(null);
	}
	
	public Skills saveSkill(
			String skill,
			MultipartFile file
			) throws IOException {
				RestTemplate restTemplate = new RestTemplate();
				String serverUrl = imgBBHostUrl + imgBBHostKey;
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				
				MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
				
				ContentDisposition contentDisposition = ContentDisposition
						.builder("form-data")
						.name("image")
						.filename(file.getOriginalFilename())
						.build();
				
				fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
				
				HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
				
				MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
				body.add("image", fileEntity);
				
				HttpEntity<MultiValueMap<String, Object>> requestEntity =
						new HttpEntity<>(body, headers);
				
				ResponseEntity<ImgBBDTO> response = null;
				ImgBBDTO imgDTO = new ImgBBDTO();
				Skills novaSkill = new Skills(); 
				try {
					response = restTemplate.exchange(
							serverUrl,
							HttpMethod.POST,
							requestEntity,
							ImgBBDTO.class);
					
					imgDTO = response.getBody();
				} catch (HttpClientErrorException e) {
					e.printStackTrace();
				}
				
				if(null != imgDTO) {
					
					Skills skillFromJson = convertSkillFromStringJson(skill);
					
					skillFromJson.setImage(imgDTO.getData().getUrl());
					novaSkill = skillsRepository.save(skillFromJson);
					
				}
				return novaSkill;
		}

		private Skills convertSkillFromStringJson(String skillJson) {
			Skills novoSkill = new Skills();
			
			try {
				ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);

				objectMapper.registerModule(new JavaTimeModule());
				novoSkill = objectMapper.readValue(skillJson, Skills.class);
			} catch (IOException err) {
				System.out.printf("Ocorreu um erro ao tentar converter a string json para um instÃ¢ncia do DTO Editora", err.toString());
			}
			
			return novoSkill;
		}
	
	public Skills updateSkill(Integer id, Skills skill) {
		Skills skillAtualizado = skillsRepository.findById(id).orElse(null);
		if(skillAtualizado != null) {
			skillAtualizado.setSkillName(skill.getSkillName());
			skillAtualizado.setSkillName(skill.getSkillName());
			return skillsRepository.save(skillAtualizado);
		}else {
			return null;
		}
	}

	public Boolean deleteSkill(Integer id) {
		Skills skill = skillsRepository.findById(id).orElse(null);
		if(skill != null) {
			skillsRepository.delete(skill);
			return true;
		}else {
			return false;
		}
	}
}
