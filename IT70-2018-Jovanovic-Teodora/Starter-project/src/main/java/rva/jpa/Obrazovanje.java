package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the obrazovanje database table.  
 * 
 */
@JsonIgnoreProperties ({"hibernateLazyInitializer", "handler"})//spreciti da se ne pojavljuje
@Entity
@NamedQuery(name="Obrazovanje.findAll", query="SELECT o FROM Obrazovanje o")
public class Obrazovanje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OBRAZOVANJE_ID_GENERATOR", sequenceName="OBRAZOVANJE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OBRAZOVANJE_ID_GENERATOR")
	private Integer id;

	private String naziv;

	private String opis;

	@Column(name="stepen_strucne_spreme")
	private String stepenStrucneSpreme;

	//bi-directional many-to-one association to Radnik
	@JsonIgnore //sprecava beskonacnu petlju 
	@OneToMany(mappedBy="obrazovanje", cascade = {CascadeType.DETACH, CascadeType.REMOVE}) //obrazovanje moze da ima vise radnika
	private List<Radnik> radniks;

	public Obrazovanje() { 
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getStepenStrucneSpreme() {
		return this.stepenStrucneSpreme;
	}

	public void setStepenStrucneSpreme(String stepenStrucneSpreme) {
		this.stepenStrucneSpreme = stepenStrucneSpreme;
	}

	public List<Radnik> getRadniks() {
		return this.radniks;
	}

	public void setRadniks(List<Radnik> radniks) {
		this.radniks = radniks;
	}

	public Radnik addRadnik(Radnik radnik) {
		getRadniks().add(radnik);
		radnik.setObrazovanje(this);

		return radnik;
	}

	public Radnik removeRadnik(Radnik radnik) {
		getRadniks().remove(radnik);
		radnik.setObrazovanje(null);

		return radnik;
	}

}