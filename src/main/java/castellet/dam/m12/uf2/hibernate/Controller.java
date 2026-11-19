package castellet.dam.m12.uf2.hibernate;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class Controller {
	public static void main(String[] args) { 
		System.out.println("Current directory (aquí heu de posar el XML): " + System.getProperty("user.dir"));
		Controller controller = new Controller();
		controller.init();
	}

	private void init() {
		MainView mainView = new MainView();
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
		UserHibernatePgDAO userPgDAO = new UserHibernatePgDAO(sessionFactory);
        //UserPgDAO userPgDAO = new UserPgDAO(ConnectionManager.getConnection("database.xml"));

		int option = mainView.mainMenu();

        
		// switch option
		switch (option) {
			case 1:
					mainView.userList(userPgDAO.getAll());
				break;
			
			case 2:
					User newUser = mainView.addUserForm();
					if (newUser != null){
						userPgDAO.add(newUser);
					}
					else{
						//mainView.showError("Error al crear l'usuari");
					}
		
			default:
				break;
		}
	}
}