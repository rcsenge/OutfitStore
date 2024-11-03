package org.example.model;
import java.util.List;
import java.util.Map;

public class Product {
    private long id;
    private String name;
    private String description;
    private ItemColor itemColor;
    private Map<Size, Integer> stockBySize;
    private String material;
    private Season season;
    private float price;
    private List<Occasion> occasions;
    private String URL;
    private String pictureURL;

    public Product() {
    }

    public Product(String name, String description, ItemColor itemColor, Map<Size, Integer> stockBySize, String material, Season season, float price, List<Occasion> occasions, String URL, String pictureURL) {
        this.name = name;
        this.description = description;
        this.itemColor = itemColor;
        this.stockBySize = stockBySize;
        this.material = material;
        this.season = season;
        this.price = price;
        this.occasions = occasions;
        this.URL = URL;
        this.pictureURL = pictureURL;
    }

    public Map<Size, Integer> getStockBySize() {
        return stockBySize;
    }

    public void setStockBySize(Map<Size, Integer> stockBySize) {
        this.stockBySize = stockBySize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemColor getItemColor() {
        return itemColor;
    }

    public void setItemColor(ItemColor itemColor) {
        this.itemColor = itemColor;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Occasion> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<Occasion> occasions) {
        this.occasions = occasions;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", itemColor=" + itemColor +
                ", stockBySize=" + stockBySize +
                ", material='" + material + '\'' +
                ", season=" + season +
                ", price=" + price +
                ", occasions=" + occasions +
                ", URL='" + URL + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                '}';
    }
}
