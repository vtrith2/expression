import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> data = readFile();

        for (String originExpression : data) {
            ArrayList<String> infixExp = splitIntoPart(originExpression.replaceAll(" ", "").trim());
            ArrayList<String> postfixExp = convertFromInfixToPostfix(infixExp);
            double result = postfixExpEvaluate(postfixExp);
            System.out.println(originExpression + " = " + result);
        }
    }

    public static ArrayList<String> readFile() {
        final String filePath = "src/expression";
        BufferedReader reader;
        ArrayList<String> data = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static ArrayList<String> splitIntoPart(String expression) {
        ArrayList<String> result = new ArrayList<String>();

        int i = 0;
        while (i < expression.length()) {
            if (Character.isDigit(expression.charAt(i)) && i < expression.length() - 1) {
                int j = i;
                while (Character.isDigit(expression.charAt(j + 1))) j++;
                result.add(expression.substring(i, j + 1));
                i = j + 1;
            } else {
                result.add(String.valueOf(expression.charAt(i)));
                i++;
            }
        }

        return result;
    }

    public static boolean isOperand(String part) {
        try {
            Double.parseDouble(part);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int precedence(String operator) {
        return switch (operator) {
            case "^" -> 4;
            case "*", "/" -> 3;
            case "+", "-" -> 2;
            default -> 0;
        };
    }

    public static ArrayList<String> convertFromInfixToPostfix(ArrayList<String> infixExpression) {
        ArrayList<String> postfixExpression = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();

        for (String part : infixExpression) {
            if (isOperand(part)) postfixExpression.add(part);
            else if (part.equals("(")) stack.push(part);
            else if (part.equals(")")) {
                while (!stack.isEmpty() && !(stack.peek().equals("(")))
                    postfixExpression.add(stack.pop());

                stack.pop();
            } else if (precedence(part) > 0) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(part))
                    postfixExpression.add(stack.pop());

                stack.push(part);
            }
        }

        while (!stack.isEmpty()) {
            postfixExpression.add(stack.pop());
        }

        return postfixExpression;
    }

    public static double calByOperator(String operator, double firstOperand, double secondOperand) {
        return switch (operator) {
            case "^" -> Math.pow(secondOperand, firstOperand);
            case "*" -> firstOperand * secondOperand;
            case "/" -> secondOperand / firstOperand;
            case "+" -> firstOperand + secondOperand;
            default -> secondOperand - firstOperand;
        };
    }

    public static double postfixExpEvaluate(ArrayList<String> postfixExp) {
        Stack<String> stack = new Stack<String>();

        for (String part : postfixExp) {
            if (isOperand(part)) {
                stack.push(part);
            } else {
                double result = calByOperator(part, Double.parseDouble(stack.pop()), Double.parseDouble(stack.pop()));
                stack.push(String.valueOf(result));
            }
        }

        return Double.parseDouble(stack.pop());
    }
}