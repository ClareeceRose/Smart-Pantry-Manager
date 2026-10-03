package com.example.smartpantrymanager.domain.services;

import com.example.smartpantrymanager.core.enums.UnitType;

// this file is where unit conversions will take place
public class UnitConversionService {
    // l <---> ml conversion
    public double convertLiterToMilliliter(double liter){
        double ml = liter * 1000;
        return ml;
    }

    public double convertMilliliterToLiter(double ml){
        double liter = ml / 1000;
        return liter;
    }

    // fluid ounce <---> ml conversion
    public double convertFluidOunceToMilliliter(double fluidOunce){
        double ml = fluidOunce * 29.5735;
        return ml;
    }

    public double convertMilliliterToFluidOunce(double ml){
        double fluidOunce = ml * 0.033814;
        return fluidOunce;
    }

    // kg <---> g conversion
    public double convertKilogramToGram(double kg){
        double g = kg * 1000;
        return g;
    }

    public double convertGramToKilogram(double g){
        double kg = g / 1000;
        return kg;
    }

    // pound <---> gram conversion
    public double convertPoundToGram(double pound){
        double g = pound * 453.592;
        return g;
    }

    public double convertGramToPound(double g){
        double pound = g * 0.00220462;
        return pound;
    }

    // ounce <---> gram conversion
    public double convertOunceToGram(double ounce){
        double g = ounce * 28.3495;
        return g;
    }

    public double convertGramToOunce(double g){
        double ounce = g * 0.035274;
        return ounce;
    }

    // tsp <---> tbsp
    public double convertTeaspoonToTablespoon(double tsp){
        double tbsp = tsp / 3;
        return tbsp;
    }

    public double convertTablespoonToTeaspoon(double tbsp){
        double tsp = tbsp * 3;
        return tsp;
    }

    // since I'd like to compare base units, I'd need methods to act as middle men for conversion.
    // the base units are namely grams, ml, and teaspoons

    // converts units to grams
    public double convertToGrams(double quantity, UnitType unit) {

        switch (unit){

            case GRAM:
                return quantity;

            case KILOGRAM:
                return convertKilogramToGram(quantity);

            case OUNCE:
                return convertOunceToGram(quantity);

            case POUND:
                return convertPoundToGram(quantity);

            default:
                return -1;

        }
    }

    // converts units to ml
    public double convertToMilliliters(double quantity, UnitType unit) {

        switch (unit) {
            case MILLILITER:
                return quantity;

            case LITER:
                return convertLiterToMilliliter(quantity);

            case FLUID_OUNCE:
                return convertFluidOunceToMilliliter(quantity);

            default:
                return -1;

        }
    }

    // converts units tsp
    public double convertToTeaspoons(double quantity, UnitType unit) {

        switch (unit) {
            case TEASPOON:
                return quantity;

            case TABLESPOON:
                return convertTablespoonToTeaspoon(quantity);

            default:
                return -1;
        }
    }
}
