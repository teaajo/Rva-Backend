package rva.ctrls;

import java.util.Collection;

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
import rva.jpa.Radnik;
import rva.jpa.Sektor;
import rva.repository.ObrazovanjeRepository;
import rva.repository.RadnikRepository;
import rva.repository.SektorRepository;
@CrossOrigin
@RestController
@Api(tags = {"Radnik CRUD operacije"})

public class RadnikRestController {
	
	@Autowired /*springov kontejner prilikom pokretanja automatski detektuje bin na osnovu tipa na osnovu kog deklarisemo
	kontroler i injektuje tip bina u to polje kako bi injektovali zavisnost*/

	private JdbcTemplate jdbcTamplate;
	
	@Autowired
	private RadnikRepository radnikRepository;
	@Autowired
	private SektorRepository sektorRepository;
	@Autowired
	private ObrazovanjeRepository obrazovanjeRepository;
	
	@GetMapping("radnik")
	@ApiOperation(value = "Vraća kolekciju svih radnika iz baze podataka")
	public Collection<Radnik> getRadnici() {
		return radnikRepository.findAll();
	}
	
	@GetMapping("radnik/{id}")
	@ApiOperation(value = "Vraća radnika iz baze podataka cija je id vrednost prosledjena kao path varijabla")
	public Radnik getRadnik(@PathVariable("id") Integer id) {
		
		return radnikRepository.getOne(id);
		
	}
	
	@GetMapping("radnikIme/{ime}")
	@ApiOperation(value = "Vraća radnike iz baze podataka koje u imenu sadrze string prosledjen kao path varijabla")
	public Collection<Radnik> getRadnikByIme(@PathVariable("ime") String ime)
	{
		return radnikRepository.findByIme(ime);
	}
	
	@GetMapping("radnikPoSektoru/{id}")
	@ApiOperation(value = "Vraća radnike iz baze podataka za prosledjenu id vrednost sektora")
	public Collection<Radnik> getRadnikBySektor(@PathVariable("id")Integer id){
		
		Sektor s = sektorRepository.getOne(id);
		return radnikRepository.findBySektor(s);
		
		
	}
	
	@PostMapping("radnik")
	@ApiOperation(value = "Upisuje radnika u bazu podataka")
	public ResponseEntity<Radnik> insertRadnik(@RequestBody Radnik radnik) {
		if(!radnikRepository.existsById(radnik.getId())) {
			radnikRepository.save(radnik);
			return new ResponseEntity<Radnik>(HttpStatus.OK);
		}
		return new ResponseEntity<Radnik>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("radnik")
	@ApiOperation(value = "Modifikuje radnika iz postojece baze podataka")
	public ResponseEntity<Radnik> updateRadnik(@RequestBody Radnik radnik)
	{
		if(!radnikRepository.existsById(radnik.getId())) {
			return new ResponseEntity<Radnik>(HttpStatus.NO_CONTENT);
		
		}
		radnikRepository.save(radnik);
		return new ResponseEntity<Radnik>(HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("radnik/{id}")
	@ApiOperation(value = "Brise radnika iz baze podataka cija je vrednost proslednjena kao path varijabla")
	public ResponseEntity<Radnik> deleteRadnik(@PathVariable ("id") Integer id) {
		if(!radnikRepository.existsById(id)) {
			return new ResponseEntity<Radnik>(HttpStatus.NO_CONTENT);
		
		}
		radnikRepository.deleteById(id);
		if(id==-100)
		{
			jdbcTamplate.execute( "insert into \"radnik\"(\"id\", \"ime\", \"prezime\", \"broj_lk\",  \"obrazovanje\", \"sektor\"  ) \"\r\n"
					+ "					+ \"VALUES (-100, 'Testime', 'TestPrezime', '38746', 1, 2)\""
						
					
					
					);
			
		}
		return new ResponseEntity<Radnik>(HttpStatus.OK);
		 
	}
	
	@GetMapping("radniciPoObrazovanju/{id}")
	@ApiOperation(value = "Vraća radnikae iz baze podataka za prosledjenu id vrednost obrazovanja")
	public Collection <Radnik> radniciPoObrazovanju(@PathVariable ("id") Integer id)
	{
		Obrazovanje o = obrazovanjeRepository.getOne(id);
		return radnikRepository.findByObrazovanje(o);
	}
	
	
	
	
	
	
	
	

}
