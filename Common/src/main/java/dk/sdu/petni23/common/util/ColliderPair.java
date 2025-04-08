package dk.sdu.petni23.common.util;

public record ColliderPair(Collider c1, Collider c2)
{

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ColliderPair c) {
            return ((c1 == c.c1) && (c2 == c.c2)) || ((c1 == c.c2) && (c2 == c.c1));
        }
        return false;
    }

}
