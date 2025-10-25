package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.FotoRepository;
import application.GattoRepository;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class DataLoader implements org.springframework.boot.CommandLineRunner {
	
	@Autowired
    private FotoRepository fotoRepository;
	
	@Autowired
    private GattoRepository gattoRepository;

    @Override
    public void run(String... args) throws Exception {
    	salvaGatti();
    	salvaFoto();
    }
    
    
    
    private void salvaGatti() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
        InputStream is = new ClassPathResource("data/gatti.json").getInputStream();
        List<Gatto> gatti = Arrays.asList(mapper.readValue(is, Gatto[].class));
        gattoRepository.saveAll(gatti);		
		
    }
    
    
    /*private void salvaFoto() throws Exception {
    	String directoryPath = "src/main/resources/images";

    	ArrayList<String> fileNames = new ArrayList<String>();

    	
        Path directory = Paths.get(directoryPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
            	fileNames.add(file.getFileName().toString());
            }
        }
        int id;
        for (String fileName : fileNames) {
            ClassPathResource imgFile = new ClassPathResource("images/" + fileName);
            id=Integer.valueOf(fileName.split("_")[0]);
            Optional<Gatto> optionalGatto = gattoRepository.findById(id);

            if (optionalGatto.isPresent()) {
            	Gatto gatto = optionalGatto.get();
				Foto foto = new Foto();
				foto.setFoto(imgFile.getInputStream().readAllBytes());
				foto.setGatto(gatto);
				fotoRepository.save(foto);
			}
            
            else {
                throw new RuntimeException("Gatto con ID " + id + " non trovato!");
            }
		}

    	
    }
    */

    private void salvaFoto() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:/images/*");

        for (Resource imgFile : resources) {
            String fileName = imgFile.getFilename();
            int id = Integer.parseInt(fileName.split("_")[0]);
            Optional<Gatto> optionalGatto = gattoRepository.findById(id);

            if (optionalGatto.isPresent()) {
                Gatto gatto = optionalGatto.get();
                Foto foto = new Foto();
                foto.setFoto(imgFile.getInputStream().readAllBytes());
                foto.setGatto(gatto);
                fotoRepository.save(foto);
            } else {
                throw new RuntimeException("Gatto con ID " + id + " non trovato!");
            }
        }
    }
}