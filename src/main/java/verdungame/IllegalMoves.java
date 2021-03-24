package verdungame;

public enum IllegalMoves {

    BAD_TARGET ("(ALLEGIANCE), this unit can't go there!"),
    NOT_ENOUGH_MOVES ("(ALLEGIANCE), you don't have enough moves left!"),
    ENEMY_TOO_FAR_AWAY ("(ALLEGIANCE), this unit can't attack this far away!"),
    ALLY_ATTACK ("(ALLEGIANCE), you traitor! You can't attack your allies!");

    private String text;

    IllegalMoves(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
