package com.spring.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "id_category")
	private Long id;
	@Column(name = "title_category")
	private String title;
	@Column(name = "url_img")
	private String img;
	@Column(name = "slug")
	private String slug;
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private Set<AticlesEntity> listAticles = new HashSet<>();
	
	public void addAticle(AticlesEntity aticlesEntity) {
		listAticles.add(aticlesEntity);
	}
	public void removeAticle(AticlesEntity aticlesEntity) {
		listAticles.remove(aticlesEntity);
	}
}
