package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.util.List;

public class PlotGraph {
    public static void plotPlayerHeadshotGraph(List<String> playerNames, List<String> headshots) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < playerNames.size(); i++) {
            var headshot = Double.parseDouble(headshots.get(i).replace(",", ""));
            dataset.addValue(headshot, "Headshots", playerNames.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 5 Players com Mais Headshots",
                "Jogador",
                "Número de Headshots",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Headshots");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotPlayerDeathsGraph(List<String> playerNames, List<String> deaths) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < playerNames.size(); i++) {
            var kill = Double.parseDouble(deaths.get(i).replace(",", ""));
            dataset.addValue(kill, "Deaths", playerNames.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 5 Players com Mais Mortes",
                "Jogador",
                "Número de Mortes",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Mortes");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotPrimaryWeaponWinrateGraph(List<String> weapons, List<String> winrates) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < weapons.size(); i++) {
            var winrate = Double.parseDouble(winrates.get(i).replace(",", ""));
            dataset.addValue(winrate, "Winrate", weapons.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 5 armas com escolha primária por media de vitoria",
                "Arma",
                "Taxa de vitória",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Winrate por Arma Primaria");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotPlayersWithMostWinrate(List<String> playerNames, List<Double> winrates) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < playerNames.size(); i++) {
            dataset.addValue(winrates.get(i), "Winrate", playerNames.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 players com maior taxa de vitória com pelo menos 10 vitórias",
                "Jogador",
                "Taxa de vitória",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Winrate por Jogador");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotPrimaryWeaponChoiceCount(List<String> weapons, List<String> total) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < weapons.size(); i++) {
            dataset.addValue(Integer.parseInt(total.get(i)), "Total", weapons.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 armas com escolha primária por total de players",
                "Arma",
                "Total de escolhas por players",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Escolha de Armas Primarias por Total de Players");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotPrimarAgentChoiceCount(List<String> agents, List<String> total) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < agents.size(); i++) {
            dataset.addValue(Integer.parseInt(total.get(i)), "Total", agents.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 agentes com escolha primária por total de players",
                "Agente",
                "Total de escolhas por players",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Escolha de Agente Primários por Total de Players");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void plotTotalPlayersPerRankCount(List<String> rankings, List<String> total) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < rankings.size(); i++) {
            dataset.addValue(Integer.parseInt(total.get(i)), "Total", rankings.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Quantidade total de players por ranking",
                "Ranking",
                "Total de players",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico Total de Players por Ranking");
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
