package application;

import java.util.List;

import lombok.Data;

@Data
public class GattoFotoDTO  {
	
    private int id;
    private String nome;
    private String bio;
    private List<String> foto;
    
    public GattoFotoDTO() {
    }
    

}