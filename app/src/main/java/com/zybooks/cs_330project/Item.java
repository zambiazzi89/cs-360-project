package com.zybooks.cs_330project;

public class Item {
    private long itemId;
    private String username;
    private String itemTitle;
    private String itemQuantity;
    private String itemQuantityUnits;
    private boolean expanded;

    public Item(long id, String username, String title, String quantity, String quantityUnits) {
        this.itemId = id;
        this.username = username;
        this.itemTitle = title;
        this.itemQuantity = quantity;
        this.itemQuantityUnits = quantityUnits;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public long getId() {
        return itemId;
    }
    public void setId(int id) {
        this.itemId = id;
    }

    public String getTitle() {
        return itemTitle;
    }
    public void setTitle(String title) {
        this.itemTitle = title;
    }

    public String getQuantity() {
        return itemQuantity;
    }
    public void setQuantity(String quantity) {
        this.itemQuantity = quantity;
    }

    public String getQuantityUnits() {
        return itemQuantityUnits;
    }
    public void setQuantityUnits(String quantityUnits) {
        this.itemQuantityUnits = quantityUnits;
    }
}