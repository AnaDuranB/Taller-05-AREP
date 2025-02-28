package arep.taller5.propertymanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property propertyDetails) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (propertyDetails.getAddress() != null) {
            existingProperty.setAddress(propertyDetails.getAddress());
        }
        if (propertyDetails.getPrice() != null) {
            existingProperty.setPrice(propertyDetails.getPrice());
        }
        if (propertyDetails.getSize() != null) {
            existingProperty.setSize(propertyDetails.getSize());
        }
        if (propertyDetails.getDescription() != null) {
            existingProperty.setDescription(propertyDetails.getDescription());
        }

        return propertyRepository.save(existingProperty);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}