package com.example.smartpantrymanager.utils;

public final class InputValidator {

    private InputValidator() {
        // making it a private constructor prevents instantiation
    }

    // checks if the value passed is null or is empty when trimmed (so, no blank spaces)

    public static boolean isNullOrBlank(String value) {

        return value == null || value.trim().isEmpty();

    }

    // checks if a value is a number an int

    public static boolean isValidInteger(String value) {

        if (isNullOrBlank(value)) {

            return false;

        }

        try {

            Integer.parseInt(value);
            return true;

        }
        catch (NumberFormatException e) {

            return false;

        }

    }

    // checks if a number is a positive int

    public static boolean isPositiveInteger(String value) {

        return isValidInteger(value) && Integer.parseInt(value) > 0;

    }

    // checks if a value is a valid decimal

    public static boolean isValidDecimal(String value) {

        if (isNullOrBlank(value)) {

            return false;

        }

        try {

            double number = Double.parseDouble(value);

            return !Double.isNaN(number) && !Double.isInfinite(number);

        }
        catch (NumberFormatException e) {

            return false;

        }

    }

    // checks if a decimal is positive

    public static boolean isPositiveDecimal(String value) {

        return isValidDecimal(value) && Double.parseDouble(value) > 0;

    }

}