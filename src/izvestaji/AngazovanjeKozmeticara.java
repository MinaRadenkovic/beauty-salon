package izvestaji;

import java.util.List;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import CRUD.KozmeticariCRUD;
import korisniciSistema.Kozmeticar;

public class AngazovanjeKozmeticara implements ExampleChart<PieChart> {

    @Override
    public PieChart getChart() {	//stanje pojedinacnih tretmana

	    PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
	    Dijagrami dijagram = new Dijagrami();
	
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> kozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
	
	    for (Kozmeticar k : kozmeticari) {
	    	int broj = dijagram.brojIzvrsenihTretmanaPoKozmeticaru(k.getKorisnickoIme());
	    	String kozmeticar = k.getIme() + " " + k.getPrezime();
	    	chart.addSeries(kozmeticar, broj);
	    }
	    return chart;
    }

	@Override
	public String getExampleChartName() {
		// TODO Auto-generated method stub
		return null;
	}
}
