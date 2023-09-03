package animal.shelter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import animal.shelter.service.ShelterService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/animal_shelter")
@Slf4j
public class ShelterController {
	@Autowired
	private ShelterService shelterService;
	
	@PostMapping("/contributor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
		log.info("Creating contributor {}", contributorData);
		return shelterService.saveContributor(contributorData);
	}
	@PutMapping("/contributor/{contributorId}")
	public ContributorData updateContributor (@PathVariable Long contributorId, @RequestBody ContributorData contributorData) {
		contributorData.setContributorId(contributorId);
		log.info("Updating contributor {}", contributorData);
		return shelterService.saveContributor(contributorData);
	}
	
	@GetMapping("/contributor")
	public List<ContributorData> retrieveAllContributors() {
		log.info("Retrieve all contributors called.");
		return shelterService.retrieveAllContributors();
	}
	
	@GetMapping("/contributor/{contributorId}")
	public ContributorData retrieveContributorById(@PathVariable Long contributorId) {
		log.info("Retrieving contributor with ID = {}", contributorId);
		return shelterService.retrieveContributorById(contributorId);
	}
	
	@DeleteMapping("/contributor")
	public void deleteAllContributors() { 
		log.info("Attempting to delete all contributors");
		throw new UnsupportedOperationException("Deleting all contributors is not allowed.");
	}
	
	@DeleteMapping("/contributor/{contributorId}")
	public Map<String, String> deleteContributorById(@PathVariable Long contributorId) {
		log.info("Deleting contributor with Id={}", contributorId);
		shelterService.deleteContributorById(contributorId);
		return Map.of("message", "Deletion of contributor with ID=" + contributorId +  " was successful");
	}
	
	@PostMapping("/contributor/{contributorId}/shelter")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AnimalShelterData insertAnimalShelter(@PathVariable Long contributorId, @RequestBody AnimalShelterData animalShelterData) {
		log.info("Creating shelter {} for contributor with ID={}", contributorId, animalShelterData);
		return shelterService.saveAnimalShelter(contributorId, animalShelterData);
	}
	
	@GetMapping("/contributor/{contributorId}/shelter/{shelterId}")
	public AnimalShelterData retrieveAnimalShelterById(@PathVariable Long contributorId, @PathVariable Long shelterId) {
		log.info("Retrieve animal shelter with Id={} for contributor with ID={}", shelterId, contributorId);
		return shelterService.retrieveAnimalShelterById(contributorId, shelterId);
	}
}
