package com.spring.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "user_status")
	private boolean userStatus;
	@Column(name = "birth_date")
	private Date birthDate;
	@Column(name = "img_user")
	private String img;
	@Column(name = "gender")
	private String gender;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> listRoles = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "aticles_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "aticles_id"))
	private Set<AticlesEntity> likedAticles = new HashSet<AticlesEntity>();
	@ManyToMany
	@JoinTable(name = "blog_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "blog_id"))
	private Set<BlogEntity> likedBlog = new HashSet<BlogEntity>();
	@OneToMany(mappedBy = "authId")
	private Set<BlogEntity> listBlog ;
	public void removeLikedAticles (AticlesEntity aticlesEntity) {
		this.likedAticles.remove(aticlesEntity);
	}
	
}
