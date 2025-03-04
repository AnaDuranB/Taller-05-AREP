package arep.taller5;

import arep.taller5.propertymanagement.Property;
import arep.taller5.propertymanagement.PropertyController;
import arep.taller5.propertymanagement.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

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
        when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property));

        List<Property> properties = propertyController.getAllProperties();
        assertFalse(properties.isEmpty());
        assertEquals(1, properties.size());
    }

    @Test
    void testGetPropertyById() {
        when(propertyService.getPropertyById(1L)).thenReturn(Optional.of(property));

        ResponseEntity<Property> response = propertyController.getPropertyById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(property.getId(), response.getBody().getId());
    }

    @Test
    void testCreateProperty() {
        when(propertyService.createProperty(any(Property.class))).thenReturn(property);

        Property created = propertyController.createProperty(property);
        assertNotNull(created);
        assertEquals(property.getAddress(), created.getAddress());
    }

    @Test
    void testUpdateProperty() {
        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(property);

        ResponseEntity<Property> response = propertyController.updateProperty(1L, property);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(property.getAddress(), response.getBody().getAddress());
    }

    @Test
    void testDeleteProperty() {
        doNothing().when(propertyService).deleteProperty(1L);
        ResponseEntity<Void> response = propertyController.deleteProperty(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
