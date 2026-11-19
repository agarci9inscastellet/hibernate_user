package castellet.dam.m12.uf2.hibernate;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Controller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainView mainView = new MainView(scanner);

        // Inicialitzar la SessionFactory i el DAO
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        UserHibernatePgDAO userDAO = new UserHibernatePgDAO(sessionFactory);

        int option = -1;

        do {
            try {
                option = mainView.showMenu();

                switch (option) {
                    case 1: {
                        User newUser = mainView.userForm();
                        userDAO.saveUser(newUser);
                        System.out.println("Usuari guardat correctament.");
                        break;
                    }
                    case 2: {
                        List<User> users = userDAO.getAllUsers();
                        if (users.isEmpty()) {
                            System.out.println("No hi ha users.");
                        } else {
                            System.out.println("Llista d'usuaris:");
                            for (User u : users) {
                                System.out.println(u);
                            }
                        }   
                        break;
                    }
                    case 3: {
                        String name = mainView.askForUserName();
                        List<User> users = userDAO.findUserByName(name);
                        mainView.userList(users);
                        break;
                    }
                    case 0: {
                        System.out.println("Saliendo...");
                        break;
                    }
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);

        // Tancar la SessionFactory en sortir
        sessionFactory.close();
        scanner.close();
    }
}