package izvestaji;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

public class StanjePojedinacnihTretmana implements ExampleChart<PieChart> {

@Override
  public PieChart getChart() {	//stanje pojedinacnih tretmana

    PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();

    Dijagrami dijagram = new Dijagrami();
    
    int os = dijagram.brojSalonOtkazaoTretmana();
    int z = dijagram.brojZakazanihTretmana();
    int i = dijagram.brojIzvrsenihTretmana();
    int ok = dijagram.brojKlijentOtkazaoTretmana();
    int nsp = dijagram.brojNijeSePojavioTretmana();

    chart.addSeries("otkazao salon", os);
    chart.addSeries("zakazan", z);
    chart.addSeries("izvršen", i);
    chart.addSeries("otkazao klijent", ok);
    chart.addSeries("nije se pojavio", nsp);

    return chart;
  }

@Override
public String getExampleChartName() {
	// TODO Auto-generated method stub
	return null;
}

}