package animal.shelter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import animal.shelters.entity.Contributor;

public interface ContributorDao extends JpaRepository<Contributor, Long> {

	Optional<Contributor> findByContributorEmail(String contributorEmail);

}
