package animal.shelters.entity;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Amenity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AmenityId;
	private String amenity;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "amenities")
	private Set<AnimalShelter> animalShelters = new HashSet<>();
}
