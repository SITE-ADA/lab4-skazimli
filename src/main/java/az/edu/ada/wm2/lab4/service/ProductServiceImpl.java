package az.edu.ada.wm2.lab4.repository;
import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getId() == null) {
            product = new Product(
                    UUID.randomUUID(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getExpirationDate()
            );
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        UUID id = product.getId();
        if (id == null || !productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsExpiringBefore(LocalDate date) {
        return productRepository.findAll().stream()
                .filter(p ->
                        p.getExpirationDate() != null &&
                                p.getExpirationDate().isBefore(date)
                ).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByPriceRange(double min, double max) {
        return productRepository.findAll().stream()
                .filter(p ->
                        p.getPrice() != null && p.getPrice().doubleValue() >= min &&
                                p.getPrice().doubleValue() <= max
                ).collect(Collectors.toList());
    }
}