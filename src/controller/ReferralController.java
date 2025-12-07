package controller;

import model.Referral;
import java.util.ArrayList;
import java.util.List;

public class ReferralController {
    private List<Referral> referrals = new ArrayList<>();

    public void createReferral(Referral r) { referrals.add(r); }
    public List<Referral> getReferralsForPatient(String patientId) {
        List<Referral> result = new ArrayList<>();
        for (Referral r : referrals)
            if (r.getPatientId().equals(patientId)) result.add(r);
        return result;
    }
}
