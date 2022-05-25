package zook.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Friend {

	@OneToMany(mappedBy="user_id", fetch=FetchType.LAZY)
	private int userOne;
	
	@OneToMany(mappedBy="user_id", fetch=FetchType.LAZY)
	private int userTwo;
	
	@Column(name="request_status")
	private String requestStatus;
}
