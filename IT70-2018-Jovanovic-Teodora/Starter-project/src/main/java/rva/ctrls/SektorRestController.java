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
import rva.jpa.Sektor;
import rva.repository.PreduzeceRepository;
import rva.repository.SektorRepository;
@CrossOrigin
@RestController
@Api(tags = {"Sektor CRUD operacije"})

public class SektorRestController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SektorRepository sektorRepository;
	@Autowired
	private PreduzeceRepository preduzeceRepository;
	
	@GetMapping("sektor")
	@ApiOperation(value = "Vraća kolekciju svih sektora iz baze podataka")
	public Collection<Sektor> getsektori() {
		return sektorRepository.findAll();
	}
	
	@GetMapping("sektor/{id}")
	@ApiOperation(value = "Vraća sektor iz baze podataka cija je id vrednost prosledjena kao path varijabla ")
	public Sektor getSektor(@PathVariable("id") Integer id) {
		
		return sektorRepository.getOne(id);
		
	} 
	
	@GetMapping("sektorNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju sektora iz baze podataka koji u nazivu sadrzi string proslednjen kao path varijabla")
	public Collection<Sektor> getSektorByNaziv(@PathVariable("naziv") String naziv)
	{
		return sektorRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@GetMapping("sektoriPreduzeca/{id}")
	@ApiOperation(value = "Vraća kolekciju svih preduzeca iz baze podataka za id vrednost preduzeca proslednjen kaopath varijabla")
	public Collection<Sektor> sektoriPreduzeca(@PathVariable("id")Integer id)
	{
		Preduzece p = preduzeceRepository.getOne(id);
		return sektorRepository.findByPreduzece(p);
	}
	
	@PostMapping("sektor")
	@ApiOperation(value = "Upisuje sektor u bazu podataka")
	public ResponseEntity<Sektor> insertSektor(@RequestBody Sektor sektor) {
		if(!sektorRepository.existsById(sektor.getId())) {
			sektorRepository.save(sektor);
			return new ResponseEntity<Sektor>(HttpStatus.OK);
		}
		return new ResponseEntity<Sektor>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("sektor")
	@ApiOperation(value = "Modifikuje postojeci sektor iz baze podataka")
	public ResponseEntity<Sektor> updateSektor(@RequestBody Sektor sektor)
	{
		if(!sektorRepository.existsById(sektor.getId())) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
		
		}
		sektorRepository.save(sektor);
		return new ResponseEntity<Sektor>(HttpStatus.OK);
		
		
	}
	
	@Transactional 
	@DeleteMapping("sektor/{id}")
	@ApiOperation(value = "Brise sektor iz baze podataka za proslednjenu id vrednost kao path varijabla")
	public ResponseEntity<Sektor> deleteSektor(@PathVariable ("id") Integer id) {
		if(!sektorRepository.existsById(id)) {
			return new ResponseEntity<Sektor>(HttpStatus.NO_CONTENT);
		
		}
		jdbcTemplate.execute("delete from radnik where sektor = " +id );
		
		sektorRepository.deleteById(id);
		if(id==-100)
		{
			jdbcTemplate.execute( "insert into \"sektor\"(\"id\", \"naziv\", \"oznaka\", \"preduzece\"  ) \"\r\n"
					+ "					+ \"VALUES (-100, 'TestNaziv', 'TestOznaka', 1)\""
						
					
					
					);
			
		}
		return new ResponseEntity<Sektor>(HttpStatus.OK);
	}

}
