package Core.Class;

public class Score {
    private String playername;
    private int nbPoints;

    public Score (String playername,int nbPoints){
        this.playername=playername;
        this.nbPoints=nbPoints;
    }

    public String getPlayername() {
        return playername;
    }

    public int getNbPoints() {
        return nbPoints;
    }

}