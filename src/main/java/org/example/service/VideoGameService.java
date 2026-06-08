package org.example.service;

import org.example.model.VideoGame;
import org.example.repository.VideoGameRepository;

import java.util.ArrayList;
import java.util.List;

public class VideoGameService {

    private final VideoGameRepository repository;

    public VideoGameService() {
        this.repository = new VideoGameRepository();
    }

    public void addVideoGame(VideoGame videoGame) throws Exception {

        if (videoGame.getTitle() == null ||
                videoGame.getTitle().trim().isEmpty()) {

            throw new Exception("El título no puede estar vacío");
        }

        if (videoGame.getPrice() <= 0) {
            throw new Exception("El precio debe ser mayor a 0");
        }

        if (videoGame.getStock() < 0) {
            throw new Exception("El stock no puede ser negativo");
        }

        VideoGame existing =
                repository.findByTitle(videoGame.getTitle());

        if (existing != null) {
            throw new Exception("El videojuego ya existe en el catálogo");
        }

        repository.save(videoGame);
    }

    public List<VideoGame> getAllVideoGames() {
        return repository.findAll();
    }

    public VideoGame findByTitle(String title) {

        if (title == null || title.trim().isEmpty()) {
            return null;
        }

        return repository.findByTitle(title);
    }

    public List<VideoGame> findByPlatform(String platform) {

        List<VideoGame> result = new ArrayList<>();

        for (VideoGame game : repository.findAll()) {

            if (game.getPlatform()
                    .equalsIgnoreCase(platform)) {

                result.add(game);
            }
        }

        return result;
    }

    public boolean updateVideoGame(
            String title,
            VideoGame newGame
    ) {

        return repository.update(title, newGame);
    }

    public boolean deleteVideoGame(String title) {

        return repository.delete(title);
    }
}