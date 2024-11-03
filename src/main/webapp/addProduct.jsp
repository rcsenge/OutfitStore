<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>The Style Studio</title>
    <link rel="stylesheet" href="style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
          rel="stylesheet">
</head>
<body>

<header class="header">
    <div class="logo">
        <h1>The Style Studio</h1>
    </div>
</header>

<form class="form-container" action="addProduct" method="post">


    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required>

    <label for="color">Color:</label>
    <select id="color" name="color" required>
        <option value="RED">Red</option>
        <option value="BLUE">Blue</option>
        <option value="GREEN">Green</option>
        <option value="YELLOW">Yellow</option>
        <option value="ORANGE">Orange</option>
        <option value="Purple">Purple</option>
        <option value="PINK">Pink</option>
        <option value="BLACK">Black</option>
        <option value="WHITE">White</option>
        <option value="GRAY">Gray</option>
        <option value="BROWN">Brown</option>
        <option value="TEAL">Teal</option>
        <option value="NAVY">Navy</option>
        <option value="MAROON">Maroon</option>
        <option value="BEIGE">Beige</option>
        <option value="MULTI">Multi</option>
    </select>

    <label>Sizes and Stock:</label>
    <table id="sizes">
        <tr>
            <th>Size</th>
            <th>In Stock</th>
        </tr>
        <tr>
            <td><label for="XXS_stock">XXS</label></td>
            <td><input type="number" id="XXS_stock" name="stock[XXS]" min="0" placeholder="Stock" required></td>
        </tr>
        <tr>
            <td><label for="XS_stock">XS</label></td>
            <td><input type="number" id="XS_stock" name="stock[XS]" min="0" placeholder="Stock" required></td>
        </tr>
        <tr>
            <td><label for="S_stock">S</label></td>
            <td><input type="number" id="S_stock" name="stock[S]" min="0" placeholder="Stock" required></td>
        </tr>
        <tr>
            <td><label for="M_stock">M</label></td>
            <td><input type="number" id="M_stock" name="stock[M]" min="0" placeholder="Stock" required></td>
        </tr>
        <tr>
            <td><label for="L_stock">L</label></td>
            <td><input type="number" id="L_stock" name="stock[L]" min="0" placeholder="Stock" required></td>
        </tr>
        <tr>
            <td><label for="XL_stock">XL</label></td>
            <td><input type="number" id="XL_stock" name="stock[XL]" min="0" placeholder="Stock" required></td>
        </tr>
    </table>

    <label for="material">Material:</label>
    <input type="text" id="material" name="material" required>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required>

    <label for="season">Season:</label>
    <select id="season" name="season" required>
        <option value="Spring">Spring</option>
        <option value="Summer">Summer</option>
        <option value="Autumn">Autumn</option>
        <option value="Winter">Winter</option>
    </select>

    <label for="occasions">Occasions:</label>
    <select id="occasions" name="occasions" multiple required>
        <option value="CASUAL">Casual</option>
        <option value="FORMAL">Formal</option>
        <option value="SEMIFORMAL">Semi-Formal</option>
        <option value="BUSINESS">Business</option>
        <option value="COCKTAIL">Cocktail</option>
        <option value="EVENING">Evening</option>
        <option value="PARTY">Party</option>
        <option value="WEDDING">Wedding</option>
        <option value="BEACH">Beach</option>
        <option value="TRAVEL">Travel</option>
        <option value="SPORTS">Sports</option>
        <option value="LOUNGE">Lounge</option>
        <option value="WORK">Work</option>
        <option value="WEEKEND">Weekend</option>
        <option value="HOLIDAY">Holiday</option>
        <option value="DATE">Date</option>
        <option value="FESTIVAL">Festival</option>
        <option value="OUTDOOR">Outdoor</option>
        <option value="ATHLETIC">Athletic</option>
    </select>

    <label for="clothingURL">Clothing URL:</label>
    <input type="url" id="clothingURL" name="clothingURL" required>

    <!-- Hidden file input -->
    <input type="file" id="imageUpload" name="imageUpload" accept="image/*" onchange="previewImage(event)" required>

    <!-- Custom styled button -->
    <button type="button" class="button-69" onclick="document.getElementById('imageUpload').click()">Upload Image</button>

    <!-- Image preview box -->
    <div id="imagePreviewContainer">
        <img id="imagePreview" src="#" alt="Image Preview"/>
    </div>


    <button type="submit">Add Product</button>

</form>
<script>
    function previewImage(event) {
        const imagePreview = document.getElementById('imagePreview');
        const imageFile = event.target.files[0];

        if (imageFile) {
            const reader = new FileReader();

            reader.onload = function() {
                imagePreview.src = reader.result;
                imagePreview.style.display = 'block';
            };

            reader.readAsDataURL(imageFile);
        } else {
            imagePreview.src = '#';
            imagePreview.style.display = 'none';
        }
    }
</script>
</body>
</html>
