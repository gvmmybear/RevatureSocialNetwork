package zook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Authentication")
public class Authentication {
	
	@Id
	@Column(name="token_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY	)
	private int id;
	
	// this will be the actual token value used for password recovery.
	@Column(name="token_value", unique=true, nullable=false)
	private String token;
	
	@Column(name="userEmail", nullable=false)
	private String userEmail;
//	@ManyToOne(fetch=FetchType.LAZY)	
	
//	@Column(name="status", nullable=false)
//	private boolean isValid;
	
	public Authentication() {}

	public Authentication(String token, String userEmail) {
		this.token = token;
		this.userEmail = userEmail;
//		this.isValid = true;
	}
	
//	public Authentication(int id, String token, String userEmail, boolean isValid) {
//		this.id = id;
//		this.token = token;
//		this.userEmail = userEmail;
//		this.isValid = isValid;
//	}
	
	
}





