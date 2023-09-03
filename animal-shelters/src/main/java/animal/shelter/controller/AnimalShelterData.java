package animal.shelter.controller;

import java.util.HashSet;
import java.util.Set;

import animal.shelters.entity.Amenity;
import animal.shelters.entity.AnimalShelter;
import animal.shelters.entity.Contributor;
import animal.shelters.entity.GeoLocation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class AnimalShelterData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long animalShelterId;
	private String shelterName;
	private String directions;
	private String state;
	private String country;
	private GeoLocation geoLocation;
	private AnimalShelterContributor contributor;
	private Set<String> amenties = new HashSet<>();
	
	public AnimalShelterData(AnimalShelter animalShelter) {
		animalShelterId = animalShelter.getAnimalShelterId();
		shelterName = animalShelter.getShelterName();
		directions = animalShelter.getDirections();
		state = animalShelter.getState();
		country = animalShelter.getCountry();
		geoLocation = animalShelter.getGeoLocation();
		contributor = new AnimalShelterContributor(animalShelter.getContributor());
		
		for(Amenity amenity : animalShelter.getAmenties()) {
			amenties.add(amenity.getAmenity());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AnimalShelterContributor {
		private Long contributorId;
		private String contributorName;
		private String contributorEmail;
		
		public AnimalShelterContributor(Contributor contributor) {
			contributorId = contributor.getContributorId();
			contributorName = contributor.getContributorName();
			contributorEmail = contributor.getContributorEmail();
		}
	}
}
