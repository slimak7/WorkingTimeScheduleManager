package sk.scheduleManager.Security;

public enum UserRoles {

    DEFAULT(Constants.DEFAULT_VALUE),
    ADMIN(Constants.ADMIN_VALUE);

    public static class Constants {
        public static final String ADMIN_VALUE = "ROLE_Admin";
        public static final String DEFAULT_VALUE = "ROLE_Default";
    }
    private final String role;

    UserRoles(String role) {

        this.role = role;
    }

    public String GetRoleName() {

        return role;
    }
}
