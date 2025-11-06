package controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.Foto;
import application.FotoRepository;
import application.Gatto;
import application.GattoFotoDTO;
import application.GattoRepository;
import jakarta.validation.Valid;
import services.saveDataService;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class CatchController {
	@Autowired
    private GattoRepository gattoRepository;

	@Autowired
    private FotoRepository fotoRepository;
	
    private final saveDataService service;

    public CatchController(saveDataService service) {
        this.service = service;
    }
	

    @PostMapping("/addCat")
    public ResponseEntity<String> aggiungiGatto(@Valid @RequestBody GattoFotoDTO dto) {
        this.service.salvaNuovoGatto(dto);
        return ResponseEntity.status(200).body("Dati inseriti correttamente");
    }
	
    @GetMapping("/getAllCats")
    public ResponseEntity<?> getAllCats() {
    	 List<Gatto> gatti = gattoRepository.findAll();
         if (gatti.isEmpty()) {
             return ResponseEntity.status(404).body("Non ci sono gatti nel db");
         }
         
         List<GattoFotoDTO>gattiDTO=new ArrayList<>();
         for(Gatto gatto:gatti) {
        	 List<Foto> fotoList = fotoRepository.findByGatto(gatto);
        	 GattoFotoDTO dto = new GattoFotoDTO();
             dto.setId(gatto.getID());
             dto.setNome(gatto.getNome());
             dto.setBio(gatto.getBio());
             dto.setFoto(
            	        fotoList.stream()
            	            .map(foto -> Base64.getEncoder().encodeToString(foto.getFoto()))
            	            .collect(Collectors.toList())
            	    );
             gattiDTO.add(dto);
         }
         return ResponseEntity.ok(gattiDTO);    
         }

    }  
 

