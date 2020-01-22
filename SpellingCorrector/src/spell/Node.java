package spell;

public class Node implements INode {
    Node[] nodes;
    int frequency;

    public Node(){
        nodes = new Node[26];
        frequency = 0;
    }

    @Override
    public int getValue() {
        return frequency;
    }
}
