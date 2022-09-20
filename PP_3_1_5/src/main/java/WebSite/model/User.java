package WebSite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Target;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id")
   private Long id;

   @Column(name = "username")
   private String firstName;
   @Column(name = "password")
   private String password;

   @Column(name = "name")
   private String lastName;


   @Column(name = "email", unique = true, nullable = false)
   private String username;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
   )

   @ToString.Exclude
   private Set<Role> roles = new HashSet<>();

   public User() {
   }
   public User(String username, String password) {
      this.username = username;
      this.password = password;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   @Override
   public String getUsername() {
      return username;
   }
   @Override
   @JsonIgnore
   public boolean isAccountNonExpired() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isAccountNonLocked() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isCredentialsNonExpired() {
      return true;
   }
   @Override
   @JsonIgnore
   public boolean isEnabled() {
      return true;
   }

   public void setRole(Role role) {
      roles.add(role);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      User user = (User) o;
      return id != null && Objects.equals(id, user.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + username + '\'' +
              ", roleList=" + roles +
              '}';
   }
}
