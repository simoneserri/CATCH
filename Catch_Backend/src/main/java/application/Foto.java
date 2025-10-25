package application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "IMMAGINI")
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INDEX")
	private int index;

	@ManyToOne
	@JoinColumn(name = "ID", nullable = false)
	private Gatto gatto;

	@Lob
	@Column(nullable = false)
	private byte[] foto;
}
