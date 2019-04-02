package dundry.hibernate.first;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dundry.model.TestTable;

public class TestTableTest {

    private SessionFactory sessionFactory;
    
    @Before
    public void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory 
                = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch  (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }

    @After
    public void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testReadingFromDb() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        List<TestTable> results = (List<TestTable>) session.createQuery("from TestTable").list();
        for ( TestTable r :  results) {
            String msg = String.format("%10d %10d %10.2f %s%n", r.getTest_table_id(),
                    r.getTest_int(), r.getTest_float(), r.getTest_string());
            System.out.print(msg);
        }
        
        session.getTransaction().commit();
        session.close();
    }

}
