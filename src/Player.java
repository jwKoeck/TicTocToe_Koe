class Player {
    // Comment

    private String name;
    private char figure;
    private boolean isHuman;

    public Player(String name, char figure, boolean isHuman) {
        this.name = name;
        this.figure = figure;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public char getFigure() {
        return figure;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
