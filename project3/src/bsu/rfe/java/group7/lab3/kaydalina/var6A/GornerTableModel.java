package bsu.rfe.java.group7.lab3.kaydalina.var6A;


import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GornerTableModel extends AbstractTableModel{

    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }

    public int getRowCount() {
        // Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;

        double result = coefficients[0];
        for (int i = 1; i < coefficients.length; ++i)
            result = result * x + coefficients[i];

        switch (col) {
            // Если запрашивается значение 1-го столбца, то это X
            case 0: {
                return x;
            }
            // Если запрашивается значение 2-го столбца, то это значение
            // многочлена
            case 1: {
                result=round(result, 2);
                return result;
            }
            // Если запрашивается значение 3-го столбца, то проверяем близко ли значение многочлена к целому
            case 2:
                result=round(result, 2);

                int full = (int) result;
                double drob = Math.abs(result - full);
                if(drob >= 0 && drob <= 0.1 || drob >= 0.9 && drob < 1)
                    return true;
                else
                    return false;

            default:
                return 0.0;
        }
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена";
// Название 3-го столбца
            case 2:
                return "Близко к целому";
            default:
                return "";
        }
    }
    public Class<?> getColumnClass(int col) {
// И в 1-ом и во 2-ом столбце находятся значения типа Double
        if (col==2)
            return Boolean.class;
        else return Double.class;

    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}