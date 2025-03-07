package arep.taller5.propertymanagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAddressContainingIgnoreCase(String address);
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Property> findBySizeBetween(Double minSize, Double maxSize);
}