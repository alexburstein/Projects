package quizzs.deep_integer;

class Int extends Node{
    private final Integer num;
    Int(int num, int depth){
        super(depth);
        this.num = num;
    }

    int getSum() {
        return num * getDepth();
    }
}
