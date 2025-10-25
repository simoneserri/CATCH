package application;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "GATTI")
public class Gatto {
		
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String bio;

    @OneToMany(mappedBy = "gatto", cascade = CascadeType.ALL)
    private List<Foto> fotoGallery;
	
}
