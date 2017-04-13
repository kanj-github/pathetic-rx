import java.util.ArrayList;

/**
 * Created by naraykan on 23/02/17.
 */
public class AddObjectToListAndModifyItThere {
    public static void main (String[] args) {
        AtomBomb a = new AtomBomb(100);
        Bomb b = new Bomb(100);
        ArrayList<Bomb> list = new ArrayList<>();
        list.add(b);
        list.add(a);
        list.get(0).change();
        list.get(1).change();
        System.out.println(b.n);
        System.out.println(list.get(0).n);

        System.out.println(a.n);
        System.out.println(list.get(1).n);
    }

    static class Bomb {
        int n;

        public Bomb(int n) {
            this.n = n;
        }

        public void change() {
            n/=2;
        }
    }

    static class AtomBomb extends Bomb {

        public AtomBomb(int n) {
            super(n);
        }
    }
}
