package controllers;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.Foto;
import application.FotoRepository;
import application.Gatto;
import application.GattoFotoDTO;
import application.GattoRepository;

@RestController


public class CatchController {
	@Autowired
    private GattoRepository gattoRepository;

	@Autowired
    private FotoRepository fotoRepository;
	
	@GetMapping("/getRandomFotoCat")
    public ResponseEntity<?> getRandomCat() {
    	 Gatto gatto = gattoRepository.findAll().getLast();

    	 byte[] fotoBytes= fotoRepository.findByGatto(gatto).getLast().getFoto();
         
    	 return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(fotoBytes);
	}
         
    

	
    @GetMapping("/getCat/{id}")
    public ResponseEntity<?> getGattoById(@PathVariable int id) {
    	 Optional<Gatto> optionalGatto = gattoRepository.findById(id);

         if (optionalGatto.isEmpty()) {
             return ResponseEntity.status(404).body("Gatto con ID " + id + " non trovato.");
         }

         Gatto gatto = optionalGatto.get();
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
         return ResponseEntity.ok(dto);
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

