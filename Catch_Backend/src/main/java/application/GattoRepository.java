package application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import application.Gatto;

@Repository
public interface GattoRepository extends JpaRepository<Gatto, Integer> {
	
}