package com.salononline.salonbusiness.Parse;

public class QueryData {
    private String shopUuid,query;

    public QueryData() {
    }

    public QueryData(String shopUuid, String query) {
        this.shopUuid = shopUuid;
        this.query = query;
    }

    public String getShopUuid() {
        return shopUuid;
    }

    public void setShopUuid(String shopUuid) {
        this.shopUuid = shopUuid;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
