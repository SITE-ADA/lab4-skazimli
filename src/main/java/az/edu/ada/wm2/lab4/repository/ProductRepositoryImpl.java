package az.edu.ada.wm2.lab4.repository;
import az.edu.ada.wm2.lab4.model.Product;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final Map<UUID, Product> storage = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product = new Product(
                    UUID.randomUUID(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getExpirationDate()
            );
        }

        storage.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }
}

