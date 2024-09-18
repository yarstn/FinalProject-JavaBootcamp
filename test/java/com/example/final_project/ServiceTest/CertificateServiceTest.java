package com.example.final_project.ServiceTest;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import com.example.final_project.Service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CertificateServiceTest {

    @InjectMocks
    private CertificateService certificateService;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private ParentReposotiry parentRepository;

    @Mock
    private ChildRepository childRepository;

    @Mock
    private ProgramRepository programRepository;

    private Certificate certificate;
    private Child child;
    private Parent parent;
    private User user;
    private Program program;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize entities
        user = new User();
        user.setId(1);
        user.setUsername("parent");
        user.setRole("PARENT");

        parent = new Parent();
        parent.setUser(user);

        child = new Child();
        child.setId(1);
        child.setParent(parent);

        program = new Program();
        program.setId(1);
        program.setTitle("Math Competition");
        program.setEndDate(LocalDate.now().minusDays(1));  // Program has ended

        certificate = new Certificate();
        certificate.setCertificateDescription("Math Certificate Description XXX");
        certificate.setProgram_Competition("Math Competition");
        certificate.setGraduation_date(LocalDate.now().minusDays(1));
        certificate.setCenterName("Center A");
        certificate.setChild(child);
    }

    @Test
    void getAllCertificates_ReturnAllCertificates() {
        when(certificateRepository.findAll()).thenReturn(List.of(certificate));

        List<Certificate> certificates = certificateService.getAllCertificates();

        assertNotNull(certificates);
        assertEquals(1, certificates.size());
        verify(certificateRepository, times(1)).findAll();
    }

    @Test
    void childCertificates_CertificatesForGivenChild() {
        when(authRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(parentRepository.findParentById(user.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findChildById(1)).thenReturn(Optional.of(child));
        when(certificateRepository.findCertificateByChild(child)).thenReturn(List.of(certificate));

        List<Certificate> certificates = certificateService.childCertificates(1, 1);

        assertNotNull(certificates);
        assertEquals(1, certificates.size());
        verify(certificateRepository, times(1)).findCertificateByChild(child);
    }

    @Test
    void childCertificates_UserNotFound() {
        when(authRepository.findUserById(1)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> certificateService.childCertificates(1, 1));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void childCertificates_ParentNotFound() {

        when(authRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(parentRepository.findParentById(user.getId())).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> certificateService.childCertificates(1, 1));
        assertEquals("parent not found", exception.getMessage());
    }

    @Test
    void childCertificates_childNotfound() {

        when(authRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(parentRepository.findParentById(user.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findChildById(1)).thenReturn(Optional.empty());


        ApiException exception = assertThrows(ApiException.class, () -> certificateService.childCertificates(1, 1));
        assertEquals("child not found", exception.getMessage());
    }

    @Test
    void childCertificates() {

        Parent differentParent = new Parent(); // Different parent
        when(authRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(parentRepository.findParentById(user.getId())).thenReturn(Optional.of(parent));
        when(childRepository.findChildById(1)).thenReturn(Optional.of(child));
        child.setParent(differentParent);  // Set the child with a different parent

        ApiException exception = assertThrows(ApiException.class, () -> certificateService.childCertificates(1, 1));
        assertEquals("Parent and child are not the same", exception.getMessage());
    }

//    @Test
//    void issueCertificate_IssuedCertificate() {
//        when(childRepository.findById(1)).thenReturn(Optional.of(child));
//        when(programRepository.findById(1)).thenReturn(Optional.of(program));
//        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);
//
//        Certificate issuedCertificate = certificateService.issueCertificate(1, 1, certificate);
//
//        assertNotNull(issuedCertificate);
//        assertEquals("Math Competition", issuedCertificate.getProgram_Competition());
//        verify(certificateRepository, times(1)).save(certificate);
//    }

    @Test
    void issueCertificate() {

        program.setEndDate(LocalDate.now().plusDays(1));  // Program has not ended
        when(childRepository.findById(1)).thenReturn(Optional.of(child));
        when(programRepository.findById(1)).thenReturn(Optional.of(program));


        ApiException exception = assertThrows(ApiException.class, () -> certificateService.issueCertificate(1, 1, certificate));
        assertEquals("Program has not ended yet", exception.getMessage());
    }
}