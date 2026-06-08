package org.example.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.model.DigitalVideoGame;
import org.example.model.PhysicalVideoGame;
import org.example.model.VideoGame;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VideoGameRepository {

    private static final String FILE_PATH = "data/videoGames.json";

    private final Gson gson;

    public VideoGameRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    private List<VideoGame> loadGames() {

        try {

            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return new ArrayList<>();
            }

            JsonArray array = JsonParser.parseReader(
                    new FileReader(file)
            ).getAsJsonArray();

            List<VideoGame> games = new ArrayList<>();

            for (JsonElement element : array) {

                JsonObject obj = element.getAsJsonObject();

                String type = obj.get("type").getAsString();

                if (type.equals("digital")) {

                    DigitalVideoGame game =
                            gson.fromJson(
                                    obj,
                                    DigitalVideoGame.class
                            );

                    games.add(game);

                } else if (type.equals("physical")) {

                    PhysicalVideoGame game =
                            gson.fromJson(
                                    obj,
                                    PhysicalVideoGame.class
                            );

                    games.add(game);
                }
            }

            return games;

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    private void saveGames(List<VideoGame> games) {

        try {

            FileWriter writer =
                    new FileWriter(FILE_PATH);

            gson.toJson(games, writer);

            writer.flush();
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<VideoGame> findAll() {
        return loadGames();
    }

    public VideoGame findByTitle(String title) {

        for (VideoGame game : loadGames()) {

            if (game.getTitle()
                    .equalsIgnoreCase(title)) {

                return game;
            }
        }

        return null;
    }

    public void save(VideoGame videoGame) {

        List<VideoGame> games = loadGames();

        games.add(videoGame);

        saveGames(games);
    }

    public boolean update(
            String title,
            VideoGame newGame
    ) {

        List<VideoGame> games = loadGames();

        for (int i = 0; i < games.size(); i++) {

            if (games.get(i)
                    .getTitle()
                    .equalsIgnoreCase(title)) {

                games.set(i, newGame);

                saveGames(games);

                return true;
            }
        }

        return false;
    }

    public boolean delete(String title) {

        List<VideoGame> games = loadGames();

        boolean removed =
                games.removeIf(
                        game ->
                                game.getTitle()
                                        .equalsIgnoreCase(title)
                );

        if (removed) {
            saveGames(games);
        }

        return removed;
    }
}