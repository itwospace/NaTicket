package com.NaTicket.n.flights.adpaters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.flights.Flights_Domestic_Onward;
import com.NaTicket.n.flights.pojo.DomesticOnwardFlightDTO;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ankit on 31-12-2017.
 */

public class Domestic_Onward_Adapter extends RecyclerView.Adapter<Domestic_Onward_Adapter.ViewHolder>{

    ArrayList<DomesticOnwardFlightDTO> mFilteredList=new ArrayList<>();
    Flights_Domestic_Onward activity;
    Currency_Utils currency_utils;
    private static final String TAG = "DomesticListAdapter";

    public Domestic_Onward_Adapter(Flights_Domestic_Onward flights_onward_activity, ArrayList<DomesticOnwardFlightDTO> availableFlights) {
        this.mFilteredList=availableFlights;
        this.activity=flights_onward_activity;
        currency_utils=new Currency_Utils(activity);
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Domestic_Onward_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flights_dom_onward, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Domestic_Onward_Adapter.ViewHolder viewHolder, final int position) {

        double curr_value= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String CurrencySymbol=currency_utils.getCurrencyValue("Currency_Symbol");

        viewHolder.FlightName_tv.setText(mFilteredList.get(position).getFlightSegments().get(0).getAirLineName());
        viewHolder.code_number_tv.setText(mFilteredList.get(position).getFlightSegments().get(0).getOperatingAirlineCode()+" - "+
                                   mFilteredList.get(position).getFlightSegments().get(0).getOperatingAirlineFlightNumber());
        if (mFilteredList.get(position).getFlightSegments().get(0).getBookingClassFare().getRule().equals("Refundable")){
            viewHolder.Refundable_tv.setText("      " + mFilteredList.get(position).getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }else {
            viewHolder.Refundable_tv.setText("" + mFilteredList.get(position).getFlightSegments().get(0).getBookingClassFare().getRule() + "");
        }

        double d = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getActualBaseFare()));
        double Servicecharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getChargeableFares().getTax()));
        double Operatercharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTCharge()));
        double Markepcharge = Double.parseDouble(String.valueOf(mFilteredList.get(position).getFareDetails().getNonchargeableFares().getTMarkup()));

        final double TotalFare=d+Servicecharge+Operatercharge+Markepcharge;
        viewHolder.netFares_tv.setText(CurrencySymbol+""+ Util.getprice(TotalFare*curr_value)+"");

        String DepartureTime,ArravialTime;
        int size=mFilteredList.get(position).getFlightSegments().size();

        if(mFilteredList.get(position).getFlightSegments().size()==1){
            DepartureTime= Flight_Utils.getTime(mFilteredList.get(position).getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime=Flight_Utils.getTime(mFilteredList.get(position).getFlightSegments().get(0).getArrivalDateTime());
        }else{
            DepartureTime= Flight_Utils.getTime(mFilteredList.get(position).getFlightSegments().get(0).getDepartureDateTime());
            ArravialTime=Flight_Utils.getTime(mFilteredList.get(position).getFlightSegments().get(size-1).getArrivalDateTime());
        }

        viewHolder.FlightsTimetv.setText(""+DepartureTime+" - "+ArravialTime);

        if(mFilteredList.get(position).getFlightSegments().size()==1){
            DepartureTime= mFilteredList.get(position).getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime=mFilteredList.get(position).getFlightSegments().get(0).getArrivalDateTime();
        }else{
            DepartureTime= mFilteredList.get(position).getFlightSegments().get(0).getDepartureDateTime();
            ArravialTime=mFilteredList.get(position).getFlightSegments().get(size-1).getArrivalDateTime();
        }

        String Onward_Duration=Flight_Utils.getDuration(DepartureTime.replace("T"," "),ArravialTime.replace("T"," "));

        String Numberofstops=Flight_Utils.getLength(mFilteredList.get(position).getFlightSegments().size());

        /*String Duration;
        int FlightsegmentsSize=mFilteredList.get(position).getFlightSegments().size()-1;
        if (mFilteredList.get(position).getFlightSegments().size()==1){
            Duration=mFilteredList.get(position).getFlightSegments().get(0).getDuration();
        }else {
            Duration=mFilteredList.get(position).getFlightSegments().get(FlightsegmentsSize).getAccumulatedDuration();
        }*/

       
        viewHolder.duration_tv.setText(" " + Onward_Duration+ " | " + Numberofstops + " - Stop");

        String url= Constants.FLIGHTS_IMAGE_URL+mFilteredList.get(position).getFlightSegments().get(0).getImageFileName()+".png";

        Glide.with(activity)
                .load(url)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.sperit);


        //Bitmap bitmap=Flight_Utils.getImage(activity,mFilteredList.get(position).getFlightSegments().get(0).getImageFileName());
        //viewHolder.sperit.setImageBitmap(bitmap);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFlightData(TotalFare,mFilteredList.get(position));
            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView code_number_tv, FlightName_tv, FlightsTimetv, duration_tv, netFares_tv, Refundable_tv,CouponFare;
        ImageView sperit;
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
        }
    }


    public ArrayList<DomesticOnwardFlightDTO> sortByNameAsc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {
                return object1.getFlightSegments().get(0).getAirLineName().compareToIgnoreCase(object2.getFlightSegments().get(0).getAirLineName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<DomesticOnwardFlightDTO> sortByNameDesc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {
                return object2.getFlightSegments().get(0).getAirLineName().compareToIgnoreCase(object1.getFlightSegments().get(0).getAirLineName());
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }

    public ArrayList<DomesticOnwardFlightDTO> sortByTimeAsc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {

                String value1 = ""+Flight_Utils.getTime(object1.getFlightSegments().get(0).getDepartureDateTime());
                String value2 = ""+Flight_Utils.getTime(object2.getFlightSegments().get(0).getDepartureDateTime());
                return value1.compareTo(value2);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<DomesticOnwardFlightDTO> sortByTimeDesc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {
                String value1 = ""+Flight_Utils.getTime(object1.getFlightSegments().get(0).getDepartureDateTime());
                String value2 = ""+Flight_Utils.getTime(object2.getFlightSegments().get(0).getDepartureDateTime());
                return value2.compareTo(value1);
            }
        };
        Collections.sort(mFilteredList, comparator);
        return mFilteredList;
    }


    public ArrayList<DomesticOnwardFlightDTO> sortByPriceAsc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {

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


    public ArrayList<DomesticOnwardFlightDTO> sortByPriceDesc() {
        Comparator<DomesticOnwardFlightDTO> comparator = new Comparator<DomesticOnwardFlightDTO>() {
            @Override
            public int compare(DomesticOnwardFlightDTO object1, DomesticOnwardFlightDTO object2) {
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


    public void refreshList(ArrayList<DomesticOnwardFlightDTO> list){
        if (list != null) {
            this.mFilteredList= list;
            for (DomesticOnwardFlightDTO ho : mFilteredList) {
                Log.e(TAG, "refreshList:  " + ho.getFlightSegments().get(0).getAirLineName());
            }
            notifyDataSetChanged();
        }
    }
}
