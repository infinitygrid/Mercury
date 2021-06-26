package net.infinitygrid.mercury.pojo;

public class GsonPermissionGroup {

    public String permissionNode = null;
    public String hexColor = "2c6f98";
    public String scoreboardTeamName = "default";
    public String shortName = null;

    public int getHexAsInteger() {
        return Integer.parseInt(hexColor, 16);
    }

}
