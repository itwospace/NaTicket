package com.NaTicket.n.flights;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.NaTicket.n.serviceclasses.Service_SavingPayments;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.R;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Nagarjuna on 1/4/2018.
 */

public class Flight_Booking_Activity extends AppCompatActivity {


    String data, refno, PaymentType,Response,RequestParams,Paymentgatewayid,msg;
    String mfirstname; // From Previous Activity
    String memail, amt; // From Previous Activity
    double mamount; // From Previous Activity
    String mphone, IsAgent; // From Previous Activity
    InputStream inputStream;
    Boolean PayMentstatus;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_booking);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            mfirstname = bundle.getString("name");
            memail = bundle.getString("email");
            amt = bundle.getString("Amount");
            mphone = bundle.getString("phone");
            refno = bundle.getString("referencenumber");
            System.out.println("referenceNo  :" + refno + "Amount  :" + amt);
            System.out.println("referenceNo" + refno);
            PaymentType = bundle.getString("PaymentType");
            IsAgent = bundle.getString("IsAgent");
            Paymentgatewayid=bundle.getString("PaymentGatewayId");
            Response=bundle.getString("Response");
            RequestParams=bundle.getString("Request");
            PayMentstatus=bundle.getBoolean("Status");
            msg=bundle.getString("PaymentId");

            if (IsAgent.equals("Yes")) {
                callBookFlight();
            } else {
                if (PayMentstatus) {
                    callUpdatePaymentParams(msg);
                }
            }
            //  String amt="1";
            Log.i(amt, "" + mfirstname + " : " + memail + " : " + mphone + " : " + refno);
        }

    }


    /*private void callSavePaymentDetails() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            Service_SavingPayments.SavePaymentDetails(Flight_Booking_Activity.this, Constants.SAVEPAYMENTDETAILS,preparePaymentdetails());
        }else{
            Util.showMessage(Flight_Booking_Activity.this,Constants.NO_INT_MSG);
        }
    }


    private String preparePaymentdetails() {
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("PGResponse",null);
            jsonBody.put("PaymentId",0);
            jsonBody.put("PaymentGatewayId",Paymentgatewayid);
            jsonBody.put("UserId",0);
            jsonBody.put("ReferenceNumber",refno);
            jsonBody.put("Amount",amt);
            jsonBody.put("TransactionCharges",0);
            jsonBody.put("ResponseString","Request sent to payment gateway");
            jsonBody.put("PaymentStatus",3);
            jsonBody.put("IPAddress","::1");
            jsonBody.put("PaymentGatewayName","Atom");
            jsonBody.put("Request","null");
            jsonBody.put("ClientId",0);
            jsonBody.put("PGRequest",RequestParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postSaveDetailsParams(String response){
        if (response!=null){
            if(Util.getResponseCode(response)==200){
                if(Util.getResponseMessage(response)!=null) {
                    callUpdatePaymentParams(Util.getResponseMessage(response));
                }else {
                    hideProgressDialog();
                    showAlertDialog("Booking Failed !\n"+Util.getResponseMessage(response));
                }
            }else{
                Util.showMessage(this,"Something Went Wrong!");
            }
        }
    }*/

    private void callUpdatePaymentParams(String Message) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            Service_SavingPayments.UpdatePaymentDetails(Flight_Booking_Activity.this, Constants.UPDATEPAYMENTDETAILS,prepareUpdatePaymentdetails(Message));
            showProgressDialog("Please wait",this);
        }else{
            Util.showMessage(Flight_Booking_Activity.this,Constants.NO_INT_MSG);
        }
    }

    private String prepareUpdatePaymentdetails(String Message) {
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("PGResponse",Response);
            jsonBody.put("PaymentId",Message);
            jsonBody.put("PaymentGatewayId",Paymentgatewayid);
            jsonBody.put("UserId",0);
            jsonBody.put("ReferenceNumber",refno);
            jsonBody.put("Amount",amt);
            jsonBody.put("TransactionCharges",0);
            jsonBody.put("ResponseString",Response);
            jsonBody.put("PaymentStatus",1);
            jsonBody.put("IPAddress","::1");
            jsonBody.put("PaymentGatewayName","Atom");
            jsonBody.put("Request","null");
            jsonBody.put("ClientId",0);
            jsonBody.put("PGRequest",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postUpdateDetailsParams(String response){
        if (response!=null){
            if(Util.getResponseCode(response)==200){
                if(Util.getResponseMessage(response)!=null) {
                    callBookFlight();
                }else {
                    hideProgressDialog();
                    showAlertDialog("Booking Failed !\n"+Util.getResponseMessage(response));
                }
            }else{
                Util.showMessage(this,"Something Went Wrong!");
            }
        }
    }



    private void callBookFlight(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait",this);
            ServiceClasses.BookFlight(Flight_Booking_Activity.this, Constants.FlightBookingTicket+refno);
        }else{
            Util.showMessage(Flight_Booking_Activity.this, Constants.NO_INT_MSG);
        }
    }

    public void getBookingResponse(String Response){
        hideProgressDialog();
        if(Util.getBlockResponseCode(Response)==200) {
                if (Util.getBookingStatus(Response).matches("3")){
                    Intent ip=new Intent(Flight_Booking_Activity.this,Flight_Ticket_Details.class);
                    ip.putExtra("referencenumber",refno);
                    startActivity(ip);
                }else {


                    showAlertDialog(Util.getResponseMessage(Response)+"\nBooking Failed... !\n"+Util.getResponseMessage(Response));
                }
        }else {
            showAlertDialog(Util.getResponseMessage(Response)+"\nBooking Failed!! \n");
        }
    }


    private  void showProgressDialog(String msg,Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();


    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }


    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent ip = new Intent(Flight_Booking_Activity.this,Flights_Search_Activity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
            }
        });
        alertDialog.show();
    }
}
