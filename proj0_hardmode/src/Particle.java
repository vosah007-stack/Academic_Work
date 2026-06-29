import edu.princeton.cs.algs4.StdRandom;

import java.util.Map;
import java.awt.Color;

public class Particle {
    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                    ParticleFlavor.PLANT, PLANT_LIFESPAN,
                    ParticleFlavor.FIRE, FIRE_LIFESPAN);
    ParticleFlavor flavor;
    int lifespan;
    public Particle(ParticleFlavor flavor)
    {
        this.flavor = flavor;
        lifespan = -1;
        if (flavor.equals(ParticleFlavor.FLOWER) || flavor.equals(ParticleFlavor.PLANT) ||
        flavor.equals(ParticleFlavor.FIRE))
        {
            lifespan = LIFESPANS.get(flavor);
        }
    }

    public Color color()
    {
        if (flavor.equals(ParticleFlavor.EMPTY))
        {
            return Color.black;
        }
        if (flavor.equals(ParticleFlavor.SAND))
        {
            return Color.yellow;
        }
        if (flavor.equals(ParticleFlavor.BARRIER))
        {
            return Color.gray;
        }
        if (flavor.equals(ParticleFlavor.WATER))
        {
            return Color.blue;
        }
        if (flavor.equals(ParticleFlavor.FOUNTAIN))
        {
            return Color.cyan;
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }
        return new Color(0, 0, 0);
    }
    void moveInto(Particle other)
    {
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;
        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }
    public void fall(Map<Direction, Particle> neighbors)
    {
      if(neighbors.get(Direction.DOWN).flavor == ParticleFlavor.EMPTY)
      {
          moveInto(neighbors.get(Direction.DOWN));
      }
    }
    public void action(Map<Direction, Particle> neighbors)
    {
        if (flavor == ParticleFlavor.EMPTY)
        {
            return;
        }
        if (!flavor.equals(ParticleFlavor.BARRIER))
        {
            fall(neighbors);
        }
        if (flavor.equals(ParticleFlavor.WATER))
        {
            flow(neighbors);
        }
        if (flavor.equals(ParticleFlavor.PLANT) || flavor.equals(ParticleFlavor.FLOWER))
        {
            grow(neighbors);
        }
        if (flavor.equals(ParticleFlavor.FIRE))
        {
            burn(neighbors);
        }
    }
    public void flow(Map<Direction, Particle> neighbors)
    {
        int randomNum = StdRandom.uniformInt(3);
        if (randomNum == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY)
        {
            moveInto(neighbors.get(Direction.LEFT));
        }
        if (randomNum == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY)
        {
            moveInto(neighbors.get(Direction.RIGHT));
        }
    }
    public void grow(Map<Direction, Particle> neighbors)
    {
        int randomNum = StdRandom.uniformInt(10);
        if (randomNum == 0 && neighbors.get(Direction.UP).flavor == ParticleFlavor.EMPTY)
        {
            neighbors.get(Direction.UP).flavor = flavor;
            neighbors.get(Direction.UP).lifespan = LIFESPANS.get(flavor);
        }
        if (randomNum == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY)
        {
            neighbors.get(Direction.LEFT).flavor = flavor;
            neighbors.get(Direction.LEFT).lifespan = LIFESPANS.get(flavor);
        }
        if (randomNum == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY)
        {
            neighbors.get(Direction.RIGHT).flavor = flavor;
            neighbors.get(Direction.RIGHT).lifespan = LIFESPANS.get(flavor);
        }
    }
    public void decrementLifespan()
    {
        if (lifespan > 0)
        {
            lifespan -= 1;
        }
        if (lifespan == 0)
        {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }
    public void burn(Map<Direction, Particle> neighbors)
    {
        int randomNum;
        for (Particle p: neighbors.values())
        {
            randomNum = StdRandom.uniformInt(5);
            if((randomNum == 2 || randomNum == 1) && (p.flavor.equals(ParticleFlavor.FLOWER) || p.flavor.equals(ParticleFlavor.PLANT)))
            {
                p.flavor = ParticleFlavor.FIRE;
                p.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
            }
        }
    }
}
