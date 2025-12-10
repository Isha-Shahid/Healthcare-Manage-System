package controller;

import model.Referral;
import model.ReferralManager;
import model.DataStore;
import java.util.List;
import java.util.stream.Collectors;

public class ReferralController {

    public void createReferral(Referral r) {
        ReferralManager.getInstance().addReferral(r);
        DataStore.saveReferrals(ReferralManager.getInstance().getReferrals(), "referrals.csv");
    }

    public List<Referral> getReferralsForPatient(String patientId) {
        return ReferralManager.getInstance().getReferrals()
                .stream()
                .filter(r -> r.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }
}
