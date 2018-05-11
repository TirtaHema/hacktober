package advprog.example.bot.faceprofile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import org.junit.Before;
import org.junit.Test;

public class getProfileTest {

    private GetProfile GetProfile;

    @Before
    public void setUp() {
        GetProfile = new GetProfile();
    }

    @Test
    public void ProfileTest() {
        String profileDetection = GetProfile.profileDetect();
        assertNotNull(profileDetection);
    }

    @Test
    public void AttrTest() {
        String profileAttr = GetProfile.getAttributes();
        assertNotNull(profileAttr);
    }

    @Test
    public void AgeTest() {
        String ageTest = GetProfile.getAge();
        assertNotNull(ageTest);
    }

    @Test
    public void GenderTest() {
        String genderTest = GetProfile.getGender();
        assertNotNull(genderTest);
    }
}
