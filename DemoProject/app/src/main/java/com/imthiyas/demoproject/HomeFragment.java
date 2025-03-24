package com.imthiyas.demoproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.imthiyas.demoproject.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {


    FragmentHomeBinding fragmentHomeBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);




        fragmentHomeBinding.add.setOnClickListener(v -> calculateResult('+'));
        fragmentHomeBinding.subtract.setOnClickListener(v -> calculateResult('-'));
        fragmentHomeBinding.product.setOnClickListener(v -> calculateResult('*'));
        fragmentHomeBinding.divide.setOnClickListener(v -> calculateResult('/'));

        // Return the root view of the inflated binding
        return fragmentHomeBinding.getRoot();
    }


    private void calculateResult(char operation) {
        String firstInput = fragmentHomeBinding.firstNumber.getText().toString();
        String secondInput = fragmentHomeBinding.secondNumber.getText().toString();

        if (firstInput.isEmpty() || secondInput.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(firstInput);
            double num2 = Double.parseDouble(secondInput);
            double result = 0;

            // Perform the calculation based on the selected operation
            switch (operation) {
                case '+':
                    result = MathOperations.add((int) num1, (int) num2);
                    break;
                case '-':
                    result = MathOperations.subtract((int) num1, (int) num2);
                    break;
                case '*':
                    result = MathOperations.multiply((int) num1, (int) num2);
                    break;
                case '/':
                    if (num2 != 0) {
                        result = MathOperations.divide((int) num1, (int) num2);
                    } else {
                        Toast.makeText(getActivity(), "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                default:
                    Toast.makeText(getActivity(), "Invalid operation", Toast.LENGTH_SHORT).show();
                    return;
            }

            // Convert result to a string and display in the TextView
            fragmentHomeBinding.result.setText(String.valueOf(result));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up the binding reference
        fragmentHomeBinding = null;
    }


}