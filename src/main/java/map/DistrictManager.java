package main.java.map;

import main.java.exceptions.PlaceableBelongsToNoSectorException;
import main.java.exceptions.SectorOverlappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * The DistrictManager is a class to manage n Districts, so that not all placeble objects have to be consulted, just the
 * ones of the sector that is returned by the DistrictManager.
 */
public class DistrictManager {

    private ArrayList<District> mapDistircts = new ArrayList<>();

    /**
     * the constructor of the SectorManger takes a list of sectors and checks that they dont overlap.
     *
     * @param districts a list of sectors.
     * @throws SectorOverlappingException sectors should not overlap each other to guarantee that the intersection logic
     *                                    works as intended.
     */
    public DistrictManager(List<District> districts) throws SectorOverlappingException {
        if (districts.isEmpty()) {
            throw new IllegalArgumentException("districts cant be empty");
        }
        // TODO: Sectoren besser abtrennen damit sie sich nciht mehr überschneiden und dann wieder einkommentieren
        // check that sectors not overlap.
        /*
        for (District district : districts) {
            for (District other_district : districts) {
                if (district.getSector().intersects(other_district.getSector()))
                    throw new SectorOverlappingException("Sectors cannot Overlap each other");
            }
        }
        */
        // sector manager has all sectors.
        this.mapDistircts.addAll(districts);
    }

    /**
     * takes a placeable and assign it to a district.
     *
     * @param placeable placeable which will be assigned to a sector.
     */
    public void assignDistrict(Placeable placeable) {
        for (District district : this.mapDistircts) {
            if (district.getSector().contains(placeable)) {
                district.getSector().addPlaceable(placeable);
                // placeble is fully contained by a sector. No further checkin necessary.
                break;
            } else {
                if (placeable.intersects(district.getSector())) {
                    district.getSector().addPlaceable(placeable);
                    // No need to break/end the loop. A placeble can be part of multiple sectors.
                }
            }
        }
    }

    /**
     * returns a list of districts to which a already placed placeble belongs. This method is NOT for new placebles.
     *
     * @param placeble placeble from which the lookup is done.
     * @return a list of districts, normally just holding one sector but it is possible that a placeble is part of more
     * than one district.
     * @throws PlaceableBelongsToNoSectorException a placeble should always belong to a sector.
     */
    public List<District> belongsToDistricts(Placeable placeble) throws PlaceableBelongsToNoSectorException {
        ArrayList<District> districts = new ArrayList<>();
        for (District district : mapDistircts) {
            if (district.getSector().contains(placeble))
                districts.add(district);
        }
        if (districts.isEmpty())
            throw new PlaceableBelongsToNoSectorException("a placeble cannot belong to no sector");
        return districts;
    }

    /**
     * this method just returns the first available district, in which further place checking is done.
     *
     * @param placeable the new placeble
     * @return a district in which the new placeble can possibly be placed.
     */
    public District getDistrict(Placeable placeable) throws PlaceableBelongsToNoSectorException {
        for (District district : mapDistircts) {
            if (placeable.intersects(district.getSector()))
                return district;
        }
        throw new PlaceableBelongsToNoSectorException("a placeble cannot intersect with no sector");
    }


}