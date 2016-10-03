package source;

import entity.Drug;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by RUSLAN on 04.05.2016.
 */
public class StandartMedecinesSource {

    private static List<Drug> medecines;

    public static List<Drug> getData() {
        medecines = Arrays.asList(
                new Drug("Экванид таб.№70", 26985, "Biomardon pharma", Calendar.getInstance().getTime()),
                new Drug("Цинепар 75 р-р д/инъек. 75мг/3мл №5", 8735, "Марион Биотеч ЛТД", Calendar.getInstance().getTime()),
                new Drug("Цинепар актив гель 20г", 13514, "Марион Биотеч ЛТД", Calendar.getInstance().getTime()),
                new Drug("Цинепар кид сусп. 60мл ", 11612, "Марион Биотеч ЛТД", Calendar.getInstance().getTime()),
                new Drug("Цинепар таб №100", 70295, "Марион Биотеч ЛТД", Calendar.getInstance().getTime()),
                new Drug("Энам таб.10мг №20", 17645, "Доктор Редис Лаб.", Calendar.getInstance().getTime()),
                new Drug("Эритромицин 250мг №20", 7890, "Ирбит ХФЗ", Calendar.getInstance().getTime()),
                new Drug("Этиловый спирт 70% 50мл", 560, "Хужаи Жахон", Calendar.getInstance().getTime())
        );
        return medecines;
    }

    public static List<Drug> getDataA() {
        medecines = Arrays.asList(
                new Drug("Экванид таб.№70 ", 22100, "BIOMARDON PHARMA ,Узбекистон", Calendar.getInstance().getTime()),
                new Drug("Цинепар  актив гель  20г", 12532, "Marion  Biotech Private,Индия", Calendar.getInstance().getTime()),
                new Drug("Цинепар  актив спрей 75мл/53г", 33601, "Marion  Biotech Private,Индия", Calendar.getInstance().getTime()),
                new Drug("Цинепар Кид сусп. 60мл", 7955, "Marion  Biotech Private,Индия", Calendar.getInstance().getTime())
        );
        return medecines;
    }

    public List<Drug> getMedecines() {
        return medecines;
    }

    public void setMedecines(List<Drug> medecines) {
        this.medecines = medecines;
    }
}
