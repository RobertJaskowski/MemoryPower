package rj.pl.memorypower;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Stats extends Activity {
    @BindView(R.id.chart)
    LineChart chart;

    @BindColor(R.color.colorAccent)
            int colorAccent;
    @BindColor(R.color.colorPrimary)
            int colorPrimary;

    @BindColor(R.color.primaryTextColor)
            int primaryTextColor;

    int[] licz = {3, 2, 4, 1, 12,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);




        List<Entry> entries = new ArrayList<Entry>();

//        for (int i = 0; i < licz.length-1; i+=2) {
//            entries.add(new Entry(licz[i],licz[i+1]));
//        }

        entries.add(new Entry(4,1));
        entries.add(new Entry(5,2));
        entries.add(new Entry(5,4));
        entries.add(new Entry(6,5));
        entries.add(new Entry(7,5));
        entries.add(new Entry(14,7));
        entries.add(new Entry(14,7));
        entries.add(new Entry(14,7));
        entries.add(new Entry(14,7));




        LineDataSet dataSet = new LineDataSet(entries,"Label");
        dataSet.setColor(colorAccent);
        dataSet.setValueTextColor(primaryTextColor);
        dataSet.setCircleColor(colorPrimary);
        dataSet.setValueTextColor(primaryTextColor);
        dataSet.setValueFormatter(new MyFormaterInt());



        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);


        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(primaryTextColor);
        xAxis.setTextSize(25);
        xAxis.setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis yAxis =chart.getAxisLeft();
        yAxis.setTextColor(primaryTextColor);
        yAxis.setTextSize(25);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(50);

        chart.getAxisRight().setEnabled(false);

        chart.setScaleYEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);



        chart.invalidate();


    }

    class MyFormaterInt implements IValueFormatter{

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ""+ (int)value;
        }
    }


}
