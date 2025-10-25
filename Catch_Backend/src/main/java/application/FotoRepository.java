package application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import application.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {
	List<Foto> findByGatto(Gatto gatto);
}