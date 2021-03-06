package com.NaTicket.n.flights.adpaters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.flights.Flights_International_Return;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.InternationalFlightsDTO;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ankit on 31-12-2017.
 */

public class International_Return_Adapter extends RecyclerView.Adapter<International_Return_Adapter.ViewHolder> {

    ArrayList<InternationalFlightsDTO> mFilteredList=new ArrayList<>();
    Flights_International_Return activity;
    Currency_Utils currency_utils;
    private static final String TAG = "InternationalListAdptr";

    public International_Return_Adapter(Flights_International_Return flights_international_return,
                                        ArrayList<InternationalFlightsDTO> availableFlights) {

        this.mFilteredList=availableFlights;
        this.activity=flights_international_return;
        currency_utils=new Currency_Utils(activity);
    }

    @Override
    public International_Return_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flights_international_return, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(International_Return_Adapter.ViewHolder viewHolder, final int position) {

        double curr_value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String CurrencySymbol=currency_utils.getCurrencyValue("Currency_Symbol");

        viewHolder.FlightName_tv.setText(mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getAirLineName());
        viewHolder.code_number_tv.setText(mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getOperatingAirlineCode()+" - "+
                mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getOperatingAirlineFlightNumber());
        if (mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule().equals("Refundable")){
            viewHolder.Refundable_tv.setText("      " + mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }else {
            viewHolder.Refundable_tv.setText("" + mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }

        double d = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getActualBaseFare()));
        double Servicecharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getTax()));
        double Operatercharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTCharge()));
        double Markepcharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTMarkup()));

        final double TotalFare=d+Servicecharge+Operatercharge+Markepcharge;
        viewHolder.netFares_tv.setText(CurrencySymbol+""+ Util.getprice(TotalFare*curr_value)+"");

        String DepartureTime,ArravialTime;
        int size=mFilteredList.get(position).getIntOnward().getFlightSegments().size();
        if(mFilteredList.get(position).getIntOnward().getFlightSegments().size()==1){
            DepartureTime= Flight_Utils.getTime(mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime=Flight_Utils.getTime(mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getArrivalDateTime());
        }else{
            DepartureTime= Flight_Utils.getTime(mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime=Flight_Utils.getTime(mFilteredList.get(position).getIntOnward().getFlightSegments().get(size-1).getArrivalDateTime());
        }



        String Numberofstops=Flight_Utils.getLength(mFilteredList.get(position).getIntOnward().getFlightSegments().size());

        viewHolder.FlightsTimetv.setText(""+DepartureTime+" - "+ArravialTime);


        if(mFilteredList.get(position).getIntOnward().getFlightSegments().size()==1){
            DepartureTime= mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime=mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getArrivalDateTime();
        }else{
            DepartureTime= mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime=mFilteredList.get(position).getIntOnward().getFlightSegments().get(size-1).getArrivalDateTime();
        }

        String Onward_Duration=Flight_Utils.getDuration(DepartureTime.replace("T"," "),ArravialTime.replace("T"," "));
       /* int FlightsegmentsSize=mFilteredList.get(position).getIntOnward().getFlightSegments().size()-1;
        if (mFilteredList.get(position).getIntOnward().getFlightSegments().size()==1){
            Onward_Duration=mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getDuration();
        }else {
            Onward_Duration=mFilteredList.get(position).getIntOnward().getFlightSegments().get(FlightsegmentsSize).getAccumulatedDuration();
        }*/

        viewHolder.duration_tv.setText(" " + Onward_Duration + " | " + Numberofstops + " - Stop");



        String url_1= Constants.FLIGHTS_IMAGE_URL+mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getImageFileName()+".png";
        Glide.with(activity)
                .load(url_1)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.sperit);

        //Bitmap bitmap=Flight_Utils.getImage(activity,mFilteredList.get(position).getIntOnward().getFlightSegments().get(0).getImageFileName());
        //viewHolder.sperit.setImageBitmap(bitmap);

        viewHolder.ret_flight_name_tv.setText(mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getAirLineName());
        viewHolder.ret_code_number_tv.setText(mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getOperatingAirlineCode()+" - "+
                mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getOperatingAirlineFlightNumber());
        if (mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getBookingClassFare().getRule().equals("Refundable")){
            viewHolder.ret_refundable_tv.setText("      " + mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }else {
            viewHolder.ret_refundable_tv.setText("" + mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }

        double d2 = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getActualBaseFare()));
        double Servicecharge2 = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getTax()));
        double Operatercharge2 = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTCharge()));
        double Markepcharge2 = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTMarkup()));

        double TotalFare2=d2+Servicecharge2+Operatercharge2+Markepcharge2;
        viewHolder.netFares_tv.setText(CurrencySymbol+""+ Util.getprice(TotalFare2*curr_value)+"");


        String DepartureTime2,ArravialTime2;
        int size1=mFilteredList.get(position).getIntReturn().getFlightSegments().size();

        if(mFilteredList.get(position).getIntReturn().getFlightSegments().size()==1) {
            DepartureTime2 = Flight_Utils.getTime(mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime2 = Flight_Utils.getTime(mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getArrivalDateTime());
        }else{
            DepartureTime2 = Flight_Utils.getTime(mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime2 = Flight_Utils.getTime(mFilteredList.get(position).getIntReturn().getFlightSegments().get(size1-1).getArrivalDateTime());
        }

        viewHolder.ret_flight_time_tv.setText(""+DepartureTime2+" - "+ArravialTime2);

        String Numberofstops2=Flight_Utils.getLength(mFilteredList.get(position).getIntReturn().getFlightSegments().size());



        if(mFilteredList.get(position).getIntReturn().getFlightSegments().size()==1) {
            DepartureTime2 = mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime2 = mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getArrivalDateTime();
        }else{
            DepartureTime2 = mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime2 = mFilteredList.get(position).getIntReturn().getFlightSegments().get(size1-1).getArrivalDateTime();
        }
        String Return_Duration=Flight_Utils.getDuration(DepartureTime2.replace("T"," "),ArravialTime2.replace("T"," "));
        int Return_FlightsegmentsSize=mFilteredList.get(position).getIntReturn().getFlightSegments().size()-1;
        /*if (mFilteredList.get(position).getIntReturn().getFlightSegments().size()==1){
            Return_Duration=mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getDuration();
        }else {
            Return_Duration=mFilteredList.get(position).getIntReturn().getFlightSegments().get(Return_FlightsegmentsSize).getAccumulatedDuration();
        }*/


        viewHolder.ret_dur_tv.setText(" " + Return_Duration + " | " + Numberofstops2 + " - Stop");

        String url= Constants.FLIGHTS_IMAGE_URL+mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getImageFileName()+".png";
        Glide.with(activity)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.ret_sperit);

        //Bitmap bitmap2=Flight_Utils.getImage(activity,mFilteredList.get(position).getIntReturn().getFlightSegments().get(0).getImageFileName());
        //viewHolder.ret_sperit.setImageBitmap(bitmap2);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFlightData(mFilteredList.get(position),TotalFare);
            }
        });

    }







    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView code_number_tv, FlightName_tv, FlightsTimetv, duration_tv, netFares_tv, Refundable_tv,CouponFare;
        TextView ret_code_number_tv,ret_flight_name_tv,ret_flight_time_tv,ret_dur_tv,ret_refundable_tv;
        ImageView sperit,ret_sperit;
        public ViewHolder(View view) {
            super(view);
            sperit = (ImageView) view.findViewById(R.id.spirte);
            code_number_tv = (TextView) view.findViewById(R.id.Flight_ID_tv);
            FlightName_tv = (TextView) view.findViewById(R.id.flightName_tv);
            FlightsTimetv = (TextView) view.findViewById(R.id.Flight_TImes_tv);
            Refundable_tv = (TextView) view.findViewById(R.id.Flight_Refundable_tv);
            duration_tv = (TextView) view.findViewById(R.id.Flight_Duritation_tv);
            netFares_tv = (TextView) view.findViewById(R.id.Flight_NetFare_tv);
            CouponFare = (TextView) view.findViewById(R.id.Flight_RPH_tv);

            ret_sperit= (ImageView) view.findViewById(R.id.ret_spirte);
            ret_code_number_tv= (TextView) view.findViewById(R.id.ret_Flight_ID);
            ret_flight_name_tv= (TextView) view.findViewById(R.id.ret_flightName);
            ret_flight_time_tv= (TextView) view.findViewById(R.id.ret_Flight_TImes);
            ret_dur_tv= (TextView) view.findViewById(R.id.ret_Flight_Duritation);
            ret_refundable_tv= (TextView) view.findViewById(R.id.ret_Flight_Refundable);
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public ArrayList<InternationalFlightsDTO> sortByNameAsc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {
                return object1.getIntOnward().getFlightSegments().get(0).getAirLineName().compareToIgnoreCase(object2.getIntOnward().getFlightSegments().get(0).getAirLineName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<InternationalFlightsDTO> sortByNameDesc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {
                return object2.getIntOnward().getFlightSegments().get(0).getAirLineName().compareToIgnoreCase(object1.getIntOnward().getFlightSegments().get(0).getAirLineName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }

    public ArrayList<InternationalFlightsDTO> sortByTimeAsc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {

                String value1 = ""+Flight_Utils.getTime(object1.getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
                String value2 = ""+Flight_Utils.getTime(object2.getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
                return value1.compareTo(value2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<InternationalFlightsDTO> sortByTimeDesc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {
                String value1 = ""+Flight_Utils.getTime(object1.getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
                String value2 = ""+Flight_Utils.getTime(object2.getIntOnward().getFlightSegments().get(0).getDepartureDateTime());
                return value2.compareTo(value1);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<InternationalFlightsDTO> sortByPriceAsc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {
                double Total1=object1.getFareDetails().getChargeableFares().getActualBaseFare()+object1.getFareDetails().getChargeableFares().getTax()+
                        object1.getFareDetails().getNonchargeableFares().getTCharge()+object1.getFareDetails().getNonchargeableFares().getTMarkup();

                double Total2=object2.getFareDetails().getChargeableFares().getActualBaseFare()+object2.getFareDetails().getChargeableFares().getTax()+
                        object2.getFareDetails().getNonchargeableFares().getTCharge()+object2.getFareDetails().getNonchargeableFares().getTMarkup();
                float f1,f2;
                f1 = (Float) Float.parseFloat(String.valueOf(Total1));
                f2 = (Float) Float.parseFloat(String.valueOf(Total2));
                return ((Float) f1).compareTo(f2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<InternationalFlightsDTO> sortByPriceDesc() {
        Comparator<InternationalFlightsDTO> comparator = new Comparator<InternationalFlightsDTO>() {
            @Override
            public int compare(InternationalFlightsDTO object1, InternationalFlightsDTO object2) {
                double Total1=object1.getFareDetails().getChargeableFares().getActualBaseFare()+object1.getFareDetails().getChargeableFares().getTax()+
                        object1.getFareDetails().getNonchargeableFares().getTCharge()+object1.getFareDetails().getNonchargeableFares().getTMarkup();

                double Total2=object2.getFareDetails().getChargeableFares().getActualBaseFare()+object2.getFareDetails().getChargeableFares().getTax()+
                        object2.getFareDetails().getNonchargeableFares().getTCharge()+object2.getFareDetails().getNonchargeableFares().getTMarkup();
                float f1,f2;
                f1 = (Float) Float.parseFloat(String.valueOf(Total1));
                f2 = (Float) Float.parseFloat(String.valueOf(Total2));
                return ((Float) f2).compareTo(f1);
            }
        };

        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public void refreshList(ArrayList<InternationalFlightsDTO> list){
        if (list != null) {
            this.mFilteredList= list;
            for (InternationalFlightsDTO ho : mFilteredList) {
                Log.e(TAG, "refreshList: " + ho.getFareDetails().getChargeableFares().getNetFare());
            }
            notifyDataSetChanged();
        }
    }
}
