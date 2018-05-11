package advprog.example.bot.faceProfile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import org.junit.Before;
import org.junit.Test;

public class getProfileTest {

    private getProfile getProfile;

    @Before
    public void setUp() {
        getProfile = new getProfile();
    }

    @Test
    public void ProfileTest() {
        String profileDetection = getProfile.profileDetect();
        assertNotNull(profileDetection);
    }

    @Test
    public void AttrTest() {
        String profileAttr = getProfile.getAttributes();
        assertNotNull(profileAttr);
    }

    @Test
    public void AgeTest() {
        String ageTest = getProfile.getAge();
        assertNotNull(ageTest);
    }

    public void GenderTest() {
        String genderTest = getProfile.getGender();
        assertNotNull(genderTest);
    }
}
