package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressWarnings("WeakerAccess")
public class Stats extends Activity {
    @BindView(R.id.chart)
    LineChart chart;

    @BindColor(R.color.colorAccent)
    int colorAccent;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @BindColor(R.color.lightGreen)
    int colorLightGreen;
    @BindColor(R.color.darkGreen)
    int colorDarkGreen;

    @BindColor(R.color.primaryTextColor)
    int primaryTextColor;

    @BindColor(R.color.blue)
    int colorBlue;
    @BindColor(R.color.darkBlue)
    int colorDarkBlue;

    @BindColor(R.color.orange)
    int colorOrange;
    @BindColor(R.color.darkOrange)
    int colorDarkOrange;

    @BindColor(R.color.purple)
    int colorPurple;
    @BindColor(R.color.darkPurple)
    int colorDarkPurple;

    @BindColor(R.color.yellow)
    int colorYellow;
    @BindColor(R.color.darkYellow)
    int colorDarkYellow;

    @BindView(R.id.statsSpinner)
    Spinner statsSpinner;


    MemoryDatabaseAdapter helper;

    ArrayList<Data> dataObjects;

    ArrayList<String> formaterValuesDays;
    ArrayList<String> formaterValuesMonth;

    List<Entry> entries;
    LineDataSet numbersSet = null;
    LineDataSet wordsSet = null;
    LineDataSet cardsSet = null;
    LineDataSet namesSet = null;

    private int[] dbHasDataCheck = {0, 0, 0, 0};

