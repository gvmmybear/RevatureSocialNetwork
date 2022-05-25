package zook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Species")
public class Species {
	
	@Id
	@Column(name= "profanity")
	private int species_id;
	
	@Column(name= "species_name")
	private String speciesName;
	
//	SimpleMai

}