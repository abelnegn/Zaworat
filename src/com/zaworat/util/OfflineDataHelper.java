package com.zaworat.util;

import com.zaworat.objects.db.ZaworatBalance;

public class OfflineDataHelper {
    private OfflineDataSaveListener offlineDataSaveListener;
    private String tag = getClass().getSimpleName();

    public void setOfflineDataSaveListener(OfflineDataSaveListener offlineDataSaveListener) {
        this.offlineDataSaveListener = offlineDataSaveListener;
    }
   
//    public void saveZaworatBalance(ZaworatBalance zBalance){
//    	WeddingCardRingProtocol newWCRP = new WeddingCardRingProtocol();
//    	newWCRP.setWcrpId(wcrp.getWcrpId());
//    	newWCRP.setWeddingCRPName(wcrp.getWeddingCRPName());
//    	newWCRP.setLocationId(wcrp.getLocationId());
//    	newWCRP.setPrice(wcrp.getPrice());
//    	newWCRP.setDiscription(wcrp.getDiscription());
//    	newWCRP.setMemberId(wcrp.getMemberId());
//    	newWCRP.setUser_Name(wcrp.getUser_Name());
//    	newWCRP.setIsFeatured(wcrp.getIsFeatured());
//    	
//    	newWCRP.save();
//    	
//    	Log.i(tag, "--------All Wedding Card, Ring and Protocol data saved to DB---------");
//    	
//	    if (offlineDataSaveListener != null) {
//	        offlineDataSaveListener.dataSaved();
//	    }
//    	
//    }
    public interface OfflineDataSaveListener {
        public void dataSaved();
    }
}
