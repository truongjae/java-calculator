import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Caculator {
    private String string;

    public Caculator(String string) {
        this.string = string;
    }
    public boolean checkInt(char n){
        if(n >= 48 && n <= 57) return true;
        return false;
    }

    public int checkMath(char n){
        if(n=='(') return 0;
        if(n=='+' || n=='-') return 1;
        if(n=='*' || n=='/' || n=='%') return 2;
        if(n=='%') return 3;
        if(n=='^') return 4;
        return 5;
    }
    public String convertToSuffixes(){
        String str = this.string;
        Stack<String> stack = new Stack<>();
        String temp="";
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c!=' '){
                if(checkInt(c)) temp+=c;
                else {
                    s+= temp+" ";
                    temp="";
                    /*
                   push vao stack
                    * */
                    while (!stack.empty() && checkMath(c)<= checkMath(stack.peek().toCharArray()[0])){
                        s+=stack.peek()+" ";
                        stack.pop();
                    }
                    stack.push(String.valueOf(c));
                }
            }
        }
        if(temp!="") s+=temp+" ";
        while (!stack.empty()){
            s+=stack.peek()+" ";
            stack.pop();
        }
        return s;
    }
    public double calculator(){
        String str = this.convertToSuffixes();
        double calculator=0;
        Stack<Double> stack = new Stack();
        String s = "";
        List<String> x = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            /*char temp = str.charAt(i);
            if(checkInt(temp)){
                s+=temp;
            }
            if(temp == ' '){
                stack.push(Long.parseLong(s));
                s="";
            }
            if(str.charAt(i)=='+'){
                while (!stack.isEmpty()){
                    cal += stack.peek();
                    stack.pop();
                }
                stack.push((long) cal);
                break;
            }*/
            if(str.charAt(i)!= ' '){
                s+=str.charAt(i);
            }
            else if (str.charAt(i) == ' '){
                x.add(s);
                s="";
            }
        }
        for (String k : x){
            char p = k.toCharArray()[0];
            if(checkMath(p) != 5){
                double b = stack.peek();
                stack.pop();
                double a = stack.peek();
                stack.pop();
                if(p == '+') calculator = a+b;
                else if(p == '-') calculator = a-b;
                else if(p == '*') calculator = a*b;
                else if(p == '/') calculator = a/b;
                else if(p == '^'){
                    double c = 1;
                    for (int i = 0; i < b; i++) {
                        c*=a;
                    }
                    calculator = c;
                }
                else if(p == '%') {
                    calculator = a % b;
                }
                stack.push(calculator);
            }else {
                stack.push(Double.parseDouble(k));
            }
        }
        return calculator;
    }
    public static void main(String[] args) {
        String str = "2*9+3^2*6/9+3-5%2"; // => 2 9 * 3 2 ^ 6 * 9 / + 3 + 5 2 % -
        Caculator caculator = new Caculator(str);
        System.out.println(caculator.calculator());
    }
}
