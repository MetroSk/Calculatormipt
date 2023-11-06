package com.example.calculatormipt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StringBuilder input = new StringBuilder();
    private TextView resultTextView;
    private TextView solutionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result_tv);
        solutionTextView = findViewById(R.id.solution_tv);


        int[] buttonIds = {
                R.id.button_c, R.id.button_open_bracket, R.id.button_sqrt, R.id.button_divide,
                R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_multiply,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_plus,
                R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_minus,
                R.id.button_ac, R.id.button_0, R.id.button_dot, R.id.button_equals
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                clearInput();
                break;
            case "AC":
                deleteLastCharacter();
                break;
            case "=":
                evaluateExpression();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                input.append(" ").append(buttonText).append(" ");
                updateResultText();
                break;
            case "SQRT":
                calculateSquareRoot();
                break;
            default:
                input.append(buttonText);
                updateResultText();
                break;
        }
    }

    private void clearInput() {
        input.setLength(0);
        updateResultText();
    }

    private void deleteLastCharacter() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            updateResultText();
        }
    }

    private void updateResultText() {
        resultTextView.setText(input.toString());
    }

    private void evaluateExpression() {
        try {
            String[] parts = input.toString().split(" ");
            if (parts.length == 3) {
                double num1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double num2 = Double.parseDouble(parts[2]);

                double result = performOperation(num1, operator, num2);

                resultTextView.setText(String.valueOf(result));
                solutionTextView.setText(input.toString() + " = " + result);
                clearInput();
            } else {
                throw new IllegalArgumentException("Invalid Expression");
            }
        } catch (Exception e) {
            resultTextView.setText("Error");
            solutionTextView.setText("Invalid Expression");
            clearInput();
        }
    }

    private double performOperation(double num1, String operator, double num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }



    private void calculateSquareRoot() {
        try {
            double num = Double.parseDouble(input.toString());
            if (num >= 0) {
                double result = Math.sqrt(num);
                resultTextView.setText(String.valueOf(result));
                solutionTextView.setText("√" + input.toString() + " = " + result);
                clearInput();
            } else {
                throw new IllegalArgumentException("Cannot calculate square root of a negative number");
            }
        } catch (Exception e) {
            resultTextView.setText("Error");
            solutionTextView.setText("Invalid Expression");
            clearInput();
        }
    }
}
