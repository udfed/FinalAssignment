package ClassManager.main;

public class Score{
    private double chinese;
    private double math;
    private double english;
    private double physics;
    private double chemistry;
    private double biology;
    private double politics;
    private double history;
    private double geography;
    private double technology;

    public Score(){
        chinese = 0;
        math = 0;
        english = 0;
        physics = 0;
        chemistry = 0;
        biology = 0;
        politics = 0;
        history = 0;
        geography = 0;
        technology = 0;
    }

    public Score(double chinese, double math, double english, double physics, double chemistry,
                 double biology, double politics, double history, double geography, double technology){
        this.chinese = chinese;
        this.math = math;
        this.english = english;
        this.physics = physics;
        this.chemistry = chemistry;
        this.biology = biology;
        this.politics = politics;
        this.history = history;
        this.geography = geography;
        this.technology = technology;
    }

    @Override
    public String toString() {
        return String.format("%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t%-4.1f\t",
                chinese, math, english, physics, chemistry,
                biology, politics, history, geography, technology);
    }
}