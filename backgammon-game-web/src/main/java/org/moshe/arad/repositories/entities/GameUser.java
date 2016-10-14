package org.moshe.arad.repositories.entities;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name="game_users")
@EntityListeners(AuditingEntityListener.class)
public class GameUser implements UserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="first_name")
	@NotBlank
	@Pattern(regexp = "[A-Z|a-z| \\-]+")
	private String firstName;
	
	@Column(name="last_name")
	@NotBlank
	@Pattern(regexp = "[A-Z|a-z| \\-]+")
	private String lastName;
	
	@Email
	@NotBlank
	@Column
	private String email;
	
	@LastModifiedDate
	@Column(name="last_modified_date")
	@NotNull
	private Date lastModifiedDate;
	
	@LastModifiedBy
	@Column(name="last_modified_by")
	@NotNull
	private String lastModifiedBy;
	
	@CreatedDate
	@Column(name="created_date", updatable=false)
	@NotNull
	private Date createdDate;
	
	@CreatedBy
	@Column(name="created_by", updatable=false)
	@NotNull
	private String createdBy;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_in_game_room", 
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "game_room_id"))
	private Set<GameRoom> gameRooms = new HashSet<>(1000);
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private BasicUser basicUser;
	
	public GameUser() {
	}
	
	public GameUser(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "GameUser [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + "]";
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void init(){
		this.userId = null;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BasicUser getBasicUser() {
		return basicUser;
	}

	public void setBasicUser(BasicUser basicUser) {
		this.basicUser = basicUser;
	}
	
	public Set<GameRoom> getGameRooms() {
		return gameRooms;
	}

	public void setGameRooms(Set<GameRoom> gameRooms) {
		this.gameRooms = gameRooms;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] authList = this.basicUser.getAuthorities()
				.stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList())
				.toArray(new String[this.basicUser.getAuthorities().size()]);
		return AuthorityUtils.createAuthorityList(authList);
	}

	@Override
	public String getUsername() {
		return this.basicUser.getUserName();
	}	

	@Override
	public String getPassword() {
		return this.basicUser.getPassword();
	}
	
	/**
	 * TODO fill logic the these 4 method of UserDetails.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameUser other = (GameUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
