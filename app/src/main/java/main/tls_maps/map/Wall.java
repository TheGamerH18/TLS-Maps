package main.tls_maps.map;

public class Wall {
    public Vector2 Position;
    public Vector2 Size;
    public double Rotation;
    public String Color;
    //public int Level;

    public Wall(Vector2 Position, Vector2 Size, double Rotation, String Color) {
        this.Position = Position;
        this.Size = Size;
        this.Rotation = Rotation;
        this.Color = Color;
        //this.Level = Level;
    }
}
