package net.infinitygrid.mercury.pojo;

public class GsonPermissionGroup {

    /*
        SUPER_ADMIN("rank.superadmin", 0xe35780),
        ADMIN("rank.admin", 0xe35780),
        DEVELOPER("rank.developer", 0x47d48d),
        MODERATOR("rank.moderator", 0xf4c990),
        DEFAULT(null, 0x2c6f98);
    */

    public String permissionNode = null;
    public String hexColor = "2c6f98";
    public String scoreboardTeamName = "default";
    public String shortName = null;

    public int getHexAsInteger() {
        return Integer.parseInt(hexColor, 16);
    }

}
