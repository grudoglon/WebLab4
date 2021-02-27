package ru.ifmo.web.SpringWeb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "points")
public class Point implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private Long id;

    private Integer x;

    private Double y;

    private Integer r;

    private Boolean hit;

    private Double time;

    @ManyToOne
    private User user;


    public Point(){}

    public Point(int x, double y, int r){
        this.x = x;
        this.y = y;
        this.r = r;

        long startTime = System.nanoTime();
        this.hit = checkArea(this.x, this.y, this.r);
        long endTime = System.nanoTime();

        this.time = ((double) (endTime - startTime) / 10000000);
    }



    private boolean checkArea(double x, double y, double r){
        // Checks triangle area
        if (x >= 0 && y <= 0 && y >= x / 2 - r / 2) {
            return true;
        }
        // Checks rectangle area
        if (x <= 0 && y >= 0 && x >= - r / 2 && y <= r) {
            return true;
        }
        // Checks 1/4 circle area
        if (x <= 0 && y <= 0 && x * x + y * y <= r * r) {
            return true;
        }
        return false;
    }

    public void calculateArea(){
        long startTime = System.nanoTime();
        this.hit = checkArea(this.x, this.y, this.r);
        long endTime = System.nanoTime();

        this.time = ((double) (endTime - startTime) / 10000000);
    }
}