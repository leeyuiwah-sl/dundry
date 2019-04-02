package dundry.data_manager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import dundry.model.Item;

public class DataManager {
    private static SessionFactory sessionFactory;
    
    static {
        setUp();
    }

    public static void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory 
                = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch  (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            final String msg = "Failed to set up DataManager.  Please double check that your "
                    + "local database instance is up and running";
            System.out.println(msg);
        }
    }
    
    public static List<Item> getItems() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        List<Item> results = (List<Item>) session.createQuery("from Item").list();
        
        session.getTransaction().commit();
        session.close();
        
        return results;
    }

}
