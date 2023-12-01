package com.GustaveEiffelTutoring.server;

import com.GustaveEiffelTutoring.common.ITutor;
import com.GustaveEiffelTutoring.common.ITutorsList;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class RebindService {

    private static final int CHECK_INTERVAL = 60000;

    private ITutorsList tutors;

    public RebindService(ITutorsList tutors) {
        this.tutors = tutors;
    }

    public void startRebinding() {
        TutorRebindThread thread = new TutorRebindThread(tutors);
        thread.start();
    }

    private static class TutorRebindThread extends Thread {

        private ITutorsList tutors;
        private int previousSize = 0;

        public TutorRebindThread(ITutorsList tutors) {
            this.tutors = tutors;
        }

        @Override
        public void run() {
            try {
                LocateRegistry.createRegistry(1095);
                String rmiUrl = "rmi://localhost:1095/tutors";

                while (!isInterrupted()) {
                    HashMap<Integer, ITutor> tutorsList = tutors.getAllTutors();
                    if (tutorsList.size() > previousSize) {
                        try {
                            Naming.unbind(rmiUrl);
                        } catch (Exception unbindException) {
                            System.out.println(unbindException.getMessage());
                        }
                        System.out.println("======== Rebinding : ========= ");
                        Naming.rebind(rmiUrl, tutors);
                        System.out.println(tutors);
                        System.out.println("Remote object rebound successfully");

                        previousSize = tutorsList.size();
                    }

                    Thread.sleep(CHECK_INTERVAL);
                }
            } catch (InterruptedException e) {
                System.out.println("Rebinding thread interrupted.");
            } catch (Exception e) {
                System.out.println("Error rebinding tutors: " + e.getMessage());
            }
        }
    }
}