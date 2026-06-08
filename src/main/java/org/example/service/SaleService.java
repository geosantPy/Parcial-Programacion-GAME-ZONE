package org.example.service;

import org.example.model.Sale;
import org.example.model.VideoGame;
import org.example.repository.SaleRepository;
import org.example.repository.VideoGameRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SaleService {

    private final SaleRepository saleRepository;
    private final VideoGameRepository videoGameRepository;

    public SaleService() {
        this.saleRepository = new SaleRepository();
        this.videoGameRepository = new VideoGameRepository();
    }

    public double sellVideoGame(String title, int quantity) throws Exception {

        VideoGame game = videoGameRepository.findByTitle(title);

        if (game == null) {
            throw new Exception("El videojuego no existe en el catálogo");
        }

        if (quantity <= 0) {
            throw new Exception("La cantidad debe ser mayor a 0");
        }

        if (game.getStock() < quantity) {
            throw new Exception("Stock insuficiente");
        }

        double totalSale = game.calculateFinalPrice() * quantity;

        game.setStock(game.getStock() - quantity);

        videoGameRepository.update(title, game);

        Sale sale = new Sale(UUID.randomUUID().toString(), game.getTitle(), quantity, game.calculateFinalPrice(), totalSale, LocalDateTime.now());

        saleRepository.save(sale);

        return totalSale;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale findSaleById(String id) {
        return saleRepository.findById(id);
    }

    public boolean deleteSale(String id) {
        return saleRepository.delete(id);
    }
}
