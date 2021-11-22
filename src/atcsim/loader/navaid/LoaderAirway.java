package atcsim.loader.navaid;

import atcsim.datatype.Altitude;
import atcsim.datatype.CoordinateWorld3D;
import atcsim.datatype.Latitude;
import atcsim.datatype.Longitude;
import atcsim.graphics.view.navigation.OverlayNavigation;
import atcsim.loader.A_Loader;
import atcsim.world.navigation.A_ComponentNavaid;
import atcsim.world.navigation.ComponentNavaidAirway;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class LoaderAirway extends A_Loader {

    /**
     * Creates an airway loader
     * @param navaids - the shared collection of navaids
     * @param overlay - the navigation overlay that holds the navaids to be loaded
     */
    public LoaderAirway(Map<String, A_ComponentNavaid<?>> navaids, OverlayNavigation overlay) {
        super(navaids, overlay);
    }

    public void load(Scanner scanner) throws IOException {

        String[] split;

        while(scanner.hasNextLine()) {
            split = scanner.nextLine().split(",");

            for(int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }

            String id = split[0];
            String type = split[1];

            if(type.equalsIgnoreCase("CC")) {

                Latitude latitude1 = readLatitude(split[2], split[3], split[4]);
                Longitude longitude1 = readLongitude(split[5], split[6], split[7]);
                Altitude altitude1 = readAltitude(split[8]);

                CoordinateWorld3D position1 = new CoordinateWorld3D(latitude1, longitude1, altitude1);

                Latitude latitude2 = readLatitude(split[9], split[10], split[11]);
                Longitude longitude2 = readLongitude(split[12], split[13], split[14]);
                Altitude altitude2 = readAltitude(split[15]);

                CoordinateWorld3D position2 = new CoordinateWorld3D(latitude2, longitude2, altitude2);

                this.overlay.addNavaid(new ComponentNavaidAirway(id, position1, position2));
            }

            if(type.equalsIgnoreCase("NC")) {
                String navId1 = split[2];


                Latitude latitude = readLatitude(split[3], split[4], split[5]);
                Longitude longitude = readLongitude(split[6], split[7], split[8]);
                Altitude altitude = readAltitude(split[9]);

                CoordinateWorld3D position = new CoordinateWorld3D(latitude, longitude, altitude);

                this.overlay.addNavaid(new ComponentNavaidAirway(id, navaids.get(navId1), position));
            }
            if(type.equalsIgnoreCase("NN")) {
                String navId1 = split[2];
                String navId2 = split[3];

                this.overlay.addNavaid(new ComponentNavaidAirway(id, navaids.get(navId1), navaids.get(navId2)));
            }



        }
    }
}
