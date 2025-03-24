package ge.ibsu.demo.entities.enums;

public enum Permission {
    CUSTOMER_ADD("customer:add"), CUSTOMER_READ("customer:read");

    private String keyword;

    Permission(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
