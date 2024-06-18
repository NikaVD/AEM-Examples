package com.traineeproject.core.utils;


public class Constants {

    public static String getProperties (String inputData) {
        String result;
        switch (inputData) {
            case "CL500B":
                result = "CL500, black";
                break;
            case "CL500S":
                result = "CL500, steel";
                break;
            case "CL500A":
                result = "CL500, aluminum";
                break;
            case "CL500G":
                result = "CL500, green";
                break;
            case "CL900B":
                result = "CL900, black";
                break;
            case "CL900S":
                result = "CL900, steel";
                break;
            case "CL150B":
                result = "CL150, black";
                break;
            case "CL150S":
                result = "CL150, steel";
                break;
            case "CL150G":
                result = "CL150, green";
                break;

            case "CB1":
                result = "Single Shelf Cup Rack Option";
                break;
            case "CB2":
                result = "Dual Shelf Cup Rack Option";
                break;

            case "HP2":
                result = "Standard Dual Hopper";
                break;
            case "HP3":
                result = "Triple Hopper";
                break;
            case "HP3B":
                result = "Large Capacity Triple Hopper";
                break;
            case "HP4B":
                result = "Large Capacity Quad Hopper";
                break;
            case "SW":
                result = "Steam Wand";
                break;
                default:
                result = "";
                break;
        }
        return result;
    }


}
