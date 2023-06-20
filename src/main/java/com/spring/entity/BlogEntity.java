package com.spring.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "blog")
public class BlogEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_blog")
	private Long id;
	@Column(name = "slug")
	private String slug;
	@Column(name = "title")
	private String title;
	@Column(name = "content",length = 65535,columnDefinition = "Text")
	private String content;
	@Column(name = "img_url")
	private String img;
	@ManyToMany
	@JoinTable(name = "blog_like", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserEntity> userLike = new HashSet<>();
	@ManyToOne
	@JoinColumn(name = "auth_id")
	private UserEntity authId;
	
	public void addUserLike(UserEntity user) {
		this.userLike.add(user);
	}

	public void removeUserLike(UserEntity user) {
		this.userLike.remove(user);
	}
}
