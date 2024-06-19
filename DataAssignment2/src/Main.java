// Enes Geldi
import java.io.*;

class Link {
    public String data;
    public Link next;

    public Link(String data) {
        this.data = data;
    }

    public void displayLink() {
        System.out.println("Data: " + data);
    }
}

class MyStack {
    public Link head;

    public MyStack() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void push(String data) {
        Link newNode = new Link(data);
        newNode.next = head;
        head = newNode;
    }

    public String pop() {
        if (isEmpty()) {
            System.out.println("ERROR");
            return "\0";
        } else {
            Link current = head;
            head = head.next;
            return current.data;
        }
    }

    public String top() {
        if (isEmpty()) {
            System.out.println("ERROR");
            return "\0";
        }
        return head.data;
    }

    public int operatorCompare(String character) {
        if (character.equals("+") || character.equals("-")) {
            return 1;
        } else if (character.equals("*") || character.equals("/")) {
            return 2;
        } else {
            return -1;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        MyStack operandStack = new MyStack();
        MyStack operatorStack = new MyStack();
        try {
            File inputFile = new File("infix.txt");
            if (!inputFile.exists()) {
                inputFile.createNewFile();
                String input = "4+9*10\n1-3/6+100-25\n4 * - 2\n4 - 1 / 10 * 2\n100 2 *";
                FileWriter fileWriter1 = new FileWriter(inputFile, false);
                try (BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1)) {
                    bufferedWriter1.write(input);
                    bufferedWriter1.close();
                }
            }
            String line;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader(inputFile));

            while ((line = objReader.readLine()) != null) {
                try{
                String operand = "";
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isDigit(line.charAt(i))) {
                        do {
                            if (!operandStack.isEmpty()&& operatorStack.isEmpty()){
                                System.out.println("ERROR");
                            }
                            operand += line.charAt(i);
                            i++;
                        } while (i < line.length() && Character.isDigit(line.charAt(i)));
                        i--;
                    }else if (line.charAt(i) == '/' || line.charAt(i) == '*' || line.charAt(i) == '-' || line.charAt(i) == '+') {
                        if (operand != "") {
                            operandStack.push(operand);
                        }
                        operand = "";

                        while (!operatorStack.isEmpty() && operatorStack.operatorCompare(String.valueOf(line.charAt(i))) <= operatorStack.operatorCompare(operatorStack.top())) {
                                String num1 = operandStack.pop();
                                String num2 = operandStack.pop();
                                double result = 0;
                                switch (operatorStack.pop()) {
                                    case "/":
                                        result = Double.valueOf(num2) / Double.valueOf(num1);
                                        break;
                                    case "*":
                                        result = Double.valueOf(num2) * Double.valueOf(num1);
                                        break;
                                    case "+":
                                        result = Double.valueOf(num2) + Double.valueOf(num1);
                                        break;
                                    case "-":
                                        result = Double.valueOf(num2) - Double.valueOf(num1);
                                        break;
                                }
                                operandStack.push(String.valueOf(result));

                        }
                        operatorStack.push(String.valueOf(line.charAt(i)));
                    }
                    else {
                        if (operand!=""){
                            operandStack.push(operand);
                            operand="";
                        }
                    }
                }
                operandStack.push(operand);
                while (!operatorStack.isEmpty()) {
                        String num1 = operandStack.pop();
                        String num2 = operandStack.pop();
                        double result = 0;
                        switch (operatorStack.pop()) {
                            case "/":
                                result = Double.valueOf(num2) / Double.valueOf(num1);
                                break;
                            case "*":
                                result = Double.valueOf(num2) * Double.valueOf(num1);
                                break;
                            case "+":
                                result = Double.valueOf(num2) + Double.valueOf(num1);
                                break;
                            case "-":
                                result = Double.valueOf(num2) - Double.valueOf(num1);
                                break;
                        }
                        operandStack.push(String.valueOf(result));

                }

                System.out.println(operandStack.pop());
            }catch (Exception e){
                continue;}
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
}