    private int lastCheckPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);


        helper = new MemoryDatabaseAdapter(this);


        Calendar calendar = Calendar.getInstance();
        int startMonth = calendar.get(Calendar.MONTH);


        statsSpinner.setSelection(startMonth);


        generateData(startMonth);


        statsSpinner.setOnItemSelectedListener(new SpinnerClass());


    }

    /**
     *
     * adds random records for testing purposes
     */
    public void testRecords(View view) {
        helper.testRecords();
    }


    private void generateData(int selectedMonth) {

        int countCheck = 0;
        lastCheckPosition = 0;
        for (int i = 0; i < dbHasDataCheck.length; i++) {//zeruj tablice testów
            dbHasDataCheck[i] = 0;
        }

        boolean check0 = getData(0, selectedMonth);
        if (check0) {
            countCheck++;
            dbHasDataCheck[0] = 1;
            getNumbersLineDataSet();
        }


        boolean check1 = getData(1, selectedMonth);
        if (check1) {
            countCheck++;
            dbHasDataCheck[1] = 1;
            getWordsLineDataSet();
        }

        boolean check2 = getData(2, selectedMonth);
        if (check2) {
            countCheck++;
            dbHasDataCheck[2] = 1;
            getCardsLineDataSet();
        }
        boolean check3 = getData(3, selectedMonth);
        if (check3) {
            countCheck++;
            dbHasDataCheck[3] = 1;
            getNamesLineDataSet();
        }


//        if ((check0? 1:0)+(check1? 1:0)+(check2? 1:0)+(check3? 1:0)>=2)

        if (countCheck >= 2) {

            Log.e("temp test", "higher that or eq to 2");

            List<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.clear();

            for (int i = 0; i < 4; i++) {
                if (getDataSetInLineData(dbHasDataCheck) != null) {
                    dataSets.add(getDataSetInLineData(dbHasDataCheck));
                }

                lastCheckPosition++;
            }
//            dataSets.add(numbersSet);
//            dataSets.add(wordsSet);

            LineData lineData = new LineData(dataSets);
            chart.setData(lineData);

        } else if (countCheck == 1) {
            Log.e("temp test", "eq to 1");

            for (int i = 0; i < 4; i++) {
                if (getDataSetInLineData(dbHasDataCheck)==null)
                    lastCheckPosition++;
            }
            LineData lineData = new LineData(getDataSetInLineData(dbHasDataCheck));
            chart.setData(lineData);


        } else {

            Log.e("temp test", "lowerr than 1");

            chart.clear();
            chart.setNoDataText("There is no data to display.");
            chart.setNoDataTextColor(primaryTextColor);
        }


        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(primaryTextColor);
        xAxis.setTextSize(15);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());


        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextColor(primaryTextColor);
        yAxis.setTextSize(25);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(50);
        yAxis.setDrawGridLines(false);



        chart.getAxisRight().setEnabled(false);


        chart.setDoubleTapToZoomEnabled(false);

        chart.setPadding(50, 50, 50, 50);


        Description description = new Description();
        description.setText("Monthy-Earned/Days");
        description.setTextSize(15);
        description.setYOffset(5);
        description.setTextColor(primaryTextColor);

        chart.setDescription(description);


        Legend legend = chart.getLegend();
        legend.setTextSize(15);
        legend.setTextColor(primaryTextColor);
        legend.setYEntrySpace(15);
        legend.setFormSize(15);
        legend.setYOffset(15);


        chart.invalidate();
    }


    private LineDataSet getDataSetInLineData(int[] tabTestow) {

        LineDataSet dataSet ;

        if (tabTestow[0] == 1 && lastCheckPosition == 0) {
            dataSet = numbersSet;
        } else if (tabTestow[1] == 1 && lastCheckPosition ==1) {
            dataSet = wordsSet;
        } else if (tabTestow[2] == 1 && lastCheckPosition ==2) {
            Log.e("tabtest 2", "2");
            dataSet = cardsSet;
        } else if (tabTestow[3] == 1 && lastCheckPosition ==3) {
            Log.e("tabtest 3", "3");
            dataSet = namesSet;
        } else {
            return null;
        }

        return dataSet;
    }

    private void getNumbersLineDataSet() {
        numbersSet = new LineDataSet(entries, "Numbers");
        numbersSet.setColor(colorBlue);
        numbersSet.setValueTextColor(primaryTextColor);
        numbersSet.setValueTextSize(17);
        numbersSet.setCircleColor(colorDarkBlue);
        numbersSet.setValueTextColor(primaryTextColor);
        numbersSet.setValueFormatter(new MyFormaterInt());
        numbersSet.setDrawVerticalHighlightIndicator(false);
        numbersSet.setDrawCircleHole(false);//todo settings draw hole
    }

    private void getWordsLineDataSet() {
        wordsSet = new LineDataSet(entries, "Words");
        wordsSet.setColor(colorLightGreen);
        wordsSet.setValueTextColor(primaryTextColor);
        wordsSet.setCircleColor(colorDarkGreen);
        wordsSet.setValueTextSize(17);
        wordsSet.setValueTextColor(primaryTextColor);
        wordsSet.setDrawCircleHole(false);
        wordsSet.setValueFormatter(new MyFormaterInt());
        wordsSet.setDrawVerticalHighlightIndicator(false);
    }

    private void getCardsLineDataSet() {
        cardsSet = new LineDataSet(entries, "Cards");
        cardsSet.setColor(colorOrange);
        cardsSet.setValueTextColor(primaryTextColor);
        cardsSet.setValueTextSize(17);
        cardsSet.setCircleColor(colorDarkOrange);
        cardsSet.setValueTextColor(primaryTextColor);
        cardsSet.setDrawCircleHole(false);
        cardsSet.setValueFormatter(new MyFormaterInt());
        cardsSet.setDrawVerticalHighlightIndicator(false);
    }

    private void getNamesLineDataSet() {
        namesSet = new LineDataSet(entries, "Names");
        namesSet.setColor(colorPurple);
        namesSet.setValueTextColor(primaryTextColor);
        namesSet.setCircleColor(colorDarkPurple);
        namesSet.setValueTextSize(17);
        namesSet.setValueTextColor(primaryTextColor);
        namesSet.setDrawCircleHole(false);
        namesSet.setValueFormatter(new MyFormaterInt());
        namesSet.setDrawVerticalHighlightIndicator(false);
    }


    private boolean getData(int type, int month) {


        String dbdata = helper.getAllDataByType(type, month);
        Log.e("dbdata", dbdata);


        if (!dbdata.equals("null")) {

            entries = new ArrayList<>();
            dataObjects = new ArrayList<>();

            formaterValuesDays = new ArrayList<>();
            formaterValuesMonth = new ArrayList<>();

            dataObjects.clear();
            entries.clear();
//        formaterValuesDays.clear();
            formaterValuesMonth.clear();


            for (int i = 0; i <= 31; i++) {
                formaterValuesDays.add(String.valueOf(i));
            }


            String[] data = dbdata.split("\\n");

            String monthText = null;

            for (String linia : data) {

                Log.e("temp", linia);


                String scoreE = linia.substring(linia.indexOf("ea") + 2, linia.indexOf("ma") - 1);
                String day = linia.substring(linia.indexOf("da") + 2, linia.indexOf("mo") - 1);
                String mo = linia.substring(linia.indexOf("mo") + 2, linia.indexOf("ye") - 1);


                dataObjects.add(new Data(Integer.parseInt(day) - 1, Integer.parseInt(scoreE)));
//            licznikDni++;

//            formaterValuesDays.add(day);

                switch (mo) {
                    case "0":
                        monthText = "Jan";
                        break;
                    case "1":
                        monthText = "Feb";
                        break;
                    case "2":
                        monthText = "Mar";
                        break;
                    case "3":
                        monthText = "Apr";
                        break;
                    case "4":
                        monthText = "May";
                        break;
                    case "5":
                        monthText = "June";
                        break;
                    case "6":
                        monthText = "July";
                        break;
                    case "7":
                        monthText = "Aug";
                        break;
                    case "8":
                        monthText = "Sept";
                        break;
                    case "9":
                        monthText = "Oct";
                        break;
                    case "10":
                        monthText = "Nov";
                        break;
                    case "11":
                        monthText = "Dec";
                        break;
                }

                if (monthText != null) {
                    formaterValuesMonth.add(monthText);
                } else {
                    formaterValuesMonth.add(mo);
                }


            }
            for (Data item : dataObjects) {
                entries.add(new Entry(item.getValueX(), item.getValueY()));

            }

            return true;

        } else {
//            Toast.makeText(this, month + "is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
    }




    class SpinnerClass implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            chart.clear();
            generateData(i);
//            Toast.makeText(Stats.this, "testspinner" + i, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class MyFormaterInt implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return "" + (int) value;
        }


    }

    public class MyCustomXAxisValueFormatter implements IAxisValueFormatter {


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "" + formaterValuesDays.get((int) value + 1);
        }

    }


    class Data {
        private int valueX;
        private int valueY;

        Data(int valueX, int valueY) {
            this.valueX = valueX;
            this.valueY = valueY;
        }

        int getValueX() {
            return valueX;
        }


        int getValueY() {
            return valueY;
        }


    }
}
