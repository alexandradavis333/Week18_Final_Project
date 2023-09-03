package animal.shelter.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animal.shelter.controller.AnimalShelterData;
import animal.shelter.controller.ContributorData;
import animal.shelter.dao.AmenityDao;
import animal.shelter.dao.AnimalShelterDao;
import animal.shelter.dao.ContributorDao;
import animal.shelters.entity.Amenity;
import animal.shelters.entity.AnimalShelter;
import animal.shelters.entity.Contributor;

@Service
public class ShelterService {

	@Autowired
	private ContributorDao contributorDao; 
	
	@Autowired
	private AmenityDao amenityDao;
	
	@Autowired
	private AnimalShelterDao animalShelterDao;
	
	@Transactional(readOnly = false)
	public ContributorData saveContributor(ContributorData contributorData) {
		Long contributorId = contributorData.getContributorId();
		Contributor contributor = findOrCreateContributor(contributorId, contributorData.getContributorEmail());
		
		setFieldsInContributor(contributor, contributorData);
		return new ContributorData(contributorDao.save(contributor));
	}

	private void setFieldsInContributor(Contributor contributor, ContributorData contributorData) {
		contributor.setContributorEmail(contributorData.getContributorEmail());
		contributor.setContributorName(contributorData.getContributorName());
		
	}

	private Contributor findOrCreateContributor(Long contributorId, String contributorEmail) {
		Contributor contributor;
		
		if(Objects.isNull(contributorId)) {
			Optional<Contributor> opContrib = contributorDao.findByContributorEmail(contributorEmail);
			
			if(opContrib.isPresent()) {
				throw new DuplicateKeyException("Contributor with email " + contributorEmail + " already exists.");
			}
			contributor = new Contributor();
		}
		else {
			contributor = findContributorById(contributorId);
		}
		return contributor;
	}

	private Contributor findContributorById(Long contributorId) {
		return contributorDao.findById(contributorId).orElseThrow(() -> new NoSuchElementException("Contributor with Id= " + contributorId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<ContributorData> retrieveAllContributors() {
		List<Contributor> contributors = contributorDao.findAll();
		List<ContributorData> response = new LinkedList();
		for(Contributor contributor : contributors) {
			response.add(new ContributorData(contributor));
		}
		return response;
	}

	@Transactional(readOnly = true)
	public ContributorData retrieveContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		return new ContributorData(contributor);
	}

	@Transactional(readOnly = false)
	public void deleteContributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		contributorDao.delete(contributor);
	}

	@Transactional(readOnly = false)
	public AnimalShelterData saveAnimalShelter(Long contributorId, AnimalShelterData animalShelterData) {
		Contributor contributor = findContributorById(contributorId);
		Set<Amenity> amenities = amenityDao.findAllByAmenityIn(animalShelterData.getAmenties());
		AnimalShelter animalShelter = findOrCreateAnimalShelter(animalShelterData.getAnimalShelterId());
		setAnimalShelter(animalShelter, animalShelterData);
		animalShelter.setContributor(contributor);
		contributor.getAnimalShelter().add(animalShelter);
		for(Amenity amenity : amenities) {
			amenity.getAnimalShelters().add(animalShelter);
			animalShelter.getAmenties().add(amenity);
		}
		
		AnimalShelter dbAnimalShelter = animalShelterDao.save(animalShelter);
		return new AnimalShelterData(dbAnimalShelter);
	}

	private void setAnimalShelter(AnimalShelter animalShelter, AnimalShelterData animalShelterData) {
		animalShelter.setCountry(animalShelterData.getCountry());
		animalShelter.setDirections(animalShelterData.getDirections());
		animalShelter.setGeoLocation(animalShelterData.getGeoLocation());
		animalShelter.setShelterName(animalShelterData.getShelterName());
		animalShelter.setAnimalShelterId(animalShelterData.getAnimalShelterId());
		animalShelter.setState(animalShelterData.getState());
	}

	private AnimalShelter findOrCreateAnimalShelter(Long animalShelterId) {
		AnimalShelter animalShelter;
		if(Objects.isNull(animalShelterId) ) {
			animalShelter = new AnimalShelter();
		}
		else {
			animalShelter = findAnimalShelterById(animalShelterId);
		}
		return animalShelter;
	}

	private AnimalShelter findAnimalShelterById(Long animalShelterId) {
		return animalShelterDao.findById(animalShelterId).orElseThrow(() -> new NoSuchElementException("Animal shelter with ID= " + animalShelterId + " does not exist"));
	}
	
	@Transactional(readOnly = true)
	public AnimalShelterData retrieveAnimalShelterById(Long contributorId, Long shelterId) {
		findContributorById(contributorId);
		AnimalShelter animalShelter = findAnimalShelterById(shelterId);
		
		if(animalShelter.getContributor().getContributorId() != contributorId) {
			throw new IllegalStateException("Animal shelter with ID=" + shelterId + " is not owned by contributor with ID=" + contributorId);
		}
		return new AnimalShelterData(animalShelter);
	}

}
