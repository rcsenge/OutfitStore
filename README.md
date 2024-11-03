# Clothing Storage Web Application

This web application allows users to store and manage clothing items without a database. The frontend is built using JSP, providing an intuitive user interface.

## Features

### Frontend Pages

1. **Main Menu**
   - A central page from which users can navigate to all other pages.

2. **Add Clothing Item**
   - Users can input:
     - **Name**
     - **Size**
     - **Color**
     - **Material**
     - **Price**
     - **Recommended Seasons/Occasions**
     - **URL** (if available) for the clothing item
     - **Image URL** (if available) for the clothing item's image
   - **Extra Feature**: Recommended Pairings
     - Users can add previously registered clothing items by name using a popup modal that displays images and names. Pairing confirmation is achieved with an "OK" button.

3. **Show Clothing Item**
   - Displays all properties of a selected clothing item.
   - Shows the clothing image directly from the URL, without displaying the URL itself.
   - Contains a "Link" button that directs users to the clothing item's URL.
   - **Extra Feature**: Recommended Pairings
     - Clicking this button opens a modal with images and names of paired clothing items, allowing easy navigation to their respective pages.

4. **List All Clothing Items**
   - Users can view a complete list of all registered clothing items, displayed in a clear and organized manner.

5. **Delete Clothing Item**
   - Users can remove a clothing item by entering its ID, with a confirmation step to prevent accidental deletions.

6. **Modify Clothing Item**
   - Allows users to change any property of a clothing item except for its ID.

### Backend

1. **Servlets**
   - **Outfit**: A POJO modeling the clothing item.
   - **OutfitService (or OutfitManager)**: This service layer encapsulates the business logic and connects the servlets with the OutfitStore.
   - **OutfitStore**: Manages the clothing items (Outfits), generating unique IDs for each item upon creation and retrieving outfits by ID. Additional methods can be added as needed.

2. **Database Integration**
   - A database and the necessary tables have been created.
   - A DAO layer (OutfitDao) is implemented using JDBC to facilitate database operations.
   - The OutfitStore has been replaced with the DAO layer, which is accessed only through the OutfitService (or OutfitManager).
