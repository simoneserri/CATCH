package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Foto;
import application.FotoRepository;
import application.Gatto;
import application.GattoFotoDTO;
import application.GattoRepository;
import jakarta.validation.Valid;

@Service
public class saveDataService {

	private final GattoRepository gattoRepository;
	private final FotoRepository fotoRepository;

	public saveDataService(GattoRepository gattoRepository, FotoRepository fotoRepository) {
	        this.gattoRepository = gattoRepository;
	        this.fotoRepository = fotoRepository;
	    }

	    @Async
	    public void salvaNuovoGatto(GattoFotoDTO dto) {
	        try {
	           
	            Gatto gatto = new Gatto();
	            gatto.setNome(dto.getNome());
	            gatto.setBio(dto.getBio());
	            for (String base64 : dto.getFoto()) {
	                byte[] imageBytes = Base64.getDecoder().decode(base64);
	                Foto foto = new Foto();
	                foto.setFoto(imageBytes);
	                foto.setGatto(gatto);
	                gatto.getFotoGallery().add(foto);
	            }
	            gattoRepository.save(gatto);

	            System.out.println("Gatto e immagini salvati con successo.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Errore durante il salvataggio del gatto: " + e.getMessage());
	        }
	    }


    	
    	



}

