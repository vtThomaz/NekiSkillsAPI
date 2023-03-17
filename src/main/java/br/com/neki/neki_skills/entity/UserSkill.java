package br.com.neki.neki_skills.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
	    generator = ObjectIdGenerators.PropertyGenerator.class,
	    property = "id"
)

@Entity
@Table(name="user_skill")
public class UserSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "knowledge_level")
    private Integer Level;

    @Column(name = "created_at")
    private Instant created;
    
    @Column(name = "updated_at")
	private Instant updated;
	
    @ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @ManyToOne
	@JoinColumn(name = "skill_id", referencedColumnName = "id")
    private Skills skill;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevel() {
		return Level;
	}

	public void setLevel(Integer level) {
		Level = level;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getUpdated() {
		return updated;
	}

	public void setUpdated(Instant updated) {
		this.updated = updated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Skills getSkills() {
		return skill;
	}

	public void setSkills(Skills skills) {
		this.skill = skills;
	}
    
    
}
