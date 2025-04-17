package com.mcarchieve.mcarchieve.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
class SessionRepositoryTest {

//    @Autowired
//    private SessionRepository sessionRepository;
//
//    @Test
//    public void testSaveSession() {
//        User owner = new User();
//        Server server = new Server();
//
//        Session session = new Session();
//        session.setName("Session1");
//        session.setOwner(owner);
//        session.setServer(server);
//        session.setStartDate(LocalDate.now());
//        session.setEndDate(LocalDate.now().plusDays(10));
//
//        Session savedSession = sessionRepository.save(session);
//
//        assertNotNull(savedSession.getId());
//        assertEquals("Session1", savedSession.getName());
//        assertNotNull(savedSession.getOwner());
//        assertNotNull(savedSession.getServer());
//        assertNotNull(savedSession.getStartDate());
//        assertNotNull(savedSession.getEndDate());
//    }
//
//    @Test
//    public void testFindSessionById() {
//        User owner = new User();
//        Server server = new Server();
//
//        Session session = new Session(null, "Session1", owner, server, LocalDate.now(), LocalDate.now().plusDays(10));
//        session = sessionRepository.save(session);
//
//        Optional<Session> foundSession = sessionRepository.findById(session.getId());
//
//        assertTrue(foundSession.isPresent());
//        assertEquals("Session1", foundSession.get().getName());
//    }
//
//    @Test
//    public void testDeleteSession() {
//        User owner = new User();
//        Server server = new Server();
//
//        Session session = new Session(null, "Session1", owner, server, LocalDate.now(), LocalDate.now().plusDays(10));
//        session = sessionRepository.save(session);
//
//        sessionRepository.delete(session);
//
//        Optional<Session> deletedSession = sessionRepository.findById(session.getId());
//        assertFalse(deletedSession.isPresent());
//    }
//
//    @Test
//    public void testUpdateSession() {
//        User owner = new User();
//        Server server = new Server();
//
//        Session session = new Session(null, "Session1", owner, server, LocalDate.now(), LocalDate.now().plusDays(10));
//        session = sessionRepository.save(session);
//
//        session.setName("Session2");
//        Session updatedSession = sessionRepository.save(session);
//
//        assertEquals("Session2", updatedSession.getName());
//    }
}