package com.example.smartpantrymanager.domain.services;

// this file is where unit conversions will take place
public class UnitConversionService {
    // l <---> ml conversion
    public double convertLiterToMilliliter(double liter){
        double ml = liter / 1000;
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
}
