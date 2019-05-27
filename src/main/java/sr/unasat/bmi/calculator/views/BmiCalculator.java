package sr.unasat.bmi.calculator.views;

import sr.unasat.bmi.calculator.app.Application;
import sr.unasat.bmi.calculator.entities.User;
import sr.unasat.bmi.calculator.repositories.BmiLogRepository;
import sr.unasat.bmi.calculator.services.Helper;

import java.sql.SQLException;
import java.util.Scanner;

class BmiCalculator extends Application {
    private User loggedInUser;
    Scanner userInput = new Scanner(System.in); //to read user input from console

    public BmiCalculator(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }




    public void showBmiCalculatorScreen(){
        //show user height
        System.out.println("Height: "+ loggedInUser.getHeight());
        System.out.println("Weight:");
//        TODO: validate if value is INT
        if(userInput.hasNextInt()){
        int userWeight =  userInput.nextInt();
        BmiLogRepository bmiLogRepository = new BmiLogRepository();

        //get BMI value
        double userBmi = bmiLogRepository.calculateBMI(loggedInUser.getHeight(), userWeight);
        System.out.println("Your Body Mass Index is: " + userBmi);
        String bmiMessage = bmiLogRepository.checkBmiRange(userBmi);
        try {
            bmiLogRepository.InsertNewBmiLog(loggedInUser.getId(),userWeight,userBmi);
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(bmiMessage);
        Helper helper = new Helper();
        helper.returnToMenu(loggedInUser);


    }
}}
