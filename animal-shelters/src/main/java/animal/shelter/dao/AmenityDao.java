package animal.shelter.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import animal.shelters.entity.Amenity;

public interface AmenityDao extends JpaRepository<Amenity, Long> {

	Set<Amenity> findAllByAmenityIn(Set<String> amenties);
	

}
