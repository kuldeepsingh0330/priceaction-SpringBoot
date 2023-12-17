package com.ransankul.priceaction.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
public class User implements UserDetails{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id")
    private String emailId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserApiMapping> apiMappings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns=@JoinColumn(name="user",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id")
    )
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "watchlist",
            joinColumns=@JoinColumn(name="user",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="upstoxInstrumentKey",referencedColumnName = "upstoxInstrumentKey")
    )
    private List<InstrumentKey> watchlist = new ArrayList<>();


    public User() {
    }

    public User(long userId, String phoneNumber, String name, String password, String emailId,
            List<UserApiMapping> apiMappings) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.emailId = emailId;
        this.apiMappings = apiMappings;
    }

    public User(long userId, String phoneNumber, String name, String password, String emailId, List<UserApiMapping> apiMappings, Set<Roles> roles, List<InstrumentKey> watchlist) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.emailId = emailId;
        this.apiMappings = apiMappings;
        this.roles = roles;
        this.watchlist = watchlist;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<UserApiMapping> getApiMappings() {
        return apiMappings;
    }

    public void setApiMappings(List<UserApiMapping> apiMappings) {
        this.apiMappings = apiMappings;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getRole()))
		.collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.getEmailId();
    }

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

    public List<InstrumentKey> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<InstrumentKey> watchlist) {
        this.watchlist = watchlist;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", apiMappings=" + apiMappings +
                ", roles=" + roles +
                ", watchlist=" + watchlist +
                '}';
    }
}