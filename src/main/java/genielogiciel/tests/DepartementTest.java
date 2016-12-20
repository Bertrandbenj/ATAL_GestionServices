/**
 * 
 */
package genielogiciel.tests;

import static org.junit.Assert.*;
import genielogiciel.model.Departement;
import genielogiciel.model.Parcours;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author salma
 *
 */
public class DepartementTest {

	Departement d=new Departement();
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#Departement()}.
	 */
	@Test
	public void testDepartement() {

	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#affecter(genielogiciel.model.Souhait)}.
	 */
	@Test
	public void testAffecter() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#publieDemande(genielogiciel.model.Souhait)}.
	 */
	@Test
	public void testPublieDemande() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#getName()}.
	 */
	@Test
	public void testGetName() {
		d.setName("Informatique");
		assertEquals("Informatique",d.getName());

	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#getParcours()}.
	 */
	@Test
	public void testGetParcours() {
		
	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link genielogiciel.model.Departement#getEnseignant()}.
	 */
	@Test
	public void testGetEnseignant() {
		fail("Not yet implemented");
	}

}
