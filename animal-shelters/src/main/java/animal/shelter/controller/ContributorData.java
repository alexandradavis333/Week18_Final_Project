package animal.shelter.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import animal.shelters.entity.Amenity;
import animal.shelters.entity.AnimalShelter;
import animal.shelters.entity.Contributor;
import animal.shelters.entity.GeoLocation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


@Data
@NoArgsConstructor
public class ContributorData {
	private Long contributorId;
	private String contributorName;
	private String contributorEmail;
	private Set<AnimalShelter> animalShelters = new HashSet<>();

	@SuppressWarnings("unchecked")
	public ContributorData(Contributor contributor) {
		contributorId = contributor.getContributorId();
		contributorName = contributor.getContributorName();
		contributorEmail = contributor.getContributorEmail();
		
		for(AnimalShelter animalShelter : contributor.getAnimalShelter()) {
			animalShelters.addAll((Collection<? extends AnimalShelter>) new AnimalShelterResponse(animalShelter));
		}
	}

	@Data
	@NoArgsConstructor
	static class AnimalShelterResponse {
		private Long animalShelterId;
		private String shelterName;
		private String directions;
		private String state;
		private String country;
		private GeoLocation geoLocation;
		private Set<String> amenties = new HashSet<>();
		
		AnimalShelterResponse(AnimalShelter animalShelter) {
			animalShelterId = animalShelter.getAnimalShelterId();
			shelterName = animalShelter.getShelterName();
			directions = animalShelter.getDirections();
			state = animalShelter.getState();
			country = animalShelter.getCountry();
			geoLocation = new GeoLocation(animalShelter.getGeoLocation());
			
			for(Amenity amenity : animalShelter.getAmenties()) {
				amenties.add(amenity.getAmenity());
			}
		}
	}
}
