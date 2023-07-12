package com.company;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;




    public class BarChart {

        private CategoryDataset createDataset(DefaultCategoryDataset categoryDataset) {

            return categoryDataset;
        }

        private JFreeChart createChart(CategoryDataset dataset) {
            CategoryAxis categoryAxis = new CategoryAxis("");
            ValueAxis valueAxis = new NumberAxis("");
            valueAxis.setVisible(false);
            BarRenderer renderer = new BarRenderer() {

                @Override
                public Paint getItemPaint(int row, int column) {
                    switch (column) {
                        case 0:
                            return Color.red;
                        case 1:
                            return Color.yellow;
                        case 2:
                            return Color.blue;
                        case 3:
                            return Color.orange;
                        case 4:
                            return Color.gray;
                        case 5:
                            return Color.green.darker();
                        default:
                            return Color.red;
                    }
                }
            };
            renderer.setDrawBarOutline(false);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                    ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
            renderer.setBaseItemLabelsVisible(Boolean.TRUE);
            renderer.setBarPainter(new StandardBarPainter());
            CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);
            JFreeChart chart = new JFreeChart("Charts", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
            chart.setBackgroundPaint(Color.white);
            return chart;
        }

        void display(DefaultCategoryDataset categoryDataset) {
            JFrame f = new JFrame("BarChart");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new ChartPanel(createChart(createDataset(categoryDataset))));
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }

    }

