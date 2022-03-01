package model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "goalId") })
public class MaxSuccessNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "goalId")
    private Goal goal;

    @Column(nullable = false)
    private int maxSuccessNumber = 0;

    public MaxSuccessNumber() {

    }
    public MaxSuccessNumber(Goal goal, int maxSuccessNumber) {
        this.goal = goal;
        this.maxSuccessNumber = maxSuccessNumber;
    }



}
