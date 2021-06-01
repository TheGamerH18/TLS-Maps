package main.tls_maps.ui.home;

public class Wall {
    public Vector2 Position;
    public Vector2 Size;
    public double Rotation;
    public int Level;

    public Wall(Vector2 Position, Vector2 Size, double Rotation, int Level) {
        this.Position = Position;
        this.Size = Size;
        this.Rotation = Rotation;
        this.Level = Level;
    }
}
