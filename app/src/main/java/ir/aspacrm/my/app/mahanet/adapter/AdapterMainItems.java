package ir.aspacrm.my.app.mahanet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import ir.aspacrm.my.app.mahanet.ActivityChangePassword;
import ir.aspacrm.my.app.mahanet.ActivityChargeOnline;
import ir.aspacrm.my.app.mahanet.ActivityPayments;
import ir.aspacrm.my.app.mahanet.ActivityPoints;
import ir.aspacrm.my.app.mahanet.ActivityShowClubScores;
import ir.aspacrm.my.app.mahanet.ActivityShowConnections;
import ir.aspacrm.my.app.mahanet.ActivityShowFactors;
import ir.aspacrm.my.app.mahanet.ActivityShowFeshfeshe;
import ir.aspacrm.my.app.mahanet.ActivityShowNews;
import ir.aspacrm.my.app.mahanet.ActivityShowNotify;
import ir.aspacrm.my.app.mahanet.ActivityShowTickets;
import ir.aspacrm.my.app.mahanet.ActivityShowTrafficeSplite;
import ir.aspacrm.my.app.mahanet.ActivityShowUserInfo;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahanet.enums.EnumMainItemsTag;
import ir.aspacrm.my.app.mahanet.model.MainItems;


/**
 * Created by FCC on 10/8/2017.
 */

public class AdapterMainItems extends RecyclerView.Adapter<AdapterMainItems.AdapterMainItemsHolder> {

    List<MainItems> mainItemses;
    Context context;

    public AdapterMainItems(List<MainItems> mainItemses, Context context) {
        this.mainItemses = mainItemses;
        this.context = context;
    }

    @Override
    public AdapterMainItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.l_main_items, parent, false);
        return new AdapterMainItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterMainItemsHolder holder, int position) {
        final MainItems mainItem = mainItemses.get(position);
        holder.imgImage.setBackgroundResource(mainItem.getImage());
        holder.txtName.setText(mainItem.getName());
        holder.layCount.setVisibility(View.GONE);

        switch (mainItem.getTag()){
            case EnumMainItemsTag.Messages:
                if (G.currentAccount.getCountNotify() != 0) {
                    holder.layCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText(String.valueOf(G.currentAccount.getCountNotify()));
                } else {
                    holder.layCount.setVisibility(View.GONE);
                }

                break;

            case EnumMainItemsTag.support:
                if (G.currentAccount.getCountTicket() != 0) {
                    holder.layCount.setVisibility(View.VISIBLE);
                    holder.txtCount.setText(String.valueOf(G.currentAccount.getCountTicket()));
                } else {
                    holder.layCount.setVisibility(View.GONE);
                }
                break;


        }

        holder.layMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = mainItem.getTag();
                switch (tag) {
                    case EnumMainItemsTag.chargeOnline:
                        if (G.currentLicense != null) {
                            if (G.currentLicense.Chargeonline) {
                                context.startActivity(new Intent(context, ActivityChargeOnline.class));
//                        finish();
                            } else {
                                U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
                            }
                        }
                        break;
                    case EnumMainItemsTag.connections:
                        context.startActivity(new Intent(context, ActivityShowConnections.class));
                        break;
                    case EnumMainItemsTag.factors:
                        context.startActivity(new Intent(context, ActivityShowFactors.class));
                        break;
                    case EnumMainItemsTag.feshfeshe:
                        context.startActivity(new Intent(context, ActivityShowFeshfeshe.class));
                        break;
                    case EnumMainItemsTag.Messages:
                        context.startActivity(new Intent(context, ActivityShowNotify.class));
                        break;
                    case EnumMainItemsTag.news:
                        context.startActivity(new Intent(context, ActivityShowNews.class));
                        break;
                    case EnumMainItemsTag.payments:
                        context.startActivity(new Intent(context, ActivityPayments.class));
                        break;
                    case EnumMainItemsTag.profile:
                        context.startActivity(new Intent(context, ActivityShowUserInfo.class));
                        break;
                    case EnumMainItemsTag.support:
                        context.startActivity(new Intent(context, ActivityShowTickets.class));
                        break;
                    case EnumMainItemsTag.trafficeSplite:
                        context.startActivity(new Intent(context, ActivityShowTrafficeSplite.class));
                        break;
                    case EnumMainItemsTag.scores:
                        context.startActivity(new Intent(context, ActivityShowClubScores.class));
                        break;
                    case EnumMainItemsTag.changePassword:
                        context.startActivity(new Intent(context, ActivityChangePassword.class));
                        break;
                    case EnumMainItemsTag.game:
                        context.startActivity(new Intent(context, ActivityPoints.class));
                        break;

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainItemses.size();
    }

    public class AdapterMainItemsHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        PersianTextViewNormal txtName;
        LinearLayout layMain, layCount;
        PersianTextViewThin txtCount;

        public AdapterMainItemsHolder(View itemView) {
            super(itemView);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            txtName = (PersianTextViewNormal) itemView.findViewById(R.id.txtName);
            layMain = (LinearLayout) itemView.findViewById(R.id.layMain);
            layCount = (LinearLayout) itemView.findViewById(R.id.layCount);
            txtCount = (PersianTextViewThin) itemView.findViewById(R.id.txtCount);
        }
    }
}

