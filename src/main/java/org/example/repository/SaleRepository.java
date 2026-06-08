package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.example.model.Sale;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository {

    private static final String FILE_PATH = "data/sales.json";

    private final Gson gson;

    public SaleRepository() {

        this.gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                                -> context.serialize(src.toString())
                )
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
                                -> LocalDateTime.parse(json.getAsString())
                )
                .setPrettyPrinting()
                .create();
    }

    private List<Sale> loadSales() {

        try {

            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return new ArrayList<>();
            }

            FileReader reader = new FileReader(file);

            Type listType = new TypeToken<List<Sale>>() {}.getType();

            List<Sale> sales = gson.fromJson(reader, listType);

            reader.close();

            return sales != null ? sales : new ArrayList<>();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveSales(List<Sale> sales) {

        try {

            FileWriter writer = new FileWriter(FILE_PATH);

            gson.toJson(sales, writer);

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Sale> findAll() {
        return loadSales();
    }

    public void save(Sale sale) {

        List<Sale> sales = loadSales();

        sales.add(sale);

        saveSales(sales);
    }

    public Sale findById(String id) {

        List<Sale> sales = loadSales();

        for (Sale sale : sales) {

            if (sale.getId().equalsIgnoreCase(id)) {
                return sale;
            }
        }

        return null;
    }

    public boolean delete(String id) {

        List<Sale> sales = loadSales();

        boolean removed = sales.removeIf(
                sale -> sale.getId().equalsIgnoreCase(id)
        );

        if (removed) {
            saveSales(sales);
        }

        return removed;
    }
}
