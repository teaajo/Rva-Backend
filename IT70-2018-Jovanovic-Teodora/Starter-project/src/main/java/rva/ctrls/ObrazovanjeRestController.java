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
import rva.jpa.Obrazovanje;
import rva.repository.ObrazovanjeRepository;


@CrossOrigin
@RestController
@Api(tags = {"Obrazovanje CRUD operacije"})
public class ObrazovanjeRestController {
	

	/*injektovanje zavisnosti, trebaju nam sve metode te klase 
	 II nacina:
	 koriscenjem konstruktora- obavezne zavisnosti
	 putem setera-opcione zavisnosti
	 */
	
	@Autowired
	private JdbcTemplate jdbcTemplate;// direktno izvrsavanje sql upita nad bazom 
	@Autowired 
	private ObrazovanjeRepository obrazovanjeRepository;
	
	@GetMapping("obrazovanje") //definisemo na kojoj putanji se poziva get metoda
	@ApiOperation(value = "Vraća kolekciju svih obrazovanja iz baze podataka")
	public Collection <Obrazovanje> getObrazovanjeS() {
		return obrazovanjeRepository.findAll();
		
	}
	
	@GetMapping("obrazovanje/{id}")
	@ApiOperation(value = "Vraća obrazovanje za prosledjeni id iz baze podataka")
	public Obrazovanje getObrazovanje(@PathVariable("id") Integer id) {
		return obrazovanjeRepository.getOne(id);
	}
	
	@GetMapping("obrazovanjeNaziv/{naziv}")
	@ApiOperation(value = "Vraća obrazovanje za prosledjeni naziv iz baze podataka")
	public Collection <Obrazovanje> getObrazovanjeByNaziv(@PathVariable("naziv") String naziv) {
		return obrazovanjeRepository.findByNazivContainingIgnoreCase(naziv);
		
	}
	
	@PostMapping("obrazovanje")
	@ApiOperation(value = "Upisuje obrazovanje u bazu podataka")
	// na ovaj nacin se vraca http status 
	public ResponseEntity<Obrazovanje> insertObrazovanje(@RequestBody Obrazovanje obrazovanje)
	{
		
		if(!obrazovanjeRepository.existsById(obrazovanje.getId()))
		{
			obrazovanjeRepository.save(obrazovanje);
			return new ResponseEntity<Obrazovanje> (HttpStatus.OK);
			
		}
		return new ResponseEntity<Obrazovanje> (HttpStatus.CONFLICT);
		
		
	}
	
	@PutMapping("obrazovanje")
	@ApiOperation(value = "Modifikuje postojece obrazovanje iz baze podataka")
	public ResponseEntity<Obrazovanje> updateObrazovanje(@RequestBody Obrazovanje obrazovanje )
	{
		if(!obrazovanjeRepository.existsById(obrazovanje.getId()))
			{
		
			return new ResponseEntity<Obrazovanje> (HttpStatus.NO_CONTENT);
			
			}
			obrazovanjeRepository.save(obrazovanje);
			return new ResponseEntity<Obrazovanje> (HttpStatus.OK);
	}
	
	@Transactional
	
	@DeleteMapping("obrazovanje/{id}")
	@ApiOperation(value = "Brise obrazovanje iz baze podataka")
	
	public ResponseEntity<Obrazovanje> deleteObrazovanje(@PathVariable("id") Integer id )
	{
		if(! obrazovanjeRepository.existsById(id)) {
			return new ResponseEntity<Obrazovanje> (HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from radnik where obrazovanje = " +id );
		obrazovanjeRepository.deleteById(id);
		if( id == -100) {
			jdbcTemplate.execute(
			
				"INSERT INTO \"obrazovanje\"(\"id\", \"naziv\", \"stepen_strucne_spreme\", \"opis\") "
				+ "VALUES (-100, 'TestNaziv', 'TestStepen', 'TestOpis')"
		);
		}
		
		return new ResponseEntity<Obrazovanje> (HttpStatus.OK);
		
		
	}
	
	
	
	
	

}
