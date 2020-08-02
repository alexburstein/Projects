package quizzs.deep_integer;

abstract class Node {
    private final int depth;

    Node(int depth) {
        this.depth = depth;
    }

    int getDepth() {
        return depth;
    }

    /**
     * @return the sum of the current node and its subNodes if exist, each multiplied by its depth
     */
    abstract int getSum();
}
