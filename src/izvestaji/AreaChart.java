package izvestaji;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import CRUD.KozmetickaUslugaCRUD;
import uslugeTretmani.KozmetickaUslugaTretman;

public class AreaChart implements ExampleChart<XYChart> {
 
  @Override
  public XYChart getChart() {
 
    // Create Chart
    XYChart chart = new XYChartBuilder().width(800).height(600).title("Prihodi po tipu kozmetičkog tretmana").build();
 
    List<Date> xData = new ArrayList<Date>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
	for (int i = 1; i <= 12; i++) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date currentDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		xData.add(currentDate);
	}
	
    String uslugeFile = ".//fajlovi/usluge.csv";
    List<KozmetickaUslugaTretman> usluge = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();

	for (KozmetickaUslugaTretman usluga : usluge) {
		List<Double> yData= new ArrayList<>();
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(new Date());
		for (int i = 1; i <= 12; i++) {
			calendar1.set(Calendar.DAY_OF_MONTH, 1);
	        Date currentDate = calendar1.getTime();
	        calendar1.add(Calendar.MONTH, -1);

          LocalDate krajMeseca = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          LocalDate pocMeseca = calendar1.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          Dijagrami dijagram = new Dijagrami();
          double prihod = dijagram.prihodUMesecPoTipuUsluge(usluga.getNazivUsluge(), pocMeseca, krajMeseca);
          yData.add(prihod);
		}
		chart.addSeries(usluga.getNazivUsluge(), xData, yData);
	}
	
	List<Double> yData= new ArrayList<>();
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(new Date());
	for (int i = 1; i <= 12; i++) {
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
        Date currentDate = calendar1.getTime();
        calendar1.add(Calendar.MONTH, -1);

      LocalDate krajMeseca = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate pocMeseca = calendar1.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      Dijagrami dijagram = new Dijagrami();
      double prihod = dijagram.prihodZaPrethodnih12MeseciUkupno(pocMeseca, krajMeseca);
      yData.add(prihod);
	}
	chart.addSeries("ukupno", xData, yData);
 
    return chart;
  }

  @Override
  public String getExampleChartName() {
	// TODO Auto-generated method stub
	return null;
  }
}
