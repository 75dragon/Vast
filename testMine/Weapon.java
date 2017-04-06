package testMine;

import java.awt.Image;

public class Weapon
{
    int range;
    int hitboxWidth;
    int hitboxHeight;
    int damage;
    int attackSpeed;
    Image img;
    
    public Weapon(int rng, int w, int h, int dmg, int atkspd, Image image)
    {
        range = rng;
        hitboxWidth = w;
        hitboxHeight = h;
        damage = dmg;
        attackSpeed = atkspd;
        img = image;
    }
    
    public void attack(int xDirection, int yDirection, int x, int y)
    {
        
    }
}
