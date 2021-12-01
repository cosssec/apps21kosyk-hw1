package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private double sum;

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }

    public double sum(){
        checkIfEmpty();
        double sum = 0;
        int i;
        for (i = 0; i < temperatureSeries.length; i++) {
            sum += temperatureSeries[i];}
        this.sum = sum;
        return this.sum;
    }
    public void checkIfEmpty(){
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("Empty array");
        }
    }
    public double average() {
        checkIfEmpty();
        sum();
        return this.sum / temperatureSeries.length;
    }

    public double deviation() {
        checkIfEmpty();
        if (temperatureSeries.length == 1) {
            return 0;
        }
        double mean = this.sum / temperatureSeries.length;

        double sd = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            sd += (temperatureSeries[i] - mean) * (temperatureSeries[i] - mean);}

        return Math.sqrt(sd / temperatureSeries.length);
    }

    public double min() {
        double max_bound = -273.0;
        return findTempClosestToValue(max_bound);
    }

    public double max() {
        checkIfEmpty();
        return findTempClosestToValue(Double.POSITIVE_INFINITY);
    }

    public double findTempClosestToZero() {
        checkIfEmpty();
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkIfEmpty();
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        double diff = Double.POSITIVE_INFINITY;
        double closestTemp = temperatureSeries[0];

        for (double temp : temperatureSeries) {
            double difference = Math.abs(temp - tempValue);
            if (difference < diff) {
                diff = difference;
                closestTemp = temp;
            }
            else if(difference == diff){
                closestTemp = Math.max(closestTemp, temp);
            }
        }
        return closestTemp;
    }

    public int findLengthLess(double tempValue) {
        checkIfEmpty();
        int length = 0;

        for (double temp : temperatureSeries) {
            if(temp<tempValue){
                length += 1;
            }
        }
        return length;
    }


    public double[] findTempsLessThen(double tempValue) {
        int len = findLengthLess(tempValue);
        double[] res = new double[len];
        int i = 0;
        for (double temp : temperatureSeries) {
            if (temp < tempValue){
                res[i] = temp;
                i += 1;
            }
        }
        return res;
    }


    public double[] findTempsGreaterThen(double tempValue) {
        checkIfEmpty();
        int len = temperatureSeries.length - findLengthLess(tempValue);
        double[] res = new double[len];
        int i = 0;
        for (double temp:temperatureSeries) {
            if (temp>=tempValue){
                res[i] = temp;
                i += 1;
            }
        }
        return res;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkIfEmpty();
        double average = this.average();
        double deviation = this.deviation();
        double minimun = this.min();
        double maximun = this.max();

        return new TempSummaryStatistics(average, deviation, minimun, maximun);

    }

    public double addTemps(double... temps) {
        checkIfEmpty();
        int len = temperatureSeries.length + temps.length;
        double[] res = new double [len];
        int i = 0;
        for (double el:temperatureSeries) {
            res[i] = el;
            i += 1;
        }
        for (double temp:temps) {
            if (temp<=-273){
                throw new InputMismatchException("Temperature less than -273");
            }
            res[i] = temp;
            i += 1;
        }
        temperatureSeries = res;
        sum();
        return this.sum;
    }
}
