package ui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;

public class AnalyticsFrame extends JFrame {
    public AnalyticsFrame() {
        super("Analytics");
        setSize(800,600); setLocationRelativeTo(null); setLayout(new BorderLayout());
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5, "Appointments", "Dr. Ravi");
        dataset.addValue(2, "Appointments", "Dr. Sharma");
        JFreeChart chart = ChartFactory.createBarChart("Appointments per Doctor","Doctor","Count",dataset);
        add(new ChartPanel(chart), BorderLayout.CENTER);
    }
}
