package com.tehila.coupons.timerTask;

import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.logic.CouponsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimerTask;
@Service
public class DeleteExpiredCouponsTimerTask extends TimerTask {

    private CouponsLogic couponsLogic;

    @Autowired
    public DeleteExpiredCouponsTimerTask(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    @Override
    public void run() {
//        try {
////            couponsLogic.deleteExpiredCoupons();
//            System.out.println("Deleted expired coupons");
//        } catch (ServerException e) {
//            e.printStackTrace();
//        }
    }
}
