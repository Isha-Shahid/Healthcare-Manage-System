package model;

import java.util.ArrayList;
import java.util.List;

public class ReferralManager {
    private static ReferralManager instance;
    private List<Referral> referrals = new ArrayList<>();

    private ReferralManager() {}

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public List<Referral> getReferrals() { return referrals; }
    public void addReferral(Referral r) { referrals.add(r); }
}
