package tn.globalnet.webscraper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.globalnet.webscraper.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription , String> {

}
