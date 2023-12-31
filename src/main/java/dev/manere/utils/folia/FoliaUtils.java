package dev.manere.utils.folia;

/**
 * Utility class for working with Folia (PaperMC's region threading system).
 */
public class FoliaUtils {

    /**
     * Checks if the server is running Folia by attempting to load the 
     * RegionizedServer class from the paper-threadedregions module.
     *
     * @return true if Folia is available on the server, false otherwise
     */
    public static boolean hasFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if the server is currently running with Folia enabled.
     *
     * @return true if Folia is enabled on the server, false otherwise  
     */
    public static boolean isFolia() {
        return hasFolia();
    }
}