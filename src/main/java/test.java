//import com.main.schoolux.validations.CommonValidation;

import static com.atc.utils.AuthUtils.hashAndSalt;

public class test {

    public static void main(String[] args){

        String password = "mdp";
        String hashedAndSaltedPassword = hashAndSalt(password);
        System.out.println(password + " " + hashedAndSaltedPassword);

    }
}
