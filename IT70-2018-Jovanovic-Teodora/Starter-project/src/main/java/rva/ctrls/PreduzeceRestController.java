package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Preduzece;
import rva.repository.PreduzeceRepository;
@CrossOrigin
@RestController
@Api(tags = {"Preduzece CRUD operacije"})
public class PreduzeceRestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	@GetMapping("preduzece") //definisemo na kojoj putanji se poziva get metoda
	@ApiOperation(value = "Vraća kolekciju svih preduzeca iz baze podataka")
	public Collection<Preduzece> getPreduzeca() {
		return preduzeceRepository.findAll();
		
		
	}
	
	@GetMapping("preduzece/{id}")
	@ApiOperation(value = "Vraća preduzece za prosledjeni id iz baze podataka")
	public Preduzece getPreduzece(@PathVariable("id") Integer id ) {
		return preduzeceRepository.getOne(id);
	}
	@GetMapping("preduzeceNaziv/{naziv}")
	@ApiOperation(value = "Vraća preduzece za prosledjeni naziv iz baze podataka")
	public Collection <Preduzece> getPreduzeceByNaziv(@PathVariable("naziv") String naziv)
	{
		return preduzeceRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("preduzece")
	@ApiOperation(value = "Upisuje preduzece u bazu podataka ")
	public ResponseEntity<Preduzece> insertPreduzece ( @RequestBody Preduzece preduzece)
	{
		if(! preduzeceRepository.existsById(preduzece.getId())) {
			preduzeceRepository.save(preduzece);
			return new ResponseEntity<Preduzece>(HttpStatus.OK);
		}
		return new ResponseEntity<Preduzece>(HttpStatus.CONFLICT);
	}
	
	@PutMapping ("preduzece")
	@ApiOperation(value = "Modifikuje postojece preduzece u bazi podataka")
	public ResponseEntity<Preduzece> updatePreduzece( @RequestBody Preduzece preduzece ) {
		if(!preduzeceRepository.existsById(preduzece.getId())) {
			return new ResponseEntity<Preduzece>(HttpStatus.NO_CONTENT);
			
		}
		preduzeceRepository.save(preduzece);
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
	}
	@Transactional //ukoliko ne bude obrisano preduzece, sektor nece biti obrisan
	@DeleteMapping("preduzece/{id}")
	@ApiOperation(value = "Brise preduzece iz baze podataka za prosledjeni id")
	public ResponseEntity<Preduzece> deletePreduzece(@PathVariable ("id") Integer id ) {
		if(!preduzeceRepository.existsById(id)) {
			return new ResponseEntity<Preduzece>(HttpStatus.NO_CONTENT);
			
			}
		//jdbcTemplate.execute("delete from sektor where preduzece = " +id );
		preduzeceRepository.deleteById(id);
		if( id == -100)
		{
			jdbcTemplate.execute(
					
					"INSERT INTO \"preduzece\"(\"id\", \"naziv\", \"pib\", \"sediste\", \"opis\") "
					+ "VALUES (-100, 'TestNaziv', '675467', 'testsed', 'ne znam')"
			);
		}
		return new ResponseEntity<Preduzece>(HttpStatus.OK);
		
	}
	
	
	
	
	
	
}
