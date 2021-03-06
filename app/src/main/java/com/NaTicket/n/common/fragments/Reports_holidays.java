package com.NaTicket.n.common.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.fragments.adapters.Adapter_Holiday_Reports;
import com.NaTicket.n.holidays.pojo.Holiday_Ticket_Details_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.Service_Reports;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 14-01-2018.
 */

public class Reports_holidays extends Fragment{

    ProgressDialog mProgressDialog;
    RecyclerView recyclerView;
    Login_utils login_utils;
    TextView no_recordFound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.reports,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        no_recordFound= (TextView) view.findViewById(R.id.no_recordFound);

        no_recordFound.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        login_utils=new Login_utils(getActivity());
        CallHolidayReports();
    }





    private void CallHolidayReports() {
        if (Util.isNetworkAvailable(getActivity())) {
            showProgressDialog("Getting Holiday Reports",getActivity());
            Service_Reports.getHolidayReports(Reports_holidays.this, Constants.REPORTS,preparePayload());
        }else{
            Util.showMessage(getActivity(),Constants.NO_INT_MSG);
        }
    }

    public void postHolidayResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Holiday_Ticket_Details_DTO[] hotel_reports=gson.fromJson(reader, Holiday_Ticket_Details_DTO[].class);
            if (hotel_reports!=null){
                no_recordFound.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Adapter_Holiday_Reports mAdapter = new Adapter_Holiday_Reports(getActivity(),hotel_reports);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
            }else{
                no_recordFound.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }


    public void errorresponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }


    private void showAlertDialogforError(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CallHolidayReports();
            }
        });
        alertDialog.show();
    }


    private String preparePayload() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("AgentId", login_utils.getUserDetails(Constants.DEPRCYT_USER_ID));
            jsonObject.put("BookingStatus", "");
            jsonObject.put("Role", login_utils.getClientDetails(Constants.USERTYPE));
            jsonObject.put("Service", "6");
            jsonObject.put("Fromdate", "");
            jsonObject.put("ToDate", "");
            jsonObject.put("BookingRefNo", "");
            jsonObject.put("APIReferenceNo", "");
            jsonObject.put("RechargeType", "");
            jsonObject.put("RechargeNumber", "");
            jsonObject.put("ClientId", login_utils.getClientDetails(Constants.PARENTID));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
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
}
