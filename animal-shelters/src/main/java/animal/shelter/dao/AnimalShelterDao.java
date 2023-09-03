package animal.shelter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import animal.shelters.entity.AnimalShelter;

public interface AnimalShelterDao extends JpaRepository<AnimalShelter, Long> {

}
