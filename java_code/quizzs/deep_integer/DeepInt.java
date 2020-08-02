package quizzs.deep_integer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

class DeepInt extends Node{
    private final Collection<Node> subNodes = new ArrayList<>();

    DeepInt(String input, int depth) {
        super(depth);
        initSubNodes(input);
    }

    private void initSubNodes(String str){
        String[] parsed = parseForFirstElm(str);

        if(parsed[0].startsWith("[")){
            String deepIntContent = parsed[0].substring(1,parsed[0].length() - 1);
            subNodes.add(new DeepInt(deepIntContent, getDepth() + 1));
        }

        else{
            subNodes.add(new Int(Integer.parseInt(parsed[0]), getDepth()));
        }

        if (parsed.length > 1){
            initSubNodes(parsed[1]);
        }
    }

    private String[] parseForFirstElm(String str){
        if (str.charAt(0) == ','){
            str = str.substring(1);
        }
        else if(str.charAt(0) == '[') {
            int index = findEndOfDeepElm(str);

            if (index < str.length()){
                return new String[]{str.substring(0, index), str.substring(index)};
            }

            return new String[]{str.substring(0, index)};
        }

        for (int i = 0; i < str.length(); ++i){
             if (str.charAt(i) == ','){
                 return new String[]{str.substring(0,i), str.substring(i + 1)};
             }
             else if (str.charAt(i) == '['){
                 return new String[]{str.substring(0,i), str.substring(i)};
             }
        }
        return new String[]{str};
    }

    private int findEndOfDeepElm(String str){
        Stack<Character> stack = new Stack<>();
        char[] arr = str.toCharArray();
        int i = 1;
        stack.push(arr[0]);

        for(; i < str.length() && !stack.isEmpty(); ++i){
            if(arr[i] == '['){
                stack.push(arr[i]);
            }
            else if(arr[i] == ']'){
                stack.pop();
            }
        }
        return i;
    }


    @Override
    int getSum() {
        int sum = 0;

        for(Node n: subNodes){
            sum+= n.getSum();
        }
        return sum;
    }
}
