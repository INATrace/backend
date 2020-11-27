package com.abelium.INATrace.db.entities;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Pair;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.tools.EncryptionTools;
import com.abelium.INATrace.types.ConfirmationTokenType;
import com.abelium.INATrace.types.Status;
import com.abelium.INATrace.types.UserStatus;

@Entity
@Table(indexes = { @Index(columnList = "token" ) })
public class ConfirmationToken extends BaseEntity {
    
    @Version
    private long entityVersion;

    @Enumerated(EnumType.STRING)
    @Column(length = Lengths.ENUM)
    private ConfirmationTokenType type;
    
    @Column(length = Lengths.AUTH_TOKEN, nullable = false, unique = true)
    private String token;

    @ManyToOne
    @NotNull
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private Status status = Status.ACTIVE;
    
    
	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
	public ConfirmationTokenType getType() {
		return type;
	}

	public void setType(ConfirmationTokenType type) {
		this.type = type;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isValidEmailConfirmationToken() {
        return getStatus() == Status.ACTIVE && getType() == ConfirmationTokenType.CONFIRM_EMAIL && getUser().getStatus() == UserStatus.UNCONFIRMED;
    }
    
    public boolean isValidPasswordResetToken() {
        return getStatus() == Status.ACTIVE && getType() == type && 
        		(getUser().getStatus() == UserStatus.ACTIVE || getUser().getStatus() == UserStatus.CONFIRMED_EMAIL);
    }
    

    // Return token and unencrypted token
    public static Pair<ConfirmationToken, String> create(User user, ConfirmationTokenType type) {
    	ConfirmationToken ct = new ConfirmationToken();
        String token = UUID.randomUUID().toString();
    	ct.setUser(user);
    	ct.setType(type);
		ct.setToken(encrypt(token));
    	return Pair.of(ct, token);
    }

	public static String encrypt(String token) {
    	try {
			return EncryptionTools.sha3_384(token);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Encryption error", e);
		}
	}
    
}
