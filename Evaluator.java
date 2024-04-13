import java.util.*;

interface IStack {

    /*** Removes the element at the top of stack and returnsthat element.
     * @return top of stack element, or through exception if empty
     */

    public Object pop();

    /*** Get the element at the top of stack without removing it from stack.
     * @return top of stack element, or through exception if empty
     */

    public Object peek();

    /*** Pushes an item onto the top of this stack.
     * @param object to insert*
     */

    public void push(Object element);

    /*** Tests if this stack is empty
     * @return true if stack empty
     */
    public boolean isEmpty();

    public int size();
}

class MyStack implements IStack {

    class Node {
        Object data;
        Node next;
    }

    Node head = null;
    static int length = 0;

    public void push(Object element) {

        Node temp = new Node();
        temp.data = element;
        if (head == null) {
            temp.next = null;
            head = temp;
        } else {
            temp.next = head;
            head = temp;
        }
        length++;
    }

    public Object pop() {

        if (head == null)
            return 0;
        Object curr = head.data;
        head = head.next;
        length--;
        return curr;
    }

    public boolean isEmpty() {
        if (head == null)
            return true;
        return false;
    }

    public Object peek() {
        if (head == null)
            return 0;
        return head.data;
    }

    public int size() {
        return length;
    }
}

interface IExpressionEvaluator {

    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */

    public String infixToPostfix(String expression);

    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * @param expression postfix expression
     * @return the expression evaluated value
     */

    public int evaluate(String expression);
}

public class Evaluator implements IExpressionEvaluator{

    static int a,b,c;
    int operator(char op)    //returns the equivalent priority of this operator
    {
        if(op=='+' || op=='-') return 1;
        else if(op=='*' || op=='/') return 2;
        else if(op=='^') return 3;
        else return 0;
    }

    public String infixToPostfix(String expression){
        MyStack stack = new MyStack();
        String postfix = "";
        int count1 = 0;
        int count2 = 0;
        for(int i=0;i<expression.length();i++){
            char var = expression.charAt(i);
            if(var =='a' || var =='b' || var =='c'){
                postfix += var;
                count1++;
            }
            else if( var =='+' || var =='-' || var =='*' || var =='/' || var =='^'){
                count2++;
                while( !stack.isEmpty() && operator(var) <= operator(String.valueOf(stack.peek()).charAt(0)) ){
                    postfix += String.valueOf(stack.pop());
                }
                stack.push(var);
            }
            else if(var =='(')
                stack.push(var);
            else if(var ==')'){
                while(!(String.valueOf(stack.peek()).equals("("))){
                    postfix += String.valueOf(stack.pop());
                }
                stack.pop();
            }
            else {
                System.out.println("Error");
                break;
            }
        }
        if(count1<count2) {
            System.out.println("Error");
            return "";
        }
        while(!stack.isEmpty())
            postfix += String.valueOf(stack.pop());
        return postfix;
    }

    /**
     * takes the evaluator stack and char operator
     * and forms the operation on on the stack elements
     * to evaluate the postfix expression
     * @param stack
     * @param op
     */
    void solve(MyStack stack,char op){

        int x=0, y=0;   //first, second operands
        String pop2 = String.valueOf(stack.pop());
        String pop1 = String.valueOf(stack.pop());

        //convert each letter by it's specific number if letter exist otherwise take the number
        if(pop1.equals("a"))
            x = a;
        else if(pop1.equals("b"))
            x = b;
        else if(pop1.equals("c"))
            x = c;
        else
            x = Integer.parseInt(pop1);

        if(pop2.equals("a"))
            y = a;
        else if(pop2.equals("b"))
            y = b;
        else if(pop2.equals("c"))
            y = c;
        else
            y = Integer.parseInt(pop2);

        String res = new String();
        switch(op){
            case '+':
                res = String.valueOf(x+y);
                break;
            case '-':
                res = String.valueOf(x-y);
                break;
            case '*':
                res = String.valueOf(x*y);
                break;
            case '/':
                res = String.valueOf(x/y);
                break;
            case '^':
                res = String.valueOf((int)Math.pow(x,y));
                break;
        }
        stack.push(res);
    }

    public int evaluate(String expression){

        MyStack stack = new MyStack();
        for(int i=0;i<expression.length();i++){
            char op = expression.charAt(i);
            if(op=='a' || op=='b' || op=='c')
                stack.push(op);
            else if(op=='+' || op=='*' || op=='/' || op=='^'){
                solve(stack, op);
            }
            else if(op=='-'){
                if(stack.size()==1){
                    String pop = String.valueOf(stack.pop());
                    int oper = 0;
                    if(pop.equals("a"))
                        oper = a;
                    else if(pop.equals("b"))
                        oper = b;
                    else if(pop.equals("c"))
                        oper = c;
                    else
                        oper = Integer.parseInt(pop);
                    String negative = String.valueOf(-oper);
                    stack.push(negative);
                }
                else{
                    solve(stack, op);
                }
            }
        }
        if(stack.size()>=2) {
            System.out.println("Error");
            return 0;
        }
        else
            return Integer.parseInt(String.valueOf(stack.pop()));
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        try{
            String infix = input.nextLine();
            String sin1 = input.nextLine();
            String sin2 = input.nextLine();
            String sin3 = input.nextLine();
            a = Integer.parseInt(sin1.replaceAll("a=", ""));
            b = Integer.parseInt(sin2.replaceAll("b=", ""));
            c = Integer.parseInt(sin3.replaceAll("c=", ""));
            int count1=0, count2=0;

            for(int i=0;i<infix.length()-1;i++){
                if(infix.charAt(i)=='(' && infix.charAt(i+1)==')') {
                    System.out.println("Error");
                    break;
                }
            }
            for(int i=0;i<infix.length();i++){
                if(infix.charAt(i)=='(')
                    count1++;
                else if(infix.charAt(i)==')')
                    count2++;
            }
            if(count1!=count2)
            {
                System.out.println("Error");
                return;
            }

            infix = infix.replaceAll("--", "+");
            if(infix.charAt(0)=='+') infix = infix.substring(1);
            infix = infix.replace("/+", "/");
            infix = infix.replace("-+", "-");
            infix = infix.replace("*+", "*");
            infix = infix.replace("^+", "^");
            infix = infix.replace("++", "+");

            if(infix.length()==1 && (infix.charAt(0)=='a' ||infix.charAt(0)=='b' || infix.charAt(0)=='c' ))
            {
                System.out.println(infix.charAt(0));
                int result=0;
                if(infix.charAt(0) =='a') result = a;
                else if(infix.charAt(0) =='b') result = b;
                else result = c;
                System.out.println(result);
                return;
            }
            for(int i=0;i<infix.length()-1;i++){
                if( infix.charAt(i)=='/' && infix.charAt(i+1)=='/' || infix.charAt(i)=='*' && infix.charAt(i+1)=='*' || infix.charAt(i)=='^' && infix.charAt(i+1)=='^' )
                {
                    System.out.println("Error");
                    break;
                }
            }
            if(infix.charAt(0)=='*' || infix.charAt(0)=='/' || infix.charAt(0)=='^')
            {
                System.out.println("Error");
                return;
            }
            if((infix.charAt(infix.length()-1) > 41 && infix.charAt(infix.length()-1) < 48) || infix.charAt(infix.length()-1)=='^')
            {
                System.out.println("Error");
                return;
            }
            Evaluator sol = new Evaluator();
            String postfix = sol.infixToPostfix(infix);
            System.out.println(postfix);
            int result = sol.evaluate(postfix);
            System.out.println(result);
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }
}