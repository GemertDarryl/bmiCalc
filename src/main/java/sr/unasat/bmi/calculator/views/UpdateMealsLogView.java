package sr.unasat.bmi.calculator.views;

import sr.unasat.bmi.calculator.entities.MealLog;
import sr.unasat.bmi.calculator.entities.User;
import sr.unasat.bmi.calculator.repositories.MealLogRepository;
import sr.unasat.bmi.calculator.services.Helper;

import java.util.Scanner;

public class UpdateMealsLogView {
    User loggedInUser;
    Scanner userInput = new Scanner(System.in); //to read user input from console

    public UpdateMealsLogView(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    Helper helper = new Helper();

    public void showUpdateMealsLogScreen(){
        boolean logIdIsNumber = false;
        MealLogRepository mealLogRepository = new MealLogRepository();
        if(mealLogRepository.getAllMealLogs(loggedInUser.getId()) == null){
            helper.errorMessage("No logs found!");
            helper.returnToMenu(loggedInUser);
        }else{
            mealLogRepository.getAllMealLogs(loggedInUser.getId()).forEach(mealLog -> {
                System.out.println("[ID]: "+ mealLog.getId() +"   "+"[USER ID]: "+ mealLog.getUserId()+" [USER]: "+ mealLog.getUserName()+"   "+"[MEAL ID]: "+ mealLog.getMealId() + " [MEAL]: "+ mealLog.getMealName());
            });
        }
        System.out.println("ID of log to update or press 'R' to cancel:");
        do {
            if (userInput.hasNextInt()){
                int logId = userInput.nextInt();
                MealLog logToUpdate = mealLogRepository.findMealLogByid(logId);
                if (logToUpdate == null){
                    System.out.println("log not found");
                    logIdIsNumber = false;
                    continue;
                } else{
                    System.out.println("User ID: ");
                    logToUpdate.setUserId(userInput.nextInt());
                    System.out.println("Meal ID: ");
                    userInput.nextLine();
                    logToUpdate.setMealId(userInput.nextInt());


                    mealLogRepository.updateMealLogById(logToUpdate);

                    helper.returnToMenu(loggedInUser);
                }
            } else if (helper.returnKeyPressed(userInput.next())) {
                helper.returnToMenu(loggedInUser);
            } else{
                helper.errorMessage();
                userInput.next();
                logIdIsNumber = false;
            }
        }while (!logIdIsNumber);

    }


}
