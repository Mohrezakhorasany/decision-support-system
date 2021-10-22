package hu.ak_akademia.ssn;

import hu.ak_akademia.database.dao.PatientDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class SSNInputManagementTest {

    private SSNInputManagement objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new SSNInputManagement();
    }

    @Test
    void shouldReturnValidSSN_whenInputIsValidAndSSNExists() {
        final PatientDAO patientDAOMock = Mockito.mock(PatientDAO.class);


        final String validSSN = "1".repeat(9);
        objectUnderTest.setScanner(new Scanner(validSSN));
        objectUnderTest.setPatientDAO(patientDAOMock);

        Mockito.when(patientDAOMock.isDataExist(validSSN)).thenReturn(true);

        final SSN actual = objectUnderTest.getSSN();

        Mockito.verify(patientDAOMock).isDataExist(validSSN);

        Assertions.assertThat(actual).isEqualTo(new SSN(validSSN, true));
    }
}