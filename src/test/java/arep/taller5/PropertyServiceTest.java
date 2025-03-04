package arep.taller5;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import arep.taller5.propertymanagement.Property;
import arep.taller5.propertymanagement.PropertyRepository;
import arep.taller5.propertymanagement.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;

    @BeforeEach
    void setUp() {
        property = new Property();
        property.setId(1L);
        property.setAddress("123 Main St");
        property.setPrice(250000.0);
        property.setSize(120.0);
        property.setDescription("Beautiful house");
    }

    @Test
    void testGetAllProperties() {
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property));

        List<Property> properties = propertyService.getAllProperties();
        assertFalse(properties.isEmpty());
        assertEquals(1, properties.size());
    }

    @Test
    void testGetPropertyById() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        Optional<Property> found = propertyService.getPropertyById(1L);
        assertTrue(found.isPresent());
        assertEquals(property.getId(), found.get().getId());
    }

    @Test
    void testCreateProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property saved = propertyService.createProperty(property);
        assertNotNull(saved);
        assertEquals(property.getAddress(), saved.getAddress());
    }

    @Test
    void testUpdateProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property updated = propertyService.updateProperty(1L, property);
        assertNotNull(updated);
        assertEquals(property.getAddress(), updated.getAddress());
    }

    @Test
    void testDeleteProperty() {
        doNothing().when(propertyRepository).deleteById(1L);
        assertDoesNotThrow(() -> propertyService.deleteProperty(1L));
    }
}