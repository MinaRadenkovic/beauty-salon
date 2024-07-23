package uslugeTretmani;

public class KozmetickaUslugaTretman {
	
	private String nazivUsluge;
	
	public KozmetickaUslugaTretman(String nazivUsluge) {
		this.nazivUsluge = nazivUsluge;		
	}

	public String getNazivUsluge() {
		return nazivUsluge;
	}

	public void setNazivUsluge(String nazivUsluge) {
		this.nazivUsluge = nazivUsluge;
	}
	
	@Override
	public String toString() {
		return getNazivUsluge();
//		return "Kozmeticka usluga - tretman [naziv usluge=" + getNazivUsluge() + "]";
	}
	
	public String toFileString() {
		return getNazivUsluge();
	}	
}
