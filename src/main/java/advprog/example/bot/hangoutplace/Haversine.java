package advprog.example.bot.hangoutplace;

/**
 * Jason Winn
 * http://jasonwinn.org
 * Created July 10, 2013
 * <p></p>
 * Description: Small class that provides approximate distance between
 * two points using the Haversine formula.
 * <p></p>
 * Call in a static context:
 * Haversine.distance(47.6788206, -122.3271205,
 *                    47.6788206, -122.5271205)
 * --> 14.973190481586224 [km]
 * <p></p>
 */

public class Haversine {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double deLat  = Math.toRadians((endLat - startLat));
        double deLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(deLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(deLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}