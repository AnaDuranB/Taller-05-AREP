const API_URL = "/api/properties";
function showSuccess(message) {
    Toastify({
        text: message,
        duration: 3000,
        close: true,
        gravity: "top",
        position: "right",
        backgroundColor: "green",
    }).showToast();
}

function showError(message) {
    Toastify({
        text: message,
        duration: 3000,
        close: true,
        gravity: "top",
        position: "right",
        backgroundColor: "red",
    }).showToast();
}

async function loadProperties() {
    try {
        const response = await fetch(API_URL);
        const properties = await response.json();
        const propertyList = document.getElementById("propertyList");
        propertyList.innerHTML = "";

        properties.forEach(property => {
            const li = document.createElement("li");
            li.innerHTML = `
                <strong>${property.address}</strong><br>
                Price: $${property.price}<br>
                Size: ${property.size} sqft<br>
                Description: ${property.description}<br>
                <button onclick="updateProperty(${property.id})">Update</button>
                <button onclick="deleteProperty(${property.id})">Delete</button>
            `;
            propertyList.appendChild(li);
        });
    } catch (error) {
        console.error("Error loading properties:", error);
    }
}

document.getElementById("propertyForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const property = {
        address: document.getElementById("address").value,
        price: parseFloat(document.getElementById("price").value),
        size: parseFloat(document.getElementById("size").value),
        description: document.getElementById("description").value
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(property)
        });
        if (response.ok) {
            showSuccess("Property added successfully!");
            loadProperties();
        }
    } catch (error) {
        showError("Failed to add property.");
        console.error("Error adding property:", error);
    }
});

async function updateProperty(id) {
    const newAddress = prompt("Enter new address:");
    const newPrice = parseFloat(prompt("Enter new price:"));
    const newSize = parseFloat(prompt("Enter new size:"));
    const newDescription = prompt("Enter new description:");

    const updatedProperty = {
        address: newAddress,
        price: newPrice,
        size: newSize,
        description: newDescription
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedProperty)
        });
        if (response.ok) {
            showSuccess("Property updated successfully!");
            loadProperties();
        }
    } catch (error) {
        showError("Failed to update property.");
        console.error("Error updating property:", error);
    }
}

async function deleteProperty(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });
        if (response.ok) {
            showSuccess("Property deleted successfully!");
            loadProperties();
        }
    } catch (error) {
        showError("Failed to delete property.");
        console.error("Error deleting property:", error);
    }
}

loadProperties();