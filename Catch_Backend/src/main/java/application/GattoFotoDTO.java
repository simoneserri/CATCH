package application;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GattoFotoDTO  {
	
    private int id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    @NotBlank(message = "La bio non può essere vuota")
    private String bio;

    @NotNull(message = "La lista di foto non può essere nulla")
    @NotEmpty(message = "Devi fornire almeno una foto")
    private List<String> foto;
    
    public GattoFotoDTO() {
    }
    
    

}