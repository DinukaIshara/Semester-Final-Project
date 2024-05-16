package lk.ijse.chama.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class Location {
    private String place;
    private Double latitude;
    private Double longitude;

    public Location(String name, Double latitude, Double longitude) {
        this.place = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
