import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergingTables {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6};

        DisjointSets disjointSets = new DisjointSets(numbers);
        disjointSets.union(2, 4);
        disjointSets.union(5, 2);
        disjointSets.union(3, 1);
        disjointSets.union(2, 3);

        System.out.println(disjointSets);
    }
}

class DisjointSets {
    private int[] parent;
    private int[] rank;

    public DisjointSets(int[] values) {
        int maxValue = Arrays.stream(values).max().getAsInt();

        this.parent = new int[maxValue + 1];
        this.rank = new int[maxValue + 1];

        for (int i = 0; i < this.rank.length; i++) {
            this.rank[i] = -1;
        }

        for (int value : values) {
            makeSet(value);
        }
    }

    public void makeSet(int i) {
        parent[i] = i;
        rank[i] = 0;
    }

    public int find(int i) {
        while (i != parent[i]) {
            i = parent[i];
        }
        return i;
    }

    public void union(int i, int j) {
        int iId = find(i);
        int jId = find(j);

        if (iId == jId) return;

        if (rank[iId] > rank[jId]) {
            parent[jId] = iId;
        } else {
            parent[iId] = jId;
            if (rank[iId] == rank[jId]) rank[jId] += 1;
        }
    }

    @Override
    public String toString() {
        List<Integer> parentFiltered = new ArrayList<>();
        List<Integer> rankFiltered = new ArrayList<>();
        List<Integer> valuesFiltered = new ArrayList<>();

        for (int i = 0; i < parent.length; i++) {
            if (rank[i] == -1) continue;

            parentFiltered.add(parent[i]);
            rankFiltered.add(rank[i]);
            valuesFiltered.add(i);
        }

        return "DisjointSets [values="
                + valuesFiltered.toString()
                + ", parent="
                + parentFiltered.toString()
                + ", rank="
                + rankFiltered.toString()
                + "]";
    }
}
