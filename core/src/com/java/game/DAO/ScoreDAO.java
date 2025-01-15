package com.java.game.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    private static final String FILE_NAME = "dados.csv";
    private List<Integer> scores;

    public ScoreDAO() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addScore(int score) {
        scores.add(score);
        saveScores();
    }

    public List<Integer> getScores() {
        return scores;
    }

    private void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado. Iniciando novo arquivo de pontuação.");
        }
    }

    private void saveScores() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Integer score : scores) {
                writer.write("Pontuacao: "+score + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de pontuação.");
        }
    }
}

