package utils;

import java.util.Random;

public class Utils {

    public String createRandomPassword(){
        Random random = new Random();
        StringBuilder strbuider = new StringBuilder();
        strbuider.append("Test@");
        Integer randomNumber = random.nextInt(99999);
        strbuider.append(randomNumber);
        return strbuider.toString();
    }
}
