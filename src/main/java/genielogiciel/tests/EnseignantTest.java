/**
 * 
 */
package genielogiciel.tests;

import static org.junit.Assert.*;
import genielogiciel.model.Enseignant;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author salma
 *
 */
public class EnseignantTest {

	Enseignant e= new Enseignant();
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
	 * Test method for {@link genielogiciel.model.Enseignant#Enseignant()}.
	 */
	@Test
	public void testEnseignant() {
		

	}



	/**
	 * Test method for {@link genielogiciel.model.Enseignant#getNom()}.
	 */
	@Test
	public void testGetNom() {
		e.setNom("BELASSAL");
		assertEquals("BELASSAL",e.getNom());
	
	}

	/**
	 * Test method for {@link genielogiciel.model.Enseignant#getPrenom()}.
	 */
	@Test
	public void testGetPrenom() {
		e.setPrenom("salma");
		assertEquals("salma",e.getPrenom());
	}

	/**
	 * Test method for {@link genielogiciel.model.Enseignant#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		
		e.setStatus("chercheur");
		assertEquals("chercheur",e.getStatus());

	}

	/**
	 * Test method for {@link genielogiciel.model.Enseignant#getDemandes()}.
	 */
	@Test
	public void testGetDemandes() {
		//fail("Not yet implemented");
	}



}
