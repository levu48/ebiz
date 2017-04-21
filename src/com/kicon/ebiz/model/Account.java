package com.kicon.ebiz.model;

import com.kicon.ebiz.crud.HasId;
//import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ACCOUNT")
//public class Account implements Principal, Serializable, HasId<String> {
public class Account implements Serializable, HasId<String> {

    @Id
    //@GeneratedValue(generator = "system-uuid")
    // @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "displayName")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "emailVerified")
    private boolean emailVerified=false;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ElementCollection(fetch=FetchType.EAGER)
    @JoinTable(name = "ACCOUNT_IDENTIFIERS", joinColumns = { @JoinColumn(name = "id_oid")})
    @Column(name="stringElem")
    private List<String> identifiers = new ArrayList<String>();
    
    @ElementCollection(fetch=FetchType.EAGER)
    @JoinTable(name = "ACCOUNT_ROLES", joinColumns = {@JoinColumn(name = "id_oid")})
    @Column(name="stringElem")
    private List<String> roles = new ArrayList<String>();

    @Column(name = "locked")
    private boolean locked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public List<String> getRoles() {
        return roles;
    }
}
