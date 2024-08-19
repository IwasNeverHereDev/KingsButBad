package kingsbutbad.kingsbutbad.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String formatMoney(double bal){
        if (Double.isInfinite(bal)) {
            return bal > 0.0 ? "Infinity" : "-Infinity";
        } else {
            boolean isNegative = bal < 0.0;
            bal = Math.abs(bal);
            if (bal < 1000.0) {
                return (isNegative ? "-" : "") + NumberFormat.getCurrencyInstance(Locale.US).format(bal);
            } else {
                String[] suffixes = new String[]{"", "K", "M", "B", "T", "P", "E", "Z", "Y"};
                int magnitude = Math.min((int)Math.log10(bal) / 3, suffixes.length - 1);
                double scaledValue = bal / Math.pow(1000.0, (double)magnitude);
                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                formatter.setMaximumFractionDigits(1);
                return (isNegative ? "-" : "") + formatter.format(scaledValue) + suffixes[magnitude];
            }
        }
    }
}
