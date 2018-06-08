package us.skidrevenant.azure.velocity;

import lombok.Getter;

/**
 * @author SkidRevenant
 * at 29/05/2018
 */
@Getter
public class Velocity {

    private final double horizontal, vertical;

    private long creationTime;

    public Velocity(double horizontal, double vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.creationTime = System.currentTimeMillis();
    }
}
