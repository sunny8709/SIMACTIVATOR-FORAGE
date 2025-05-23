package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for SIM card data access operations.
 * Extends JpaRepository to provide CRUD operations for SimCard entities.
 */
@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Long> {
    // All basic CRUD operations are inherited from JpaRepository
}