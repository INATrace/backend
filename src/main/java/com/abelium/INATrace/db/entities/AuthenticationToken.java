package com.abelium.INATrace.db.entities;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.ApiException;
import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.BaseEntity;
import com.abelium.INATrace.tools.EncryptionTools;
import com.abelium.INATrace.types.Status;
import com.abelium.INATrace.types.UserStatus;

@Entity
@Table(indexes = { @Index(columnList = "token" ) })
public class AuthenticationToken extends BaseEntity {
    
    @Version
    private long entityVersion;
    
    @Column(length = Lengths.AUTH_TOKEN, nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiration;
    
    @ManyToOne
    @NotNull
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private Status status = Status.ACTIVE;
    
    
    protected AuthenticationToken() {
    	super();
    }
    
    public AuthenticationToken(User user) {
		super();
		this.user = user;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Instant getExpiration() {
		return expiration;
	}

	public void setExpiration(Instant expiration) {
		this.expiration = expiration;
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

    public boolean isValid() {
        return getExpiration().isAfter(Instant.now()) && 
                getStatus() == Status.ACTIVE && 
                getUser().getStatus() == UserStatus.ACTIVE || getUser().getStatus() == UserStatus.CONFIRMED_EMAIL;
    }

    private Instant calculateExpiryDate(long expiryTimeInSeconds) {
        return Instant.now().plus(expiryTimeInSeconds, ChronoUnit.SECONDS);
    }

    public void updateToken(String token, long expirationSeconds) throws ApiException {
        setExpiration(calculateExpiryDate(expirationSeconds));
        setToken(encrypt(token));
        setStatus(Status.ACTIVE);
    }
    
    public static String encrypt(String token) throws ApiException {
        try {
            return EncryptionTools.sha3_384(token);
        } catch (NoSuchAlgorithmException e) {
            throw new ApiException(ApiStatus.ERROR, "Encryption error");
        }
    }
    
}
